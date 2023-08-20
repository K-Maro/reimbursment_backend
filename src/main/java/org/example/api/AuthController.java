package org.example.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.example.Controller;
import org.example.api.dto.UserCredentialsDto;
import org.example.service.AuthService;
import org.example.utils.HttpMethod;
import org.example.utils.JwtUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Objects;
import java.util.Optional;

public class AuthController implements Controller {

    private final ObjectMapper objectMapper;
    private final AuthService authService;

    public AuthController(AuthService authService,ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.authService = authService;
    }

    @Override
    public void registerController(HttpServer server) {
        server.createContext("/auth/login", new RootHandler(new LoginHandler()));
        server.createContext("/auth/refresh-token", new RootHandler(new RefreshTokenHandler()));
    }

    class LoginHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if (HttpMethod.isPost(exchange.getRequestMethod())) {
                final String token = Optional.of(getRequestBody(exchange))
                        .map(this::parseCredentialsFromJson)
                        .map(authService::getUser)
                        .map(user -> JwtUtils.generateToken(user.getUsername(), user.getUserType()))
                        .orElse(null);
                if (Objects.nonNull(token)) {
                    exchange.sendResponseHeaders(200, token.length());
                    OutputStream os = exchange.getResponseBody();
                    os.write(token.getBytes());
                    os.close();
                } else {
                    exchange.sendResponseHeaders(403, -1); // Metoda nieobsługiwana
                }
            } else {
                exchange.sendResponseHeaders(405, -1); // Metoda nieobsługiwana
            }
        }

        private String getRequestBody(HttpExchange exchange) throws IOException {
            return new String(exchange.getRequestBody().readAllBytes());
        }

        private UserCredentialsDto parseCredentialsFromJson(String json) {
            try {
                return objectMapper.readValue(json, UserCredentialsDto.class);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
    }

    class RefreshTokenHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if (HttpMethod.isPost(exchange.getRequestMethod())) {
                final String refreshedToken = Optional.ofNullable(exchange.getRequestHeaders().getFirst("Authorization"))
                        .map(JwtUtils::refreshToken)
                        .orElse(null);
                if (refreshedToken != null) {
                    final String response = objectMapper.writeValueAsString(refreshedToken);
                    exchange.sendResponseHeaders(200, response.length());
                    OutputStream os = exchange.getResponseBody();
                    os.write(response.getBytes());
                    os.close();
                } else {
                    exchange.sendResponseHeaders(401, -1); // Nieautoryzowany dostęp
                }
            } else {
                exchange.sendResponseHeaders(405, -1); // Metoda nieobsługiwana
            }
        }
    }
}


package org.example.api;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.example.service.UserNotFoundException;
import org.example.utils.HttpMethod;

import java.io.IOException;
import java.io.OutputStream;

public class RootHandler implements HttpHandler {

    private final HttpHandler nextHandler;

    public RootHandler(HttpHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "*");
        exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "*");
        exchange.getResponseHeaders().add("Access-Control-Max-Age", "86400");  // 24 hours
        if (HttpMethod.isOptions(exchange.getRequestMethod())) {
            exchange.sendResponseHeaders(200, -1);
        }
        try {
            nextHandler.handle(exchange);
        } catch (Exception exception) {
            final String errorMessage = "An error occurred: " + exception.getMessage();
            if (exception instanceof UserNotFoundException) {
                exchange.sendResponseHeaders(404, errorMessage.length());
            } else {
                exchange.sendResponseHeaders(500, errorMessage.length());
            }
            OutputStream os = exchange.getResponseBody();
            os.write(errorMessage.getBytes());
            os.close();
        }
    }
}

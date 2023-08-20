package org.example.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.example.Controller;
import org.example.api.dto.*;
import org.example.mapper.ReimbursementMapper;
import org.example.mapper.UserReimbursementMapper;
import org.example.service.PermissionsService;
import org.example.service.ReimbursementService;
import org.example.service.dto.UserReimbursement;
import org.example.utils.HttpMethod;
import org.example.utils.JwtUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URI;
import java.util.Objects;
import java.util.Optional;

public class ReimbursementController implements Controller {

    private final ReimbursementService reimbursementService;
    private final PermissionsService permissionsService;
    private final ObjectMapper objectMapper;
    private final ReimbursementMapper reimbursementMapper;
    private final UserReimbursementMapper userReimbursementMapper;

    public ReimbursementController(ReimbursementService reimbursementService,
                                   PermissionsService permissionsService,
                                   ObjectMapper objectMapper, ReimbursementMapper reimbursementMapper, UserReimbursementMapper userReimbursementMapper) {
        this.reimbursementService = reimbursementService;
        this.permissionsService = permissionsService;
        this.objectMapper = objectMapper;
        this.reimbursementMapper = reimbursementMapper;
        this.userReimbursementMapper = userReimbursementMapper;
    }

    @Override
    public void registerController(HttpServer server) {
        server.createContext("/reimbursement/rates", new RootHandler(new RatesHandler()));
        server.createContext("/reimbursement/receipts", new RootHandler(new ReceiptsHandler()));
        server.createContext("/reimbursement/limits", new RootHandler(new LimitsHandler()));
        server.createContext("/reimbursement/calculate", new RootHandler(new CalculateHandler()));
        server.createContext("/reimbursement/submitted", new RootHandler(new SubmittedReimbursementHandler()));
        server.createContext("/reimbursement/submit", new RootHandler(new SubmitReimbursementHandler()));
    }

    private boolean hasNoAdminPermissions(HttpExchange exchange) throws IOException {
        final boolean hasNoPermissions = getUsernameFromToken(exchange)
                .map(permissionsService::isNotAdmin)
                .orElse(true);
        if (hasNoPermissions) {
            exchange.sendResponseHeaders(403, -1);
            return true;
        }
        return false;
    }

    private boolean hasNoUserPermissions(HttpExchange exchange) throws IOException {
        final boolean hasNoPermissions = getUsernameFromToken(exchange)
                .map(permissionsService::isNotUser)
                .orElse(true);
        if (hasNoPermissions) {
            exchange.sendResponseHeaders(403, -1);
            return true;
        }
        return false;
    }

    private boolean hasNoAdminOrUserPermissions(HttpExchange exchange) throws IOException {
        final boolean hasNoPermissions = getUsernameFromToken(exchange)
                .map(permissionsService::isNotAdminAndNotUser)
                .orElse(true);
        if (hasNoPermissions) {
            exchange.sendResponseHeaders(403, -1);
            return true;
        }
        return false;
    }

    private Optional<String> getUsernameFromToken(HttpExchange exchange) {
        return Optional.ofNullable(exchange.getRequestHeaders())
                .map(headers -> headers.getFirst("Authorization"))
                .map(JwtUtils::getUsernameFromToken);
    }

    private void sendMethodNotSupported(HttpExchange exchange) throws IOException {
        exchange.sendResponseHeaders(405, -1);
    }

    private void addResponse(HttpExchange exchange, String response) throws IOException {
        exchange.sendResponseHeaders(200, response.length());
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    class RatesHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if (HttpMethod.isPost(exchange.getRequestMethod())) {
                if (hasNoAdminPermissions(exchange)) return;
                final ReimbursementRatesDto request = objectMapper.readValue(
                        new String(exchange.getRequestBody().readAllBytes()),
                        ReimbursementRatesDto.class
                );
                final ReimbursementRatesDto reimbursementRates = reimbursementMapper.map(
                        reimbursementService.updateRates(
                                reimbursementMapper.map(request)
                        )
                );
                addResponse(exchange, objectMapper.writeValueAsString(reimbursementRates));
            } else if (HttpMethod.isGet(exchange.getRequestMethod())) {
                if (hasNoAdminOrUserPermissions(exchange)) return;
                final ReimbursementRatesDto reimbursementRates = reimbursementMapper.map(reimbursementService.getRates());
                addResponse(exchange, objectMapper.writeValueAsString(reimbursementRates));
            } else {
                sendMethodNotSupported(exchange);
            }
        }
    }

    class ReceiptsHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if (HttpMethod.isPost(exchange.getRequestMethod())) {
                if (hasNoAdminPermissions(exchange)) return;
                final ReceiptDto request = objectMapper.readValue(
                        new String(exchange.getRequestBody().readAllBytes()),
                        ReceiptDto.class
                );
                final ReimbursementReceiptsDto reimbursementReceipts = reimbursementMapper.mapToReceiptsDto(
                        reimbursementService.addReceipt(
                                reimbursementMapper.mapToDto(request)
                        )
                );
                addResponse(exchange, objectMapper.writeValueAsString(reimbursementReceipts));
            } else if (HttpMethod.isDelete(exchange.getRequestMethod())) {
                if (hasNoAdminPermissions(exchange)) return;
                final String receiptName = Optional.ofNullable(exchange.getRequestURI())
                        .map(URI::getPath)
                        .map(uri -> uri.replace("/reimbursement/receipts/", ""))
                        .filter(s -> !s.isBlank())
                        .orElse(null);
                if (Objects.nonNull(receiptName)) {
                    final ReimbursementReceiptsDto reimbursementReceipts = reimbursementMapper.mapToDto(
                            reimbursementService.removeReceipt(receiptName)
                    );
                    addResponse(exchange, objectMapper.writeValueAsString(reimbursementReceipts));
                } else {
                    exchange.sendResponseHeaders(400, -1);
                }

            } else if (HttpMethod.isPut(exchange.getRequestMethod())) {
                if (hasNoAdminPermissions(exchange)) return;
                final String currentReceiptName = Optional.ofNullable(exchange.getRequestURI())
                        .map(URI::getPath)
                        .map(uri -> uri.replace("/reimbursement/receipts/", ""))
                        .filter(s -> !s.isBlank())
                        .orElse(null);
                if (Objects.nonNull(currentReceiptName)) {
                    final ReceiptDto request = objectMapper.readValue(
                            new String(exchange.getRequestBody().readAllBytes()),
                            ReceiptDto.class
                    );
                    final ReimbursementReceiptsDto reimbursementReceipts = reimbursementMapper.mapToDto(
                            reimbursementService.updateReceipt(currentReceiptName,reimbursementMapper.map(request))
                    );
                    addResponse(exchange, objectMapper.writeValueAsString(reimbursementReceipts));
                } else {
                    exchange.sendResponseHeaders(400, -1);
                }

            } else if (HttpMethod.isGet(exchange.getRequestMethod())) {
                if (hasNoAdminOrUserPermissions(exchange)) return;
                final ReimbursementReceiptsDto reimbursementReceipts = reimbursementMapper.mapToDto(reimbursementService.getReceipts());
                addResponse(exchange, objectMapper.writeValueAsString(reimbursementReceipts));
            } else {
                sendMethodNotSupported(exchange);
            }
        }
    }

    class LimitsHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if (HttpMethod.isPost(exchange.getRequestMethod())) {
                if (hasNoAdminPermissions(exchange)) return;
                final ReimbursementLimitsDto request = objectMapper.readValue(
                        new String(exchange.getRequestBody().readAllBytes()),
                        ReimbursementLimitsDto.class
                );
                final ReimbursementLimitsDto reimbursementLimits = reimbursementMapper.map(
                        reimbursementService.updateLimits(
                                reimbursementMapper.map(request)
                        )
                );
                addResponse(exchange, objectMapper.writeValueAsString(reimbursementLimits));
            } else if (HttpMethod.isGet(exchange.getRequestMethod())) {
                if (hasNoAdminPermissions(exchange)) return;
                final ReimbursementLimitsDto reimbursementLimits = reimbursementMapper.map(reimbursementService.getLimits());
                addResponse(exchange, objectMapper.writeValueAsString(reimbursementLimits));
            } else {
                sendMethodNotSupported(exchange);
            }
        }
    }

    public class CalculateHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if (hasNoAdminOrUserPermissions(exchange)) return;
            if (HttpMethod.isPost(exchange.getRequestMethod())) {
                final UserReimbursementDto request = objectMapper.readValue(
                        new String(exchange.getRequestBody().readAllBytes()),
                        UserReimbursementDto.class
                );

                final BigDecimal calculatedRefund = reimbursementService.calculate(reimbursementMapper.map(request));
                addResponse(exchange, objectMapper.writeValueAsString(calculatedRefund));
            } else {
                sendMethodNotSupported(exchange);
            }
        }
    }

    public class SubmittedReimbursementHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if (hasNoUserPermissions(exchange)) return;
            if (HttpMethod.isGet(exchange.getRequestMethod())) {
                final UserReimbursementsDto userReimbursements = userReimbursementMapper.mapToDto(
                        reimbursementService.getUserReimbursements(getUsernameFromToken(exchange).get()) //Musi być username w tokenie
                );
                addResponse(exchange, objectMapper.writeValueAsString(userReimbursements));
            } else {
                sendMethodNotSupported(exchange);
            }
        }
    }

    public class SubmitReimbursementHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if (hasNoUserPermissions(exchange)) return;
            if (HttpMethod.isPost(exchange.getRequestMethod())) {
                final UserReimbursementDto request = objectMapper.readValue(
                        new String(exchange.getRequestBody().readAllBytes()),
                        UserReimbursementDto.class
                );
                final String username = getUsernameFromToken(exchange).get();//Musi być username w tokenie
                final UserReimbursement userReimbursement = userReimbursementMapper.map(
                        request,
                        reimbursementService.calculate(reimbursementMapper.map(request))
                );
                addResponse(exchange, objectMapper.writeValueAsString(userReimbursementMapper.mapToDto(
                        reimbursementService.addUserReimbursement(username, userReimbursement
                        ))));
            } else {
                sendMethodNotSupported(exchange);
            }
        }
    }
}
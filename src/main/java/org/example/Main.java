package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpServer;
import org.example.api.AuthController;
import org.example.api.ReimbursementController;
import org.example.mapper.ReimbursementMapper;
import org.example.mapper.UserReimbursementMapper;
import org.example.repository.ReimbursementRepositoryImpl;
import org.example.repository.UserMapper;
import org.example.repository.UserReimbursementRepositoryImpl;
import org.example.repository.UserRepositoryImpl;
import org.example.service.*;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        startServer(initControllers());
        System.out.println("SERVER WORKING");
    }

    private static List<Controller> initControllers() {
        final AuthService authService = new AuthService(new UserRepositoryImpl(new UserMapper()));
        final PermissionsService permissionsService = new PermissionsService(authService);
        final ReimbursementMapper reimbursementMapper = new ReimbursementMapper();
        final ReimbursementRepository reimbursementRepository = new ReimbursementRepositoryImpl(reimbursementMapper);
        final UserReimbursementMapper userReimbursementMapper = new UserReimbursementMapper();
        final UserReimbursementRepository userReimbursementRepository = new UserReimbursementRepositoryImpl(userReimbursementMapper);
        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        return Arrays.asList(
                new ReimbursementController(
                        new ReimbursementService(reimbursementRepository, userReimbursementRepository, new ReimbursementCalculator(reimbursementRepository)),
                        permissionsService,
                        objectMapper,
                        reimbursementMapper, userReimbursementMapper),
                new AuthController(authService, objectMapper)
        );
    }

    public static void startServer(List<Controller> controllers) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        controllers.forEach(controller -> controller.registerController(server));
        server.start();
    }
}
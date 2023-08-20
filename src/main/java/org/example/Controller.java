package org.example;

import com.sun.net.httpserver.HttpServer;

public interface Controller {

    void registerController(HttpServer server);
}

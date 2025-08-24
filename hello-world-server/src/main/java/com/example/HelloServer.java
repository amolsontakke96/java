package com.example;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class HelloServer {
    public static void main(String[] args) throws IOException {
        int port = 8080; // you can change this if needed
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/", new HelloHandler());
        server.setExecutor(null); // default executor
        server.start();
        System.out.println("🚀 Server started at http://localhost:" + port);
    }

    static class HelloHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String response = "Hello, World from Java HTTP Server!";
            exchange.sendResponseHeaders(200, response.getBytes().length);
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
}

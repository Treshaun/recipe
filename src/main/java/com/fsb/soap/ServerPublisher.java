package com.fsb.soap;

import javax.xml.ws.Endpoint;

public class ServerPublisher {

    private static Endpoint endpoint;
    private static final String URL = "http://localhost:8080/ws/recipes";

    public static synchronized void start() {
        if (endpoint != null) {
            return;
        }
        endpoint = Endpoint.publish(URL, new RecipeServiceImpl());
        System.out.println("Server started at: " + URL);
    }

    public static synchronized void stop() {
        if (endpoint != null) {
            endpoint.stop();
            endpoint = null;
            System.out.println("Server stopped and port released.");
        }
    }

    public static void main(String[] args) {
        start();

        Runtime.getRuntime().addShutdownHook(new Thread(ServerPublisher::stop));
    }
}
package com.educacionit.orders.services;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class CRMSocketServer {
    private ServerSocket serverSocket;

    public void start(int port) throws Exception {
        serverSocket = new ServerSocket(port);

        while (true) {
            System.out.println("Waiting...");
            new EchoClientHandler(serverSocket.accept()).start();
        }
    }

    public void stop() throws Exception {
        serverSocket.close();
    }

    private static class EchoClientHandler extends Thread {
        private Socket clientSocket;
        private PrintWriter out;
        private BufferedReader in;

        public EchoClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        public void run() {
            System.out.println(String.format("New connection from %s....", clientSocket.getRemoteSocketAddress().toString()));
            try {
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                in = new BufferedReader(
                        new InputStreamReader(clientSocket.getInputStream()));

                System.out.println(String.format("Processing from %s....", clientSocket.getRemoteSocketAddress().toString()));
                String inputLine;
                while((inputLine = in.readLine()) != null) {

                    System.out.println(String.format("Saving %s in Database....", inputLine));
                    out.println("OK");
                    System.out.println(String.format("Sending OK to %s...", clientSocket.getRemoteSocketAddress().toString()));
                    break;
                }
            } catch(Exception e) {
            } finally {
                try {
                    in.close();
                    out.close();
                    clientSocket.close();
                } catch(Exception e) {
                }
            }
        }
    }
}
package com.example.User;

import java.io.*;
import java.net.*;
import java.util.Arrays;

public class ISO8583Server {

    public static void main(String[] args) {
        try {
            int port = 12345; // Server port
            ServerSocket serverSocket = new ServerSocket(port);

            System.out.println("Server started and waiting for connections...");

            while (true) {
                // Accept new client connections
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket.getInetAddress());

                // Handle client communication in a separate thread
                new Thread(new ClientHandler(clientSocket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class ClientHandler implements Runnable {
        private Socket socket;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                InputStream inputStream = socket.getInputStream();
                byte[] receivedData = new byte[4096];
                int bytesRead = inputStream.read(receivedData);

                if (bytesRead > 0) {
                    // Log the raw received data
                    System.out.println("Received ISO 8583 message from client.");
                    System.out.println("Message received (raw bytes): " + Arrays.toString(receivedData));

                    // Process the message (e.g., parse as ISO 8583)
                    // Send a response back to the client
                    OutputStream outputStream = socket.getOutputStream();
                    outputStream.write("Response sent to client.".getBytes());
                    outputStream.flush();
                } else {
                    System.out.println("No data received from client.");
                }

                socket.close(); // Close the client connection after processing
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

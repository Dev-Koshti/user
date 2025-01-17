package com.example.User;

import com.solab.iso8583.IsoMessage;
import com.solab.iso8583.IsoType;
import com.solab.iso8583.MessageFactory;
import com.solab.iso8583.parse.ConfigParser;

import java.io.*;
import java.net.*;
import java.text.ParseException;

public class ISO8583Server {

    public static void main(String[] args) {
        try {
            int port = 12345; // Server port
            ServerSocket serverSocket = new ServerSocket(port);

            System.out.println("Server started and waiting for connections...");

            // Initialize MessageFactory
            MessageFactory<IsoMessage> messageFactory = ConfigParser.createFromClasspathConfig("fields.xml");
            messageFactory.setUseBinaryMessages(true);
//            messageFactory.setConfigPath("/fields.xml"); // Ensure this path is correct

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket.getInetAddress());

                // Pass the initialized messageFactory to ClientHandler
                new Thread(new ClientHandler(clientSocket, messageFactory)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class ClientHandler implements Runnable {
        private Socket socket;
        private MessageFactory<IsoMessage> messageFactory;

        public ClientHandler(Socket socket, MessageFactory<IsoMessage> messageFactory) {
            this.socket = socket;
            this.messageFactory = messageFactory;
        }

        @Override
        public void run() {
            try (InputStream inputStream = socket.getInputStream();
                 OutputStream outputStream = socket.getOutputStream()) {

                byte[] receivedData = new byte[4096];
                int bytesRead = inputStream.read(receivedData);

                if (bytesRead > 0) {
                    System.out.println("Received ISO 8583 message from client.");
                    try {
                        IsoMessage receivedMessage = messageFactory.parseMessage(receivedData, 0);
                        if (receivedMessage != null) {
                            System.out.println("Parsed ISO 8583 Message: " + receivedMessage.debugString());

                            // Create and send response message
                            IsoMessage responseMessage = messageFactory.newMessage(0x4B0); // Response MTI
                            responseMessage.setValue(39, "00", IsoType.NUMERIC, 2); // Response code
                            outputStream.write(responseMessage.writeData());
                            outputStream.flush();
                        } else {
                            System.err.println("Failed to parse ISO 8583 message.");
                        }
                    } catch (ParseException e) {
                        System.err.println("Error parsing ISO 8583 message: " + e.getMessage());
                    }
                } else {
                    System.out.println("No data received from client.");
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    socket.close();
                    System.out.println("Connection closed with client.");
                } catch (IOException e) {
                    System.err.println("Error closing connection: " + e.getMessage());
                }
            }
        }
    }
}

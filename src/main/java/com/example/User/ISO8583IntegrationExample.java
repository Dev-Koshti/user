package com.example.User;

import com.solab.iso8583.IsoMessage;
import com.solab.iso8583.IsoType;
import com.solab.iso8583.MessageFactory;
import com.solab.iso8583.impl.SimpleTraceGenerator;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.text.ParseException;

public class ISO8583IntegrationExample {

    public static void main(String[] args) {
        try {
            // Create a new MessageFactory
            MessageFactory messageFactory = new MessageFactory();
            messageFactory.setUseBinaryMessages(true); // Use binary messages for efficiency
            messageFactory.setAssignDate(true);
            messageFactory.setTraceNumberGenerator(new SimpleTraceGenerator((int) (System.currentTimeMillis() % 100000)));

            // Define the custom field (if needed) - Example: Field 48
//            messageFactory.setCustomField(48, new CustomBinaryFieldCodec());

            // Create a new ISO 8583 message (Sale Transaction)
            IsoMessage saleMessage = messageFactory.newMessage(0x200);
            saleMessage.setValue(3, "000000", IsoType.NUMERIC, 6); // Processing Code (Sale)
            saleMessage.setValue(4, "10000", IsoType.NUMERIC, 12); // Transaction Amount (in cents)
            saleMessage.setValue(7, "0729075811", IsoType.NUMERIC, 10); // Transmission Date and Time (MMDDhhmmss)
            saleMessage.setValue(11, "123456", IsoType.NUMERIC, 6); // System Trace Audit Number
            saleMessage.setValue(41, "12345678", IsoType.ALPHA, 8); // Card Acceptor Terminal ID
            saleMessage.setValue(42, "EXTIOTECH", IsoType.ALPHA, 12); // Card Acceptor ID

            // Calculate and set the Message Authentication Code (MAC) if needed

            // Convert the message to byte array for transmission over the network
            byte[] messageBytes = saleMessage.writeData();

            // Simulate sending the ISO 8583 message to a server
            sendToServer(messageBytes);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void sendToServer(byte[] messageBytes) throws IOException, ParseException {
        String serverAddress = "127.0.0.1"; // Replace with the actual server IP address
        int serverPort = 12345; // Replace with the actual server port

        try (Socket socket = new Socket(serverAddress, serverPort)) {
            OutputStream outputStream = socket.getOutputStream();

            // Send the ISO 8583 message to the server
            outputStream.write(messageBytes);
            outputStream.flush();

            System.out.println("ISO 8583 message sent to the server.");            // Simulate receiving the response from the server
            receiveFromServer(socket);
        }
    }

    private static void receiveFromServer(Socket socket) throws IOException, ParseException {
        InputStream inputStream = socket.getInputStream();

        // Assuming the server sends back the response in the same format (ISO 8583)
        byte[] responseBytes = new byte[4096]; // Adjust buffer size as per your expected response size
        int bytesRead = inputStream.read(responseBytes);

        if (bytesRead > 0) {
            // Create a new MessageFactory for parsing the received response
            MessageFactory responseFactory = new MessageFactory();
            responseFactory.setUseBinaryMessages(true);

            // Define the custom field codec for parsing (if needed) - Example: Field 48
//            responseFactory.setCustomField(48, new CustomBinaryFieldCodec());

            // Parse the received response ISO 8583 message
            IsoMessage receivedResponse = responseFactory.parseMessage(responseBytes, 0);
            if (receivedResponse == null) {
                System.err.println("Failed to parse ISO 8583 message.");
                return; // Exit or handle the failure gracefully
            }
            // Process the received response message
            System.out.println("\nReceived ISO 8583 Response Message:");
//            System.out.println(receivedResponse.debugString());
            String processingCode = getFieldValue(receivedResponse, 3);
            String transactionAmount = getFieldValue(receivedResponse, 4);
            String transmissionDate = getFieldValue(receivedResponse, 7);

// Output the extracted values
            System.out.println("Processing Code: " + processingCode);
            System.out.println("Transaction Amount: " + transactionAmount);
            System.out.println("Transmission Date: " + transmissionDate);
//            // Access individual fields from the received response message
//            String processingCode = receivedResponse.getField(3).toString();
//            String transactionAmount = receivedResponse.getField(4).toString();
//            String transmissionDate = receivedResponse.getField(7).toString();

        } else {
            System.out.println("No response received from the server.");
        }
    }
    private static String getFieldValue(IsoMessage message, int fieldNumber) {
        Object field = message.getField(fieldNumber);
        return (field != null) ? field.toString() : "Field not present";
    }
}
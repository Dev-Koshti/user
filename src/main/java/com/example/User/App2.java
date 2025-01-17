package com.example.User;

import com.solab.iso8583.IsoMessage;
import com.solab.iso8583.IsoType;
import com.solab.iso8583.IsoValue;
import com.solab.iso8583.MessageFactory;
import com.solab.iso8583.impl.SimpleTraceGenerator;
import com.solab.iso8583.parse.ConfigParser;
import org.apache.poi.ss.formula.functions.T;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.text.ParseException;

public class App2 {

    private static final String HEADER = "ISO1987";

    public static void main(String[] args) throws Exception {
        // Create a new MessageFactory with binary messages
        MessageFactory<IsoMessage> messageFactory = ConfigParser.createFromClasspathConfig("fields.xml");
//        MessageFactory<IsoMessage> messageFactory = ConfigParser.createDefault();

//        messageFactory.setUseBinaryMessages(true); // Use binary messages for efficiency
//        messageFactory.setAssignDate(true); // Assign the current date automatically
//        messageFactory.setTraceNumberGenerator(new SimpleTraceGenerator((int) (System.currentTimeMillis() % 100000)));

        // Define custom field codec for custom fields (if needed)
        // messageFactory.setCustomField(48, new CustomBinaryFieldCodec()); // Uncomment if custom fields are required

        // Create a new ISO 8583 message (MTI = 0x200 for Financial)
        IsoMessage isoMessage = messageFactory.newMessage(0x4B0);

        // Set header
        isoMessage.setIsoHeader(HEADER);

        // Set data fields (same as before)
        isoMessage.setValue(2, "5642570404782927", IsoType.LLVAR, 19);
        isoMessage.setValue(3, "011000", IsoType.NUMERIC, 6);
        isoMessage.setValue(4, "780.00", IsoType.AMOUNT, 12);
        isoMessage.setValue(7, "1220145711", IsoType.DATE10, 12);
        isoMessage.setValue(11, "101183", IsoType.NUMERIC, 6);
        isoMessage.setValue(12, "145711", IsoType.TIME, 6);
        isoMessage.setValue(13, "1220", IsoType.DATE4, 4);
        isoMessage.setValue(14, "2408", IsoType.DATE_EXP, 4);
        isoMessage.setValue(15, "1220", IsoType.DATE4, 4);
        isoMessage.setValue(18, "6011", IsoType.NUMERIC, 4);
        isoMessage.setValue(22, "051", IsoType.NUMERIC, 3);
        isoMessage.setValue(25, "00", IsoType.NUMERIC, 2);
        isoMessage.setValue(26, "04", IsoType.NUMERIC, 2);
        isoMessage.setValue(28, "C00000000", IsoType.ALPHA, 9);
        isoMessage.setValue(30, "C00000000", IsoType.ALPHA, 9);
        isoMessage.setValue(32, "56445700", IsoType.LLVAR, 11);
        isoMessage.setValue(37, "567134101183", IsoType.ALPHA, 12);
        isoMessage.setValue(41, "N1742", IsoType.ALPHA, 8);
        isoMessage.setValue(42, "ATM004", IsoType.LLVAR, 15);
        isoMessage.setValue(43, "45 SR LEDERSHIP DUABANAT NUEVA ECIJAQ PH", IsoType.ALPHA, 40);
        isoMessage.setValue(49, "608", IsoType.NUMERIC, 3);
        isoMessage.setValue(102, "970630181070041", IsoType.LLVAR, 28);
        isoMessage.setValue(120, "BRN015301213230443463", IsoType.LLLVAR, 999);

        // Convert the message to a byte array for transmission
        byte[] messageBytes = isoMessage.writeData();

        // Send the ISO 8583 message to the server (simulation)
        sendToServer(messageBytes);

        // Read the response from the server
        readIsoMessage(messageBytes);
    }

    private static void sendToServer(byte[] messageBytes) throws IOException, ParseException {
        String serverAddress = "127.0.0.1"; // Replace with actual server IP
        int serverPort = 12345; // Replace with actual server port

        try (Socket socket = new Socket(serverAddress, serverPort)) {
            OutputStream outputStream = socket.getOutputStream();

            // Send the ISO 8583 message to the server
            outputStream.write(messageBytes);
            outputStream.flush();

            System.out.println("ISO 8583 message sent to the server.");
            // Simulate receiving the response from the server
            receiveFromServer(socket);
        }
    }

    private static void receiveFromServer(Socket socket) throws IOException, ParseException {
        InputStream inputStream = socket.getInputStream();

        byte[] responseBytes = new byte[4096]; // Buffer size for response
        int bytesRead = inputStream.read(responseBytes);

        if (bytesRead > 0) {
            // Parse the response ISO 8583 message
            MessageFactory<IsoMessage> messageFactory = new MessageFactory<>();
            messageFactory.setUseBinaryMessages(true);
            IsoMessage receivedIsoMessage = messageFactory.parseMessage(responseBytes, HEADER.length());

            if (receivedIsoMessage != null) {
                System.out.println("\nReceived ISO 8583 Response Message:");
                printIsoField(receivedIsoMessage, 3);  // Processing code
                printIsoField(receivedIsoMessage, 4);  // Transaction amount
                printIsoField(receivedIsoMessage, 7);  // Transmission date
            }
        } else {
            System.out.println("No response received from the server.");
        }
    }

    private static void printIsoField(IsoMessage isoMessage, int fieldNumber) {
        IsoValue<Object> isoValue = isoMessage.getField(fieldNumber);
        System.out.println(fieldNumber + " : " + isoValue.getType() + " : " + isoValue.getLength() + " : " + isoValue.getValue());
    }

    private static void readIsoMessage(byte[] messageStream) throws Exception {
        MessageFactory<IsoMessage> messageFactory = new MessageFactory<>();
        messageFactory.setUseBinaryMessages(true);
        IsoMessage receivedIsoMessage = messageFactory.parseMessage(messageStream, HEADER.length());

        System.out.println("\nReceived ISO8583 message:\n" + new String(receivedIsoMessage.writeData()));
        System.out.println("\nHeader: " + receivedIsoMessage.getIsoHeader());
        printIsoField(receivedIsoMessage, 2);
        printIsoField(receivedIsoMessage, 4);
        printIsoField(receivedIsoMessage, 14);
        printIsoField(receivedIsoMessage, 37);
        printIsoField(receivedIsoMessage, 120);
    }
}

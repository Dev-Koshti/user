package com.example.User;

// Define a custom codec for Field 48 to handle binary data
public class CustomBinaryFieldCodec implements FieldCodec {

    @Override
    public Object decode(byte[] data) {
        // Logic to decode the binary data into the desired format
        return new String(data); // Example: Convert binary data to a String
    }

    @Override
    public byte[] encode(Object object) {
        // Logic to encode the object back to binary data
        return object.toString().getBytes(); // Example: Convert object to byte array
    }
}

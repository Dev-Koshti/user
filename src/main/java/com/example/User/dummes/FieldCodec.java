package com.example.User.dummes;

// Define an interface for field codecs
public interface FieldCodec {
    Object decode(byte[] data);   // Method to decode data
    byte[] encode(Object object); // Method to encode data
}

package graph;

import java.util.Date; //  Message m = new Message("Hello");


public class Message {
    public final byte[] data; // Message as bytes
    public final String asText; // Message as Text
    public final double asDouble; // Message as double
    public final Date date; // Date of Message


    public Message(byte[] data) { // main cons

        this.data = data; // Saving Message
        this.asText = new String(data); // Convert byte[] to String 
        this.asDouble = toDouble(asText); // Trying to convert String to Double. "123" will return 123.0 . "hello" will return Double.NaN
        this.date = new Date(); // Creating Date

    }

    public Message(String text){
        
        this(text.getBytes()); // converting 'text' to Bytes and sending it to cons number 1. this(...) calls to another cons

    }

    public Message(double db){

        this(Double.toString(db).getBytes()); // same as cons nubmer 2

    }

    private static double toDouble(String text){ // 

        try {
            return Double.parseDouble(text);
        } catch (NumberFormatException e) {
            return Double.NaN;
        }

    }

    
}

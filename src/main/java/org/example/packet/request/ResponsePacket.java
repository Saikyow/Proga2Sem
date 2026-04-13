package org.example.packet.request;

import java.io.Serializable;

public class ResponsePacket implements Serializable {
    private final String message;
    private final Object data;

    public ResponsePacket(String message, Object data) {
        this.message = message;
        this.data = data;
    }

    public String getMessage() { return message; }
    public Object getData() { return data; }
}
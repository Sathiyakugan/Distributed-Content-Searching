package com.semicolon.ds.comms;

public class ChannelMessage {
    private final String address;
    private final int port;
    private final String message;

    public ChannelMessage(String address, int port, String message) {
        this.address = address;
        this.port = port;
        this.message = message;
    }

    public int getPort() {
        return port;
    }

    public String getMessage() {
        return message;
    }

    public String getAddress() {
        return address;
    }
}

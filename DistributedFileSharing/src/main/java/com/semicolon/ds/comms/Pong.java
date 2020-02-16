package com.semicolon.ds.comms;

public class Pong implements P2PMessage{
    private final String destAddress;
    private final int detPort;

    public Pong(String destAddress, int detPort) {
        this.destAddress = destAddress;
        this.detPort = detPort;
    }

    public String getDestAddress() {
        return destAddress;
    }

    public int getDetPort() {
        return detPort;
    }
}

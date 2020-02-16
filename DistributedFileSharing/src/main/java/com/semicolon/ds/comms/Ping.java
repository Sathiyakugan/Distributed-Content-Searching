package com.semicolon.ds.comms;

public class Ping implements P2PMessage {
    private final String sourceAddress;
    private final int sourcePort;


    public Ping(String sourceAddress, int sourcePort) {
        this.sourceAddress = sourceAddress;
        this.sourcePort = sourcePort;

    }

    public String getSourceAddress() {
        return sourceAddress;
    }

    public int getSourcePort() {
        return sourcePort;
    }

}

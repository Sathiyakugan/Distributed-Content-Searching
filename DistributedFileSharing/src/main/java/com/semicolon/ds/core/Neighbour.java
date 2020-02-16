package com.semicolon.ds.core;

public class Neighbour {
    private final String address;
    private final int port;
    private int pingPongs;
    private int clientPort;

    public Neighbour(String address, int port, int clientPort) {
        this.address = address;
        this.port = port;
        this.pingPongs = 0;
        this.clientPort = clientPort;
    }

    public boolean equals (Neighbour newN) {
        return (newN.getPort() == this.port | newN.getClientPort() == this.port)
                & this.address.equals(newN.getAddress());
    }

    public boolean equals(String address, int port) {
        return (this.port == port | this.clientPort == port)
                & this.address.equals(address);

    }

    public String getAddress() {
        return address;
    }

    public int getPort() {
        return port;
    }

    public int getPingPongs() {
        return pingPongs;
    }

    public void Ping() {
        this.pingPongs++;
    }

    public String toString() {
        return this.address + ":" + this.port;
    }

    public int getClientPort() {
        return clientPort;
    }
}

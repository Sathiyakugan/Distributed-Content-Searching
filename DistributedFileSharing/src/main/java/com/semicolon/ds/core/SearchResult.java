package com.semicolon.ds.core;

import com.semicolon.ds.Constants;

public class SearchResult {

    private String fileName;
    private String address;
    private int port;
    private int tcpPort;
    private int hops;
    private long timeElapsed;

    public SearchResult(String fileName, String address, int port, int hops, long timeElapsed) {
        this.fileName = fileName;
        this.address = address;
        this.port = port;
        this.tcpPort = port + Constants.FTP_PORT_OFFSET;
        this.hops = hops;
        this.timeElapsed = timeElapsed;
    }

    public String getFileName() {
        return fileName;
    }

    public String getAddress() {
        return address;
    }

    public int getPort() {
        return port;
    }

    public int getTcpPort() {
        return tcpPort;
    }

    public int getHops() {
        return hops;
    }

    public long getTimeElapsed() {
        return timeElapsed;
    }
}

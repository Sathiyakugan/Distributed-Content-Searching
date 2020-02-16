package com.semicolon.ds.comms.ftp;

import javafx.scene.control.TextArea;
import java.net.Socket;

public class FTPClient {

    public FTPClient(String IpAddress, int port, String fileName) throws Exception {

        long start = System.currentTimeMillis();
        Socket serverSock = new Socket(IpAddress, port);

        System.out.println("Connecting...");
        Thread t = new Thread(new DataReceivingOperation(serverSock, fileName));
        t.start();
        long stop = System.currentTimeMillis();
    }

    public FTPClient(String IpAddress, int port, String fileName, TextArea textArea) throws Exception {

        long start = System.currentTimeMillis();
        Socket serverSock = new Socket(IpAddress, port);

        textArea.setText("Connecting...");
        Thread t = new Thread(new DataReceivingOperation(serverSock, fileName, textArea));
        t.start();
        long stop = System.currentTimeMillis();
    }
}

package com.semicolon.ds.comms.ftp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;


public class FTPServer implements Runnable{

    private ServerSocket serverSocket;
    private Socket clientsocket;
    private final Logger LOG = Logger.getLogger(FTPServer.class.getName());
    private String userName;

    public FTPServer(int port, String userName) throws Exception {
        // create socket
        serverSocket = new ServerSocket(port);
        this.userName = userName;
    }

    public int getPort(){
        return serverSocket.getLocalPort();
    }

    @Override
    public void run() {
        while (true) {

            try {
                clientsocket = serverSocket.accept();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Thread t = new Thread(new DataSendingOperation(clientsocket, userName));
            t.start();
        }
    }
}

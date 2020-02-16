package com.semicolon.ds.comms.ftp;

import javafx.scene.control.TextArea;
import java.io.*;
import java.net.Socket;

public class DataReceivingOperation implements Runnable {

    private Socket serverSock;
    private BufferedReader in = null;
    private String fileName;

    private TextArea textArea;

    public DataReceivingOperation(Socket server, String fileName) {
        this.serverSock = server;
        this.fileName = fileName;
    }

    public DataReceivingOperation(Socket server, String fileName, TextArea textArea){
        this.textArea = textArea;
        this.serverSock = server;
        this.fileName = fileName;
    }

    @Override
    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(
                    serverSock.getInputStream()));
            DataOutputStream dOut = new DataOutputStream(serverSock.getOutputStream());
            dOut.writeUTF(fileName);
            dOut.flush();
            receiveFile();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void receiveFile() {
        try {
            int bytesRead;

            DataInputStream serverData = new DataInputStream(serverSock.getInputStream());

            String fileName = serverData.readUTF();
            OutputStream output = new FileOutputStream(fileName);
            long size = serverData.readLong();
            byte[] buffer = new byte[1024];
            while (size > 0 && (bytesRead = serverData.read(buffer, 0, (int) Math.min(buffer.length, size))) != -1) {
                output.write(buffer, 0, bytesRead);
                size -= bytesRead;
            }

            output.close();
            serverData.close();

            if (textArea == null){
                System.out.println("File " + fileName + " successfully downloaded.");
            } else {
                this.textArea.setText("File " + fileName + " successfully downloaded.");
            }


        } catch (IOException ex) {
            System.err.println("server error. Connection closed.");
            ex.printStackTrace();
        }
    }
}

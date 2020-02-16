package com.semicolon.ds.gui;

import com.semicolon.ds.core.GNode;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;

public class Controller extends Thread implements Initializable {


    public GNode node;

    public Button buttonSearch;
    public Button leaveButton;
    public Button buttonDownload;

    public Label labelAddress;
    public Label labelPort;


    public TextArea areaAvailable;
    public TextArea areaSearch;
    public TextArea areaTable;
    public TextArea areaDownload;

    public TextField textSearch;
    public TextField textDownload;

    private int resultsCount = 0;


    public void searchAction() {
        String data = textSearch.getText();
        if (data.equals(null) || data.isEmpty()) {
            areaSearch.setText("select a number to download");
        } else {
            List<String> results = node.doUISearch(data);
            this.resultsCount = results.size();

            if (resultsCount == 0){
                areaSearch.setText("Sorry, no files found");

            } else {
                String output = "";
                int optionNo = 1;
                for(String s : results){
                    output += optionNo + "\t" + s + "\n";
                    optionNo++;
                }

                areaSearch.setText(output);
            }
            textSearch.setText("");
        }


    }

    public void downloadAction() {
        // write donwload method
        String data = textDownload.getText();

        if (data.equals(null) || data.isEmpty()) {
            setDownloadLog("type something to search");
        } else {
            if (isStringInt(data)){
                int intData = Integer.parseInt(data);
                if (intData <= resultsCount){
                    this.setDownloadLog("File starting to download");
                    node.getFile(intData, this.areaDownload);
                    textDownload.setText("");
                    this.resultsCount = 0;
                } else {
                    setDownloadLog("Invalid option");
                }

            } else {
                setDownloadLog("Enter a valid integer");
            }

        }


    }

    public void setData(String ipaddress, String port) {
        labelAddress.setText(ipaddress);
        areaAvailable.setText(getAvailableFiles());
        labelPort.setText(port);

    }

    public void leaveAction() {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you leaving ?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            Platform.exit();
            node.unRegister();
            System.exit(0);
        }


    }

    public void setRoutingTable(String table) {
        areaTable.setText(table);

    }

    private String getRoutingTable() {
        return node.getRoutingTable();
    }

    private String getAvailableFiles() {
        return node.getFileNames();
    }


    //use this method to get the file no
    private String getDowloadFileNO() {
        return textDownload.getText();
    }

    private String getDownloadLog() {
        return "this is the log";
    }

    private void setDownloadLog(String log) {
        this.areaDownload.setText(log);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String uniqueID = UUID.randomUUID().toString();
        try {
            node = new GNode("node" + uniqueID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        node.init();

        this.setData(node.getIpAddress(), String.valueOf(node.getPort()));
        this.setRoutingTable(this.getRoutingTable());

        Thread thread = new Thread("New Thread") {
            public void run() {
                int count = 0;
                while (true)
                    try {
                        this.sleep(1000);
                        setRoutingTable(getRoutingTable());
                        count++;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
            }

        };
        thread.start();

    }

    private boolean isStringInt(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }


}

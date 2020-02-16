package com.semicolon.ds.core;

import com.semicolon.ds.Constants;
import com.semicolon.ds.comms.ftp.DataSendingOperation;

import java.io.*;
import java.util.*;
import java.util.logging.Logger;

public class FileManager {

    private static FileManager fileManager;

    private Map<String, String> files;

    private String userName;

    private String fileSeparator = System.getProperty("file.separator");
    private String rootFolder;


    private final Logger LOG = Logger.getLogger(FileManager.class.getName());

    private FileManager(String userName) {
        files = new HashMap<>();

        this.userName = userName;
        this.rootFolder =   "." + fileSeparator + this.userName;

        ArrayList<String> fullList = readFileNamesFromResources();

        Random r = new Random();

        for (int i = 0; i < 5; i++){
            files.put(fullList.get(r.nextInt(fullList.size())), "");
        }

        printFileNames();
    }

    public static synchronized FileManager getInstance(String userName) {
        if (fileManager == null) {
            fileManager = new FileManager(userName);

        }
        return fileManager;
    }

    public boolean addFile(String fileName, String filePath) {
        this.files.put(fileName, filePath);
        return true;
    }

    public Set<String> searchForFile(String query) {
        String[] querySplit = query.split(" ");

        Set<String> result = new HashSet<String>();

        for (String q: querySplit){
            for (String key: this.files.keySet()){
                String[] fileNameSplit = key.split(" ");
                for (String f : fileNameSplit){
                    if (f.toLowerCase().equals(q.toLowerCase())){
                        result.add(key);
                    }
                }
            }
        }

        return result;
    }

    private ArrayList<String> readFileNamesFromResources(){

        ArrayList<String> fileNames = new ArrayList<>();

        //Get file from resources folder
        ClassLoader classLoader = getClass().getClassLoader();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader
                (classLoader.getResourceAsStream(Constants.FILE_NAMES)));

        try {

            for (String line; (line = bufferedReader.readLine()) != null;) {
                fileNames.add(line);
            }

            bufferedReader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return fileNames;
    }

    private void printFileNames() {
        System.out.println("Total files: " + files.size());
        System.out.println("++++++++++++++++++++++++++");
        for (String s: files.keySet()) {
            System.out.println(s);
            createFile(s);
        }
    }

    public String getFileNames() {
        String fileString = "Total files: " + files.size() + "\n";
        fileString += "++++++++++++++++++++++++++\n";
        for (String s: files.keySet()) {
            fileString += s + "\n";
        }
        return fileString;
    }

    public void createFile(String fileName) {
        try {
            String absoluteFilePath = this.rootFolder + fileSeparator + fileName;
            File file = new File(absoluteFilePath);
            file.getParentFile().mkdir();
            if (file.createNewFile()) {
                LOG.fine(absoluteFilePath + " File Created");
            } else LOG.fine("File " + absoluteFilePath + " already exists");
            RandomAccessFile f = new RandomAccessFile(file, "rw");
            f.setLength(1024 * 1024 * 8);
        } catch (IOException e) {
            LOG.severe("File creating failed");
            e.printStackTrace();
        }
    }

    public File getFile(String fileName) {
        File file = new File(rootFolder + fileSeparator + fileName);
        return file;
    }
}

package com.semicolon.ds;

public class Constants {

    public static final String BS_PROPERTIES = "Bootstrap.properties";
//    public static final String LOCALHOST = "127.0.0.1";

    public static final int FTP_PORT_OFFSET = 100;

    public static final String MSG_FORMAT = "%04d %s";

    public static final String REG_FORMAT = "REG %s %s %s";
    public static final String UNREG_FORMAT = "UNREG %s %s %s";

    public static final String REGOK = "REGOK";
    public static final String UNROK = "UNROK";

    public static final String PING_FORMAT = "PING %s %s";
    public static final String PONG_FORMAT = "PONG %s %s";

    public static final String LEAVE_FORMAT = "LEAVE %s %s";

    public static final String BPING_FORMAT = "BPING %s %s %s";
    public static final String BPONG_FORMAT = "BPONG %s %s";
    public static final int BPING_HOP_LIMIT = 3;

    public static final String QUERY_FORMAT = "SER %s %s %s %s";
    public static final String QUERY_HIT_FORMAT = "SEROK %s %s %s %s %s";

    public static final String ENCODE_CLASS = "UTF-8";

    public static final String ADDRESS_KEY_FORMAT = "%s:%s";

    public static final int TIMEOUT_REG = 10000;

    public static final int MIN_NEIGHBOURS = 2;
    public static final int MAX_NEIGHBOURS = 6;

    public static final int PING_TIMEOUT = 3000;
    public static final int PING_INTERVAL = 8000;
    public static final int PING_RETRY = 3;

    public static final int SEARCH_TIMEOUT = 3000;
    public static final int FILE_DOWNLOAD_TIMEOUT = 2000;

    public static final String R_PING_MESSAGE_ID = "rPingMessage";

    public static final int HOP_COUNT = 5;

    public static final String FILE_NAMES = "FileNames.txt";

    public static final String PING_MESSAGE_ID_FORMAT = "PINGID:%s:%s";

}

package com.semicolon.ds.handlers;

import com.semicolon.ds.Constants;
import com.semicolon.ds.comms.ChannelMessage;
import com.semicolon.ds.core.FileManager;
import com.semicolon.ds.core.Neighbour;
import com.semicolon.ds.core.RoutingTable;
import com.semicolon.ds.core.TimeoutManager;
import com.semicolon.ds.utils.StringEncoderDecoder;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Logger;

public class SearchQueryHandler implements AbstractResponseHandler, AbstractRequestHandler {

    private final Logger LOG = Logger.getLogger(SearchQueryHandler.class.getName());

    private RoutingTable routingTable;

    private BlockingQueue<ChannelMessage> channelOut;

    private TimeoutManager timeoutManager;

    private static SearchQueryHandler searchQueryHandler;

    private FileManager fileManager;

    private SearchQueryHandler(){
        fileManager = FileManager.getInstance("");
    }

    public synchronized static SearchQueryHandler getInstance(){
        if (searchQueryHandler == null){
            searchQueryHandler = new SearchQueryHandler();
        }
        return searchQueryHandler;
    }

    public void doSearch(String keyword) {

        String payload = String.format(Constants.QUERY_FORMAT,
                this.routingTable.getAddress(),
                this.routingTable.getPort(),
                StringEncoderDecoder.encode(keyword),
                Constants.HOP_COUNT);

        String rawMessage = String.format(Constants.MSG_FORMAT, payload.length() + 5, payload);

        ChannelMessage initialMessage = new ChannelMessage(
                this.routingTable.getAddress(),
                this.routingTable.getPort(),
                rawMessage);

        this.handleResponse(initialMessage);
    }

    @Override
    public void sendRequest(ChannelMessage message) {
        try {
            channelOut.put(message);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init(RoutingTable routingTable, BlockingQueue<ChannelMessage> channelOut,
                     TimeoutManager timeoutManager) {
        this.routingTable = routingTable;
        this.channelOut = channelOut;
        this.timeoutManager = timeoutManager;
    }

    @Override
    public void handleResponse(ChannelMessage message) {
        LOG.fine("Received SER : " + message.getMessage()
                + " from: " + message.getAddress()
                + " port: " + message.getPort());

        StringTokenizer stringToken = new StringTokenizer(message.getMessage(), " ");

        String length = stringToken.nextToken();
        String keyword = stringToken.nextToken();
        String address = stringToken.nextToken().trim();
        int port = Integer.parseInt(stringToken.nextToken().trim());

        String fileName = StringEncoderDecoder.decode(stringToken.nextToken().trim());
        int hops = Integer.parseInt(stringToken.nextToken().trim());

        //search for the file in the current node
        Set<String> resultSet = fileManager.searchForFile(fileName);
        int fileNamesCount = resultSet.size();

        if (fileNamesCount != 0) {

            StringBuilder fileNamesString = new StringBuilder("");

            Iterator<String> itr = resultSet.iterator();

            while(itr.hasNext()){
                fileNamesString.append(StringEncoderDecoder.encode(itr.next()) + " ");
            }

            String payload = String.format(Constants.QUERY_HIT_FORMAT,
                    fileNamesCount,
                    routingTable.getAddress(),
                    routingTable.getPort(),
                    Constants.HOP_COUNT- hops,
                    fileNamesString.toString());

            String rawMessage = String.format(Constants.MSG_FORMAT, payload.length() + 5, payload);

            ChannelMessage queryHitMessage = new ChannelMessage(address,
                    port,
                    rawMessage);

            this.sendRequest(queryHitMessage);
        }

        //if the hop count is greater than zero send the message to all neighbours again

        if (hops > 0){
            ArrayList<Neighbour> neighbours = this.routingTable.getNeighbours();

            for(Neighbour neighbour: neighbours){

                //skip sending search query to the same node again
                if (neighbour.getAddress().equals(message.getAddress())
                        && neighbour.getClientPort() == message.getPort()) {
                    continue;
                }

                String payload = String.format(Constants.QUERY_FORMAT,
                        address,
                        port,
                        StringEncoderDecoder.encode(fileName),
                        hops - 1);

                String rawMessage = String.format(Constants.MSG_FORMAT, payload.length() + 5, payload);

                ChannelMessage queryMessage = new ChannelMessage(neighbour.getAddress(),
                        neighbour.getPort(),
                        rawMessage);

                this.sendRequest(queryMessage);
            }
        }
    }
}

package com.semicolon.ds.handlers;

import com.semicolon.ds.comms.ChannelMessage;

public interface AbstractResponseHandler extends AbstractMessageHandler {

    void handleResponse(ChannelMessage message);
}

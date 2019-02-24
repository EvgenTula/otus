package ru.otus.hw16messageserver.messageserver.messagesystem.message.frontend;

import ru.otus.hw16messageserver.messageserver.messagesystem.FrontendService;
import ru.otus.hw16messageserver.messageserver.messagesystem.Address;

public class MessageToClient extends MessageToFrontend {

    //private final static String ALL_CLIENT = "ALL_CLIENT";

    private String uuid;
    public MessageToClient(Address from, Address to, String data, String uuid) {
        super(from, to, data);
        this.uuid = uuid;
    }
/*
    public MessageToClient(Address from, Address to, String data) {
        super(from, to, data);
        this.uuid = ALL_CLIENT;
    }
*/
    public void exec(FrontendService frontendService) {
        frontendService.sendDataClient(uuid,getData());
    }
}

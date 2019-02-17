package ru.otus.hw16messageserver.messageserver.messagesystem.message.client;

import ru.otus.hw16messageserver.messageserver.messagesystem.Address;

public class MessageGetDataClient {} /*extends MessageToFrontend {
    private final static String ALL_CLIENT = "ALL_CLIENT";
    private String data;
    private String uuid;
    public MessageGetDataClient(Address from, Address to, String data) {

        super(from, to);
        this.data = data;
        this.uuid = ALL_CLIENT;
    }

    public MessageGetDataClient(Address from, Address to, String data, String uuid) {
        super(from, to);
        this.data = data;
        this.uuid = uuid;
    }

    @Override
    public void exec(FrontendService frontendService) {
        frontendService.sendDataClient(uuid, getData());
    }

    public String getData() {
        return data;
    }
}*/

package ru.otus.hw16messageserver.messageserver.messagesystem.message.frontend;

import ru.otus.hw16messageserver.messageserver.messagesystem.FrontendService;
import ru.otus.hw16messageserver.messageserver.messagesystem.Address;

public class MessageToClient extends MessageToFrontend {

    private String uuid;
    public MessageToClient(Address from, Address to, String data, String uuid) {
        super(from, to, data);
        this.uuid = uuid;
    }

    public void exec(FrontendService frontendService) {
        frontendService.sendDataClient(uuid,getData());
    }
}

package ru.otus.hw16messageserver.server.messageserver.messagesystem.message.frontend;

import ru.otus.hw16messageserver.server.messageserver.messagesystem.Address;
import ru.otus.hw16messageserver.server.messageserver.messagesystem.FrontendService;
import ru.otus.hw16messageserver.server.messageserver.messagesystem.Member;

public class MessageToClient extends MessageToFrontend {
    private String uuid;
    public MessageToClient(Address from, Address to, String data, String uuid) {
        super(from, to, data);
        this.uuid = uuid;
    }
    @Override
    public void exec(Member sender) {
        if (sender instanceof FrontendService) {
            exec((FrontendService)sender);
        }
    }
    public void exec(FrontendService frontendService) {
        frontendService.sendDataClient(uuid,getData());
    }
}

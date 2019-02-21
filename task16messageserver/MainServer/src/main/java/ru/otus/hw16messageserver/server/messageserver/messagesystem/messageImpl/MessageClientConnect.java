package ru.otus.hw16messageserver.server.messageserver.messagesystem.messageImpl;

import ru.otus.hw16messageserver.server.messageserver.messagesystem.Address;
import ru.otus.hw16messageserver.server.messageserver.messagesystem.FrontendService;
import ru.otus.hw16messageserver.server.messageserver.messagesystem.Member;
import ru.otus.hw16messageserver.server.messageserver.messagesystem.message.Message;

public class MessageClientConnect extends Message {

    public MessageClientConnect(Address from, Address to, String data) {
        super(from, to, data);
    }


    @Override
    public void exec(Member sender) {
        if (sender instanceof FrontendService)
            exec((FrontendService)sender);
    }

    public void exec(FrontendService frontend) {
        frontend.addClient(this.getData());
    }
}

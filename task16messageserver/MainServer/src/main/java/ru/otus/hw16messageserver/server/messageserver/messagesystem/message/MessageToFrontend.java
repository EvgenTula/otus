package ru.otus.hw16messageserver.server.messageserver.messagesystem.message;

import ru.otus.hw16messageserver.server.messageserver.messagesystem.Address;
import ru.otus.hw16messageserver.server.messageserver.messagesystem.Member;

public class MessageToFrontend extends Message {

    public MessageToFrontend(Address from, Address to, String data) {
        super(from, to, data);
    }

    @Override
    public void exec(Member sender) {

    }

    /*
    @Override
    public void exec(Member sender) {
        if (sender instanceof FrontendService) {
            exec((FrontendService)sender);
        }
    }

    public abstract void exec(FrontendService frontendService);
    */
}

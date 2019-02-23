package ru.otus.hw16messageserver.messageserver.messagesystem.message.frontend;

import ru.otus.hw16messageserver.messageserver.messagesystem.FrontendService;
import ru.otus.hw16messageserver.messageserver.messagesystem.Address;
import ru.otus.hw16messageserver.messageserver.messagesystem.Member;
import ru.otus.hw16messageserver.messageserver.messagesystem.message.Message;

public abstract class MessageToFrontend extends Message {

    public MessageToFrontend(Address from, Address to, String data) {
        super(from, to, data);
    }

    @Override
    public void exec(Member sender) {
        if (sender instanceof FrontendService) {
            exec((FrontendService)sender);
        }
    }

    public abstract void exec(FrontendService dbServer);

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

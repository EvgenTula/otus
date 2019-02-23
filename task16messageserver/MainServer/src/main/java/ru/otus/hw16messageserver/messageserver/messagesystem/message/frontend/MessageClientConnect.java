package ru.otus.hw16messageserver.messageserver.messagesystem.message.frontend;

import ru.otus.hw16messageserver.messageserver.messagesystem.FrontendService;
import ru.otus.hw16messageserver.messageserver.messagesystem.message.Message;
import ru.otus.hw16messageserver.messageserver.messagesystem.Address;
import ru.otus.hw16messageserver.messageserver.messagesystem.Member;

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
        //frontend.addClient(this.getData());
        frontend.sendMessageLoadData(getFrom(),this.getData());
    }
}

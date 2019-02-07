package ru.otus.hw15messagesystem.messagesystem.message.client;

import ru.otus.hw15messagesystem.frontend.FrontendService;
import ru.otus.hw15messagesystem.messagesystem.Address;
import ru.otus.hw15messagesystem.messagesystem.Message;
import ru.otus.hw15messagesystem.messagesystem.Sender;

public abstract class MessageToFrontend extends Message {

    public MessageToFrontend(Address from, Address to) {
        super(from, to);
    }

    @Override
    public void exec(Sender sender) {
        if (sender instanceof FrontendService) {
            exec((FrontendService)sender);
        }
    }

    public abstract void exec(FrontendService frontendService);
}

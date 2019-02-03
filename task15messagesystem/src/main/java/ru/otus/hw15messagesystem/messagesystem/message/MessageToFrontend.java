package ru.otus.hw15messagesystem.messagesystem.message;

import ru.otus.hw15messagesystem.frontend.FrontendService;
import ru.otus.hw15messagesystem.messagesystem.Address;
import ru.otus.hw15messagesystem.messagesystem.Message;

public abstract class MessageToFrontend extends Message {

    public MessageToFrontend(Address from, Address to, String data) {
        super(from, to, data);
    }

    @Override
    public void exec(Address address) {
        if (address instanceof FrontendService) {
            exec((FrontendService)address);
        }
    }

    public abstract void exec(FrontendService frontendService);
}

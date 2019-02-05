package ru.otus.hw15messagesystem.messagesystem.message;

import ru.otus.hw15messagesystem.frontend.FrontendService;
import ru.otus.hw15messagesystem.messagesystem.Address;
import ru.otus.hw15messagesystem.messagesystem.Message;
import ru.otus.hw15messagesystem.messagesystem.Sender;

public abstract class MessageToFrontend extends Message {

    public MessageToFrontend(Sender from, Sender to, String data) {
        super(from, to, data);
    }

    @Override
    public void exec(Sender sender) {
        if (sender instanceof FrontendService) {
            exec((FrontendService)sender);
        }
    }

    public abstract void exec(FrontendService frontendService);
}

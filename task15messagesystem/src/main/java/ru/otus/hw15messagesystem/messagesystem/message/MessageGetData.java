package ru.otus.hw15messagesystem.messagesystem.message;

import ru.otus.hw15messagesystem.frontend.FrontendService;
import ru.otus.hw15messagesystem.messagesystem.Address;
import ru.otus.hw15messagesystem.messagesystem.Sender;

import java.io.IOException;

public class MessageGetData extends MessageToFrontend {
    public MessageGetData(Sender from, Sender to, String data) {
        super(from, to, data);
    }

    @Override
    public void exec(FrontendService frontendService) {
        try {

            frontendService.getSession().getRemote().sendString(super.getData());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

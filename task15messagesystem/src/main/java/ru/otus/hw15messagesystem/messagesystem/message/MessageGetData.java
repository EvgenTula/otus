package ru.otus.hw15messagesystem.messagesystem.message;

import ru.otus.hw15messagesystem.frontend.FrontendService;
import ru.otus.hw15messagesystem.messagesystem.Address;

import java.io.IOException;

public class MessageGetData extends MessageToFrontend {
    public MessageGetData(Address from, Address to, String data) {
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

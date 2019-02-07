package ru.otus.hw15messagesystem.messagesystem.message.client;

import ru.otus.hw15messagesystem.frontend.FrontendService;
import ru.otus.hw15messagesystem.messagesystem.Address;
import ru.otus.hw15messagesystem.messagesystem.Sender;
import ru.otus.hw15messagesystem.messagesystem.message.client.MessageToFrontend;

public class MessageGetDataAllClient extends MessageToFrontend {
    private String data;
    public MessageGetDataAllClient(Address from, Address to, String data) {
        super(from, to);
        this.data = data;
    }

    @Override
    public void exec(FrontendService frontendService) {
        frontendService.sendDataAllClient(getData());
    }

    public String getData() {
        return data;
    }
}

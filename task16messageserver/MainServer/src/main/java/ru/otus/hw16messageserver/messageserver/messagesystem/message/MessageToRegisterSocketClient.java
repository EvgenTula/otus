package ru.otus.hw16messageserver.messageserver.messagesystem.message;

import ru.otus.hw16messageserver.messageserver.messagesystem.DBServer;
import ru.otus.hw16messageserver.messageserver.messagesystem.FrontendService;
import ru.otus.hw16messageserver.messageserver.messagesystem.Address;
import ru.otus.hw16messageserver.messageserver.messagesystem.Member;

public class MessageToRegisterSocketClient extends Message {

    public MessageToRegisterSocketClient(Address from, Address to, String data) {
        super(from, to, data);
    }

    @Override
    public void exec(Member sender) {
        if (sender instanceof FrontendService) {
            exec((FrontendService) sender);
        }
        if (sender instanceof DBServer) {
            exec((DBServer)sender);
        }
    }
    public void exec(FrontendService frontendService) {
        //frontendService.setDBServerAddress(this.getData());
    }

    public void exec(DBServer dbServer) {
        //dbServer.setFrontendAddress(this.getData());
    }
}

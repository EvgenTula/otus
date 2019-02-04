package ru.otus.hw15messagesystem.frontend;

import org.eclipse.jetty.websocket.api.Session;
import ru.otus.hw15messagesystem.messagesystem.Sender;

public interface FrontendService extends Sender {
    Session getSession();
}

package ru.otus.hw16messageserver.server.websocket;

import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;
import ru.otus.hw16messageserver.server.messageserver.MessageServer;
import ru.otus.hw16messageserver.server.messageserver.messagesystem.Message;

import java.util.concurrent.TimeUnit;

public class DBServiceWebSocketServlet extends WebSocketServlet {
    private final static long LOGOUT_TIME = TimeUnit.MINUTES.toMillis(10);

    private MessageServer messageServer;
    public DBServiceWebSocketServlet(MessageServer messageServer) {
        this.messageServer = messageServer;
    }

    @Override
    public void configure(WebSocketServletFactory factory) {
        factory.getPolicy().setIdleTimeout(LOGOUT_TIME);
        factory.setCreator(new DBServiceWebSocketCreator(messageServer));
    }
}

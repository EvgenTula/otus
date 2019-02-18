package ru.otus.hw16messageserver.servermain.websocket;

import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;
import ru.otus.hw16messageserver.servermain.SocketWorker;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class DBServiceWebSocketServlet extends WebSocketServlet {
    private final static long LOGOUT_TIME = TimeUnit.MINUTES.toMillis(10);

    private SocketWorker socketWorker;
    public DBServiceWebSocketServlet(SocketWorker socketWorker) {
        this.socketWorker = socketWorker;
    }

    @Override
    public void configure(WebSocketServletFactory factory) {
        factory.getPolicy().setIdleTimeout(LOGOUT_TIME);
        factory.setCreator(new DBServiceWebSocketCreator(socketWorker));
    }
}

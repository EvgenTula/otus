package ru.otus.hw15messagesystem.websocket;

import org.eclipse.jetty.websocket.servlet.ServletUpgradeRequest;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeResponse;
import org.eclipse.jetty.websocket.servlet.WebSocketCreator;
import ru.otus.hw15messagesystem.frontend.FrontendService;

public class DBServiceWebSocketCreator implements WebSocketCreator {

    private FrontendService frontendService;
    public DBServiceWebSocketCreator(FrontendService frontendService) {
        this.frontendService = frontendService;
    }

    @Override
    public Object createWebSocket(ServletUpgradeRequest servletUpgradeRequest, ServletUpgradeResponse servletUpgradeResponse) {
        DBServiceWebSocket webSocket = new DBServiceWebSocket(this.frontendService);
        return webSocket;
    }
}

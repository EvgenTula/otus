package ru.otus.hw16messageserver.frontend.websocket;

import org.eclipse.jetty.websocket.servlet.ServletUpgradeRequest;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeResponse;
import org.eclipse.jetty.websocket.servlet.WebSocketCreator;
import ru.otus.hw16messageserver.frontend.frontendservice.FrontendServiceImpl;
import ru.otus.hw16messageserver.messageserver.MessageServer;
import ru.otus.hw16messageserver.messageserver.messagesystem.FrontendService;

import java.util.HashMap;

public class ServiceWebSocketCreator implements WebSocketCreator {

    private FrontendServiceImpl frontendService;
    public ServiceWebSocketCreator(FrontendServiceImpl frontendService) {
        this.frontendService = frontendService;
    }

    @Override
    public Object createWebSocket(ServletUpgradeRequest servletUpgradeRequest, ServletUpgradeResponse servletUpgradeResponse) {
        ServiceWebSocket webSocket = new ServiceWebSocket(frontendService);
        return webSocket;
    }
}

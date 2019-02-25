package ru.otus.hw16messageserver.frontend.websocket;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import java.util.HashMap;

import ru.otus.hw16messageserver.frontend.frontendservice.FrontendServiceImpl;

@WebSocket
public class ServiceWebSocket {

    private Session session;
    private String uuid;

    private FrontendServiceImpl frontendService;

    public ServiceWebSocket(FrontendServiceImpl frontendService) {
        this.frontendService = frontendService;
    }

    @OnWebSocketMessage
    public void onMessage(String data) {
        this.frontendService.sendSaveData(data);
    }

    @OnWebSocketConnect
    public void onOpen(Session session) {
        setSession(session);
        uuid = this.frontendService.addClient(this);
        this.frontendService.sendUserList(uuid);
    }

    @OnWebSocketClose
    public void onClose(int statusCode, String reason) {
        this.frontendService.removeClient(uuid);
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public Session getSession() {
        return this.session;
    }
}

package ru.otus.hw15messagesystem.websocket;

import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;
import ru.otus.hw15messagesystem.frontend.FrontendService;

import java.util.concurrent.TimeUnit;

public class DBServiceWebSocketServlet extends WebSocketServlet {
    private final static long LOGOUT_TIME = TimeUnit.MINUTES.toMillis(10);

    private FrontendService frontendService;
    public DBServiceWebSocketServlet(FrontendService frontendService) {
        this.frontendService = frontendService;
    }

    @Override
    public void configure(WebSocketServletFactory factory) {
        factory.getPolicy().setIdleTimeout(LOGOUT_TIME);
        factory.setCreator(new DBServiceWebSocketCreator(frontendService));
    }
}

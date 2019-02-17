package ru.otus.hw16messageserver.servermain.websocket;

import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

import java.util.concurrent.TimeUnit;

public class DBServiceWebSocketServlet extends WebSocketServlet {
    private final static long LOGOUT_TIME = TimeUnit.MINUTES.toMillis(10);

    //private FrontendService frontendService;
    public DBServiceWebSocketServlet(/*FrontendService frontendService*/) {
        //this.frontendService = frontendService;
    }

    @Override
    public void configure(WebSocketServletFactory factory) {
        factory.getPolicy().setIdleTimeout(LOGOUT_TIME);
        factory.setCreator(new DBServiceWebSocketCreator(/*frontendService*/));
    }
}

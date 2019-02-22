package ru.otus.hw16messageserver.frontend.websocket;

import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;
import ru.otus.hw16messageserver.frontend.frontendservice.FrontendServiceImpl;

import java.util.concurrent.TimeUnit;

public class ServiceWebSocketServlet extends WebSocketServlet {
    private final static long LOGOUT_TIME = TimeUnit.MINUTES.toMillis(10);

    private FrontendServiceImpl frontendService;
    public ServiceWebSocketServlet(FrontendServiceImpl frontendService) {
        this.frontendService = frontendService;
    }

    @Override
    public void configure(WebSocketServletFactory factory) {
        factory.getPolicy().setIdleTimeout(LOGOUT_TIME);
        factory.setCreator(new ServiceWebSocketCreator(frontendService));
    }
}

package ru.otus.hw12webserver;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import ru.otus.hw12webserver.hibernate.DBService;
import ru.otus.hw12webserver.servlets.AdminServlet;
import ru.otus.hw12webserver.servlets.TemplateProcessor;

public class ServerManager {



    private int port = 8090;
    private String PUBLIC_HTML = "html_pages";

    DBService dbService;
    public Server createServer(DBService service, int port) throws Exception {

        this.dbService = service;
        this.port = port;
        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setResourceBase(PUBLIC_HTML);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        TemplateProcessor templateProcessor = new TemplateProcessor();

        context.addServlet(new ServletHolder(new AdminServlet(templateProcessor, service)), "/admin");
        //context.addServlet(AdminServlet.class, "/admin");
//        context.addServlet(new ServletHolder(new AdminServlet(TemplateProcessor.getInstance()), "/admin");
        //context.addServlet(TimerServlet.class, "/timer");



        Server server = new Server(port);
        server.setHandler(new HandlerList(resourceHandler, context));

        return server;
    }

}

package ru.otus.hw14war;
/*
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import ru.otus.hw14war.webserver.hibernate.DBService;
import ru.otus.hw14war.webserver.servlets.AdminServlet;
import ru.otus.hw14war.webserver.servlets.TemplateProcessor;
import ru.otus.hw14war.webserver.servlets.UserServlet;
*/
public class ServerHelper {
/*
    public static Server createServer(DBService service, int port) throws Exception {
        final String PUBLIC_HTML = "html";
        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setResourceBase(PUBLIC_HTML);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        TemplateProcessor templateProcessor = new TemplateProcessor();

        context.addServlet(new ServletHolder(new AdminServlet(templateProcessor, service)), "/user_list");
        context.addServlet(new ServletHolder(new UserServlet(templateProcessor, service)), "/user");

        Server server = new Server(port);
        server.setHandler(new HandlerList(resourceHandler, context));
        return server;
    }*/
}

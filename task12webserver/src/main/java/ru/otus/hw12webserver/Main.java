package ru.otus.hw12webserver;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import ru.otus.hw12webserver.hibernate.DBService;
import ru.otus.hw12webserver.hibernate.config.ConfigurationHibernate;
import ru.otus.hw12webserver.hibernate.datasets.AddressDataSetHibernate;
import ru.otus.hw12webserver.hibernate.datasets.PhoneDataSetHibernate;
import ru.otus.hw12webserver.hibernate.datasets.UserDataSetHibernate;
import ru.otus.hw12webserver.hibernate.dbservice.DBServiceHibernateImpl;
import ru.otus.hw12webserver.servlets.AdminServlet;
import ru.otus.hw12webserver.servlets.TemplateProcessor;

import java.util.ArrayList;
import java.util.List;

public class Main {

    private final static int PORT = 8090;
    private final static String PUBLIC_HTML = "html_public";


    public static void main(String[] args) throws Exception {

        /*hibernate*/
        DBService dbService = new DBServiceHibernateImpl(new ConfigurationHibernate());
        List<PhoneDataSetHibernate> phones = new ArrayList<>();
        phones.add(new PhoneDataSetHibernate("1111"));
        phones.add(new PhoneDataSetHibernate("2222"));
        phones.add(new PhoneDataSetHibernate("3333"));
        dbService.save(new UserDataSetHibernate("test1 hibernate",1,new AddressDataSetHibernate("test1 address hibernate"),phones));

        phones.clear();
        phones.add(new PhoneDataSetHibernate("4444"));
        phones.add(new PhoneDataSetHibernate("5555"));
        phones.add(new PhoneDataSetHibernate("6666"));
        dbService.save(new UserDataSetHibernate("test2 hibernate",1,new AddressDataSetHibernate("test2 address hibernate"),phones));

        phones.clear();
        phones.add(new PhoneDataSetHibernate("7777"));
        phones.add(new PhoneDataSetHibernate("8888"));
        phones.add(new PhoneDataSetHibernate("9999"));
        dbService.save(new UserDataSetHibernate("test3 hibernate",1,new AddressDataSetHibernate("test3 address hibernate"),phones));
/*
        List<UserDataSetHibernate> userList = ((DBServiceHibernateImpl) dbService).userGetAllList();
        for (UserDataSetHibernate user: userList) {
            System.out.println(user.toString());
        }

        System.out.println(((DBServiceHibernateImpl) dbService).userGetByName("test1 hibernate").toString());
*/

/*
        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setResourceBase(PUBLIC_HTML);
        resourceHandler.setWelcomeFiles(new String[] { "index.html" });
        Server server = new Server(PORT);
        server.setHandler(resourceHandler);

        server.start();
        server.join();
*/
        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setResourceBase(PUBLIC_HTML);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        TemplateProcessor templateProcessor = new TemplateProcessor();

        //context.addServlet(new ServletHolder(new LoginServlet(templateProcessor, "anonymous")), "/login");
        context.addServlet(AdminServlet.class, "/admin");
        //context.addServlet(TimerServlet.class, "/timer");

        Server server = new Server(PORT);
        server.setHandler(new HandlerList(resourceHandler, context));

        server.start();
        server.join();
    }
}

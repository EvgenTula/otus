package ru.otus.hw14war.servlets;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.hw14war.hibernate.DBService;
import ru.otus.hw14war.hibernate.config.ConfigurationHibernate;
import ru.otus.hw14war.hibernate.datasets.UserDataSetHibernate;
import ru.otus.hw14war.hibernate.dbservice.DBServiceHibernateImpl;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminServlet extends HttpServlet {

    private static final String PAGE_TEMPLATE = "user_list.html";

    private TemplateProcessor templateProcessor;
    private DBService dbService;
    //private final CacheEngine cacheEngine;

    //@SuppressWarnings("WeakerAccess")
    public AdminServlet() throws IOException {
        this.templateProcessor = new TemplateProcessor();
        this.dbService = new DBServiceHibernateImpl(new ConfigurationHibernate());
        //this.cacheEngine = cacheEngine;
    }
    public AdminServlet(TemplateProcessor templateProcessor, DBService service/*, CacheEngine cacheEngine*/) {
        this.templateProcessor = templateProcessor;
        this.dbService = service;
        //this.cacheEngine = cacheEngine;
    }

    public void init() {
        ApplicationContext context =
                new ClassPathXmlApplicationContext(
                        "SpringBeans.xml");
        try {
            this.templateProcessor = new TemplateProcessor();//context.getBean("templateProcessor", TemplateProcessor.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.dbService = context.getBean("dbService", DBServiceHibernateImpl.class);
    }



    private Map<String, Object> createPageVariablesMap(HttpServletRequest request) {
        Map<String, Object> pageVariables = new HashMap<>();
        List<UserDataSetHibernate> userList = ((DBServiceHibernateImpl)this.dbService).userGetAllList();
        pageVariables.put("user_list", userList);
        pageVariables.put("user_count",userList.size());
        return pageVariables;
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws IOException {
       Map<String, Object> pageVariables = createPageVariablesMap(request);
        response.setContentType("text/html;charset=utf-8");
        String page = templateProcessor.getPage(PAGE_TEMPLATE, pageVariables);
        response.getWriter().println(page);
        response.setStatus(HttpServletResponse.SC_OK);
    }
}


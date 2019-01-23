package ru.otus.hw14war.webserver.servlets;

import ru.otus.hw14war.mycacheengine.CacheEngine;
import ru.otus.hw14war.webserver.hibernate.DBService;
import ru.otus.hw14war.webserver.hibernate.datasets.UserDataSetHibernate;
import ru.otus.hw14war.webserver.hibernate.dbservice.DBServiceHibernateImpl;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminServlet extends HttpServlet {

    private static final String PAGE_TEMPLATE = "user_list.html";

    private final TemplateProcessor templateProcessor;
    private final DBService dbService;
    private final CacheEngine cacheEngine;

    @SuppressWarnings("WeakerAccess")
    public AdminServlet(TemplateProcessor templateProcessor, DBService service, CacheEngine cacheEngine) {
        this.templateProcessor = templateProcessor;
        this.dbService = service;
        this.cacheEngine = cacheEngine;
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


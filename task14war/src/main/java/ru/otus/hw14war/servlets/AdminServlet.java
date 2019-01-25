package ru.otus.hw14war.servlets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import ru.otus.hw14war.hibernate.DBService;
import ru.otus.hw14war.hibernate.datasets.UserDataSetHibernate;
import ru.otus.hw14war.hibernate.dbservice.DBServiceHibernateImpl;
import ru.otus.hw14war.mycacheengine.CacheEngine;
import ru.otus.hw14war.mycacheengine.Element;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configurable
public class AdminServlet extends HttpServlet {

    private static final String PAGE_TEMPLATE = "user_list.html";

    @Autowired
    private TemplateProcessor templateProcessor;
    @Autowired
    private DBService dbService;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
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


package ru.otus.hw14war.servlets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import ru.otus.hw14war.hibernate.DBService;
import ru.otus.hw14war.hibernate.datasets.AddressDataSetHibernate;
import ru.otus.hw14war.hibernate.datasets.PhoneDataSetHibernate;
import ru.otus.hw14war.hibernate.datasets.UserDataSetHibernate;
import ru.otus.hw14war.hibernate.dbservice.DBServiceHibernateImpl;
import ru.otus.hw14war.mycacheengine.CacheEngine;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configurable
public class UserServlet extends HttpServlet {

    private static final String PAGE_TEMPLATE = "user.html";

    @Autowired
    private TemplateProcessor templateProcessor;
    @Autowired
    private DBService dbService;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }


    private Map<String, Object> createPageDataFromHibernate(long userId) {
        Map<String, Object> pageVariables = new HashMap<>();
        if (userId != 0) {
            UserDataSetHibernate user = dbService.load(userId,UserDataSetHibernate.class);
            pageVariables.put("user", user);
        }
        else
        {
            pageVariables.put("user",new UserDataSetHibernate());
        }
        return pageVariables;
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws IOException {
        Map<String, Object> pageVariables = createPageDataFromHibernate(Long.parseLong(request.getParameter("id")));
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");
        String page = templateProcessor.getPage(PAGE_TEMPLATE, pageVariables);
        response.getWriter().println(page);
        response.setStatus(HttpServletResponse.SC_OK);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws IOException {
        this.dbService.save(userFromRequest(request));
        response.sendRedirect("user_list");
    }

    private UserDataSetHibernate userFromRequest(HttpServletRequest request) throws UnsupportedEncodingException {
        request.setCharacterEncoding("UTF-8");
        Long id = Long.parseLong(request.getParameter("id"));
        UserDataSetHibernate user = null;
        if (id != 0) {
            user = dbService.load(id, UserDataSetHibernate.class);
        }
        else {
            user = new UserDataSetHibernate();
        }
        user.setName(request.getParameter("name"));
        user.setAge(Integer.parseInt(request.getParameter("age")));
        String userAddress = request.getParameter("address");
        if (user.getAddress() == null) {
            AddressDataSetHibernate addressDataSetHibernate = new AddressDataSetHibernate(userAddress);
            user.setAddress(addressDataSetHibernate);
        } else{
            if (!user.getAddress().toString().equals(userAddress)) {
                user.getAddress().setStreet(userAddress);
            }
        }
        List<String> listParamPhone = new ArrayList<>();
        String[] listPhone = request.getParameter("phone").split(",");
        for (int i = 0 ; i<listPhone.length ; i++) {
            listParamPhone.add(listPhone[i]);
        }
        for (String phone : listParamPhone) {
            PhoneDataSetHibernate newPhone = new PhoneDataSetHibernate();
            newPhone.setNumber(phone);
            user.addPhone(newPhone);
        }
        return user;
    }


}
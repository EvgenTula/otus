package ru.otus.hw14war.servlets;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.hw14war.hibernate.DBService;
import ru.otus.hw14war.hibernate.datasets.AddressDataSetHibernate;
import ru.otus.hw14war.hibernate.datasets.PhoneDataSetHibernate;
import ru.otus.hw14war.hibernate.datasets.UserDataSetHibernate;
import ru.otus.hw14war.hibernate.dbservice.DBServiceHibernateImpl;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserServlet extends HttpServlet {

    private static final String PAGE_TEMPLATE = "user.html";

    private TemplateProcessor templateProcessor;
    private DBService dbService;

    @SuppressWarnings("WeakerAccess")
    public UserServlet(TemplateProcessor templateProcessor, DBService service) {
        this.templateProcessor = templateProcessor;
        this.dbService = service;
    }

    public void init() {
        ApplicationContext context =
                new ClassPathXmlApplicationContext(
                        "SpringBeans.xml");
        this.templateProcessor = context.getBean("templateProcessor", TemplateProcessor.class);
        this.dbService = context.getBean("dbService", DBServiceHibernateImpl.class);
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

    private UserDataSetHibernate userFromRequest(HttpServletRequest request) {
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
        List<String> listParamPhone = List.of(request.getParameter("phone").split(","));
        for (String phone : listParamPhone) {
            PhoneDataSetHibernate newPhone = new PhoneDataSetHibernate();
            newPhone.setNumber(phone);
            user.addPhone(newPhone);
        }
        return user;
    }


}
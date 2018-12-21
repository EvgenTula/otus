package ru.otus.hw12webserver.servlets;

import ru.otus.hw12webserver.hibernate.DBService;
import ru.otus.hw12webserver.hibernate.datasets.UserDataSetHibernate;
import ru.otus.hw12webserver.hibernate.dbservice.DBServiceHibernateImpl;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminServlet extends HttpServlet {

    private static final String DEFAULT_USER_NAME = "UNKNOWN";
    private static final String ADMIN_PAGE_TEMPLATE = "user_list.html";

    private final TemplateProcessor templateProcessor;
    private final DBService dbService;

    @SuppressWarnings("WeakerAccess")
    public AdminServlet(TemplateProcessor templateProcessor, DBService service) {
        this.templateProcessor = templateProcessor;
        this.dbService = service;
    }

    private Map<String, Object> createPageVariablesMap(HttpServletRequest request) {
        //    ${user_list}
        //</table>
        //<p>Count row = ${user_count}</p>
        Map<String, Object> pageVariables = new HashMap<>();
        List<UserDataSetHibernate> userList = ((DBServiceHibernateImpl)this.dbService).userGetAllList();
        StringBuilder stringBuilder = new StringBuilder();
        for (UserDataSetHibernate user: userList) {
            stringBuilder.append("<tr>");
            stringBuilder.append("<td>");
            stringBuilder.append(user.getId());
            stringBuilder.append("</td>");
            stringBuilder.append("<td>");
            stringBuilder.append(user.getName());
            stringBuilder.append("</td>");
            stringBuilder.append("<td>");
            stringBuilder.append(user.getAge());
            stringBuilder.append("</td>");
            stringBuilder.append("</tr>");
        }
        pageVariables.put("user_list",stringBuilder.toString());
        pageVariables.put("user_count",userList.size());
        return pageVariables;
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws IOException {
       Map<String, Object> pageVariables = createPageVariablesMap(request);
        response.setContentType("text/html;charset=utf-8");
        String page = templateProcessor.getPage(ADMIN_PAGE_TEMPLATE, pageVariables);
        response.getWriter().println(page);
        response.setStatus(HttpServletResponse.SC_OK);
    }
}


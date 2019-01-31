package ru.otus.hw15messagesystem.old.servlets;
/*
import ru.otus.hw12webserver.hibernate.DBService;
import ru.otus.hw12webserver.hibernate.datasets.UserDataSetHibernate;
import ru.otus.hw12webserver.hibernate.dbservice.DBServiceHibernateImpl;
*/
import javax.servlet.http.HttpServlet;

public class AdminServlet extends HttpServlet {
/*
    private static final String PAGE_TEMPLATE = "user_list.html";

    private final TemplateProcessor templateProcessor;
    private final DBService dbService;

    @SuppressWarnings("WeakerAccess")
    public AdminServlet(TemplateProcessor templateProcessor, DBService service) {
        this.templateProcessor = templateProcessor;
        this.dbService = service;
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
    }*/
}


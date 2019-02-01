package ru.otus.hw15messagesystem.websocket;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import ru.otus.hw15messagesystem.hibernate.DBService;
import ru.otus.hw15messagesystem.hibernate.datasets.UserDataSetHibernate;
import ru.otus.hw15messagesystem.hibernate.dbservice.DBServiceHibernateImpl;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@WebSocket
public class DBServiceWebSocket {
    private Set<DBServiceWebSocket> userList;
    private DBService dbService;

    private Session session;

    public DBServiceWebSocket(DBService dbService, Set<DBServiceWebSocket> userList) {
        this.dbService = dbService;
        this.userList = userList;
    }

    @OnWebSocketMessage
    public void onMessage(String data) throws IOException {
/*
        if (data.equals("onopen")) {
            List<UserDataSetHibernate> userList = ((DBServiceHibernateImpl)this.dbService).userGetAllList();
            JSONObject jsonObject = new JSONObject();
            for (UserDataSetHibernate user: userList) {
                jsonObject.put("id",user.getId());
                jsonObject.put("name",user.getName());
                jsonObject.put("age",user.getAge());
                jsonObject.put("address",user.getAddress().toString());
                jsonObject.put("phone",user.printPhoneList());
            }
            this.session.getRemote().sendString(jsonObject.toString());
        }
        else {
            for (DBServiceWebSocket user : userList) {
                try {
                    user.getSession().getRemote().sendString(data);
                    System.out.println("Sending message: " + data);
                } catch (Exception e) {
                    System.out.print(e.toString());
                }
            }
        }*/
    }

    @OnWebSocketConnect
    public void onOpen(Session session) throws IOException {
        userList.add(this);
        setSession(session);

        List<UserDataSetHibernate> dbUserList = ((DBServiceHibernateImpl)this.dbService).userGetAllList();
        //Gson gson = new Gson();

        //JSONObject jsonObject = new JSONObject();
/*
        for (UserDataSetHibernate user: dbUserList) {
            jsonObject.put("id",user.getId());
            jsonObject.put("name",user.getName());
            jsonObject.put("age",user.getAge());
            jsonObject.put("address",user.getAddress().toString());
            jsonObject.put("phone",user.printPhoneList());
        }
        */
        //String array = JSONArray.toJSONString(dbUserList);
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        this.session.getRemote().sendString(gson.toJson(dbUserList)/*array*//*jsonObject.toString()*/);
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    @OnWebSocketClose
    public void onClose(int statusCode, String reason) {
        this.userList.remove(this);
    }
}

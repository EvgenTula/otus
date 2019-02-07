package ru.otus.hw15messagesystem.messagesystem.message.service;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.otus.hw15messagesystem.hibernate.DBService;
import ru.otus.hw15messagesystem.hibernate.datasets.UserDataSetHibernate;
import ru.otus.hw15messagesystem.hibernate.dbservice.DBServiceHibernateImpl;
import ru.otus.hw15messagesystem.messagesystem.Sender;
import ru.otus.hw15messagesystem.websocket.DBServiceWebSocket;

import java.io.IOException;
import java.util.List;

public class MessageLoadData extends MessageToDBService {
    private DBServiceWebSocket webSocket;
    public MessageLoadData(Sender from, Sender to, DBServiceWebSocket webSocket) {
        super(from, to);
        this.webSocket = webSocket;
    }

    @Override
    public void exec(DBService dbService) {
        Gson gson = createGsonWithFilter();
        List<UserDataSetHibernate> dbUserList = ((DBServiceHibernateImpl)dbService).userGetAllList();
        try {
            webSocket.getSession().getRemote().sendString(gson.toJson(dbUserList));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Gson createGsonWithFilter() {
        return new GsonBuilder().addSerializationExclusionStrategy(new ExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes fieldAttributes) {
                boolean shouldSkipField = fieldAttributes.getDeclaredClass().equals(UserDataSetHibernate.class);
                return shouldSkipField;
            }

            @Override
            public boolean shouldSkipClass(Class<?> aClass) {
                return false;
            }
        }).create();
    }
}
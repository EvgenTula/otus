package ru.otus.hw16messageserver.messageserver.messagesystem.message.service;
/*
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.otus.hw16messageserver.messageserver.messagesystem.Address;

import java.util.List;
*/
public class MessageLoadData {}/*extends MessageToDBService {
    private String uuid;
    public MessageLoadData(Address from, Address to, String uuid) {
        super(from, to);
        this.uuid = uuid;
    }

    @Override
    public void exec(DBService dbService) {
        Gson gson = createGsonWithFilter();
        List<UserDataSetHibernate> dbUserList = ((DBServiceHibernateImpl)dbService).userGetAllList();
        dbService.getMessageSystem().sendMessage(new MessageGetDataClient(getTo(), getFrom(), gson.toJson(dbUserList), uuid));
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
}*/

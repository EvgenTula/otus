package ru.otus.hw16messageserver.messageserver.messagesystem.message.service;
/*
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;
*/
public class MessageSaveData {} /*extends MessageToDBService {

    private String data;
    public MessageSaveData(Address from, Address to, String data) {
        super(from, to);
        this.data = data;
    }

    public String getData() {
        return data;
    }

    @Override
    public void exec(DBService dbService) {
        Gson gson = createGsonWithFilter();
        UserDataSetHibernate newUser = gson.fromJson(getData(), UserDataSetHibernate.class);
        for (PhoneDataSetHibernate phone : newUser.getPhoneList()) {
            phone.setUserDataSet(newUser);
        }
        dbService.save(newUser);
        List<UserDataSetHibernate> listData = new ArrayList<>();
        listData.add(newUser);
        dbService.getMessageSystem().sendMessage(new MessageGetDataClient(getTo(), getFrom(), gson.toJson(listData)));
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

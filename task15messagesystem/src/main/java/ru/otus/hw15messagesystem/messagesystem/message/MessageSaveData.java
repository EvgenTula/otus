package ru.otus.hw15messagesystem.messagesystem.message;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.otus.hw15messagesystem.hibernate.DBService;
import ru.otus.hw15messagesystem.hibernate.datasets.PhoneDataSetHibernate;
import ru.otus.hw15messagesystem.hibernate.datasets.UserDataSetHibernate;
import ru.otus.hw15messagesystem.hibernate.dbservice.DBServiceHibernateImpl;
import ru.otus.hw15messagesystem.messagesystem.Address;

import java.util.List;

public class MessageSaveData extends MessageToDBService {
    public MessageSaveData(Address from, Address to, String data) {
        super(from, to, data);
    }

    @Override
    public void exec(DBService dbService) {
        Gson gson = createGsonWithFilter();
        if (!getData().equals("")) {
            UserDataSetHibernate newUser = gson.fromJson(getData(), UserDataSetHibernate.class);
            for (PhoneDataSetHibernate phone : newUser.getPhoneList()) {
                phone.setUserDataSet(newUser);
            }
            dbService.save(newUser);
        }
        List<UserDataSetHibernate> dbUserList = ((DBServiceHibernateImpl)dbService).userGetAllList();
        getFrom().getMessageSystem().sendMessage(new MessageGetData(getTo(), getFrom(), gson.toJson(dbUserList)));
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

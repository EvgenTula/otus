package ru.otus.hw16messageserver.server.messageserver.messagesystem.message.dbservice;

import com.google.gson.Gson;
import org.json.simple.JSONObject;
import ru.otus.hw16messageserver.server.messageserver.messagesystem.Address;
import ru.otus.hw16messageserver.server.messageserver.messagesystem.DBServer;
import ru.otus.hw16messageserver.server.messageserver.messagesystem.Member;
import ru.otus.hw16messageserver.server.messageserver.messagesystem.message.dbservice.MessageToDBServer;

import java.util.List;

public class MessageLoadData extends MessageToDBServer {
    private String uuid;
    public MessageLoadData(Address from, Address to, String uuid) {
        super(from, to, uuid);
        this.uuid = uuid;
    }

    @Override
    public void exec(DBServer dbServer) {
        dbServer.loadUserList();
    }


    /*
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
    */
}

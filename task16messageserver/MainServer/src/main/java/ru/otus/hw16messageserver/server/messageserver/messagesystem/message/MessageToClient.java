package ru.otus.hw16messageserver.server.messageserver.messagesystem.message;

import com.google.gson.Gson;
import org.json.simple.JSONObject;

import java.util.UUID;

public class MessageToClient {
    public UUID uuid;
    public String data;

    public String getJsonObject() {
        Gson gson = new Gson();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("className", this.getClass().getName());
        jsonObject.put("data", gson.toJson(this));
        return jsonObject.toString();
    }
}

package ru.otus.hw15messagesystem.messagesystem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

public class MessageSystem {
    private final Map<Address,Address> addressMap;
    private final Map<Address, LinkedBlockingQueue<Message>> messagesMap;
    private final List<Thread> workers;

    public MessageSystem() {
        this.addressMap = new HashMap<>();
        this.messagesMap = new HashMap<>();
        this.workers = new ArrayList<>();
    }

    public void addAddress(Sender sender) {
        this.addressMap.put(sender.getAddress(), null);
        this.messagesMap.put(sender.getAddress(), new LinkedBlockingQueue<>());
    }

    public void removeAddress(Sender sender) {
        this.addressMap.remove(sender.getAddress());
        this.messagesMap.remove(sender.getAddress());
    }

    public void sendMessage(Message message) {
        messagesMap.get(message.getTo()).add(message);
    }

    public void dispose() {
        workers.forEach(Thread::interrupt);
    }
}

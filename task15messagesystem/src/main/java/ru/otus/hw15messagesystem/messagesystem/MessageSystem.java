package ru.otus.hw15messagesystem.messagesystem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;

public class MessageSystem {
    private final Map<Sender, LinkedBlockingQueue<Message>> messagesMap;
    private final List<Thread> workers;

    public MessageSystem() {
        this.messagesMap = new HashMap<>();
        this.workers = new ArrayList<>();
    }

    public void addAddress(Sender sender) {
        this.messagesMap.put(sender, new LinkedBlockingQueue<>());
        Thread thread = new Thread(() -> {
            while (true) {
                LinkedBlockingQueue<Message> queue = messagesMap.get(sender);
                try {
                    Message message = queue.take();
                    message.exec(message.getFrom());
                } catch (InterruptedException e) {
                    return;
                }
            }
        });
        thread.setName(sender.getAddress().getId());
        thread.start();
        workers.add(thread);
    }

    public void removeAddress(Sender sender) {
        this.messagesMap.remove(sender);
    }

    public void sendMessage(Message message) {
        messagesMap.get(message.getTo()).add(message);
    }

    public void dispose() {
        workers.forEach(Thread::interrupt);
    }
}

package client;

import common.Message;

import java.util.ArrayList;
import java.util.List;

public class Conversation {

    private int with;
    private List<Message> messages;

    public Conversation(int with) {
        this.with = with;
        this.messages = new ArrayList<>();
    }

    public int getWith() {
        return with;
    }

    public void setWith(int with) {
        this.with = with;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void addMessage(Message message) {
        this.messages.add(message);
    }
}

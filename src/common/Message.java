package common;

import java.io.Serializable;

public class Message implements Serializable {
    
    private String sender;
    private String content;
    
    public static final long serialVersionUID = 12345678902L;

    public Message(String sender, String content) {
        this.sender = sender;
        this.content = content;
    }


    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Message{" +
                "sender='" + sender + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}

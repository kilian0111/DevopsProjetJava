package main.java.common;

import java.io.Serial;
import java.io.Serializable;

/**
 * Objet envoyé au serveur
 */
public class ObjectSend implements Serializable {

    private Object object;
    private Action action;

    @Serial
    private static final long serialVersionUID =  1350092881346723533L;

    public ObjectSend(Object object, Action action) {
        this.object = object;
        this.action = action;
    }
    public ObjectSend() { }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }
}

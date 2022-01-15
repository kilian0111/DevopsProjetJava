package main.java.common;

import java.io.Serializable;

public class ObjectSend implements Serializable {

    private Object object;
    private Action action;

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

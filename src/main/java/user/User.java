package main.java.user;

import java.math.BigInteger;

public class User {

    private BigInteger id;
    private String userName;
    // TODO : Compléter les attributs

    public User(BigInteger id, String userName) {
        this.id = id;
        this.userName = userName;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}

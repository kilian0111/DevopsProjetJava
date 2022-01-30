package main.java.common;

import java.io.Serial;
import java.io.Serializable;

public class ChoixChifoumi implements Serializable {

    private Integer choix;
    private Long userId;
    private Long gameId;

    @Serial
    private static final long serialVersionUID =  2551092881346723531L;

    public ChoixChifoumi() { }

    public ChoixChifoumi(Integer choix, Long userId, Long gameId) {
        this.choix = choix;
        this.userId = userId;
        this.gameId = gameId;
    }

    public Integer getChoix() {return choix;}
    public void setChoix(Integer choix) {this.choix = choix;}

    public Long getUserId() {return userId;}
    public void setUserId(Long userId) {this.userId = userId;}

    public Long getGameId() {return gameId;}
    public void setGameId(Long gameId) {this.gameId = gameId;}

}

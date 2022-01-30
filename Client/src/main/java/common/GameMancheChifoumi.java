package main.java.common;

import java.io.Serial;
import java.io.Serializable;

/**
 * Informations liées à une manche de jeu
 */
public class GameMancheChifoumi  implements Serializable{


    private Long id;
    private Long gameId;
    private Integer choixJ1;
    private Integer choixJ2;


    @Serial
    private static final long serialVersionUID =  1450122881346723589L;

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public Long getGameId() { return gameId;}
    public void setGameId(Long gameId) {this.gameId = gameId;}

    public Integer getChoixJ1() {return choixJ1;}
    public void setChoixJ1(Integer choixJ1) {this.choixJ1 = choixJ1;}

    public Integer getChoixJ2() {return choixJ2;}
    public void setChoixJ2(Integer choixJ2) {this.choixJ2 = choixJ2;}
}

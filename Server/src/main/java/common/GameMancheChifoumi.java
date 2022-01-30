package main.java.common;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * Informations liées à une manche de jeu
 */
@Entity
@Table(name = "T_MANCHE_CHIFOUMI")
public class GameMancheChifoumi  implements Serializable{


    private Long id;
    private Long gameId;
    private Integer choixJ1;
    private Integer choixJ2;


    @Serial
    private static final long serialVersionUID =  1450122881346723589L;

    @GenericGenerator(name = "generator", strategy = "increment")
    @Id
    @GeneratedValue(generator = "generator")
    @Column(name = "MC_ID")
    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    @Column(name = "GC_ID")
    public Long getGameId() { return gameId;}
    public void setGameId(Long gameId) {this.gameId = gameId;}

    @Column(name = "MC_CHOIX_J1")
    public Integer getChoixJ1() {return choixJ1;}
    public void setChoixJ1(Integer choixJ1) {this.choixJ1 = choixJ1;}

    @Column(name = "MC_CHOIX_J2")
    public Integer getChoixJ2() {return choixJ2;}
    public void setChoixJ2(Integer choixJ2) {this.choixJ2 = choixJ2;}
}

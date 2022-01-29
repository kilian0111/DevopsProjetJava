package main.java.common;

import java.io.Serial;
import java.io.Serializable;

public class Game implements Serializable {
    private UserSafeData userInitGame;
    private UserSafeData userJ2;
    private Integer scoreJ1;
    private Integer scoreJ2;
    private JeuxChoix jeuxChoixJ1;
    private JeuxChoix jeuxChoixJ2;

    @Serial
    private static final long serialVersionUID =  1350092881346723588L;


    public UserSafeData getUserInitGame() {return userInitGame;}
    public void setUserInitGame(UserSafeData userInitGame) {this.userInitGame = userInitGame;}

    public UserSafeData getUserJ2() {return userJ2;}
    public void setUserJ2(UserSafeData userJ2) {this.userJ2 = userJ2;}

    public Integer getScoreJ1() {return scoreJ1;}
    public void setScoreJ1(Integer scoreJ1) {this.scoreJ1 = scoreJ1;}

    public Integer getScoreJ2() {return scoreJ2;}
    public void setScoreJ2(Integer scoreJ2) {this.scoreJ2 = scoreJ2;}

    public JeuxChoix getJeuxChoixJ1() {return jeuxChoixJ1;}
    public void setJeuxChoixJ1(JeuxChoix jeuxChoixJ1) {this.jeuxChoixJ1 = jeuxChoixJ1;}

    public JeuxChoix getJeuxChoixJ2() {return jeuxChoixJ2;}
    public void setJeuxChoixJ2(JeuxChoix jeuxChoixJ2) {this.jeuxChoixJ2 = jeuxChoixJ2;}
}

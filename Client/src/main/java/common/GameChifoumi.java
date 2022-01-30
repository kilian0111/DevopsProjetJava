package main.java.common;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Toutes les informations liées à une partie de jeu
 * Joueurs, scores, manches
 */
public class GameChifoumi implements Serializable {

    private Long id;
    private Long conversationId;
    private UserSafeData idUtilisateurJ1;
    private UserSafeData idUtilisateurJ2;
    private Date dateGame;
    private Integer scoreJ1;
    private Integer scoreJ2;
    private List<GameMancheChifoumi> lesManches;
    private Boolean accepter;

    public GameChifoumi() {}

    public GameChifoumi(Long conversationId, UserSafeData idUtilisateurJ1, UserSafeData idUtilisateurJ2) {
        this.conversationId = conversationId;
        this.idUtilisateurJ1 = idUtilisateurJ1;
        this.idUtilisateurJ2 = idUtilisateurJ2;
    }

    @Serial
    private static final long serialVersionUID =  1350122881346723588L;

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public Long getConversationId() {return conversationId;}
    public void setConversationId(Long conversationId) {this.conversationId = conversationId;}

    public UserSafeData getIdUtilisateurJ1() {return idUtilisateurJ1;}
    public void setIdUtilisateurJ1(UserSafeData idUtilisateurJ1) {this.idUtilisateurJ1 = idUtilisateurJ1;}

    public UserSafeData getIdUtilisateurJ2() {return idUtilisateurJ2;}
    public void setIdUtilisateurJ2(UserSafeData idUtilisateurJ2) {this.idUtilisateurJ2 = idUtilisateurJ2;}

    public Date getDateGame() {return dateGame;}
    public void setDateGame(Date dateGame) {this.dateGame = dateGame;}

    public Integer getScoreJ1() {return scoreJ1;}
    public void setScoreJ1(Integer scoreJ1) {this.scoreJ1 = scoreJ1;}

    public Integer getScoreJ2() {return scoreJ2;}
    public void setScoreJ2(Integer scoreJ2) {this.scoreJ2 = scoreJ2;}

    public Boolean getAccepter() {return accepter;}
    public void setAccepter(Boolean accepter) {this.accepter = accepter;}

    public List<GameMancheChifoumi> getLesManches() {return lesManches;}
    public void setLesManches(List<GameMancheChifoumi> lesManches) {this.lesManches = lesManches;}

    public void addManche(GameMancheChifoumi gameMancheChifoumi){
        this.lesManches.add(gameMancheChifoumi);
    }

    public void remove (GameMancheChifoumi gameMancheChifoumi){
        this.lesManches.remove(gameMancheChifoumi);
    }
}

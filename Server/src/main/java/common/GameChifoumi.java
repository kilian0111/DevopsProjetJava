package main.java.common;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "T_GAME_CHIFOUMI")
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

    @GenericGenerator(name = "generator", strategy = "increment")
    @Id
    @GeneratedValue(generator = "generator")
    @Column(name = "GC_ID",updatable = false, nullable = false)
    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    @Column(name = "CO_ID")
    public Long getConversationId() {return conversationId;}
    public void setConversationId(Long conversationId) {this.conversationId = conversationId;}

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="UT_ID_J1", referencedColumnName="UT_ID")
    public UserSafeData getIdUtilisateurJ1() {return idUtilisateurJ1;}
    public void setIdUtilisateurJ1(UserSafeData idUtilisateurJ1) {this.idUtilisateurJ1 = idUtilisateurJ1;}

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="UT_ID_J2", referencedColumnName="UT_ID")
    public UserSafeData getIdUtilisateurJ2() {return idUtilisateurJ2;}
    public void setIdUtilisateurJ2(UserSafeData idUtilisateurJ2) {this.idUtilisateurJ2 = idUtilisateurJ2;}

    @Column(name = "GC_DT")
    public Date getDateGame() {return dateGame;}
    public void setDateGame(Date dateGame) {this.dateGame = dateGame;}

    @Column(name = "GC_SCORE_J1")
    public Integer getScoreJ1() {return scoreJ1;}
    public void setScoreJ1(Integer scoreJ1) {this.scoreJ1 = scoreJ1;}

    @Column(name = "GC_SCORE_J2")
    public Integer getScoreJ2() {return scoreJ2;}
    public void setScoreJ2(Integer scoreJ2) {this.scoreJ2 = scoreJ2;}

    @Column(name = "GC_ACCEPTER")
    public Boolean getAccepter() {return accepter;}
    public void setAccepter(Boolean accepter) {this.accepter = accepter;}

    @OneToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinColumn(name="GC_ID", referencedColumnName="GC_ID")
    @OrderBy("GC_ID")
    public List<GameMancheChifoumi> getLesManches() {return lesManches;}
    public void setLesManches(List<GameMancheChifoumi> lesManches) {this.lesManches = lesManches;}

    public void addManche(GameMancheChifoumi gameMancheChifoumi){
        this.lesManches.add(gameMancheChifoumi);
    }

    public void remove (GameMancheChifoumi gameMancheChifoumi){
        this.lesManches.remove(gameMancheChifoumi);
    }
}

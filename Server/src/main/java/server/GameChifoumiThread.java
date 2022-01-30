package main.java.server;

import main.java.common.*;
import main.java.repository.GameChifoumiJpaRepository;
import main.java.repository.GameMancheChifomiJpaRepository;
import main.java.repository.UserJpaRepository;

import java.util.ArrayList;
import java.util.Date;

public class GameChifoumiThread implements Runnable{

    private Server server;

    private GameChifoumi game;

    private ConnectedClient clientJ1;

    private ConnectedClient clientJ2;

    private Boolean accepter;

    private Boolean fermer;

    private GameMancheChifoumi mancheEnCours;


    public GameChifoumiThread(){}

    public GameChifoumiThread(Server server, GameChifoumi game, ConnectedClient clientJ1) {
        this.server = server;
        this.game = game;
        this.clientJ1 = clientJ1;
    }

    @Override
    public void run() {
        this.fermer = false;
        game.setScoreJ1(0);
        game.setScoreJ2(0);
        game.setLesManches(new ArrayList<>());
        game.setDateGame(new Date());

        // on cherche si le j2 est connecter

        if(game.getIdUtilisateurJ2() != null ){
            for(ConnectedClient connectedClient : this.server.getLesClients()){
                if(connectedClient.getUser() != null){
                    if(game.getIdUtilisateurJ2().getId().equals(connectedClient.getUser().getId())){
                        this.clientJ2 = connectedClient;
                        break;
                    }
                }
            }
            // si le j2 est connecté alors on lui envoie un message
            if(this.clientJ2 != null){
                GameChifoumiJpaRepository.saveGame(game);
                this.clientJ2.sendToClient(new ObjectSend(game,Action.DEMANDE_JEUX));
                // on laisse 2 minutes pour que le j2 repondes
                long temps = System.currentTimeMillis() + 2 *60 *1000;
                while(accepter == null && temps > System.currentTimeMillis() ){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ignored) { }
                }
                if(accepter != null && accepter){
                    this.clientJ1.sendToClient(new ObjectSend(this.game, Action.LANCER_JEUX));

                    while(!fermer && this.clientJ2 != null && this.clientJ1 != null){
                        this.mancheEnCours = new GameMancheChifoumi();
                        this.mancheEnCours.setGameId(this.game.getId());
                        while(this.mancheEnCours.getChoixJ1() == null || this.mancheEnCours.getChoixJ2() == null) {
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException ignored) {
                            }
                        }
                          /* public GameChifoumi(Long conversationId, UserSafeData idUtilisateurJ1, UserSafeData idUtilisateurJ2) {
                            this.conversationId = conversationId;
                            this.idUtilisateurJ1 = idUtilisateurJ1;
                            this.idUtilisateurJ2 = idUtilisateurJ2;
                        }*/
                        this.traitementJeux();
                        GameMancheChifomiJpaRepository.saveMancheGame(this.mancheEnCours);

                        GameChifoumi gameChifoumi = new GameChifoumi(this.game.getConversationId(),this.game.getIdUtilisateurJ1(),this.game.getIdUtilisateurJ2());
                        gameChifoumi.setDateGame(this.game.getDateGame());
                        gameChifoumi.setScoreJ1(this.game.getScoreJ1());
                        gameChifoumi.setScoreJ2(this.game.getScoreJ2());
                        gameChifoumi.setId(this.game.getId());
                        gameChifoumi.setLesManches(new ArrayList<>());
                        gameChifoumi.setAccepter(true);
                        gameChifoumi.addManche(this.mancheEnCours);
                        this.game = gameChifoumi;

                        if (this.clientJ2 != null) {
                            this.clientJ2.sendToClient(new ObjectSend(gameChifoumi,Action.RESULTAT_JEUX));
                        }
                        if(this.clientJ1 != null){
                            this.clientJ1.sendToClient(new ObjectSend(gameChifoumi,Action.RESULTAT_JEUX));
                        }
                    }

                    GameChifoumiJpaRepository.updateGame(this.game);
                    if (this.clientJ2 != null && this.clientJ1 != null) {
                        this.server.sendMessageToConv(this.clientJ1.getUser().getPseudo() + "a " + this.game.getScoreJ1() + " point \n " + this.clientJ2.getUser().getPseudo() + "a " + this.game.getScoreJ2() + " point ",this.game.getConversationId());
                    }
                } else {
                    this.clientJ1.sendToClient(new ObjectSend("l'adversaire à refusé le chifoumi ou n'a pas répondu dans les temps", Action.LANCER_JEUX));
                }
            }else{
                this.clientJ1.sendToClient(new ObjectSend("l'adversaire n'est pas connécté", Action.LANCER_JEUX));
            }
        }else{
            this.clientJ1.sendToClient(new ObjectSend("Erreur adversaire non trouvée",Action.LANCER_JEUX));
        }
        if (accepter != null && accepter && this.clientJ2 != null) {
            this.clientJ2.remove(this);
        }
        if(this.clientJ1 != null){
            this.clientJ1.remove(this);
        }

        this.server.removeGame(this);
        Thread.currentThread().interrupt();
    }
// 1 == feuille 2 == pierre 3== ciseaux
    private void traitementJeux() {
        if(this.mancheEnCours.getChoixJ1().equals(1)){
            if(this.mancheEnCours.getChoixJ2().equals(2)){
                this.game.setScoreJ1(this.game.getScoreJ1() + 1);
            }else if(this.mancheEnCours.getChoixJ2().equals(3)){
                this.game.setScoreJ2(this.game.getScoreJ2() + 1);
            }
        }else if(this.mancheEnCours.getChoixJ1().equals(2)){
            if(this.mancheEnCours.getChoixJ2().equals(1)){
                this.game.setScoreJ2(this.game.getScoreJ2() + 1);
            }else if(this.mancheEnCours.getChoixJ2().equals(3)){
                this.game.setScoreJ1(this.game.getScoreJ1() + 1);
            }
        }else if(this.mancheEnCours.getChoixJ1().equals(3)){
            if(this.mancheEnCours.getChoixJ2().equals(1)){
                this.game.setScoreJ1(this.game.getScoreJ1() + 1);
            }else if(this.mancheEnCours.getChoixJ2().equals(2)){
                this.game.setScoreJ2(this.game.getScoreJ2() + 1);
            }
        }

    }



    public GameChifoumi getGame() {return game;}
    public void setGame(GameChifoumi game) { this.game = game;}

    public Boolean getAccepter() {return accepter;}
    public void setAccepter(Boolean accepter) {this.accepter = accepter;}

    public Boolean getFermer() {return fermer;}
    public void setFermer(Boolean fermer) {this.fermer = fermer;}

    public GameMancheChifoumi getMancheEnCours() {return mancheEnCours;}
    public void setMancheEnCours(GameMancheChifoumi mancheEnCours) {this.mancheEnCours = mancheEnCours;}
}

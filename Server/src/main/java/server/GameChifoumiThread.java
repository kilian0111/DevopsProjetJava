package main.java.server;

import main.java.common.Action;
import main.java.common.Conversations;
import main.java.common.GameChifoumi;
import main.java.common.ObjectSend;
import main.java.repository.GameChifoumiJpaRepository;
import main.java.repository.UserJpaRepository;

import java.util.ArrayList;
import java.util.Date;

public class GameChifoumiThread implements Runnable{

    private Server server;

    private GameChifoumi game;

    private ConnectedClient clientJ1;

    private ConnectedClient clientJ2;

    private Boolean accepter;

    public GameChifoumiThread(){}

    public GameChifoumiThread(Server server, GameChifoumi game, ConnectedClient clientJ1) {
        this.server = server;
        this.game = game;
        this.clientJ1 = clientJ1;
    }

    @Override
    public void run() {
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


                } else {
                    this.clientJ1.sendToClient(new ObjectSend("l'adversaire a refusé ou n'est pas connecter ", Action.LANCER_JEUX));
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

    public GameChifoumi getGame() {return game;}
    public void setGame(GameChifoumi game) { this.game = game;}

    public Boolean getAccepter() {return accepter;}
    public void setAccepter(Boolean accepter) {this.accepter = accepter;}
}

package main.java.server;


import main.java.common.*;
import main.java.repository.ConversationJpaRepository;
import main.java.repository.MessageJpaRepository;
import main.java.repository.UserJpaRepository;
import main.java.repository.UtilisateursConversationJpaRepository;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class ConnectedClient implements Runnable {
    private static int idCounter;
    private int id;
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private Server server;
    private User user;
    private ChangeMdp chaineMdpOubliee;
    private List<GameChifoumiThread> lesGamesChifoumi;

    public ConnectedClient(Socket socket, Server server) throws IOException {
        this.socket = socket;
        this.server = server;
        this.out = new ObjectOutputStream(this.socket.getOutputStream());
        this.lesGamesChifoumi = new ArrayList<>();
    }

    public static int getIdCounter() {return idCounter;}
    public static void setIdCounter(int idCounter) {ConnectedClient.idCounter = idCounter;}

    public int getId() {return id;}
    public void setId(int id) {this.id = id;}

    public Socket getSocket() {return socket;}
    public void setSocket(Socket socket) {this.socket = socket;}

    public ObjectOutputStream getOut() {return out;}
    public void setOut(ObjectOutputStream out) {this.out = out;}

    public ObjectInputStream getIn() {return in;}
    public void setIn(ObjectInputStream in) {this.in = in;}

    public Server getServer() {return server;}
    public void setServer(Server server) {this.server = server;}

    public User getUser() {return user;}
    public void setUser(User user) {this.user = user;}

    @Override
    public void run() {
       try {
            this.in = new ObjectInputStream(this.socket.getInputStream());
            boolean isActive = true;
            while(isActive){
                Object object =  this.in.readObject();
                if(object != null ){
                   if(object instanceof ObjectSend objectSend){
                        //connexion
                       if(objectSend.getAction() == Action.CONNECTION && objectSend.getObject() instanceof User){
                           this.connexionClient((User) objectSend.getObject());
                        // inscription
                       } else if(objectSend.getAction() == Action.INSCRIPTION && objectSend.getObject() instanceof User ){
                           this.inscription((User) objectSend.getObject());
                           // Mdp Oubliée
                       } else if(objectSend.getAction() == Action.MDP_OUBLIEE && objectSend.getObject() instanceof String ){
                           this.mdpOubliee((String) objectSend.getObject());
                       //code reçu par mail + nouveau mdp
                       }else if(objectSend.getAction() == Action.CHANGEMENT_MDP && objectSend.getObject() instanceof ChangeMdp ){
                           this.changeMdp((ChangeMdp) objectSend.getObject());
                           //code reçu par mail + nouveau mdp
                       }else if(objectSend.getAction() == Action.MODIF_USER && objectSend.getObject() instanceof User ) {
                           this.modifUser((User) objectSend.getObject());
                       }else if(objectSend.getAction() == Action.MESSAGE && objectSend.getObject() instanceof Message ){
                           this.addMessage((Message) objectSend.getObject());
                       } else if(objectSend.getAction() == Action.LANCER_JEUX && objectSend.getObject() instanceof GameChifoumi){
                           this.lancerJeux((GameChifoumi) objectSend.getObject() );
                       }else if(objectSend.getAction() == Action.REPONSEJ2_JEUX && objectSend.getObject() instanceof GameChifoumi){
                           this.reponseChifoumiJ2((GameChifoumi) objectSend.getObject());
                       } else if(objectSend.getAction() == Action.CHOIX_JEUX && objectSend.getObject() instanceof ChoixChifoumi){
                           this.addChoixToPartie((ChoixChifoumi) objectSend.getObject()  );
                       } else if(objectSend.getAction() == Action.LIST_USER ){
                           this.envoyerListUser();
                       }
                   }
                }else{
                    server.disconnectedClient(this);
                    isActive = false;
                    Thread.currentThread().interrupt();
                }
            }
        } catch (IOException e) {
            server.disconnectedClient(this);
           Thread.currentThread().interrupt();
        } catch(Exception e ){
            e.printStackTrace();
        }
    }

    private void envoyerListUser() {
        List<UserSafeData> lesUsers = UserJpaRepository.getAllUser();
        this.sendToClient(new ObjectSend(lesUsers,Action.LIST_USER));
    }

    private void addChoixToPartie(ChoixChifoumi choixChifoumi) {
        GameChifoumiThread leJeux = null ;
        for(GameChifoumiThread gameChifoumiThread : this.server.getLesGames()){
            if(gameChifoumiThread.getGame().getId().equals(choixChifoumi.getGameId())){
                leJeux = gameChifoumiThread;
                break;
            }
        }
        if(leJeux != null){
            GameMancheChifoumi gameMancheChifoumi = leJeux.getMancheEnCours();
            if( leJeux.getGame().getIdUtilisateurJ1().getId().equals(choixChifoumi.getUserId())){
               gameMancheChifoumi.setChoixJ1(choixChifoumi.getChoix());
            }else{
                gameMancheChifoumi.setChoixJ2(choixChifoumi.getChoix());
            }
            leJeux.setMancheEnCours(gameMancheChifoumi);
        }
    }

    private void reponseChifoumiJ2(GameChifoumi accepter) {
        GameChifoumiThread leJeux = null ;
        for(GameChifoumiThread gameChifoumiThread : this.server.getLesGames()){
            if(gameChifoumiThread.getGame().getId().equals(accepter.getId())){
                leJeux = gameChifoumiThread;
                break;
            }
        }
        if(leJeux != null ){
            if(accepter.getAccepter()){
                leJeux.setAccepter(true);
                this.lesGamesChifoumi.add(leJeux);
            }else{
                leJeux.setAccepter(false);
            }
        }
    }


    public void inscription(User user) {
        if(UserJpaRepository.getUserByEmail(user.getMail()).getId() == null ){
            if(UserJpaRepository.getUserByPseudo(user.getPseudo()).getId() == null){
                User userInscrit =  UserJpaRepository.saveUser(user);
                this.sendToClient(new ObjectSend(userInscrit,Action.INSCRIPTION));
                this.user = userInscrit;
                this.sendToClient(new ObjectSend(UtilisateursConversationJpaRepository.getUtilisateurConversationByUserId(this.user.getId()),Action.LIST_CONVERSATION));
                this.server.sendToAll(this.user.getPseudo() + " vient d'arriver sur KIJOKI (comment a t-il pu faire sans kijoki)");
            }else{
                this.sendToClient(new ObjectSend("Pseudo déjà utiliser",Action.INSCRIPTION));
            }
        }else{
            this.sendToClient(new ObjectSend("E-mail déjà utiliser",Action.INSCRIPTION));
        }

    }

    public void mdpOubliee(String mail)  {
        if(mail != null){
            User user = UserJpaRepository.getUserByEmail(mail);
            if(user != null && user.getId() != null){
                if(user.getActif()){
                    this.chaineMdpOubliee = new ChangeMdp();
                    chaineMdpOubliee.setCodeMail(Utils.generateChaine());
                    chaineMdpOubliee.setDateDemande(new Date());
                    chaineMdpOubliee.setIdUser(user.getId());
                    chaineMdpOubliee.setNbEssais(0);
                    Mail.sendMail("kilian.marmilliot@gmail.com","Rénitialisation de votre mots de passe",
                            "le code générere est  : " +  chaineMdpOubliee.getCodeMail());
                    System.out.println(chaineMdpOubliee.getCodeMail());
                    this.sendToClient(new ObjectSend("ok",Action.REPONSE_MDP_OUBLIEE));
                }else{
                    this.sendToClient(new ObjectSend("nonActif",Action.REPONSE_MDP_OUBLIEE));
                }

            }else{
                this.sendToClient(new ObjectSend(null,Action.REPONSE_MDP_OUBLIEE));
            }
        }

    }

    public void changeMdp(ChangeMdp renitMdp){
        ObjectSend objectSend = new ObjectSend();
        objectSend.setAction(Action.REPONSE_CHANGEMENT_MDP);
        if(this.chaineMdpOubliee != null) {
            this.chaineMdpOubliee.setNbEssais(this.chaineMdpOubliee.getNbEssais() + 1);
            if(this.chaineMdpOubliee.getNbEssais() <= 3){
                if(this.chaineMdpOubliee.getCodeMail().equals(renitMdp.getCodeMail())){
                    if( new Date(this.chaineMdpOubliee.getDateDemande().getTime() + 1000 * 60 *5).getTime() > new Date().getTime()){
                        User user = UserJpaRepository.getUserById(this.chaineMdpOubliee.getIdUser());
                        user.setMdp(Utils.convertMdpWithSalt(renitMdp.getNewMdp(),user.getSalt()));
                        UserJpaRepository.updateUserRecord(user);
                        objectSend.setObject("ok");
                    }else {
                        this.chaineMdpOubliee = null;
                        objectSend.setObject("tooLong");
                    }
                } else {
                    objectSend.setObject("badCode");
                }
            }else {
                this.chaineMdpOubliee = null;
                objectSend.setObject("tooManyTry");
            }
        }else {
            objectSend.setObject("codeNotExists");
        }
        this.sendToClient(objectSend);
    }

    public void closeClient() throws IOException {
        if(this.in != null){
            this.in.close();
        }
        if(this.out != null){
            this.out.close();
        }
        if(this.socket != null){
            this.socket.close();
        }
    }

    public void connexionClient(User user)  {
        User userConnecete = UserJpaRepository.seConnecter(user.getPseudo(), user.getMdp());
        ObjectSend actionSend = new ObjectSend(userConnecete, Action.CONNECTION);
        this.user = userConnecete;
        this.sendToClient(actionSend);
        if(userConnecete != null && userConnecete.getId() != null && userConnecete.getActif()){
            List<UtilisateursConversations> utilisateursConvList = UtilisateursConversationJpaRepository.getUtilisateurConversationByUserId(this.user.getId());
            this.sendToClient(new ObjectSend(utilisateursConvList,Action.LIST_CONVERSATION));
            this.server.sendToAll(this.user.getPseudo() + " est de retour sur KIJOKI ");
        }

    }

    public void sendToClient(ObjectSend object)  {
        try{
            this.out.writeObject(object);
            this.out.flush();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public Message sendMessage(Message mess) throws IOException {
        this.out.writeObject(mess);
        this.out.flush();
        return mess;
    }

    public void modifUser(User user) {
        UserJpaRepository.updateUserRecord(user);
        sendToClient(new ObjectSend("OK", Action.REPONSE_MODIFUSER));
    }

    public void addMessage(Message message){
        if(this.user.getId().equals(message.getUtilisateurSender().getId())){
            if(message.getConversationId() != 0){
                MessageJpaRepository.saveMessage(message);
                Conversations conv = ConversationJpaRepository.getConversationById(message.getConversationId());
                List<Long> userId = new ArrayList<>();
                for(UserSafeData user : conv.getLesUsers()){
                    userId.add(user.getId());
                }
                for(ConnectedClient connectedClient : this.server.getLesClients()){
                    if(connectedClient.getUser() != null && userId.contains(connectedClient.getUser().getId()) && !message.getUtilisateurSender().getId().equals(connectedClient.getUser().getId())){
                        connectedClient.sendToClient(new ObjectSend(message,Action.MESSAGE));
                    }
                }
            }else{
                for(ConnectedClient connectedClient : this.server.getLesClients()){
                    if(connectedClient.getUser() != null && !message.getUtilisateurSender().getId().equals(connectedClient.getUser().getId())){
                        connectedClient.sendToClient(new ObjectSend(message,Action.MESSAGE));
                    }
                }
            }
        }
    }



    private void lancerJeux(GameChifoumi game) {
        GameChifoumiThread lancerJeux = new GameChifoumiThread(this.server,game,this);
        this.server.addGame(lancerJeux);
        this.addGame(lancerJeux);
        Thread threadJeux = new Thread(lancerJeux);
        threadJeux.start();
    }


    public void addGame(GameChifoumiThread gameChifoumi){
        this.lesGamesChifoumi.add(gameChifoumi);
    }

    public void remove(GameChifoumiThread gameChifoumi){
        this.lesGamesChifoumi.remove(gameChifoumi);
    }

}

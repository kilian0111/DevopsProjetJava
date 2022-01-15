package main.java.server;

import main.java.repository.UserJpaRepository;

import main.java.common.*;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Date;


public class ConnectedClient implements Runnable {
    private static int idCounter;
    private int id;
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private Server server;
    private User user;
    private ChangeMdp chaineMdpOubliee;

    public ConnectedClient(Socket socket, Server server) throws IOException {
        this.socket = socket;
        this.server = server;
        this.out = new ObjectOutputStream(this.socket.getOutputStream());
        this.id = idCounter;
        idCounter++;
        System.out.println("Nouvelle connexion, id = " + id);
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
                       }

                   }
                }else{
                    server.disconnectedClient(this);
                    isActive = false;
                }
            }
        } catch (IOException e) {
            server.disconnectedClient(this);
        } catch(Exception e ){
            e.printStackTrace();
        }
    }

    public void inscription(User user) {
        //User userReturn = this.dataBaseConnectionRequest.inscription(user);
        this.sendToClient(null);
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

}

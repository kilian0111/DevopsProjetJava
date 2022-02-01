package main.java.common;

/**
 * Différentes actions d'échanges entre le serveur et le client
 */
public enum Action {
    CONNECTION,INSCRIPTION,MDP_OUBLIEE,REPONSE_MDP_OUBLIEE,CHANGEMENT_MDP,REPONSE_CHANGEMENT_MDP,
    MODIF_USER,REPONSE_MODIFUSER,LIST_CONVERSATION,MESSAGE,LANCER_JEUX,DEMANDE_JEUX,REPONSEJ2_JEUX,
    RESULTAT_JEUX,CHOIX_JEUX,LIST_USER,CREATE_CONV,AJOUT_USER_CONV,FERMER_JEUX,USER_CONNECTER,
    REMOVE_USER_CONNECTER,ADD_USER_CONNECTER,QUITTER_CONV,SUPPRESSSION_USER_CONV
}

package main.java.common;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    /**
     * Converti le mot de passe avec le sel
     * @param passwordToHash Mot de passe
     * @param salt Sel
     * @return Mot de passe avec le sel appliqué
     */
    public static String convertMdpWithSalt(String passwordToHash, String salt){
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt.getBytes(StandardCharsets.UTF_8));
            byte[] bytes = md.digest(passwordToHash.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++){
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        }
        catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        return generatedPassword;
    }

    /**
     * Chemin des ressources
     * @return Chemin vers les ressources
     */
    public static String getResourcesPath(){
        return "src/main/ressources/";
    }

    /**
     * Vérifie si une adresse mail en est vraiment une
     * @param email Adresse à vérifier
     * @return true si l'adresse fournie est valide
     */
    public static boolean isEmailAdress(String email){
        Pattern p = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}$");
        Matcher m = p.matcher(email.toUpperCase());
        return m.matches();
    }

    /**
     *
     * @return Chaine aléatoire de 10 caractères
     */
    public static String generateChaine(){
        Random rand = new Random();
        String alphabet = "abcdefghijklmnopqrstuvwxyz@$12356789";
        StringBuilder chaine = new StringBuilder("");
        int longueur = alphabet.length();
        for(int i = 0; i < 10; i++) {
            int k = rand.nextInt(longueur);
            chaine.append(alphabet.charAt(k));
        }
        return chaine.toString();
    }

}

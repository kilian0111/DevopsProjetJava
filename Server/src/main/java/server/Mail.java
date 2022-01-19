package main.java.server;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

public class Mail {

    private static final String USERNAME = "mail.auto.app@gmail.com";
    private static final String PASSWORD = "xasiplquunrhqtsd";

    public static void main(String[] args) throws Exception {
        // Email information such as from, to, subject and contents.
        String mailFrom = "mail.auto.app@gmail.com";
        String mailTo = "kilian.marmilliot@gmail.com";
        String mailSubject = "SSL - Gmail Send Email Demo";
        String mailText = "SSL - Gmail Send Email Demo";

        Mail gmail = new Mail();
        gmail.sendMail( mailTo, mailSubject, mailText);
    }

    public static void sendMail(String mailTo, String mailSubject,
                                String mailText) {

        Properties config = createConfiguration();

        // Creates a mail session. We need to supply username and
        // password for Gmail authentication.
        Session session = Session.getInstance(config, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(
                        Mail.USERNAME,
                        Mail.PASSWORD
                );
            }
        });

        try{
            // Creates email message
            Message message = new MimeMessage(session);
            message.setSentDate(new Date());
            message.setFrom(new InternetAddress(Mail.USERNAME));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(mailTo));
            message.setSubject(mailSubject);
            message.setText(mailText);

            // Send a message
            Transport.send(message);
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    private static Properties createConfiguration() {
        return new Properties() {{
            put("mail.smtp.host", "smtp.gmail.com");
            put("mail.smtp.auth", "true");
            put("mail.smtp.port", "465");
            put("mail.smtp.socketFactory.port", "465");
            put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            put("mail.smtp.ssl.protocols", "TLSv1.2");
        }};
    }

}




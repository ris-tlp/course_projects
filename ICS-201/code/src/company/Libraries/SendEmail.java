package company.Libraries;

import javafx.scene.control.Alert;

import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendEmail {

    private String AccountName;
    private String Password;
    private String from;
    private String to;
    private String subject;
    private String messageContent;


    public SendEmail(String AccountName, String Password, String to,
                     String subject, String messageContent) {
        this.AccountName = AccountName;
        this.from = AccountName;
        this.Password = Password;
        this.to = to;
        this.messageContent = messageContent;
        this.subject = subject;
    }

    private static final Logger LOGGER = Logger.getAnonymousLogger();

    String SMTP_Server;
    private static final int SMTP_Port = 587;

    public boolean sendEmail() {
        String host = null;
        int locAt = AccountName.indexOf('@');
        //if (locAt != -1) {
            host = AccountName.substring(locAt + 1);
            SMTP_Server = "smtp." + host;
            final Session session = Session.getInstance(this.getEmailProperties(), new Authenticator() {

                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(AccountName, Password);
                }

            });

            try {
                final Message message = new MimeMessage(session);
                message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
                message.setFrom(new InternetAddress(from));
                message.setSubject(subject);
                message.setText(messageContent);
                message.setSentDate(new Date());
                Transport.send(message);
            } catch (final MessagingException ex) {
                // LOGGER.log(Level.WARNING, "Error Sending Message: " + ex.getMessage(), ex);
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(ex.getMessage());
                alert.setContentText("Failed to send email to " + to);
                alert.show();
                return false;
            }
            return true;
        //}
       // else
            //return false;
    }

    public Properties getEmailProperties() {
        final Properties config = new Properties();
        config.put("mail.smtp.auth", "true");
        config.put("mail.smtp.starttls.enable", "true");
        config.put("mail.smtp.host", SMTP_Server);
        config.put("mail.smtp.port", SMTP_Port);
        return config;
    }
}
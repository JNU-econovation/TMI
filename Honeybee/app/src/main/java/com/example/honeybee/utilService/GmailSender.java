package com.example.honeybee.utilService;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;

public class GmailSender extends javax.mail.Authenticator{
    private String mailhost = "smtp.gmail.com";
    private String user = "" ;
    private String password = "";
    private Session session;
    private static String emailCode;

    public GmailSender(String user, String password){
        this.user = user;
        this.password = password;
        emailCode = createEmailCode();
        Properties props = new Properties();

        props.put("mail.smtp.host", "smtp.naver.com");
        props.put("mail.smtp.port", 465);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.ssl.enable", "true");


        /*
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.host", mailhost);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.quitwait", "false");

         */

        session = Session.getDefaultInstance(props, this);
    }

    public String getEmailCode(){
        return emailCode;
    }

    private String createEmailCode() {
        StringBuffer temp = new StringBuffer();
        for(int i = 0; i < 4; i++){
            double random = Math.random();
            int value = (int)(random*10);
            temp.append(value);
        }
        String AuthenticationKey = temp.toString();

        return AuthenticationKey;
    }

    protected PasswordAuthentication getPasswordAuthentication(){
        return new PasswordAuthentication(user, password);
    }

    public synchronized void sendMail(String subject, String body, String recipients) throws Exception{
        MimeMessage message = new MimeMessage(session);
        DataHandler handler = new DataHandler(new ByteArrayDataSource(body.getBytes(), "text/plain"));
        message.setSender(new InternetAddress(user));
        message.setSubject(subject);
        message.setDataHandler(handler);
        if (recipients.indexOf(',') > 0)
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipients));
        else
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipients));
        Transport.send(message); //메시지 전달
    }



}

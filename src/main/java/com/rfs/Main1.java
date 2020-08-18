package com.rfs;


import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Properties;

public class Main1 {

    public static void main(String[] args) {
        String to = "aagonzalezrojas@gmail.com";
        //String to = "alvaro.gonzalez@neodigitallogic.com";
        //String to = "lidia@arroyovargas.com";
        String subject = "PRUEBANDO 1 2 3 4 5";
        String text = "HOLA MUNDO DE DIOS!!";
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("190.112.221.105");
//        mailSender.setHost("mail.rfslogistica.com");
        mailSender.setPort(587);

//        mailSender.setHost("smtp.gmail.com");h

//        mailSender.setPort(587);

        mailSender.setUsername("facturacion@rfslogistica.com");
        mailSender.setPassword("$$Rfs20i8");

//        mailSender.setUsername("alvaro.gonzalez@neodigitallogic.com");
//        mailSender.setPassword("Sancarlos1975");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");

        props.put("to", "aagonzalezrojas@gmail.com");

        props.put("mail.smtp.user", "facturacion@rfslogistica.com");
        props.put("mail.smtp.host", "190.112.221.105");
//        props.put("mail.smtp.host", "mail.rfslogistica.com");
        props.put("mail.smtp.from", "facturacion@rfslogistica.com");
        props.put("mail.smtp.port", 587);
        props.put("mail.smtp.starttls.enable","true");
        props.put("mail.smtp.debug", "true");
        props.put("mail.smtp.auth", "true");

        props.put("from", "facturacion@rfslogistica.com");
//        props.put("mail.smtp.socketFactory.port", 587);
//        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
//        props.put("mail.smtp.socketFactory.fallback", "false");

        //props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");
        mailSender.setJavaMailProperties(props);

        try {
            MimeMessage message = mailSender.createMimeMessage();
            // pass 'true' to the constructor to create a multipart message
            MimeMessageHelper helper = new MimeMessageHelper(message, true);


            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text);
            helper.setFrom("facturacion@rfslogistica.com");

            InternetAddress[] myToList = InternetAddress.parse(to);
            InternetAddress[] myBccList = InternetAddress.parse("recepcion@rfslogistica.com");

            //helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text);
            helper.setFrom("facturacion@rfslogistica.com");
            message.setRecipients(Message.RecipientType.TO,myToList);
            message.setRecipients(Message.RecipientType.BCC,myBccList);

            FileSystemResource file = null;
            String pdfAttachment = "/home/alvaro/development/projects/Mar 1, 2018/17641/50601022018310165487900100001010000017641153703397.pdf";
            String xmlAttachment = "/home/alvaro/development/projects/Mar 1, 2018/17641/signed50601022018310165487900100001010000017641153703397.xml";
            String consecutivo = "17762";
            if (pdfAttachment!=null) {
                file = new FileSystemResource(new File(pdfAttachment));
                helper.addAttachment("Factura # " + consecutivo + ".pdf", file);
            }

            if (xmlAttachment!=null) {
                file = new FileSystemResource(new File(xmlAttachment));
                helper.addAttachment("Xml # " + consecutivo + ".xml", file);
            }


            mailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

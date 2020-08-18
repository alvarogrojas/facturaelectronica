package com.rfs.service.factura.billapp;


import com.rfs.domain.Empresa;
import com.rfs.domain.Usuario;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.File;

@Component
public class EmailSenderService {

    @Autowired
    public JavaMailSender emailSender;

    @Value( "${bill.facturacion.email}" )
    private String facturacionEmail;

    @Value( "${bill.recepcion.email}" )
    private String recepcionEmail;

    @Value( "${bill.proceso.target.email}" )
    private String targetProcesoEmail;

        public Integer sendMessageWithAttachment(
                String to, String subject, String text, String xmlAttachment, String pdfAttachment, String respuestaXML, String consecutivo, String tipoDocumento, String destinoEmpresa) {
        Integer result = 0;

        try {
            MimeMessage message = emailSender.createMimeMessage();
            // pass 'true' to the constructor to create a multipart message
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            InternetAddress[] myToList = InternetAddress.parse(to);
            InternetAddress[] myBccList = null;
            try {
                if (destinoEmpresa!=null && destinoEmpresa!="")
                    myBccList = InternetAddress.parse(destinoEmpresa);
            } catch (RuntimeException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            //InternetAddress[] myBccList = InternetAddress.parse("recepcion@rfslogistica.com");

            //helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text);
            helper.setFrom(facturacionEmail);
            message.setRecipients(Message.RecipientType.TO,myToList);
            if (myBccList!=null)
                message.setRecipients(Message.RecipientType.BCC,myBccList);
//            helper.setFrom("facturacion@rfslogistica.com");

            String titleAttachment = getTituloAttachment(tipoDocumento);

            FileSystemResource file = null;
            if (pdfAttachment!=null) {
                file = new FileSystemResource(new File(pdfAttachment));
                helper.addAttachment( titleAttachment + consecutivo + ".pdf", file);
            }

            if (xmlAttachment!=null) {
                file = new FileSystemResource(new File(xmlAttachment));
                helper.addAttachment("enviado_" + consecutivo + ".xml", file);
            }

            if (respuestaXML!=null) {
                file = new FileSystemResource(new File(respuestaXML));
                helper.addAttachment("respuesta_" + consecutivo + ".xml", file);
            }
            emailSender.send(message);
            try {
                Thread.sleep(1000l);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            result = 1;
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return result;
        // ...
    }

    private String getRecepcionCorreo(String destinoEmpresa) {
        if (destinoEmpresa!=null && destinoEmpresa!="") {
            return recepcionEmail + ";" + destinoEmpresa;
        }
        return recepcionEmail;
    }

    private String getTituloAttachment(String tipoDocumento) {


        if (StringUtils.isEmpty(tipoDocumento) || tipoDocumento.equals(BillHelper.FACTURA_ELECTRONICA_TIPO)) {
            return "Factura # ";
        } else if (tipoDocumento.equals(BillHelper.NOTA_CREDITO_TIPO)) {
            return "Nota Credito # ";
        } else {
            return "Comprobante Electronico ";
        }


    }

    public String getFacturacionEmail() {
        return facturacionEmail;
    }

    public void setFacturacionEmail(String facturacionEmail) {
        this.facturacionEmail = facturacionEmail;
    }

    public String getRecepcionEmail() {
        return recepcionEmail;
    }

    public void setRecepcionEmail(String recepcionEmail) {
        this.recepcionEmail = recepcionEmail;
    }

    public Integer sendMessageProcessResult(String subject, String msg, String targetEmail) {

        Integer result = 0;
        try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            InternetAddress[] myToList = null;
            if (StringUtils.isEmpty(targetEmail)) {
                myToList = InternetAddress.parse(this.targetProcesoEmail);
            } else {
                myToList = InternetAddress.parse(targetEmail);
            }
            InternetAddress[] myBccList = InternetAddress.parse(recepcionEmail);
            //helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(msg);
            helper.setFrom(facturacionEmail);
            message.setRecipients(Message.RecipientType.TO,myToList);
            message.setRecipients(Message.RecipientType.BCC,myBccList);

            FileSystemResource file = null;

            emailSender.send(message);
            try {
                Thread.sleep(1000l);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            result = 1;
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return result;
    }

    public Integer sendMessageProcessResultWithAttachments(String subject, String msg, String targetEmail, String xmlAttachment, String pdfAttachment, String respuestaXML) {

        Integer result = 0;
        try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            InternetAddress[] myToList = null;
            if (StringUtils.isEmpty(targetEmail)) {
                myToList = InternetAddress.parse(this.targetProcesoEmail);
            } else {
                myToList = InternetAddress.parse(targetEmail);
            }
            InternetAddress[] myBccList = InternetAddress.parse(recepcionEmail);
            //helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(msg);
            helper.setFrom(facturacionEmail);
            message.setRecipients(Message.RecipientType.TO,myToList);
            message.setRecipients(Message.RecipientType.BCC,myBccList);

            FileSystemResource file = null;

            if (pdfAttachment!=null) {
                file = new FileSystemResource(new File(pdfAttachment));
                helper.addAttachment(  "comprobante.pdf", file);
            }

            if (xmlAttachment!=null) {
                file = new FileSystemResource(new File(xmlAttachment));
                helper.addAttachment("enviado_"  + ".xml", file);
            }

            if (respuestaXML!=null) {
                file = new FileSystemResource(new File(respuestaXML));
                helper.addAttachment("respuesta_"  + ".xml", file);
            }

            emailSender.send(message);
            try {
                Thread.sleep(1000l);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            result = 1;
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return result;
    }

    public Integer sendMessageProcessNotaCreditoResult(String subject, String msg, String xmlAttachment, String pdfAttachment, String consecutivo, String targetEmail) {
        Integer result = 0;
        try {
            MimeMessage message = emailSender.createMimeMessage();
            // pass 'true' to the constructor to create a multipart message
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            InternetAddress[] myToList = null;
            if (StringUtils.isEmpty(targetEmail)) {
                myToList = InternetAddress.parse(this.targetProcesoEmail);
            } else {
                myToList = InternetAddress.parse(targetEmail);
            }
            InternetAddress[] myBccList = InternetAddress.parse(recepcionEmail);
//            InternetAddress[] myBccList = InternetAddress.parse("recepcion@rfslogistica.com");

            //helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(msg);
            helper.setFrom(facturacionEmail);
            message.setRecipients(Message.RecipientType.TO,myToList);
            message.setRecipients(Message.RecipientType.BCC,myBccList);
//            helper.setFrom("facturacion@rfslogistica.com");

            String titleAttachment = getTituloAttachment(BillHelper.NOTA_CREDITO_TIPO);

            FileSystemResource file = null;
            if (pdfAttachment!=null) {
                file = new FileSystemResource(new File(pdfAttachment));
                helper.addAttachment( titleAttachment + consecutivo + ".pdf", file);
            }

            if (xmlAttachment!=null) {
                file = new FileSystemResource(new File(xmlAttachment));
                helper.addAttachment("Xml # " + consecutivo + ".xml", file);
            }
            emailSender.send(message);
            try {
                Thread.sleep(1000l);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            result = 1;
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return result;
    }

//    public Integer sendMessageProcessNotaCreditoResult(String subject, String msg, String xmlAttachment, String pdfAttachment, String consecutivo, String targetEmail) {
//        Integer result = 0;
//        try {
//            MimeMessage message = emailSender.createMimeMessage();
//            // pass 'true' to the constructor to create a multipart message
//            MimeMessageHelper helper = new MimeMessageHelper(message, true);
//
//            InternetAddress[] myToList = null;
//            if (StringUtils.isEmpty(targetEmail)) {
//                myToList = InternetAddress.parse(this.targetProcesoEmail);
//            } else {
//                myToList = InternetAddress.parse(targetEmail);
//            }
//            InternetAddress[] myBccList = InternetAddress.parse(recepcionEmail);
////            InternetAddress[] myBccList = InternetAddress.parse("recepcion@rfslogistica.com");
//
//            //helper.setTo(to);
//            helper.setSubject(subject);
//            helper.setText(msg);
//            helper.setFrom(facturacionEmail);
//            message.setRecipients(Message.RecipientType.TO,myToList);
//            message.setRecipients(Message.RecipientType.BCC,myBccList);
////            helper.setFrom("facturacion@rfslogistica.com");
//
//            String titleAttachment = getTituloAttachment(BillHelper.NOTA_CREDITO_TIPO);
//
//            FileSystemResource file = null;
//            if (pdfAttachment!=null) {
//                file = new FileSystemResource(new File(pdfAttachment));
//                helper.addAttachment( titleAttachment + consecutivo + ".pdf", file);
//            }
//
//            if (xmlAttachment!=null) {
//                file = new FileSystemResource(new File(xmlAttachment));
//                helper.addAttachment("Xml # " + consecutivo + ".xml", file);
//            }
//            emailSender.send(message);
//            try {
//                Thread.sleep(1000l);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            result = 1;
//        } catch (MessagingException e) {
//            e.printStackTrace();
//        }
//        return result;
//    }

    public Integer sendMessageProcessConfirmacionRechazo(String subject, String msg, String xmlAttachment, String consecutivo, String targetEmail) {
        Integer result = 0;
        try {
            MimeMessage message = emailSender.createMimeMessage();
            // pass 'true' to the constructor to create a multipart message
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            InternetAddress[] myToList = null;
            if (StringUtils.isEmpty(targetEmail)) {
                myToList = InternetAddress.parse(this.targetProcesoEmail);
            } else {
                myToList = InternetAddress.parse(targetEmail);
            }
            InternetAddress[] myBccList = InternetAddress.parse(recepcionEmail);
//            InternetAddress[] myBccList = InternetAddress.parse("recepcion@rfslogistica.com");

            //helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(msg);
            helper.setFrom(facturacionEmail);
            message.setRecipients(Message.RecipientType.TO,myToList);
            message.setRecipients(Message.RecipientType.BCC,myBccList);

            String titleAttachment = getTituloAttachment(null);

            FileSystemResource file = null;

            if (xmlAttachment!=null) {
                file = new FileSystemResource(new File(xmlAttachment));
                helper.addAttachment("Xml # " + consecutivo + ".xml", file);
            }
            emailSender.send(message);
            try {
                Thread.sleep(1000l);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            result = 1;
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return result;
    }

    public Integer sendNewCuenta(Usuario u, Empresa empresa, String clave) {
        Integer result = 0;
        try {
            MimeMessage message = emailSender.createMimeMessage();
            // pass 'true' to the constructor to create a multipart message
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            InternetAddress[] myToList = InternetAddress.parse("aagonzalezrojas@gmail.com");
            InternetAddress[] myBccList = InternetAddress.parse("alvaro_gonrojas@hotmail.com");
            helper.setSubject("Informacion de nueva cuenta en NeoFacturacion");
            helper.setText("Se creo una nueva cuenta a nombre de " + empresa.getNombre() +  ", con un numero de facturas mensuales de: " + empresa.getCantidad() + ".\n\r" + "La informacion del usuario creado es la siguiente: \n\rUsuario: "
                    + u.getUsuario() + ".\n\r" + "Clave: " + clave +".\n\r");
            helper.setFrom(facturacionEmail);
            message.setRecipients(Message.RecipientType.TO,myToList);
            message.setRecipients(Message.RecipientType.BCC,myBccList);

            String titleAttachment = getTituloAttachment(null);


            emailSender.send(message);
            try {
                Thread.sleep(1000l);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            result = 1;
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return result;
    }

    public Integer sendActualizaCuenta(Empresa empresa) {
        Integer result = 0;
        try {
            MimeMessage message = emailSender.createMimeMessage();
            // pass 'true' to the constructor to create a multipart message
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            InternetAddress[] myToList = InternetAddress.parse("aagonzalezrojas@gmail.com");
            InternetAddress[] myBccList = InternetAddress.parse("alvaro_gonrojas@hotmail.com");
            helper.setSubject("Informacion de cuenta actualizada en Neo Facturacion");
            helper.setText("Se actualizo la cuenta a nombre de " + empresa.getNombre() +  ", con un numero de facturas mensuales de: " + empresa.getCantidad() + ".\n\r" );
            helper.setFrom(facturacionEmail);
            message.setRecipients(Message.RecipientType.TO,myToList);
            message.setRecipients(Message.RecipientType.BCC,myBccList);


            emailSender.send(message);
            try {
                Thread.sleep(1000l);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            result = 1;
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return result;
    }

}

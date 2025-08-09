/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author maxie
 */
package correo;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import jakarta.activation.*;
import java.io.File;
import java.util.Properties;

public class EnviarCorreo {

    public static void enviar(String destinatario, String asunto, String mensaje, String rutaArchivo) {
        final String remitente = "tecnossistema02@gmail.com";
        final String clave = "btmalmnybpnfhudp"; // contraseña de aplicación sin espacios
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(remitente, clave);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(remitente));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
            message.setSubject(asunto);

            // Texto del mensaje
            MimeBodyPart texto = new MimeBodyPart();
            texto.setText(mensaje);

            // Archivo adjunto
            MimeBodyPart adjunto = new MimeBodyPart();
            File file = new File(rutaArchivo);
            if (!file.exists()) {
                throw new Exception("El archivo adjunto no existe: " + rutaArchivo);
            }
            adjunto.attachFile(file);

            // Multipart para combinar texto y adjunto
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(texto);
            multipart.addBodyPart(adjunto);

            message.setContent(multipart);

            Transport.send(message);
            System.out.println("Correo enviado exitosamente con adjunto.");

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error al enviar correo: " + e.getMessage());
        }
    }
}
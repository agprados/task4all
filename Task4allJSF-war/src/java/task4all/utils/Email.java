/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package task4all.utils;

import java.util.Properties;
import javax.faces.context.FacesContext;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import task4all.entity.Proyecto;
import task4all.entity.Usuario;

public class Email {

    private static final String remitente = "task4all.noreply@gmail.com";
    private static final String contraseña = "task4all4";

    private static void sendEmail(String email, String subject, String text) {
        try {
            Properties props = new Properties();
            props.setProperty("mail.smtp.host", "smtp.gmail.com");
            props.setProperty("mail.smtp.starttls.enable", "true");
            props.setProperty("mail.smtp.port", "587");
            props.setProperty("mail.smtp.user", remitente);
            props.setProperty("mail.smtp.auth", "true");

            Session session = Session.getDefaultInstance(props);

            MimeMessage message = new MimeMessage(session);
            message.addRecipient(
                    Message.RecipientType.TO,
                    new InternetAddress(email));
            message.setSubject(subject);
            message.setText(text, "ISO-8859-1", "html");

            Transport t = session.getTransport("smtp");
            t.connect(remitente, contraseña);
            t.sendMessage(message, message.getAllRecipients());
            t.close();
        } catch (Exception e) {
        }
    }

    public static void enviarEmailContraseña(Usuario u) {

        String subject = "Recuperación de contraseña de Task4all";
        String text = "<p>Esta es tu contraseña:</p>"
                + "<li>Contraseña: " + u.getContrasena() + "</li> "
                + "<p><a href=\"http://localhost:8080" + FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/login.do\">Acceder a Task4all</a></p>"
                + "<p>No responder a este mensaje.<p/>";

        sendEmail(u.getEmail(), subject, text);
    }

    public static void enviarConfirmacionRegistro(Usuario u) {

        String subject = "Bienvenido a Task4all";
        String text;
        
        if(u.getVerificado().equals('0')) {
            text = "<p>Hola <i>" + u.getUsuario() + "</i>, bienvenido a Task4all. Disfruta tu estancia!</p>"
                + "<p><font color='red'>AVISO</font>: No podrás crear proyectos hasta que verifiques tu cuenta. Para ello, pulse "
                + "<a href=http://localhost:8080" + FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/login.do?usuario=" + u.getId() + "&verificar=" + true + ">aquí</a>.</p>"
                + "<p>No responder a este mensaje.<p/>";
        } else {
            text = "<p>Hola <i>" + u.getUsuario() + "</i>, bienvenido a Task4all. Disfruta tu estancia!</p>"
                + "<p> Puedes iniciar sesón pulsando "
                + "<a href=http://localhost:8080" + FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/login.do>aquí</a>.</p>"
                + "<p>No responder a este mensaje.<p/>";
        }
        

        sendEmail(u.getEmail(), subject, text);
    }

    public static void enviarEmailVerificacion(Usuario u) {
        String subject = "Verificar cuenta de Task4all";
        String text = "Hola <i>" + u.getUsuario() + "</i>, tal como ha solicitado, le enviamos el email de verificación de nuevo. Por favor, <a href=http://localhost:8080" + FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/login.do?usuario=" + u.getId() + "&verificar=" + true + ">verifique su cuenta</a>"
                + "<p>No responder a este mensaje.<p/>";

        sendEmail(u.getEmail(), subject, text);
    }

    public static void enviarEmailDespedida(Usuario u) {
        String subject = "Despedida de Task4all";
        String text = "<p>Sentimos que haya decidido borrar su cuenta en Task4all. Esperamos que haya disfrutado su estancia y que vuelva algun día.</p>"
                    + "<p>Su nombre de usuario y email quedan libres para volver a usarse.</p>"
                    + "<a href=http://localhost:8080" + FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/index.do>Task4all</a>"
                    + "<p>No responder a este mensaje.<p/>";

        sendEmail(u.getEmail(), subject, text);
    }
    
    public static void enviarEmailInvitacion(Usuario uInvita, Usuario uInvitado, Proyecto proyecto) {

        String subject = "Invitación a un proyecto en Task4all";
        String text = "<p>Ha recibido una invitaci&oacute;n de " + uInvita.getUsuario() + " para unirse al proyecto <i>" + proyecto.getNombre() + "</i> en Task4all.</p>"
                + "<p>Puedes <a href=http://localhost:8080" + FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/login.do?email=" + uInvitado.getEmail() + "&proyectoId=" + proyecto.getId() + "&ok=" + true + ">unirte al proyecto</a>"
                + " o <a href=http://localhost:8080" + FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/login.do?email=" + uInvitado.getEmail() + "&proyectoId=" + proyecto.getId() + "&ok=" + false + "> rechazar la invitación</a>.</p>"
                + "<p>No responder a este mensaje.<p/>";

        sendEmail(uInvitado.getEmail(), subject, text);
    }
    
    public static void enviarEmailInvitacionTask4all(Usuario uInvita, String email, Proyecto proyecto) {

        String subject = "Invitación a un proyecto en Task4all";
        String text = "<p>Ha recibido una invitaci&oacute;n de " + uInvita.getUsuario() + " para unirse al proyecto <i>" + proyecto.getNombre()
                + "</i> en Task4all. Desafortunadamente no estás registrado por lo que no puedes unirte al proyecto. Regístrate para poder disfrutar de Task4all.</p>"
                + "<p><a href=http://localhost:8080" + FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/index.do >Task4all</a>"
                + "<p>No responder a este mensaje.<p/>";

        sendEmail(email, subject, text);
    }
}

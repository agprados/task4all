package task4all.bean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import task4all.ejb.ListaFacade;
import task4all.ejb.ProyectoFacade;
import task4all.ejb.TareaFacade;
import task4all.ejb.UsuarioFacade;
import task4all.ejb.UsuarioProyectoFacade;
import task4all.entity.Lista;
import task4all.entity.Proyecto;
import task4all.entity.Tarea;
import task4all.entity.Usuario;
import task4all.entity.UsuarioProyecto;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
@ManagedBean
@RequestScoped
public class ProyectoBean {

    @EJB
    private UsuarioProyectoFacade usuarioProyectoFacade;
    @EJB
    private ListaFacade listaFacade;
    @EJB
    private TareaFacade tareaFacade;
    @EJB
    private ProyectoFacade proyectoFacade;
    @EJB
    private UsuarioFacade usuarioFacade;

    @ManagedProperty(value = "#{usuarioBean}")
    private UsuarioBean usuarioBean;
    @ManagedProperty(value = "#{proyectosBean}")
    private ProyectosBean proyectosBean;

    private List<UsuarioProyecto> listaMiembrosRoles;
    private List<Lista> listas;
    private List<List<Tarea>> tareas;
    private String error;
    private String emailInvitacion;
    private String nombre;
    private Date fechaObjetivo;

    /**
     * Creates a new instance of ProyectoBean
     */
    public ProyectoBean() {
    }

    @PostConstruct
    public void init() {
        this.error = "";
        emailInvitacion = "";
        listaMiembrosRoles = this.usuarioProyectoFacade.findUsuarioProyectoByProyecto(usuarioBean.getProyectoSeleccionado().getId());
        ordenarListaMiembros();

        listas = this.listaFacade.findListasByProyecto(usuarioBean.getProyectoSeleccionado().getId());
        tareas = new ArrayList<>();
        for (int i = 0; i < listas.size(); i++) {
            tareas.add(this.tareaFacade.findTareasByLista(listas.get(i).getId()));
        }

        for (int i = 0; i < listaMiembrosRoles.size(); i++) {
            if (listaMiembrosRoles.get(i).getUsuarioUsuario().getUsuario().equals(this.usuarioBean.getUsuario().getUsuario())) {
                this.usuarioBean.setRolActual(listaMiembrosRoles.get(i).getRol());
                break;
            }
        }
    }

    private void ordenarListaMiembros() {
        Collections.sort(listaMiembrosRoles, (UsuarioProyecto o1, UsuarioProyecto o2) -> {
            switch (o1.getRol().toUpperCase()) {
                case "LÍDER":
                    if (o2.getRol().equalsIgnoreCase("LÍDER")) {
                        return 0;
                    }
                    if (o2.getRol().equalsIgnoreCase("MIEMBRO")) {
                        return -1;
                    }
                    if (o2.getRol().equalsIgnoreCase("INVITADO")) {
                        return -1;
                    }
                    break;
                case "MIEMBRO":
                    if (o2.getRol().equalsIgnoreCase("LÍDER")) {
                        return 1;
                    }
                    if (o2.getRol().equalsIgnoreCase("MIEMBRO")) {
                        return 0;
                    }
                    if (o2.getRol().equalsIgnoreCase("INVITADO")) {
                        return -1;
                    }
                    break;
                case "INVITADO":
                    if (o2.getRol().equalsIgnoreCase("LÍDER")) {
                        return 1;
                    }
                    if (o2.getRol().equalsIgnoreCase("MIEMBRO")) {
                        return 1;
                    }
                    if (o2.getRol().equalsIgnoreCase("INVITADO")) {
                        return 0;
                    }
                    break;
                default:
                    return 0;
            }
            return 0;
        });
    }

    public void setUsuarioBean(UsuarioBean usuarioBean) {
        this.usuarioBean = usuarioBean;
    }

    public List<UsuarioProyecto> getListaMiembrosRoles() {
        return listaMiembrosRoles;
    }

    public void setListaMiembrosRoles(List<UsuarioProyecto> listaMiembrosRoles) {
        this.listaMiembrosRoles = listaMiembrosRoles;
    }

    public List<Lista> getListas() {
        return listas;
    }

    public void setListas(List<Lista> listas) {
        this.listas = listas;
    }

    public List<List<Tarea>> getTareas() {
        return tareas;
    }

    public void setTareas(List<List<Tarea>> tareas) {
        this.tareas = tareas;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getEmailInvitacion() {
        return emailInvitacion;
    }

    public void setEmailInvitacion(String emailInvitacion) {
        this.emailInvitacion = emailInvitacion;
    }

    public void setProyectosBean(ProyectosBean proyectosBean) {
        this.proyectosBean = proyectosBean;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFechaObjetivo() {
        return fechaObjetivo;
    }

    public void setFechaObjetivo(Date fechaObjetivo) {
        this.fechaObjetivo = fechaObjetivo;
    }

    public String doEditar() {
        nombre = usuarioBean.getProyectoSeleccionado().getNombre();
        if (usuarioBean.getProyectoSeleccionado().getFechaobjetivo() != null) {
            fechaObjetivo = usuarioBean.getProyectoSeleccionado().getFechaobjetivo();
        }

        return "editarProyecto";
    }

    public String doGuardar() {
        if (nombre == null || nombre.isEmpty()) {
            error = "El nombre del proyecto tiene que tener al menos 1 caracter";
            return "editarProyecto";
        }

        if (fechaObjetivo != null && !fechaObjetivo.toString().isEmpty()) {
            usuarioBean.getProyectoSeleccionado().setFechaobjetivo(fechaObjetivo);
        } else {
            usuarioBean.getProyectoSeleccionado().setFechaobjetivo(null);
        }

        usuarioBean.getProyectoSeleccionado().setNombre(nombre);
        proyectoFacade.edit(usuarioBean.getProyectoSeleccionado());

        return "proyecto";
    }

    public String doEliminarUsuario(Usuario usuario) {
        if ((usuarioBean.getRolActual().equalsIgnoreCase("Líder") && (usuarioBean.getUsuario().getUsuario().equals(usuario.getUsuario())))) {
            error = "No se puede eliminar el líder del proyecto";
        } else if (!usuarioBean.getRolActual().equalsIgnoreCase("Líder") && (!usuarioBean.getUsuario().getUsuario().equals(usuario.getUsuario()))) {
            error = "No tienes permiso para borrar a ese usuario";
        } else {
            UsuarioProyecto up = this.usuarioProyectoFacade.findUsuarioProyectoByUsuarioAndProyecto(usuario.getUsuario(), usuarioBean.getProyectoSeleccionado().getId());

            if (up == null) {
                error = "El usuario que intenta borrar no se encuentra en el proyecto";
            } else {
                this.usuarioProyectoFacade.remove(up);
                listaMiembrosRoles.remove(up);

                if (usuarioBean.getUsuario().getUsuario().equals(usuario.getUsuario())) {
                    proyectosBean.getProyectosMiembro().remove(usuarioBean.getProyectoSeleccionado());
                    return "principal";
                }
            }
        }

        return "proyecto";
    }

    public String doPasarLiderazgo(UsuarioProyecto usuario) {
        boolean encontrado = false;
        int i = 0;
        while (!encontrado || i < listaMiembrosRoles.size()) {
            if (listaMiembrosRoles.get(i).getRol().equalsIgnoreCase("líder")) {
                UsuarioProyecto lider = listaMiembrosRoles.get(i);
                lider.setRol("miembro");
                usuarioProyectoFacade.edit(lider);
                encontrado = true;
            }
            i++;
        }

        usuario.setRol("líder");
        usuarioProyectoFacade.edit(usuario);
        
        usuarioBean.setRolActual("miembro");
        ordenarListaMiembros();

        return "proyecto";
    }

    public String doBorrarProyecto() {
        if (usuarioBean.getRolActual().equalsIgnoreCase("líder")) {
            Proyecto proyecto = this.proyectoFacade.find(usuarioBean.getProyectoSeleccionado().getId());
            proyectoFacade.remove(proyecto);
            proyectosBean.getProyectosLider().remove(usuarioBean.getProyectoSeleccionado());
        }

        return "principal";
    }

    public String doInvitar() {
        error = "";
        if (emailInvitacion == null || emailInvitacion.isEmpty()) {
            error = "El email no puede estar vacío";
            return "proyecto";
        }
        if (!emailInvitacion.contains("@") || !emailInvitacion.contains(".")) {
            error = "El email no es válido";
            return "proyecto";
        }

        Usuario u = this.usuarioFacade.findUsuarioByEmail(emailInvitacion);

        if (u == null) {
            error = "El usuario no se encuentra registrado en el sistema";
            emailInvitacion = "";
            return "proyecto";
        }
        if (usuarioProyectoFacade.findUsuarioProyectoByEmailAndProyecto(emailInvitacion, usuarioBean.getProyectoSeleccionado().getId()) != null) {
            error = "Ese usuario ya pertenece al proyecto";
            emailInvitacion = "";
            return "proyecto";
        }

        try {
            mandarEmailInvitacion(emailInvitacion, usuarioBean.getProyectoSeleccionado().getId());
            emailInvitacion = "";
        } catch (Exception e) {
            error = "Se ha producido un error al enviar la invitación";
            return "proyecto";
        }

        UsuarioProyecto up = new UsuarioProyecto();
        up.setProyectoId(usuarioBean.getProyectoSeleccionado());
        up.setUsuarioUsuario(u);
        up.setRol("INVITADO");
        this.usuarioProyectoFacade.create(up);

        usuarioBean.getProyectoSeleccionado().getUsuarioProyectoCollection().add(up);
        this.proyectoFacade.edit(usuarioBean.getProyectoSeleccionado());

        listaMiembrosRoles.add(up);

        emailInvitacion = "";

        return "proyecto";
    }

    private void mandarEmailInvitacion(String email, Integer proyectoId) throws Exception {

        String remitente = "task4all.noreply@gmail.com";
        String contraseña = "task4all4";

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
        message.setSubject("Invitación a proyecto en Task4all");
        message.setText(
                "<p>Ha recibido una invitaci&oacute;n para unirse a un proyecto en Task4all.</p>"
                + "<p><a href=http://localhost:8080" + FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/faces/login.xhtml?email=" + email + "&proyectoId=" + proyectoId + "&ok=" + true + ">Unirse al proyecto</a></p>"
                + "<p>No responder a este mensaje.<p/>",
                "ISO-8859-1",
                "html");

        Transport t = session.getTransport("smtp");
        t.connect(remitente, contraseña);
        t.sendMessage(message, message.getAllRecipients());
        t.close();
    }

}

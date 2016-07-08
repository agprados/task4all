package task4all.bean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.application.FacesMessage;
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
import static task4all.utils.Email.*;
import static task4all.utils.UtilsMix.*;

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
    @ManagedProperty(value = "#{fondoBean}")
    private FondoBean fondoBean;

    private List<UsuarioProyecto> listaMiembrosRoles;
    private List<Lista> listas;
    private List<List<Tarea>> tareas;
    private String invitacion;
    private String nombre;
    private String descripcion;
    private Date fechaObjetivo;

    /**
     * Creates a new instance of ProyectoBean
     */
    public ProyectoBean() {
    }

    @PostConstruct
    public void init() {
        invitacion = "";

        // Comprobación para que no entre cuando se selecciona una tarea en tareas.xhtml
        if (usuarioBean.getProyectoSeleccionado() != null) {
            listaMiembrosRoles = this.usuarioProyectoFacade.findUsuarioProyectoByProyecto(usuarioBean.getProyectoSeleccionado().getId());
            ordenarListaMiembros();

            listas = this.listaFacade.findListasByProyecto(usuarioBean.getProyectoSeleccionado().getId());
            tareas = new ArrayList<>();
            for (int i = 0; i < listas.size(); i++) {
                tareas.add(this.tareaFacade.findTareasByLista(listas.get(i).getId()));
            }

            for (int i = 0; i < listaMiembrosRoles.size(); i++) {
                if (listaMiembrosRoles.get(i).getUsuarioId().getUsuario().equals(this.usuarioBean.getUsuario().getUsuario())) {
                    this.usuarioBean.setRolActual(listaMiembrosRoles.get(i).getRol());
                    break;
                }
            }
        }
    }

    public void setUsuarioBean(UsuarioBean usuarioBean) {
        this.usuarioBean = usuarioBean;
    }

    public void setFondoBean(FondoBean fondoBean) {
        this.fondoBean = fondoBean;
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

    public String getInvitacion() {
        return invitacion;
    }

    public void setInvitacion(String invitacion) {
        this.invitacion = invitacion;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaObjetivo() {
        return fechaObjetivo;
    }

    public void setFechaObjetivo(Date fechaObjetivo) {
        this.fechaObjetivo = fechaObjetivo;
    }

    public String doEditar() {
        return "editarProyecto?faces-redirect=true";
    }

    public String doGuardar() {        
        if (nombre == null || nombre.trim().isEmpty()) {
            FacesContext.getCurrentInstance().addMessage("proyecto", new FacesMessage(FacesMessage.SEVERITY_ERROR, "El nombre del proyecto no puede estar vacío", ""));
            return "editarProyecto";
        }
        nombre = nombre.trim();
        descripcion = descripcion.trim();

        usuarioBean.getProyectoSeleccionado().setNombre(nombre);
        usuarioBean.getProyectoSeleccionado().setDescripcion(descripcion);
        
        if (fechaObjetivo != null && !fechaObjetivo.toString().isEmpty()) {
            usuarioBean.getProyectoSeleccionado().setFechaobjetivo(fechaObjetivo);
        } else {
            usuarioBean.getProyectoSeleccionado().setFechaobjetivo(null);
        }

        if (!fondoBean.getFondo().equals(usuarioBean.getProyectoSeleccionado().getFondoId())) {
            usuarioBean.getProyectoSeleccionado().setFondoId(fondoBean.getFondo());
        }
        
        proyectoFacade.edit(usuarioBean.getProyectoSeleccionado());

        return "proyecto?faces-redirect=true";
    }

    public String doEliminarUsuario(Usuario usuario) {
        if ((usuarioBean.getRolActual().equalsIgnoreCase("Líder") && (usuarioBean.getUsuario().getUsuario().equals(usuario.getUsuario())))) {
            FacesContext.getCurrentInstance().addMessage("proyecto", new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se puede eliminar el líder del proyecto", ""));
        } else if (!usuarioBean.getRolActual().equalsIgnoreCase("Líder") && (!usuarioBean.getUsuario().getUsuario().equals(usuario.getUsuario()))) {
            FacesContext.getCurrentInstance().addMessage("proyecto", new FacesMessage(FacesMessage.SEVERITY_ERROR, "No tienes permiso para borrar a ese usuario", ""));
        } else {
            UsuarioProyecto up = this.usuarioProyectoFacade.findUsuarioProyectoByUsuarioAndProyecto(usuario.getUsuario(), usuarioBean.getProyectoSeleccionado().getId());

            if (up == null) {
                FacesContext.getCurrentInstance().addMessage("proyecto", new FacesMessage(FacesMessage.SEVERITY_ERROR, "El usuario que intenta borrar no se encuentra en el proyecto", ""));
            } else {
                this.usuarioProyectoFacade.remove(up);
                listaMiembrosRoles.remove(up);

                if (usuarioBean.getUsuario().getUsuario().equals(usuario.getUsuario())) {
                    proyectosBean.getProyectosMiembro().remove(usuarioBean.getProyectoSeleccionado());
                    return "principal?faces-redirect=true";
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
        }

        return "principal?faces-redirect=true";
    }

    public String doInvitar() {
        if (invitacion == null || invitacion.trim().isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "El email o el nombre de usuario no puede estar vacío", ""));
            return "proyecto";
        }
        invitacion = invitacion.trim();
        
        Usuario u;
        if (isValidEmail(invitacion)) {
            u = this.usuarioFacade.findUsuarioByEmail(invitacion);
        } else {
            u = this.usuarioFacade.findUsuarioByUsuario(invitacion);
        }

        // Invitación a usuario no registrado
        if (u == null && isValidEmail(invitacion)) {  
            try {
                enviarEmailInvitacionTask4all(usuarioBean.getUsuario(), invitacion, usuarioBean.getProyectoSeleccionado());
                invitacion = "";
                return "proyecto";
            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Se ha producido un error al enviar la invitación", ""));
                return "proyecto";
            }
        }

        if (u == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "El usuario no se encuentra registrado en el sistema", ""));
            return "proyecto";
        }

        if (usuarioProyectoFacade.findUsuarioProyectoByEmailAndProyecto(u.getEmail(), usuarioBean.getProyectoSeleccionado().getId()) != null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ese usuario ya pertenece al proyecto", ""));
            invitacion = "";
            return "proyecto";
        }

        try {
            enviarEmailInvitacion(usuarioBean.getUsuario(), u, usuarioBean.getProyectoSeleccionado());
            invitacion = "";
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Se ha producido un error al enviar la invitación", ""));
            return "proyecto";
        }

        UsuarioProyecto up = new UsuarioProyecto();
        up.setProyectoId(usuarioBean.getProyectoSeleccionado());
        up.setUsuarioId(u);
        up.setRol("INVITADO");
        this.usuarioProyectoFacade.create(up);

        usuarioBean.getProyectoSeleccionado().getUsuarioProyectoCollection().add(up);
        this.proyectoFacade.edit(usuarioBean.getProyectoSeleccionado());

        listaMiembrosRoles.add(up);

        invitacion = "";

        return "proyecto";
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
    
}

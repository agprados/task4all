/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package task4all.bean;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import task4all.ejb.AdjuntoFacade;
import task4all.ejb.ListaFacade;
import task4all.ejb.TareaFacade;
import task4all.ejb.UsuarioProyectoFacade;
import task4all.ejb.UsuarioTareaFacade;
import task4all.entity.Adjunto;
import task4all.entity.Lista;
import task4all.entity.Proyecto;
import task4all.entity.Tarea;
import task4all.entity.Usuario;
import task4all.entity.UsuarioProyecto;
import task4all.entity.UsuarioTarea;

@ManagedBean
@RequestScoped
public class TareaBean {

    @EJB
    private TareaFacade tareaFacade;
    @EJB
    private ListaFacade listaFacade;
    @EJB
    private UsuarioTareaFacade usuarioTareaFacade;
    @EJB
    private UsuarioProyectoFacade usuarioProyectoFacade;
    @EJB
    private AdjuntoFacade adjuntoFacade;

    @ManagedProperty(value = "#{usuarioBean}")
    private UsuarioBean usuarioBean;
    @ManagedProperty(value = "#{proyectoBean}")
    private ProyectoBean proyectoBean;

    private String nombre;
    private String descripcion;
    private Date fechaObjetivo;
    private List<UsuarioProyecto> listaAsignados;
    private List<UsuarioProyecto> listaUsuariosSinAsignar;
    private List<Adjunto> listaAdjuntos;

    /**
     * Creates a new instance of TareasBean
     */
    public TareaBean() {
    }

    @PostConstruct
    public void init() {
        cargarListaAsignados();        
        cargarListaAdjuntos();
    }

    public void setUsuarioBean(UsuarioBean usuarioBean) {
        this.usuarioBean = usuarioBean;
    }

    public void setProyectoBean(ProyectoBean proyectoBean) {
        this.proyectoBean = proyectoBean;
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

    public List<UsuarioProyecto> getListaAsignados() {
        return listaAsignados;
    }

    public void setListaUsuariosAsignados(List<UsuarioProyecto> listaAsignados) {
        this.listaAsignados = listaAsignados;
    }

    public List<UsuarioProyecto> getListaUsuariosSinAsignar() {
        return listaUsuariosSinAsignar;
    }

    public void setListaUsuariosSinAsignar(List<UsuarioProyecto> listaUsuariosSinAsignar) {
        this.listaUsuariosSinAsignar = listaUsuariosSinAsignar;
    }

    public List<Adjunto> getListaAdjuntos() {
        return listaAdjuntos;
    }

    public void setListaAdjuntos(List<Adjunto> listaAdjuntos) {
        this.listaAdjuntos = listaAdjuntos;
    }

    public String doMostrar(Tarea t) {
        usuarioBean.setTareaSeleccionada(t);
        cargarListaAsignados();
        
        return "/tarea?faces-redirect=true";
    }

    public String doMostrar(Proyecto p, Lista l, Tarea t) {
        usuarioBean.setProyectoSeleccionado(p);
        usuarioBean.setListaSeleccionada(l);
        usuarioBean.setTareaSeleccionada(t);

        cargarListaAsignados();
        return "/tarea?faces-redirect=true";
    }

    public String doEditar() {
        return "editarTarea?faces-redirect=true";
    }

    public String doBorrar() {
        tareaFacade.remove(usuarioBean.getTareaSeleccionada());

        Iterator<List<Tarea>> it = proyectoBean.getTareas().iterator();
        List<Tarea> lista;
        while (it.hasNext()) {
            lista = it.next();
            if (lista.get(0).getListaId().equals(usuarioBean.getTareaSeleccionada().getListaId())) {
                lista.remove(usuarioBean.getTareaSeleccionada());
            }
        }

        return "proyecto?faces-redirect=true";
    }

    public String doGuardar() {
        if (nombre == null || nombre.trim().isEmpty()) {
            FacesContext.getCurrentInstance().addMessage("tarea", new FacesMessage(FacesMessage.SEVERITY_ERROR, "El nombre de la tarea no puede estar vacío", ""));
            return "editarTarea";
        }

        if (fechaObjetivo != null && !fechaObjetivo.toString().isEmpty()) {
            usuarioBean.getTareaSeleccionada().setFechaobjetivo(fechaObjetivo);
        } else {
            usuarioBean.getTareaSeleccionada().setFechaobjetivo(null);
        }

        nombre = nombre.trim();
        descripcion = descripcion.trim();
        usuarioBean.getTareaSeleccionada().setNombre(nombre);
        usuarioBean.getTareaSeleccionada().setDescripcion(descripcion);

        if (usuarioBean.getTareaSeleccionada().getId() == null) {
            usuarioBean.getTareaSeleccionada().setFechacreacion(new Date());
            tareaFacade.create(usuarioBean.getTareaSeleccionada());
            Integer clave = this.tareaFacade.findMaxTareaId();
            usuarioBean.getTareaSeleccionada().setId(clave);
            usuarioBean.getTareaSeleccionada().getListaId().getTareaCollection().add(usuarioBean.getTareaSeleccionada());
            listaFacade.edit(usuarioBean.getTareaSeleccionada().getListaId());
        } else {
            tareaFacade.edit(usuarioBean.getTareaSeleccionada());
        }
        
        cargarListaAsignados();
        return "tarea?faces-redirect=true";
    }

    public String doCrear(Lista lista) {
        usuarioBean.setTareaSeleccionada(new Tarea());
        usuarioBean.getTareaSeleccionada().setListaId(lista);
        usuarioBean.getTareaSeleccionada().setPrioridad(new BigInteger("1"));

        return "editarTarea?faces-redirect=true";
    }

    public String doAsignarUsuario(Usuario u) {
        if (this.usuarioTareaFacade.findUsuarioTareaByUsuarioAndTarea(u.getUsuario(), usuarioBean.getTareaSeleccionada().getId()) != null) {
            FacesContext.getCurrentInstance().addMessage("tarea", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ese usuario ya está asignado a la tarea", ""));
            return "tarea";
        }

        UsuarioTarea ut = new UsuarioTarea();
        ut.setTareaId(usuarioBean.getTareaSeleccionada());
        ut.setUsuarioId(u);
        this.usuarioTareaFacade.create(ut);

        usuarioBean.getTareaSeleccionada().getUsuarioTareaCollection().add(ut);
        this.tareaFacade.edit(usuarioBean.getTareaSeleccionada());

        cargarListaAsignados();

        return "tarea";
    }

    public String doEliminarUsuario(Usuario usuario) {
        UsuarioTarea ut = this.usuarioTareaFacade.findUsuarioTareaByUsuarioAndTarea(usuario.getUsuario(), usuarioBean.getTareaSeleccionada().getId());
        if (ut == null) {
            FacesContext.getCurrentInstance().addMessage("tarea", new FacesMessage(FacesMessage.SEVERITY_ERROR, "El usuario que intenta borrar no se encuentra asignado a la tarea", ""));
        } else {
            this.usuarioTareaFacade.remove(ut);

            cargarListaAsignados();
        }

        return "tarea";
    }

    public void cargarListaAsignados() {
        listaAsignados = new ArrayList<>();
        listaUsuariosSinAsignar = new ArrayList<>();
        if (usuarioBean.getTareaSeleccionada() != null) {
            List<UsuarioTarea> listaUsuariosAsignados = this.usuarioTareaFacade.findUsuarioTareaByTarea(usuarioBean.getTareaSeleccionada().getId());
            if (listaUsuariosAsignados.isEmpty()) {
                listaUsuariosSinAsignar = this.usuarioProyectoFacade.findUsuarioProyectoByProyecto(usuarioBean.getProyectoSeleccionado().getId());
            } else {
                int cont;
                boolean asignado;
                for (UsuarioProyecto up : this.usuarioProyectoFacade.findUsuarioProyectoByProyecto(usuarioBean.getProyectoSeleccionado().getId())) {
                    cont = 0;
                    asignado = false;
                    while (asignado == false && cont < listaUsuariosAsignados.size()) {
                        if (listaUsuariosAsignados.get(cont).getUsuarioId().equals(up.getUsuarioId())) {
                            listaAsignados.add(up);
                            asignado = true;
                        }
                        cont++;
                    }
                    if (asignado == false && !up.getRol().equalsIgnoreCase("invitado")) {
                        listaUsuariosSinAsignar.add(up);
                    }
                }
            }
        }
    }   
    
    public void cargarListaAdjuntos() {
        listaAdjuntos = new ArrayList<>();
        if(usuarioBean.getTareaSeleccionada() != null) {
            listaAdjuntos = this.adjuntoFacade.findAdjuntosByTarea(usuarioBean.getTareaSeleccionada().getId());
            this.tareaFacade.borrarCache();
        }
    }

}

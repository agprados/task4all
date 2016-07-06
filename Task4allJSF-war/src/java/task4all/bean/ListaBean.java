/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package task4all.bean;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import task4all.ejb.ListaFacade;
import task4all.ejb.ProyectoFacade;
import task4all.entity.Lista;

@ManagedBean
@RequestScoped
public class ListaBean {

    @EJB
    private ListaFacade listaFacade;
    @EJB
    private ProyectoFacade proyectoFacade;

    @ManagedProperty(value = "#{usuarioBean}")
    private UsuarioBean usuarioBean;
    @ManagedProperty(value = "#{proyectoBean}")
    private ProyectoBean proyectoBean;

    private String nombre;
    private String descripcion;

    /**
     * Creates a new instance of ListaBean
     */
    public ListaBean() {
    }

    @PostConstruct
    public void init() {
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

    public void setUsuarioBean(UsuarioBean usuarioBean) {
        this.usuarioBean = usuarioBean;
    }

    public void setProyectoBean(ProyectoBean proyectoBean) {
        this.proyectoBean = proyectoBean;
    }

    public String doCrearLista() {
        if (nombre == null || nombre.trim().isEmpty()) {
            FacesContext.getCurrentInstance().addMessage("lista", new FacesMessage(FacesMessage.SEVERITY_ERROR, "El nombre no puede estar vacío", ""));
            return "proyecto";
        }
        
        nombre = nombre.trim();
        Lista lista = new Lista();
        lista.setNombre(nombre);
        lista.setProyectoId(usuarioBean.getProyectoSeleccionado());
        listaFacade.create(lista);
        Integer clave = listaFacade.findMaxListaId();
        lista.setId(clave);

        usuarioBean.getProyectoSeleccionado().getListaCollection().add(lista);
        this.proyectoFacade.edit(usuarioBean.getProyectoSeleccionado());

        proyectoBean.setListas(this.listaFacade.findListasByProyecto(usuarioBean.getProyectoSeleccionado().getId()));
        proyectoBean.getTareas().add(new ArrayList<>(lista.getTareaCollection()));

        nombre = "";

        return "proyecto";
    }

    public String doGuardar() {        
        if (nombre == null || nombre.trim().isEmpty()) {
            FacesContext.getCurrentInstance().addMessage("lista", new FacesMessage(FacesMessage.SEVERITY_ERROR, "El nombre de la lista no puede estar vacío", ""));
            return "editarLista";
        }

        nombre = nombre.trim();
        descripcion = descripcion.trim();
        this.usuarioBean.getListaSeleccionada().setDescripcion(descripcion);
        this.usuarioBean.getListaSeleccionada().setNombre(nombre);
        this.listaFacade.edit(this.usuarioBean.getListaSeleccionada());

        proyectoBean.setListas(this.listaFacade.findListasByProyecto(usuarioBean.getProyectoSeleccionado().getId()));

        nombre = "";

        return "proyecto?faces-redirect=true";
    }

    public String doEditarLista(Lista lista) {
        usuarioBean.setListaSeleccionada(lista);

        return "editarLista?faces-redirect=true";
    }

    public String doBorrar(Lista lista) {
        this.listaFacade.remove(lista);
        proyectoBean.getListas().remove(lista);
        proyectoBean.getTareas().remove((List) lista.getTareaCollection());

        return "proyecto";
    }
}

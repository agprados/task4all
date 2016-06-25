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
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
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
    private String error;

    /**
     * Creates a new instance of ListaBean
     */
    public ListaBean() {
    }
    
    @PostConstruct
    public void init() {
        error = "";
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getError() {
        return error;
    }

    public void setError(String errorLista) {
        this.error = errorLista;
    }

    public void setUsuarioBean(UsuarioBean usuarioBean) {
        this.usuarioBean = usuarioBean;
    }   

    public void setProyectoBean(ProyectoBean proyectoBean) {
        this.proyectoBean = proyectoBean;
    }

    public String doCrearLista() {
        if (nombre != null && !nombre.isEmpty()) {
            Lista lista = new Lista();
            lista.setNombre(nombre);
            lista.setProyectoId(usuarioBean.getProyectoSeleccionado());
            listaFacade.create(lista);
            Integer clave = listaFacade.findMaxListaId();
            lista.setId(clave);
            
            usuarioBean.getProyectoSeleccionado().getListaCollection().add(lista);
            this.proyectoFacade.edit(usuarioBean.getProyectoSeleccionado());
            
            proyectoBean.getListas().add(lista);
            proyectoBean.getTareas().add(new ArrayList<>(lista.getTareaCollection()));
            
            nombre = "";
            
            return "proyecto";
        } else {
            error = "El nombre de la lista no puede estar vacío";
            return "proyecto";
        }
    }

    public String doGuardar() {
        if(nombre == null || nombre.isEmpty()) {
            error = "El nombre de la lista no puede estar vacío";
            return "editarLista";
        }
        
        this.usuarioBean.getListaSeleccionada().setNombre(nombre);
        this.listaFacade.edit(this.usuarioBean.getListaSeleccionada());
        
        proyectoBean.setListas(this.listaFacade.findListasByProyecto(usuarioBean.getProyectoSeleccionado().getId()));
        
        return "proyecto";
    }

    public String doEditarLista(Lista lista) {
        usuarioBean.setListaSeleccionada(lista);
        nombre = lista.getNombre();

        return "editarLista";
    }
    
    public String doBorrar(Lista lista) {
        this.listaFacade.remove(lista);        
        proyectoBean.getListas().remove(lista);
        proyectoBean.getTareas().remove((List)lista.getTareaCollection());
        
        return "proyecto";
    }
}

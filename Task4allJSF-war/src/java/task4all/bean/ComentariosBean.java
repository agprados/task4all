/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package task4all.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import task4all.ejb.ProyectoFacade;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import task4all.ejb.ActividadFacade;
import task4all.ejb.ComentarioFacade;
import task4all.ejb.UsuarioFacade;
import task4all.entity.Actividad;
import task4all.entity.Comentario;
import task4all.entity.Proyecto;
import task4all.entity.Usuario;

@ManagedBean
@RequestScoped
public class ComentariosBean {
    @EJB
    private ProyectoFacade proyectoFacade;
    @EJB
    private ComentarioFacade comentarioFacade;
    @EJB
    private ActividadFacade actividadFacade;
    @EJB
    private UsuarioFacade usuarioFacade;
    
    @ManagedProperty(value="#{usuarioBean}")
    private UsuarioBean usuarioBean;
    @ManagedProperty(value="#{proyectoBean}")
    private ProyectoBean proyectoBean;
        
    private List<Comentario> listaComentarios;
    private Proyecto proyectoSeleccionado;
    private String contenido;
    private Usuario usuarioLogueado;
    private String error;
      
    public ComentariosBean() {
    }
    
    @PostConstruct
    public void init() { 
        this.error = "";
        usuarioLogueado = usuarioFacade.findUsuarioByUsuario(this.usuarioBean.getUsuario().getUsuario());
        proyectoSeleccionado = usuarioBean.getProyectoSeleccionado();
        listaComentarios =  comentarioFacade.findComentariosByProyecto(proyectoSeleccionado.getId());
    }

    public List<Comentario> getListaComentarios() {
        return listaComentarios;
    }

    public void setListaComentarios(List<Comentario> listaComentarios) {
        this.listaComentarios = listaComentarios;
    }

    public Proyecto getProyectoSeleccionado() {
        return proyectoSeleccionado;
    }

    public void setProyectoSeleccionado(Proyecto proyectoSeleccionado) {
        this.proyectoSeleccionado = proyectoSeleccionado;
    }

    public String getComentario() {
        return contenido;
    }

    public void setComentario(String comentario) {
        this.contenido = comentario;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public UsuarioBean getUsuarioBean() {
        return usuarioBean;
    }

    public void setUsuarioBean(UsuarioBean usuarioBean) {
        this.usuarioBean = usuarioBean;
    }

    public ProyectoBean getProyectoBean() {
        return proyectoBean;
    }

    public void setProyectoBean(ProyectoBean proyectoBean) {
        this.proyectoBean = proyectoBean;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
    
    public String doCrear() {
        if(this.contenido.isEmpty()) {
            error = "El comentario no puede estar vacío";
            return "proyecto";
        }
        
        int id;
        try{
            id=comentarioFacade.findMaxComentarioId();
        }catch (NullPointerException e){
            id=1;
        }
        Comentario comentario = new Comentario(id+1);                                                     
        comentario.setContenido(this.contenido);
        comentario.setFecha(new Date());
        comentario.setProyectoId(proyectoSeleccionado);
        comentario.setUsuarioUsuario(usuarioLogueado);
        Actividad a = crearActividad(usuarioLogueado, proyectoSeleccionado);
        comentario.setActividadId(a);
        this.comentarioFacade.create(comentario);
                
        proyectoSeleccionado.getComentarioCollection().add(comentario);
        this.proyectoFacade.edit(proyectoSeleccionado);
        
        if(usuarioLogueado.getComentarioCollection() == null) {
            usuarioLogueado.setComentarioCollection(new ArrayList<>());
            usuarioLogueado.getComentarioCollection().add(comentario);
        }
        this.usuarioFacade.edit(usuarioLogueado);
        a.setComentario(comentario);
        this.actividadFacade.edit(a);
        listaComentarios.add(comentario);
        this.contenido="";
        return "proyecto";
    }
    
    public String doBorrar(Comentario c){
        this.comentarioFacade.remove(c);
        listaComentarios.remove(c);
        return "proyecto";
    }
    
    private Actividad crearActividad(Usuario u, Proyecto p) {
        Actividad actividad = new Actividad();
        
        actividad.setDescripcion("Ha comentado");
        actividad.setFecha(new Date());
        actividad.setProyectoId(p);
        actividad.setUsuarioUsuario(u);
        
        this.actividadFacade.create(actividad);
        int clave = this.actividadFacade.findMaxActividadId();
        actividad.setId(clave);
        
        p.getActividadCollection().add(actividad);
        this.proyectoFacade.edit(p);
        
        if(u.getActividadCollection() == null) {
            u.setActividadCollection(new ArrayList<>());  
            u.getActividadCollection().add(actividad);
        }        
        this.usuarioFacade.edit(u);
        
        return actividad;
    }
}
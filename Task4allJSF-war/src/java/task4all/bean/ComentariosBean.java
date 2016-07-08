/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package task4all.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
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
        
    private LinkedList<Comentario> listaComentarios;
    private Proyecto proyectoSeleccionado;
    private String contenido;
    private Usuario usuarioLogueado;
      
    public ComentariosBean() {
    }
    
    @PostConstruct
    public void init() { 
        usuarioLogueado = usuarioFacade.findUsuarioByUsuario(this.usuarioBean.getUsuario().getUsuario());
        proyectoSeleccionado = usuarioBean.getProyectoSeleccionado();
        
        // Comprobación para que no entre cuando se accede a proyectos.xhtml sin iniciar sesión
        if(proyectoSeleccionado != null) {
            listaComentarios =  comentarioFacade.findComentariosByProyecto(proyectoSeleccionado.getId());
            adaptarContenidoComentarios();
        }
    }

    public List<Comentario> getListaComentarios() {
        return listaComentarios;
    }

    public void setListaComentarios(LinkedList<Comentario> listaComentarios) {
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

    public void setUsuarioBean(UsuarioBean usuarioBean) {
        this.usuarioBean = usuarioBean;
    }
    
    public String doCrear() {     
        if(this.contenido.trim().isEmpty()) {
            return "proyecto";
        }        
       
        Comentario comentario = new Comentario();
        
        comentario.setContenido(this.contenido);
        comentario.setFecha(new Date());
        comentario.setProyectoId(proyectoSeleccionado);
        comentario.setUsuarioId(usuarioLogueado);
        Actividad a = crearActividad(usuarioLogueado, proyectoSeleccionado);
        comentario.setActividadId(a);
        this.comentarioFacade.create(comentario);
        int clave = this.comentarioFacade.findMaxComentarioId();
        comentario.setId(clave);
                
        proyectoSeleccionado.getComentarioCollection().add(comentario);
        this.proyectoFacade.edit(proyectoSeleccionado);
        
        if(usuarioLogueado.getComentarioCollection() == null) {
            usuarioLogueado.setComentarioCollection(new ArrayList<>());
            usuarioLogueado.getComentarioCollection().add(comentario);
        }
        this.usuarioFacade.edit(usuarioLogueado);
        a.setComentario(comentario);
        this.actividadFacade.edit(a);
        listaComentarios.addFirst(comentario);
        this.contenido="";
        return "proyecto";
    }
    
    public String doBorrar(Comentario c){        
        this.comentarioFacade.remove(c);
        listaComentarios.remove(c);
        
        this.actividadFacade.remove(c.getActividadId());
        return "proyecto";
    }
    
    private Actividad crearActividad(Usuario u, Proyecto p) {
        Actividad actividad = new Actividad();
        
        actividad.setDescripcion("Ha comentado");
        actividad.setFecha(new Date());
        actividad.setProyectoId(p);
        actividad.setUsuarioId(u);
        
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
    
    public String overflow() {
        if (listaComentarios.size() > 5) return "height: 520px; overflow: auto;";
        else return "";
    }
    
    public void adaptarContenidoComentarios() {
        for(int i = 0; i<listaComentarios.size(); i++) {
            (listaComentarios.get(i).getContenido()).replaceAll(System.getProperty("line.separator"), "<br/>");
        }
    }
}

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
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import task4all.ejb.FondoFacade;
import task4all.ejb.ProyectoFacade;
import task4all.ejb.UsuarioProyectoFacade;
import task4all.entity.Fondo;
import task4all.entity.Proyecto;
import task4all.entity.UsuarioProyecto;

@ManagedBean
@RequestScoped
public class ProyectosBean {

    @EJB
    private UsuarioProyectoFacade usuarioProyectoFacade;
    @EJB
    private ProyectoFacade proyectoFacade;
    @EJB
    private FondoFacade fondoFacade;

    @ManagedProperty(value = "#{usuarioBean}")
    private UsuarioBean usuarioBean;

    private List<Proyecto> proyectosLider;
    private List<Proyecto> proyectosMiembro;
    private List<Proyecto> proyectosInvitado;
    private String nombre;
    private String error;

    /**
     * Creates a new instance of PoyectoBean
     */
    public ProyectosBean() {
    }

    @PostConstruct
    public void init() {
        error = "";
        List<UsuarioProyecto> listaProyectosUsuario = this.usuarioProyectoFacade.findUsuarioProyectoByUsuario(usuarioBean.getUsuario().getUsuario());

        this.proyectosLider = new ArrayList<>();
        this.proyectosMiembro = new ArrayList<>();
        this.proyectosInvitado = new ArrayList<>();

        for (int i = 0; i < listaProyectosUsuario.size(); i++) {
            if (listaProyectosUsuario.get(i).getRol().equalsIgnoreCase("Miembro")) {
                proyectosMiembro.add(this.proyectoFacade.find(listaProyectosUsuario.get(i).getProyectoId().getId()));
            } else if (listaProyectosUsuario.get(i).getRol().equalsIgnoreCase("Líder")) {
                proyectosLider.add(this.proyectoFacade.find(listaProyectosUsuario.get(i).getProyectoId().getId()));
            } else if (listaProyectosUsuario.get(i).getRol().equalsIgnoreCase("Invitado")) {
                proyectosInvitado.add(this.proyectoFacade.find(listaProyectosUsuario.get(i).getProyectoId().getId()));
            }
        }
    }

    public List<Proyecto> getProyectosLider() {
        return proyectosLider;
    }

    public void setProyectosLider(List<Proyecto> proyectosLider) {
        this.proyectosLider = proyectosLider;
    }

    public List<Proyecto> getProyectosMiembro() {
        return proyectosMiembro;
    }

    public void setProyectosMiembro(List<Proyecto> proyectosMiembro) {
        this.proyectosMiembro = proyectosMiembro;
    }

    public List<Proyecto> getProyectosInvitado() {
        return proyectosInvitado;
    }

    public void setProyectosInvitado(List<Proyecto> proyectosInvitado) {
        this.proyectosInvitado = proyectosInvitado;
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

    public void setError(String errorProyecto) {
        this.error = errorProyecto;
    }

    public UsuarioBean getUsuarioBean() {
        return usuarioBean;
    }

    public void setUsuarioBean(UsuarioBean usuarioBean) {
        this.usuarioBean = usuarioBean;
    }

    public String doCrearProyecto() {
        if (nombre != null && !nombre.isEmpty()) {
            Proyecto proyecto = new Proyecto();
            proyecto.setNombre(nombre);
            proyecto.setFechacreacion(new Date());

            Fondo fondo = this.fondoFacade.findFondoByNombre("fondo_default.png");                   
            proyecto.setFondoId(fondo);
            
            proyectoFacade.create(proyecto);
            int claveProyecto = proyectoFacade.findMaxProyectoId();
            proyecto.setId(claveProyecto);

            UsuarioProyecto up = new UsuarioProyecto();
            up.setRol("líder");
            up.setProyectoId(proyecto);
            up.setUsuarioId(this.usuarioBean.getUsuario());
            usuarioProyectoFacade.create(up);

            if (proyecto.getUsuarioProyectoCollection() == null) {
                proyecto.setUsuarioProyectoCollection(new ArrayList<>());
            }
            proyecto.getUsuarioProyectoCollection().add(up);
            this.proyectoFacade.edit(proyecto);

            usuarioBean.setProyectoSeleccionado(proyecto);

            return "proyecto?faces-redirect=true";
        } else {
            this.error = "El nombre no puede estar vacío";
            return "principal";
        }
    }

    public String doVerProyecto(Proyecto p) {
        this.usuarioBean.setProyectoSeleccionado(p);

        return "proyecto?faces-redirect=true";
    }

    public String doAceptarInvitacion(Proyecto p) {
        UsuarioProyecto up = this.usuarioProyectoFacade.findUsuarioProyectoByEmailAndProyecto(usuarioBean.getUsuario().getEmail(), p.getId());

        up.setRol("Miembro");
        this.usuarioProyectoFacade.edit(up);

        proyectosInvitado.remove(p);
        proyectosMiembro.add(p);

        return "principal";
    }

    public String doRechazarInvitacion(Proyecto p) {
        UsuarioProyecto up = this.usuarioProyectoFacade.findUsuarioProyectoByEmailAndProyecto(usuarioBean.getUsuario().getEmail(), p.getId());

        this.usuarioProyectoFacade.remove(up);
        proyectosInvitado.remove(p);

        return "principal";
    }
}

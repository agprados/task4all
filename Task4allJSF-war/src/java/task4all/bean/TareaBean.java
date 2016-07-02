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
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import task4all.ejb.ListaFacade;
import task4all.ejb.TareaFacade;
import task4all.ejb.UsuarioProyectoFacade;
import task4all.ejb.UsuarioTareaFacade;
import task4all.entity.Lista;
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

    @ManagedProperty(value = "#{usuarioBean}")
    private UsuarioBean usuarioBean;
    @ManagedProperty(value = "#{proyectoBean}")
    private ProyectoBean proyectoBean;

    private String error;
    private String errorAsignado;
    private String nombre;
    private Date fechaObjetivo;
    private List<UsuarioProyecto> listaAsignados;
    private List<UsuarioProyecto> listaUsuariosSinAsignar;

    /**
     * Creates a new instance of TareasBean
     */
    public TareaBean() {
    }

    @PostConstruct
    public void init() {
        error = "";
        errorAsignado = "";
        cargarLista();
    }

    public void setUsuarioBean(UsuarioBean usuarioBean) {
        this.usuarioBean = usuarioBean;
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

    public String getErrorAsignado() {
        return errorAsignado;
    }

    public void setErrorAsignado(String errorAsignado) {
        this.errorAsignado = errorAsignado;
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

    public String doMostrar(Tarea t) {
        usuarioBean.setTareaSeleccionada(t);
        cargarLista();
        return "tarea?faces-redirect=true";
    }

    public String doEditar() {
        nombre = usuarioBean.getTareaSeleccionada().getNombre();
        if (usuarioBean.getTareaSeleccionada().getFechaobjetivo() != null) {
            fechaObjetivo = usuarioBean.getTareaSeleccionada().getFechaobjetivo();
        }

        return "editarTarea";
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
        if (nombre == null || nombre.isEmpty()) {
            error = "El nombre de la tarea no puede estar vacío";
            return "editarTarea";
        }

        if (fechaObjetivo != null && !fechaObjetivo.toString().isEmpty()) {
            usuarioBean.getTareaSeleccionada().setFechaobjetivo(fechaObjetivo);
        } else {
            usuarioBean.getTareaSeleccionada().setFechaobjetivo(null);
        }

        usuarioBean.getTareaSeleccionada().setNombre(nombre);

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
        cargarLista();
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
            errorAsignado = "Ese usuario ya está asignado a la tarea";
            return "tarea";
        }

        UsuarioTarea ut = new UsuarioTarea();
        ut.setTareaId(usuarioBean.getTareaSeleccionada());
        ut.setUsuarioId(u);
        this.usuarioTareaFacade.create(ut);

        usuarioBean.getTareaSeleccionada().getUsuarioTareaCollection().add(ut);
        this.tareaFacade.edit(usuarioBean.getTareaSeleccionada());
        
        cargarLista();

        return "tarea";
    }

    public String doEliminarUsuario(Usuario usuario) {
        UsuarioTarea ut = this.usuarioTareaFacade.findUsuarioTareaByUsuarioAndTarea(usuario.getUsuario(), usuarioBean.getTareaSeleccionada().getId());
        if (ut == null) {
            errorAsignado = "El usuario que intenta borrar no se encuentra asignado a la tarea";
        } else {
            this.usuarioTareaFacade.remove(ut);

            cargarLista();
        }

        return "tarea";
    }

    public void cargarLista() {
        listaAsignados = new ArrayList<>();
        listaUsuariosSinAsignar = new ArrayList<>();
        if (usuarioBean.getTareaSeleccionada() != null) {
            System.out.println("hay tarea sel");
            List<UsuarioTarea> listaUsuariosAsignados = this.usuarioTareaFacade.findUsuarioTareaByTarea(usuarioBean.getTareaSeleccionada().getId());
            if (listaUsuariosAsignados.isEmpty()) {
                System.out.println("sin usuarios");
                listaUsuariosSinAsignar = this.usuarioProyectoFacade.findUsuarioProyectoByProyecto(usuarioBean.getProyectoSeleccionado().getId());
            } else {
                System.out.println("usuarios");
                int cont;
                boolean asignado;
                for (UsuarioProyecto up : this.usuarioProyectoFacade.findUsuarioProyectoByProyecto(usuarioBean.getProyectoSeleccionado().getId())) {
                    cont = 0;
                    asignado = false;
                    while (asignado == false && cont < listaUsuariosAsignados.size()) {
                        if (listaUsuariosAsignados.get(cont).getUsuarioId().equals(up.getUsuarioId())) {
                            System.out.println("usuario asignado");
                            listaAsignados.add(up);
                            asignado = true;
                        }
                        cont++;
                    }
                    if (asignado == false && !up.getRol().equalsIgnoreCase("invitado")) {
                        System.out.println("usuario no asignado");
                        listaUsuariosSinAsignar.add(up);
                    }
                }
            }
        }

    }
}

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
import javax.faces.bean.SessionScoped;
import task4all.ejb.ListaFacade;
import task4all.ejb.ProyectoFacade;
import task4all.ejb.TareaFacade;
import task4all.entity.Lista;
import task4all.entity.Proyecto;
import task4all.entity.Tarea;

@ManagedBean
@SessionScoped
public class BusquedaBean {

    @EJB
    private ProyectoFacade proyectoFacade;
    @EJB
    private ListaFacade listaFacade;
    @EJB
    private TareaFacade tareaFacade;

    private List<Proyecto> resultadosProyectos;
    private List<Lista> resultadosListas;
    private List<Tarea> resultadosTareas;
    private String cadenaABuscar;
    private String cadenaBuscada;

    /**
     * Creates a new instance of BusquedaBean
     */
    public BusquedaBean() {
    }

    @PostConstruct
    public void init() {
        resultadosProyectos = new ArrayList<>();
        resultadosListas = new ArrayList<>();
        resultadosTareas = new ArrayList<>();
    }

    public List<Proyecto> getResultadosProyectos() {
        return resultadosProyectos;
    }

    public void setResultadosProyectos(List<Proyecto> resultadosProyectos) {
        this.resultadosProyectos = resultadosProyectos;
    }

    public List<Lista> getResultadosListas() {
        return resultadosListas;
    }

    public void setResultadosListas(List<Lista> resultadosListas) {
        this.resultadosListas = resultadosListas;
    }

    public List<Tarea> getResultadosTareas() {
        return resultadosTareas;
    }

    public void setResultadosTareas(List<Tarea> resultadosTareas) {
        this.resultadosTareas = resultadosTareas;
    }

    public String getCadenaABuscar() {
        return cadenaABuscar;
    }

    public void setCadenaABuscar(String cadenaABuscar) {
        this.cadenaABuscar = cadenaABuscar;
    }

    public String getCadenaBuscada() {
        return cadenaBuscada;
    }

    public void setCadenaBuscada(String cadenaBuscada) {
        this.cadenaBuscada = cadenaBuscada;
    }

    public String doBuscar() {
        if (cadenaABuscar != null && !cadenaABuscar.isEmpty()) {
            resultadosProyectos = proyectoFacade.findProyectosByNombreLike(cadenaABuscar);
            resultadosListas = listaFacade.findListasByNombreLike(cadenaABuscar);
            resultadosTareas = tareaFacade.findTareasByNombreLike(cadenaABuscar);
        }
        
        cadenaBuscada = cadenaABuscar;
        cadenaABuscar = "";        

        return "/busqueda";
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package task4all.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import task4all.ejb.FondoFacade;
import task4all.entity.Fondo;

@ManagedBean
@ViewScoped
public class FondoBean implements Serializable {
    
    @EJB
    private FondoFacade fondoFacade;

    private List<Fondo> fondos;
    private Fondo fondo;
    private int indice;

    public FondoBean() {
    }

    @PostConstruct
    public void init() {
        fondos = new ArrayList<>();
        fondos = this.fondoFacade.findFondoByOscuro('0');
        fondos.addAll(this.fondoFacade.findFondoByOscuro('1'));
    }

    public List<Fondo> getFondos() {
        return fondos;
    }

     public void setFondos(List<Fondo> fondos) {
        this.fondos = fondos;
    }
    
    public Fondo getFondo() {
        return fondo;
    }

    public void setFondo(Fondo fondo) {
        this.fondo = fondo;
    }

    public void siguiente() {
        indice = (indice + 1) % fondos.size();
        fondo = fondos.get(indice);
    }

    public void anterior() {
        if (indice == 0) {
            indice = fondos.size() - 1;
        } else {
            indice = (indice - 1) % fondos.size();
        }
        fondo = fondos.get(indice);
    }
}

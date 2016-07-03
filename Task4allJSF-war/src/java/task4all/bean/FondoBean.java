/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package task4all.bean;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class FondoBean {

    private List<String> fondos;
    private String fondo;
    private int indice;

    public FondoBean() {
    }

    @PostConstruct
    public void init() {
        fondos = new ArrayList<>();
        for (int i = 0; i <= 7; i++) {
            fondos.add("fondo_claro_" + i + ".png");
        }
        for (int i = 1; i <= 4; i++) {
            fondos.add("fondo_oscuro_" + i + ".png");
        }
        fondo = fondos.get(indice);
    }

    public List<String> getFondos() {
        return fondos;
    }

    public String getFondo() {
        return fondo;
    }

    public void setFondo(String fondo) {
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

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
import javax.faces.bean.RequestScoped;

@ManagedBean
@RequestScoped
public class FondoBean {
    
    private List<String> images;

    public FondoBean() {
    }   
     
    @PostConstruct
    public void init() {
        images = new ArrayList<>();
        for (int i = 0; i <= 11; i++) {
            images.add("fondo_" + i + ".png");
        }
    }
 
    public List<String> getImages() {
        return images;
    }
}

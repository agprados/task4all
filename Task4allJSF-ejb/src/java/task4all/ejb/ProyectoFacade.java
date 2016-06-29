/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package task4all.ejb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import task4all.entity.Proyecto;


@Stateless
public class ProyectoFacade extends AbstractFacade<Proyecto> {

    @PersistenceContext(unitName = "Task4allJSF-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProyectoFacade() {
        super(Proyecto.class);
    }
    
    public Integer findMaxProyectoId () {
        Query q;
        
        q = em.createQuery("select max(p.id) from Proyecto p");
        return (Integer)q.getSingleResult();
    }
}

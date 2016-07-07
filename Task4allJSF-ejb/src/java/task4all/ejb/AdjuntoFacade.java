/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package task4all.ejb;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import task4all.entity.Adjunto;


@Stateless
public class AdjuntoFacade extends AbstractFacade<Adjunto> {

    @PersistenceContext(unitName = "Task4allJSF-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AdjuntoFacade() {
        super(Adjunto.class);
    }
    
    public Integer findMaxAdjuntoId () {
        Query q;
        
        q = em.createQuery("SELECT MAX(a.id) FROM Adjunto a");
        return (Integer)q.getSingleResult();
    }
    
    public List<Adjunto> findAdjuntosByTarea (Integer tarea) {
        Query q;
        
        q = em.createQuery("SELECT a FROM Adjunto a WHERE a.tareaId.id = :tarea");
        q.setParameter("tarea", tarea);
        
        return q.getResultList();
    }
    
}

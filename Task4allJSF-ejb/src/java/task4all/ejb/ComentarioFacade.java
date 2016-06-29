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
import task4all.entity.Comentario;


@Stateless
public class ComentarioFacade extends AbstractFacade<Comentario> {

    @PersistenceContext(unitName = "Task4allJSF-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ComentarioFacade() {
        super(Comentario.class);
    }
    
    public List<Comentario> findComentariosByProyecto(Integer proyecto) {
        Query q;
        
        q = em.createNamedQuery("Comentario.findByProyecto");
        q.setParameter("id", proyecto);
        
        return q.getResultList();
    }
    
     public Integer findMaxComentarioId () {
        Query q;
        
        q = em.createQuery("select max(c.id) from Comentario c");
        return (Integer)q.getSingleResult();
    }
}

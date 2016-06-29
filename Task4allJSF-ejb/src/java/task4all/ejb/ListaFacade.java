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
import task4all.entity.Lista;


@Stateless
public class ListaFacade extends AbstractFacade<Lista> {

    @PersistenceContext(unitName = "Task4allJSF-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ListaFacade() {
        super(Lista.class);
    }
    
    public List<Lista> findListasByProyecto (Integer proyecto) {
        Query q;
        
        q = em.createNamedQuery("Lista.findByProyecto");
        q.setParameter("id", proyecto);
        return q.getResultList();
    }
    
    public Integer findMaxListaId () {
        Query q;
        
        q = em.createQuery("select max(l.id) from Lista l");
        return (Integer)q.getSingleResult();
    }
}

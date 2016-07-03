/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package task4all.ejb;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import task4all.entity.Fondo;


@Stateless
public class FondoFacade extends AbstractFacade<Fondo> {

    @PersistenceContext(unitName = "Task4allJSF-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FondoFacade() {
        super(Fondo.class);
    }
    
    public Integer findMaxFondoId () {
        Query q;
        
        q = em.createQuery("select max(f.id) from Fondo f");
        return (Integer)q.getSingleResult();
    }
    
    public Fondo findFondoByNombre(String nombre) {
        Query q;
        
        q = em.createNamedQuery("Fondo.findByNombre").setParameter("nombre", nombre);
        
        Fondo f;
        try {
            f = (Fondo) q.getSingleResult();
        } catch (NoResultException e) {
            f = null;
        }
        return f; 
    }
    
    public List<Fondo> findFondoByOscuro(Character c){
        Query q;
        
        q = em.createNamedQuery("Fondo.findByOscuro").setParameter("oscuro", c);
        
        return q.getResultList();
    }
}

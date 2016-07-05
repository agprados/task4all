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
import task4all.entity.Actividad;


@Stateless
public class ActividadFacade extends AbstractFacade<Actividad> {

    @PersistenceContext(unitName = "Task4allJSF-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ActividadFacade() {
        super(Actividad.class);
    }
    
    public Integer findMaxActividadId() {
        Query q;
        
        q = em.createQuery("select max(a.id) from Actividad a");
        return (Integer)q.getSingleResult();
    }
    
    public void deleteActividadesByUsuario(int id) {
        Query q;
        
        q = em.createQuery("DELETE FROM Actividad a WHERE a.usuarioId.id = :usuario");
        q.setParameter("usuario", id);
        q.executeUpdate();
    }
    
}

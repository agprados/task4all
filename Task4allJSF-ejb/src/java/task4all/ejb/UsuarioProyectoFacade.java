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
import task4all.entity.UsuarioProyecto;


@Stateless
public class UsuarioProyectoFacade extends AbstractFacade<UsuarioProyecto> {

    @PersistenceContext(unitName = "Task4allJSF-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsuarioProyectoFacade() {
        super(UsuarioProyecto.class);
    }
    
    public List<UsuarioProyecto> findUsuarioProyectoByUsuario (String usuario) {
        Query q;
        
        q = em.createNamedQuery("UsuarioProyecto.findByUsuario");
        q.setParameter("u", usuario);
        return q.getResultList();
    }
    
    public List<UsuarioProyecto> findUsuarioProyectoByProyecto (Integer proyecto) {
        Query q;
        
        q = em.createNamedQuery("UsuarioProyecto.findByProyecto");
        q.setParameter("id", proyecto);
        return q.getResultList();
    }
    
    public UsuarioProyecto findUsuarioProyectoByEmailAndProyecto(String email, Integer proyecto) {
        Query q;
        
        q = em.createNamedQuery("UsuarioProyecto.findByEmailAndProyecto");
        q.setParameter("email", email);
        q.setParameter("id", proyecto);
        
        UsuarioProyecto up;
        try{
            up = (UsuarioProyecto)q.getSingleResult();
        }catch(NoResultException e) {
            up = null;
        }
        return up;
    }
    
    public UsuarioProyecto findUsuarioProyectoByUsuarioAndProyecto (String u, Integer id) {
        Query q;
        
        q = em.createNamedQuery("UsuarioProyecto.findByUsuarioAndProyecto");
        q.setParameter("u", u);
        q.setParameter("id",id);
        
        UsuarioProyecto up;
        try{
            up = (UsuarioProyecto)q.getSingleResult();
        }catch(NoResultException e) {
            up = null;
        }
        return up;
    }
    
}

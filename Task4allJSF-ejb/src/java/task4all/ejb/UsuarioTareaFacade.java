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
import task4all.entity.Tarea;
import task4all.entity.Usuario;
import task4all.entity.UsuarioTarea;


@Stateless
public class UsuarioTareaFacade extends AbstractFacade<UsuarioTarea> {

    @PersistenceContext(unitName = "Task4allJSF-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsuarioTareaFacade() {
        super(UsuarioTarea.class);
    }
    
    public List<UsuarioTarea> findUsuarioTareabyUsuarioUsuario(Usuario usuarioUsuario) {
       Query q;
      
       q = em.createNamedQuery("UsuarioTarea.findByUsuarioUsuario");
       q.setParameter("usuarioUsuario", usuarioUsuario);
       
       List<UsuarioTarea> l;
       try {
           l = (List<UsuarioTarea>)q.getResultList();
       } catch(NoResultException e) {
           l = null;
       }
       return l;
    }
    
    public List<UsuarioTarea> findUsuarioTareaByTarea(Integer tarea) {
        Query q;
        
        q = em.createNamedQuery("UsuarioTarea.findByTarea");
        q.setParameter("id", tarea);
        return q.getResultList();
    }
    
    public UsuarioTarea findUsuarioTareaByUsuarioAndTarea(String u, Integer id) {
        Query q;
        
        q = em.createNamedQuery("UsuarioTarea.findByTareaAndUsuario");
        q.setParameter("u", u);
        q.setParameter("id",id);
        
        UsuarioTarea up;
        try{
            up = (UsuarioTarea)q.getSingleResult();
        }catch(NoResultException e) {
            up = null;
        }
        return up;
    }
    
    public List<Tarea> findTareasAsignadasByUsuario(Integer id) {
        Query q;
        
        q = em.createNamedQuery("UsuarioTarea.findTareasAsignadasByUsuario");
        q.setParameter("id",id);        
        
        return q.getResultList();
    }
    
}

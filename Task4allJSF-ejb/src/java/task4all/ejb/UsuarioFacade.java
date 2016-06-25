/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package task4all.ejb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import task4all.entity.Usuario;

/**
 *
 * @author aidag
 */
@Stateless
public class UsuarioFacade extends AbstractFacade<Usuario> {

    @PersistenceContext(unitName = "Task4allJSF-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsuarioFacade() {
        super(Usuario.class);
    }
    
    public Usuario findUsuarioByUsuarioAndContrasena(String usuario, String contrasena) {
       Query q;
      
       q = em.createNamedQuery("Usuario.findByUsuarioAndContrasena");
       q.setParameter("usuario", usuario);
       q.setParameter("contrasena", contrasena);
       
       Usuario u;
       try {
           u = (Usuario)q.getSingleResult();
       } catch(NoResultException e) {
           u = null;
       }
       return u;
    }  
    
    public Usuario findUsuarioByEmailAndContrasena(String email, String contrasena) {
       Query q;
      
       q = em.createNamedQuery("Usuario.findByEmailAndContrasena");
       q.setParameter("email", email);
       q.setParameter("contrasena", contrasena);
       
       Usuario u;
       try {
           u = (Usuario)q.getSingleResult();
       } catch(NoResultException e) {
           u = null;
       }
       return u;
    }
    
    public Usuario findUsuarioByUsuario(String usuario) {
       Query q;
      
       q = em.createNamedQuery("Usuario.findByUsuario");
       q.setParameter("usuario", usuario);
       
       Usuario u;
       try {
           u = (Usuario)q.getSingleResult();
       } catch(NoResultException e) {
           u = null;
       }
       return u;
    }
    
    public Usuario findUsuarioByEmail(String email) {
       Query q;
      
       q = em.createNamedQuery("Usuario.findByEmail");
       q.setParameter("email", email);
       
       Usuario u;
       try {
           u = (Usuario)q.getSingleResult();
       } catch(NoResultException e) {
           u = null;
       }
       return u;
    }
    
    public Usuario findUsuarioByUsuarioOrEmail(String usuario, String email) {
       Query q;
      
       q = em.createNamedQuery("Usuario.findByUsuarioOrEmail");
       q.setParameter("usuario", usuario);
       q.setParameter("email", email);
       
       Usuario u;
       try {
           u = (Usuario)q.getSingleResult();
       } catch(NoResultException e) {
           u = null;
       }
       return u;
    }
    
}

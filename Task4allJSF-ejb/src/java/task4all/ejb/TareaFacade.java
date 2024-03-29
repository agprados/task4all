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
import task4all.entity.Tarea;
import task4all.entity.Usuario;


@Stateless
public class TareaFacade extends AbstractFacade<Tarea> {

    @PersistenceContext(unitName = "Task4allJSF-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TareaFacade() {
        super(Tarea.class);
    }
    
    public List<Tarea> findTareasByLista (Integer lista) {
        Query q;
        
        q = em.createNamedQuery("Tarea.findByLista");
        q.setParameter("id", lista);
        return q.getResultList();
    }
    
    public List<Tarea> findTareasByUsuario(Usuario usuario) {
        Query q;
        
        q = em.createNamedQuery("UsuarioTarea.findByUsuarioUsuario").setParameter("usuarioUsuario", usuario);
        return q.getResultList();
    }
    
    public Integer findMaxTareaId () {
        Query q;
        
        q = em.createQuery("select max(t.id) from Tarea t");
        return (Integer)q.getSingleResult();
    }
    
    public List<Tarea> findTareasByUsuarioAndNombreLike(Integer usuario, String nombre) {
        Query q;
        
        q = em.createQuery("SELECT t FROM Tarea t, Proyecto p, UsuarioProyecto up WHERE t.listaId.proyectoId.id = p.id AND p.id = up.proyectoId.id AND UPPER(t.nombre) LIKE UPPER(:nombre) AND up.usuarioId.id = :usuario");
        
        q.setParameter("nombre", "%" + nombre + "%");
        q.setParameter("usuario", usuario);
        
        return q.getResultList();
    }
    
    public void borrarCache() {
        em.getEntityManagerFactory().getCache().evict(Tarea.class);
    }
}
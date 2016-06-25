/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package task4all.ejb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import task4all.entity.Adjunto;

/**
 *
 * @author aidag
 */
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
    
}

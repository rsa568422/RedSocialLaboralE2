/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package redsociallaborale2.ejb;

import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import redsociallaborale2.jpa.Solicitud;

/**
 *
 * @author NetBeans
 */
@Stateless
public class SolicitudFacade extends AbstractFacade<Solicitud> {

    @PersistenceContext(unitName = "RedSocialLaboralE2-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SolicitudFacade() {
        super(Solicitud.class);
    }
    
    // Autora: Inmaculada Sanchez
    public List<Solicitud> findByEmisor(BigInteger usuario) {
        Query q = em.createNamedQuery("Solicitud.findByEmisor");
        q.setParameter("emisor", usuario);
        return q.getResultList(); 
    }
    
    // Autora: Inmaculada Sanchez
    public Solicitud findByEmisorAndReceptor (BigInteger emisor, BigInteger receptor) {
        Query q = em.createNamedQuery("Solicitud.findByEmisorAndReceptor");
        q.setParameter("emisor", emisor);
        q.setParameter("receptor", receptor);
        List<Solicitud> ls = q.getResultList();
        return ls.isEmpty() ? null : ls.get(0);
    }
    
    // Autora: Inmaculada Sanchez
    public List<Solicitud> findByReceptor(BigInteger usuario) {
        Query q = em.createNamedQuery("Solicitud.findByReceptor");
        q.setParameter("receptor", usuario);
        return q.getResultList(); 
    }
}

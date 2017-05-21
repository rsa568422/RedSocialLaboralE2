/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package redsociallaborale2.ejb;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import redsociallaborale2.jpa.Mensaje;
import redsociallaborale2.jpa.Usuario;

/**
 *
 * @author NetBeans
 */
@Stateless
public class MensajeFacade extends AbstractFacade<Mensaje> {

    @PersistenceContext(unitName = "RedSocialLaboralE2-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MensajeFacade() {
        super(Mensaje.class);
    }
    
    // author: Antonio Joaquín Luque
    public List<Mensaje> findByEmisor(Usuario emisor) {
        Query q = em.createNamedQuery("Mensaje.findByEmisor");
        q.setParameter("emisor", emisor);
        return q.getResultList();
    }
    
    // author: Antonio Joaquín Luque
    public List<Mensaje> findByReceptor(Usuario receptor) {
        Query q = em.createNamedQuery("Mensaje.findByReceptor");
        q.setParameter("receptor", receptor);
        return q.getResultList();
    }
    
    // author: Antonio Joaquín Luque
    public List<Mensaje> findByVistoAndReceptor(char visto, Usuario receptor) {
        Query q = em.createNamedQuery("Mensaje.findByVistoAndReceptor");
        q.setParameter("visto", visto);
        q.setParameter("receptor", receptor);
        return q.getResultList();
    }
}

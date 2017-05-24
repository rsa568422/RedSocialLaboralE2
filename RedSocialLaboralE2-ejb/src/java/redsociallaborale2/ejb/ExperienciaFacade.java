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
import redsociallaborale2.jpa.Experiencia;

/**
 *
 * @author NetBeans
 */
@Stateless
public class ExperienciaFacade extends AbstractFacade<Experiencia> {

    @PersistenceContext(unitName = "RedSocialLaboralE2-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ExperienciaFacade() {
        super(Experiencia.class);
    }
    
    // author: Antonio Joaquin Luque
    public List<Experiencia> findByIdUsuario(BigInteger id) {
        Query q = em.createNamedQuery("Experiencia.findByIdUsuario");
        q.setParameter("id", id);
        return q.getResultList();
    }
}

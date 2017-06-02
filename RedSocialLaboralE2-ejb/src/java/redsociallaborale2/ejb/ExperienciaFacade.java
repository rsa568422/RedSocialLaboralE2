/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package redsociallaborale2.ejb;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import redsociallaborale2.jpa.Estudios;
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
    
    // author: Roberto Benítez  
    
    public Experiencia findByFechasYUsuario(Date fechainicio, Date fechafin, BigInteger usuario) {
        
        Query q = em.createNamedQuery("Experiencia.findByFechasYUsuario");
        q.setParameter("fechainicio", fechainicio);
        q.setParameter("fechafin", fechafin);
        q.setParameter("usuario", usuario);
        List<Experiencia> list = q.getResultList();
        return list == null || list.size() < 1 ? null : list.get(0);
    }
}

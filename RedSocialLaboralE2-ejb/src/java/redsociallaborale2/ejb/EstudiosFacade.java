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
import redsociallaborale2.jpa.Usuario;

/**
 *
 * @author NetBeans
 */
@Stateless
public class EstudiosFacade extends AbstractFacade<Estudios> {

    @PersistenceContext(unitName = "RedSocialLaboralE2-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EstudiosFacade() {
        super(Estudios.class);
    }
    
    // author: Roberto Sanchez
    public List<Estudios> findByIdUsuario(BigInteger id) {
        Query q = em.createNamedQuery("Estudios.findByIdUsuario");
        q.setParameter("id", id);
        return q.getResultList();
    }
    
    // author: Roberto Benítez  
    
    public Estudios findByFechasYUsuario(Date fechainicio, Date fechafin, BigInteger usuario) {
        
        Query q = em.createNamedQuery("Estudios.findByFechasYUsuario");
        q.setParameter("fechainicio", fechainicio);
        q.setParameter("fechafin", fechafin);
        q.setParameter("usuario", usuario);
        List<Estudios> list = q.getResultList();
        return list == null || list.size() < 1 ? null : list.get(0);
    }
}

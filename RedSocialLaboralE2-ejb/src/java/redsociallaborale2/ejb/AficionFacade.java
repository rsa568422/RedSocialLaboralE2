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
import redsociallaborale2.jpa.Aficion;

/**
 *
 * @author NetBeans
 */
@Stateless
public class AficionFacade extends AbstractFacade<Aficion> {

    @PersistenceContext(unitName = "RedSocialLaboralE2-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AficionFacade() {
        super(Aficion.class);
    }
    
    // author: Roberto Sanchez
    public List<Aficion> findByIdUsuario(BigInteger id) {
        Query q = em.createNamedQuery("Aficion.findByIdUsuario");
        q.setParameter("id", id);
        return q.getResultList();
    }
    
    // author: Roberto Sanchez
    public Aficion findByIdUsuarioAndNombreAficion(BigInteger id, String nombre) {
        Query q = em.createNamedQuery("Aficion.findByIdUsuarioAndNombreAficion");
        q.setParameter("id", id);
        q.setParameter("nombre", nombre);
        List<Aficion> list = q.getResultList();
        return list == null || list.size() < 1 ? null : list.get(0);
    }
}

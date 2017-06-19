/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package redsociallaborale2.ejb;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import redsociallaborale2.jpa.Usuario;

/**
 *
 * @author NetBeans
 */
@Stateless
public class UsuarioFacade extends AbstractFacade<Usuario> {

    @PersistenceContext(unitName = "RedSocialLaboralE2-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsuarioFacade() {
        super(Usuario.class);
    }
    
    // author: Roberto Sanchez
    public Usuario findByEmail(String email) {
        Query q = em.createNamedQuery("Usuario.findByEmail");
        q.setParameter("email", email);
        List<Usuario> lu = q.getResultList();
        return lu.isEmpty() ? null : lu.get(0);
    }
    
    // Autora: Inmaculada Sanchez
    public List<Usuario> findBySimilarName(String nombre) {
        Query q = em.createNamedQuery("Usuario.findBySimilarName");
        q.setParameter("nombre", "%" + nombre + "%");
        List<Usuario> usu = (List<Usuario>) q.getResultList();
        return usu;
    }
    
    // Autor: Roberto Benitez
    public List<Usuario> busquedaEspecifica(String nombre, Integer[] marcados) {
        
        List<Usuario> usu;
        Query q;
        String consulta="SELECT u from Usuario u WHERE";
        
        
        for (Integer i: marcados)
        {
            if(i==1)
            {
                consulta+= " u.nombre LIKE :nombre OR";
            }
            if (i==2)
            {
                consulta+= " u.apellidos LIKE :nombre OR"; 
            }
            if (i==3)
            {
                consulta+= " u.email LIKE :nombre OR";
            }
            if (i==4)
            {
                consulta+= " u.twitter LIKE :nombre OR";
            }
            if (i==5)
            {
                consulta+= " u.web LIKE :nombre OR";
            }
        }
        /*if(marcados[0]==1){
            consulta+= " u.nombre LIKE :nombre OR";
        }
        if(marcados[1]==1){
           consulta+= " u.apellidos LIKE :nombre OR"; 
        }
        if(marcados[2]==1){
           consulta+= " u.email LIKE :nombre OR";     
        }
        if(marcados[3]==1){
           consulta+= " u.instagram LIKE :nombre OR"; 
        }
        if(marcados[4]==1){
           consulta+= " u.twitter LIKE :nombre OR";     
        }
        if(marcados[5]==1){
           consulta+= " u.web LIKE :nombre OR"; 
        }
*/
        
        //Acortar la consulta los ultimos 3 caracteres
        int cantidad= 3;       
        consulta = consulta.substring(0, consulta.length() - cantidad);
        
        q= em.createQuery(consulta)
                .setParameter("nombre","%" + nombre + "%");
        
        usu = (List<Usuario>) q.getResultList();
        return usu;
        
        
        
    }
}

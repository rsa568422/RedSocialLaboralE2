/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package redsociallaborale2.bean;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import redsociallaborale2.ejb.SolicitudFacade;
import redsociallaborale2.ejb.UsuarioFacade;
import redsociallaborale2.jpa.Usuario;

/**
 *
 * @author Inma
 */
@Named(value = "contactoBean")
@RequestScoped
public class ContactoBean {

    @EJB
    private UsuarioFacade usuarioFacade;

    @EJB
    private SolicitudFacade solicitudFacade;

    
    @Inject
    private UsuarioBean sesion;
    
    private List<Usuario> listaAmigos;
    private Usuario usu;
    
    /**
     * Creates a new instance of ContactoBean
     */
    public ContactoBean() {
    }
    
    public String goContactos ()
    {
        return "contactos.xhtml?faces-redirect=true";
    }
    
    public List<Usuario> muestraAmigos()
    {
        //Buscamos la lista de amigos
        listaAmigos = (List<Usuario>) sesion.usuario.getAmigos();

        return listaAmigos;
    }
}

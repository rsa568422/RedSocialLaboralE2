/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package redsociallaborale2.bean;

import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import redsociallaborale2.ejb.UsuarioFacade;

/**
 *
 * @author Roberto
 */
@Named(value = "signOutBean")
@RequestScoped
public class SignOutBean {
    
    @EJB
    private UsuarioFacade usuarioFacade;
    
    @Inject
    private UsuarioBean sesion;

    /**
     * Creates a new instance of SignOutBean
     */
    public SignOutBean() {
    }
    
    public String doSignOut() {
        usuarioFacade.remove(sesion.usuario);
        sesion.init();
        return "login.xhtml";
    }
}

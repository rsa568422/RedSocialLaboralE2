/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package redsociallaborale2.bean;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import redsociallaborale2.ejb.UsuarioFacade;

/**
 *
 * @author Roberto Sanchez
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
        if (sesion != null && sesion.usuario != null && usuarioFacade.find(sesion.usuario.getId()) != null) {
            usuarioFacade.remove(sesion.usuario);
            sesion.init();
        } else {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("error.xhtml");
            } catch (IOException ex) {
                Logger.getLogger(AficionBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return "login.xhtml";
    }
}

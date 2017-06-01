/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package redsociallaborale2.bean;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import redsociallaborale2.ejb.UsuarioFacade;
import redsociallaborale2.jpa.Usuario;

/**
 *
 * @author Roberto Sanchez
 */
@Named(value = "logInBean")
@RequestScoped
public class LogInBean {
    
    @EJB
    private UsuarioFacade usuarioFacade;
    
    @Inject
    UsuarioBean sesion;
    
    protected String email;
    protected String pass;

    /**
     * Creates a new instance of LogInBean
     */
    public LogInBean() {
    }
    
    @PostConstruct
    void init() {
        if (sesion != null) {
            email = "";
            pass = "";
            sesion.error = 0;
        } else {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("error.xhtml");
            } catch (IOException ex) {
                Logger.getLogger(AficionBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
    
    public String doLogIn() {
        String next = null;
        if (sesion != null && sesion.usuario != null) {
            sesion.error = email != null && !email.isEmpty() && pass != null && !pass.isEmpty() ? 0 : 1;
            if (sesion.error == 0) {
                Usuario u = usuarioFacade.findByEmail(email);
                if (u != null && u.getPass().equals(pass)) {
                    sesion.usuario = u;
                    next = sesion.doMain();
                } else {
                    sesion.error = 1;
                }
            }
        } else {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("error.xhtml");
            } catch (IOException ex) {
                Logger.getLogger(AficionBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return next;
    }
    
    public String doSignIn() {
        if (sesion != null && sesion.usuario != null) {
            sesion.init();
            sesion.seleccionado = Boolean.TRUE;
        } else {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("error.xhtml");
            } catch (IOException ex) {
                Logger.getLogger(AficionBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return "signin.xhtml";
    }
    
    public String doShowErrorMsg() {
        String str = "";
        if (sesion != null) {
            if (sesion.error != 0) {
                str = "Error: email o pass incorrectos";
            }
        } else {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("error.xhtml");
            } catch (IOException ex) {
                Logger.getLogger(AficionBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return str;
    }
}

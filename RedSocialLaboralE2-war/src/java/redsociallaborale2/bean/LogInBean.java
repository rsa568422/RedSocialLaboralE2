/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package redsociallaborale2.bean;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import redsociallaborale2.ejb.UsuarioFacade;
import redsociallaborale2.jpa.Usuario;

/**
 *
 * @author Roberto
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
        email = "";
        pass = "";
        sesion.error = 0;
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
        String next = "login.xhtml";
        int error;
        error = email != null && !email.isEmpty() ? 0 : 1; // <----- [1 3] Falta email
        error = pass != null && !pass.isEmpty() ? error : error + 2; // <------- [2 3] Falta pass
        if (error == 0) {
            Usuario u = usuarioFacade.findByEmail(email);
            if (u != null) {
                if (pass.equals(u.getPass())) {
                    sesion.usuario = u;
                    next = "main.xhtml";
                } else {
                    error = 5; // <--------------------------------------------- [5] No coincide pass
                }
            } else {
                error = 4; // <------------------------------------------------- [4] No se encuentra mail
            }
        }
        sesion.error = error;
        return next;
    }
    
    public String doSignIn() {
        sesion.init();
        return "signin.xhtml";
    }
    
    public String doShowErrorMsg() {
        String str;
        switch (sesion.error) {
            /*case 1: str = "Error: introduzca email"; break;
            case 2: str = "Error: introduzca pass"; break;
            case 3: str = "Error: introduzca email y pass"; break;*/
            case 4: // No separo los dos casos por seguridad
            case 5: str = "Error: email o pass incorrectos"; break;
            default: str = "";
        }
        return str;
    }
}

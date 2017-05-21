/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package redsociallaborale2.bean;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.math.BigInteger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import redsociallaborale2.ejb.UsuarioFacade;
import redsociallaborale2.jpa.Usuario;

/**
 *
 * @author Roberto Sanchez
 */
@Named(value = "usuarioBean")
@SessionScoped
public class UsuarioBean implements Serializable {
    
    @EJB
    private UsuarioFacade usuarioFacade;
    
    protected BigInteger id;
    protected String email;
    protected String pass;
    protected int error;

    /**
     * Creates a new instance of UsuarioBean
     */
    public UsuarioBean() {
    }
    
    @PostConstruct
    void init() {
        id = null;
        email = "";
        pass = "";
        error = 0;
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

    public int getError() {
        return error;
    }
    
    public void setError(int error) {
        this.error = error;
    }
    
    public String doLogIn() {
        error = 0;
        error = email != null && !email.isEmpty() ? error : error + 1; // <----- [1 3] Falta email
        error = pass != null && !pass.isEmpty() ? error : error + 2; // <------- [2 3] Falta pass
        if (error == 0) {
            Usuario u = usuarioFacade.findByEmail(email);
            if (u != null) {
                if (pass.equals(u.getPass())) {
                    id  = u.getId();
                } else {
                    error = 5; // <--------------------------------------------- [5] No coincide pass
                }
            } else {
                error = 4; // <------------------------------------------------- [4] No se encuentra mail
            }
        }
        return error == 0 ? "main.xhtml" : "login.xhtml";
    }
    
    public String doLogOut() {
        init();
        return "login.xhtml";
    }
    
    public String doSignIn() {
        init();
        return "signin.xhtml";
    }
    
    public String doShowErrorMsg() {
        String str;
        switch (error) {
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

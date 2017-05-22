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
 * @author Roberto Sanchez
 */
@Named(value = "signInBean")
@RequestScoped
public class SignInBean {
    
    @EJB
    private UsuarioFacade usuarioFacade;
    
    @Inject
    private UsuarioBean sesion;
    
    private String email;
    private String pass;
    private String rePass;
    private String nombre;
    private String apellidos;
    private String twitter;
    private String instagram;
    private String web;
    private String foto;

    /**
     * Creates a new instance of SignInBean
     */
    public SignInBean() {
    }
    
    @PostConstruct
    void init() {
        email = "";
        pass = "";
        rePass = "";
        nombre = "";
        apellidos = "";
        twitter = "";
        instagram = "";
        web = "";
        foto = "";
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
    public String getRePass() {
        return rePass;
    }

    public void setRePass(String rePass) {
        this.rePass = rePass;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getInstagram() {
        return instagram;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
    
    public String doSave() {
        String next = "signin.xhtml";
        int error;
        error = email != null && !email.isEmpty() ? 0 : 1; // <----------------- [ 1  3  5  7  9 11 13 15]
        error = pass != null && !pass.isEmpty() ? error : error + 2; // <------- [ 2  3  6  7 10 11 14 15]
        error = rePass != null && !rePass.isEmpty() ? error : error + 4; // <--- [ 4  5  6  7 12 13 14 15]
        error = nombre != null && !nombre.isEmpty() ? error : error + 8; // <--- [ 8  9 10 11 12 13 14 15]
        if (error == 0) {
            if (pass.equals(rePass)) {
                Usuario u = usuarioFacade.findByEmail(email);
                if (u == null) {
                    u = new Usuario();
                    u.setEmail(email);
                    u.setPass(pass);
                    u.setNombre(nombre);
                    u.setApellidos(apellidos);
                    u.setTwitter(twitter);
                    u.setInstagram(instagram);
                    u.setWeb(web);
                    u.setFoto(foto);
                    usuarioFacade.create(u);
                    next = "login.xhtml";
                } else {
                    error = 17; // <-------------------------------------------- [17]
                }
            } else {
                error = 16; // <------------------------------------------------ [16]
            }
        }
        sesion.error = error;
        return next;
    }
    
    public String doShowErrorMsg() {
        String str;
        switch (sesion.error) {
            case 1: str = "Error: introduzca email"; break;
            case 8: str = "Error: introduzca nombre"; break;
            case 3:
            case 5:
            case 6:
            case 7:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15: str = "Error: faltan campos obligatorios"; break;
            case 2:
            case 4:
            case 16: str = "Error: el pass no coincide"; break;
            case 17: str = "Error: email ya registrado"; break;
            default: str = "";
        }
        return str;
    }
    
    public boolean doShowErrorEmail() {
        return  sesion.error == 1  ||
                sesion.error == 3  ||
                sesion.error == 5  ||
                sesion.error == 7  ||
                sesion.error == 9  ||
                sesion.error == 11 ||
                sesion.error == 13 ||
                sesion.error == 15;
    }
    
    public boolean doShowErrorPass() {
        return sesion.error > 0 && sesion.error < 18;
    }
    
    public boolean doShowErrorNombre() {
        return  sesion.error == 8  ||
                sesion.error == 9  ||
                sesion.error == 10 ||
                sesion.error == 11 ||
                sesion.error == 12 ||
                sesion.error == 13 ||
                sesion.error == 14 ||
                sesion.error == 15;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package redsociallaborale2.bean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
        if (sesion != null && sesion.usuario != null) {
            email = "";
            pass = "";
            rePass = "";
            nombre = "";
            apellidos = "";
            twitter = "";
            instagram = "";
            web = "";
            foto = "";
            if (sesion.seleccionado != null && sesion.seleccionado instanceof Boolean && ((Boolean) sesion.seleccionado)) {
                sesion.error = 1111111;
            } else {
                sesion.error = errorDatosUsuario();
            }
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
        sesion.seleccionado = null;
        if (sesion != null && sesion.usuario != null) {
            sesion.error = errorDatosUsuario();
            if (errorValido(sesion.error)) {
                sesion.usuario.setEmail(email);
                sesion.usuario.setPass(pass);
                sesion.usuario.setNombre(nombre);
                sesion.usuario.setApellidos(apellidos);
                sesion.usuario.setTwitter(twitter);
                sesion.usuario.setInstagram(instagram);
                sesion.usuario.setWeb(web);
                sesion.usuario.setFoto(foto);
                usuarioFacade.create(sesion.usuario);
                next = "login.xhtml";
            } else {
                pass = "";
                rePass = "";
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
    
    private static int[] errorToList(int error) {
        int[] res = new int[7];
        int aux;
        for (int i = 6; i > 0; i--) {
            aux = error / 10;
            res[i] = (error % ((aux) * 10)) - 1;
            error = aux;
        }
        res[0] = error - 1;
        return res;
    }
    
    private static boolean errorValido(int error) {
        boolean test = false;
        int aux;
        int errorEmail = 0;
        int errorFoto;
        int errorTwitter;
        int errorNombre = 0;
        int errorPass = 0;
        int errorRePass = 0;
        int errorDiferentes = 0;
        for (int a = 0; a < 2; a++) {
            errorFoto = a;
            for (int b = 0; b < 2; b++) {
                errorTwitter = b;
                aux = errorEmail + 1;
                aux *= 10;
                aux += errorFoto + 1;
                aux *= 10;
                aux += errorTwitter + 1;
                aux *= 10;
                aux += errorNombre + 1;
                aux *= 10;
                aux += errorPass + 1;
                aux *= 10;
                aux += errorRePass + 1;
                aux *= 10;
                aux += errorDiferentes + 1;
                test |= error == aux;
            }
        }    
        return test;
    }
    
    private int errorDatosUsuario() {
        int errorEmail = sesion.errorEmail(email);
        int errorPass = pass != null && !pass.isEmpty() ? 0 : 1;
        int errorRePass = rePass != null && !rePass.isEmpty() ? 0 : 1;
        int errorDiferentes = errorPass == 0 && errorRePass == 0 && pass.equals(rePass) ? 0 : 1;
        int errorNombre = nombre != null && !nombre.isEmpty() ? 0 : 1;
        int errorTwitter = UsuarioBean.errorTwitter(twitter);
        int errorFoto = UsuarioBean.errorNombreFichero(foto);
        int error = errorEmail + 1;
        error *= 10;
        error += errorFoto + 1;
        error *= 10;
        error += errorTwitter + 1;
        error *= 10;
        error += errorNombre + 1;
        error *= 10;
        error += errorPass + 1;
        error *= 10;
        error += errorRePass + 1;
        error *= 10;
        error += errorDiferentes + 1;
        return error;
    }
    
    public List<String> doShowErrorMsg() {
        List<String> str = new ArrayList<>();
        if (sesion != null && sesion.usuario != null) {
            int[] errores = errorToList(sesion.error);
            if (errores[0] != 0) {
                str.add(UsuarioBean.errorEmailToString(errores[0]));
            }
            if (errores[1] > 1) {
                str.add(UsuarioBean.errorFotoToString(errores[1]));
            }
            if (errores[2] > 1) {
                str.add(UsuarioBean.errorTwitterToString(errores[2]));
            }
            if (errores[3] != 0) {
                str.add("Error: introduzca nombre");
            }
            if (errores[4] != 0 && errores[5] != 0) {
                str.add("Error: introduzca pass");
            } else if (errores[6] != 0) {
                str.add("Error: el pass no coincide");
            }
        }
        return str;
    }
    
    public boolean doShowErrorEmail() {
        int[] errores = errorToList(sesion.error);
        return errores[0] != 0;
    }
    
    public boolean doShowErrorFoto() {
        int[] errores = errorToList(sesion.error);
        return errores[1] > 1;
    }
    
    public boolean doShowErrorTwitter() {
        int[] errores = errorToList(sesion.error);
        return errores[2] > 1;
    }
    
    public boolean doShowErrorNombre() {
        int[] errores = errorToList(sesion.error);
        return errores[3] != 0;
    }
    
    public boolean doShowErrorPass() {
        int[] errores = errorToList(sesion.error);
        return errores[4] != 0 && errores[5] != 0 && errores[6] != 0;
    }
}

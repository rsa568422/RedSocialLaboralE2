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
@Named(value = "editarUsuarioBean")
@RequestScoped
public class EditarUsuarioBean {

    @EJB
    private UsuarioFacade usuarioFacade;
    
    @Inject
    UsuarioBean sesion;
    
    protected String email;
    protected String pass;
    protected String rePass;
    protected String nombre;
    protected String apellidos;
    protected String twitter;
    protected String instagram;
    protected String web;
    protected String foto;

    /**
     * Creates a new instance of EditarUsuarioBean
     */
    public EditarUsuarioBean() {
    }
    
    @PostConstruct
    void init() {
        if (sesion != null && sesion.usuario != null && sesion.seleccionado != null && sesion.seleccionado instanceof Usuario) {
            Usuario u = (Usuario) sesion.seleccionado;
            email = u.getEmail();
            pass = u.getPass();
            rePass = u.getPass();
            nombre = u.getNombre();
            apellidos = u.getApellidos();
            twitter = u.getTwitter();
            instagram = u.getInstagram();
            web = u.getWeb();
            foto = u.getFoto();
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
        String next = "editarPerfil.xhtml";
        if (sesion != null && sesion.usuario != null && sesion.seleccionado != null && sesion.seleccionado instanceof Usuario && sesion.usuario.equals(sesion.seleccionado)) {
            sesion.error = errorDatosUsuario();
            if (errorValido(sesion.error)) {
                Usuario u = (Usuario) sesion.seleccionado;
                u.setEmail(email);
                u.setPass(pass);
                u.setNombre(nombre);
                u.setApellidos(apellidos);
                u.setTwitter(twitter);
                u.setInstagram(instagram);
                u.setWeb(web);
                u.setFoto(foto);
                sesion.usuario = u;
                usuarioFacade.edit(sesion.usuario);
                next = "verPerfil.xhtml";
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
        for (int i = 7; i > 0; i++) {
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
        return true;
    }
    
    private int errorDatosUsuario() {
        int errorEmail = UsuarioBean.errorEmail(email);
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
    
    public String doShowErrorMsg() {
        String str;
        if (sesion != null) {
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
        } else {
            str = "";
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

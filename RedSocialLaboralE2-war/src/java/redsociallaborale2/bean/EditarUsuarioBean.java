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

/**
 *
 * @author Roberto
 */
@Named(value = "editarUsuarioBean")
@RequestScoped
public class EditarUsuarioBean {

    @EJB
    private UsuarioFacade usuarioFacade;
    
    @Inject
    UsuarioBean sesion;
    
    protected String rePass;

    /**
     * Creates a new instance of EditarUsuarioBean
     */
    public EditarUsuarioBean() {
    }
    
    @PostConstruct
    void init() {
        rePass = sesion.usuarioSeleccionado.getPass();
        sesion.error = 0;
    }

    public String getRePass() {
        return rePass;
    }

    public void setRePass(String rePass) {
        this.rePass = rePass;
    }
    
    public String doSave() {
        String next = "editarPerfil.xhtml";
        int error;
        error = sesion.usuarioSeleccionado.getEmail() != null && !sesion.usuarioSeleccionado.getEmail().isEmpty() ? 0 : 1; // <----------------- [ 1  3  5  7  9 11 13 15]
        error = sesion.usuarioSeleccionado.getPass() != null && !sesion.usuarioSeleccionado.getPass().isEmpty() ? error : error + 2; // <------- [ 2  3  6  7 10 11 14 15]
        error = rePass != null && !rePass.isEmpty() ? error : error + 4; // <------------------------------------------------------------------- [ 4  5  6  7 12 13 14 15]
        error = sesion.usuarioSeleccionado.getNombre() != null && !sesion.usuarioSeleccionado.getNombre().isEmpty() ? error : error + 8; // <--- [ 8  9 10 11 12 13 14 15]
        
        
        if (error == 0) {
            if (sesion.usuarioSeleccionado.getPass().equals(rePass)) {
                sesion.usuario = sesion.usuarioSeleccionado;
                usuarioFacade.edit(sesion.usuario);
                next = "verPerfil.xhtml";
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

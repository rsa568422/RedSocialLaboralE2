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
        String next = "verPerfil.xhtml";
        sesion.error = sesion.usuarioSeleccionado.getEmail() == null || sesion.usuarioSeleccionado.getEmail().isEmpty() ? 1 : 0;                            // error = [1 3 5 7]
        sesion.error = sesion.usuarioSeleccionado.getPass()== null || sesion.usuarioSeleccionado.getPass().isEmpty() ? sesion.error + 2 : sesion.error;     // error = [2 3 6 7]
        sesion.error = sesion.usuarioSeleccionado.getNombre()== null || sesion.usuarioSeleccionado.getNombre().isEmpty() ? sesion.error + 4 : sesion.error; // error = [4 5 6 7]
        if (sesion.error == 0) {
            sesion.usuario = sesion.usuarioSeleccionado;
            usuarioFacade.edit(sesion.usuario);
        } else {
            next = "editarPerfil.xhtml";
        }
        
        return next;
    }
    
    public String doShowErrorMsg() {
        String str;
        switch (sesion.error) {
            default: str = "";
        }
        return str;
    }
}

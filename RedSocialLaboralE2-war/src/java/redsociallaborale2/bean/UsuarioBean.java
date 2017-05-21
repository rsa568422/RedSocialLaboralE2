/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package redsociallaborale2.bean;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import redsociallaborale2.jpa.Usuario;

/**
 *
 * @author Roberto Sanchez
 */
@Named(value = "usuarioBean")
@SessionScoped
public class UsuarioBean implements Serializable {
    
    protected Usuario usuario;
    protected Usuario usuarioSeleccionado;
    protected int error;

    /**
     * Creates a new instance of UsuarioBean
     */
    public UsuarioBean() {
    }
    
    @PostConstruct
    void init() {
        usuario = new Usuario();
        usuarioSeleccionado = null;
        error = 0;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Usuario getUsuarioSeleccionado() {
        return usuarioSeleccionado;
    }

    public void setUsuarioSeleccionado(Usuario usuarioSeleccionado) {
        this.usuarioSeleccionado = usuarioSeleccionado;
    }

    public int getError() {
        return error;
    }
    
    public void setError(int error) {
        this.error = error;
    }
    
    public String doLogOut() {
        init();
        return "login.xhtml";
    }
    
    public String doSignOut() {
        return "confirmacion.xhtml";
    } 
    
    public String doVerPerfil() {
        usuarioSeleccionado = usuario;
        return "verPerfil.xhtml";
    }
    
    public String doEditarPerfil() {
        usuarioSeleccionado = usuario;
        return "editarPerfil.xhtml";
    }
    
    public String doMain() {
        usuarioSeleccionado = null;
        return "main.xhtml";
    }
}

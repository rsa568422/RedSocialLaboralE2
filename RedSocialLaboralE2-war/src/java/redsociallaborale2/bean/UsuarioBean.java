/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package redsociallaborale2.bean;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    protected Object seleccionado;
    protected int error;

    /**
     * Creates a new instance of UsuarioBean
     */
    public UsuarioBean() {
    }
    
    @PostConstruct
    void init() {
        usuario = new Usuario();
        seleccionado = null;
        error = 0;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Object getSeleccionado() {
        return seleccionado;
    }

    public void setSeleccionado(Object seleccionado) {
        this.seleccionado = seleccionado;
    }

    public int getError() {
        return error;
    }
    
    public void setError(int error) {
        this.error = error;
    }
    
    public String formatoFecha(Date f){
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String fecha = sdf.format(f);
        return fecha;
    }
    
    public String doLogOut() {
        init();
        return "login.xhtml";
    }
    
    public String doSignOut() {
        return "confirmacion.xhtml";
    } 
    
    public String doVerPerfil() {
        seleccionado = usuario;
        return "verPerfil.xhtml";
    }
    
    public String doEditarPerfil() {
        seleccionado = usuario;
        return "editarPerfil.xhtml";
    }
    
    public String doMain() {
        seleccionado = null;
        return "main.xhtml";
    }
}

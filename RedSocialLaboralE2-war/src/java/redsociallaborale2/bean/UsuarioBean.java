/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package redsociallaborale2.bean;

import java.io.File;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;
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
    
    private int errorNombreFichero(String fichero) {
        int err = 0;
        if (fichero != null && !fichero.isEmpty()) {
            if (!fichero.startsWith(".")) {
                if (!fichero.contains("..")) {
                    StringTokenizer tokens = new StringTokenizer(fichero, ".");
                    String nombre = "";
                    String extension = tokens.nextToken();
                    while (tokens.hasMoreTokens()) {
                        nombre += extension;
                        extension = "." + tokens.nextToken();
                    }
                    if (!nombre.isEmpty()) {
                        if (!extension.isEmpty()) {
                            boolean extensionOk = false;
                            extensionOk |= extension.equals(".png");
                            extensionOk |= extension.equals(".jpg");
                            extensionOk |= extension.equals(".gif");
                            extensionOk |= extension.equals(".bmp");
                            if (extensionOk) {
                                File prueba = new File("./");
                                List<String> nombres = new ArrayList<>();
                                for (File f : prueba.listFiles()) {
                                    String n = f.getName();
                                    nombres.add(n);
                                }
                                nombres.isEmpty();
                                if (!true) {
                                    // no existe ese fichero
                                    err = 7;
                                }
                            } else {
                                // extension no valida
                                err = 6;
                            }
                        } else {
                            // extension vacia
                            err = 5;
                        }
                    } else {
                        // nombre vacio
                        err = 4;
                    }
                } else {
                    // fichero contiene cadena ".."
                    err = 3;
                }
            } else {
                // fichero comienza por "."
                err = 2;
            }
        } else {
            // fichero vacio
            err = 1;
        }
        return err;
    }
    
    public String doShowFoto(Usuario usuario) {
        String str = "default.png";
        if (usuario != null) {
            String fichero = usuario.getFoto();
            if (errorNombreFichero(fichero) == 0) {
                str = fichero;
            }
        }
        return str;
    }
}

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
import java.util.Date;
import java.util.StringTokenizer;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
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
    
    protected static int errorNombreFichero(String fichero) {
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
                                String path = FacesContext.getCurrentInstance().getExternalContext().getRealPath(fichero);
                                if (path != null) {
                                    File prueba = new File(path);
                                    if (!prueba.exists()) {
                                        // no se puede abrir ese fichero
                                        err = 8;
                                    }
                                } else {
                                    // no se encuentra ese fichero
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
    
    public static String errorToString(int error) {
        String str;
        switch (error) {
            //error == 1 --> el campo foto del formulario esta vacio, pero no es un campo requerido
            //case 1: str = ""; break;
            case 2: str = "Error: formato incorrecto (comienza por \".\")"; break;
            case 3: str = "Error: formato incorrecto (contiene \"..\")"; break;
            case 4: str = "Error: formato incorrecto (falta nombre \".extension\")"; break;
            case 5: str = "Error: formato incorrecto (falta extension \"nombre.\")"; break;
            case 6: str = "Error: extension no reconocida (compatibles: png, jpg, gif, bmp)"; break;
            case 7: str = "Error: no se encuentra el fichero"; break;
            case 8: str = "Error: no se puede acceder al fichero"; break;
            default: str = "";
        }
        return str;
    }
    
    public static String doShowFoto(Usuario usuario) {
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

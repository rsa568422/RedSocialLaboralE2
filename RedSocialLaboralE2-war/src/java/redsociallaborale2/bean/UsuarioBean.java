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
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
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
    
    // Author: Antonio Joaquin Luque
    public static String formatoFecha(Date f){
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String fecha = sdf.format(f);
        return fecha;
    }
    
    public String doLogOut() {
        init();
        return "login.xhtml";
    }
    
    public String doSignOut() {
        return usuario != null ? "confirmacion.xhtml" : "error.xhtml";
    } 
    
    public String doVerPerfil() {
        seleccionado = usuario;
        return usuario != null ? "verPerfil.xhtml" : "error.xhtml";
    }
    
    public String doEditarPerfil() {
        seleccionado = usuario;
        return usuario != null ? "editarPerfil.xhtml" : "error.xhtml";
    }
    
    public String doMain() {
        seleccionado = null;
        return usuario != null ? "main.xhtml" : "error.xhtml";
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
    
    public static String errorFotoToString(int error) {
        String str;
        switch (error) {
            //error == 1 --> el campo foto del formulario esta vacio, pero no es un campo requerido
            //case 1: str = ""; break;
            case 2: str = "Error: foto comienza por \".\""; break;
            case 3: str = "Error: foto contiene \"..\""; break;
            case 4: str = "Error: foto sin nombre (\".extension\")"; break;
            case 5: str = "Error: foto sin extensión (\"nombre.\")"; break;
            case 6: str = "Error: foto con extensión no reconocida (compatibles: png, jpg, gif, bmp)"; break;
            case 7: str = "Error: no se encuentra el fichero con la foto"; break;
            case 8: str = "Error: no se puede acceder al fichero con la foto"; break;
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
    
    protected int errorEmail(String email) {
        int err = 0;
        if (email != null && !email.isEmpty()) {
            String str = " !#$%&'()*+,-./:;<=>?@[]^_`{|}~0123456789";
            if (!str.contains(String.valueOf(email.charAt(0)))) {
                if (email.contains("@")) {
                    if (!email.contains("@@")) {
                        StringTokenizer tokens = new StringTokenizer(email, "@");
                        if (tokens.hasMoreTokens()) {
                            String usr = tokens.nextToken();
                            if (usr != null && !usr.isEmpty()) {
                                if (tokens.hasMoreTokens()) {
                                    String dom = tokens.nextToken();
                                    if (dom != null && !dom.isEmpty()) {
                                        boolean especiales = false;
                                        str = " !#$%&'()*+,/:;<=>?@[]^`{|}~";
                                        for (int i = 0; i < str.length(); i++) {
                                            char[] aux = {str.charAt(i)};
                                            especiales |= usr.contains(new String(aux));
                                        }
                                        if (!especiales) {
                                            especiales = false;
                                            str = " !#$%&'()*+,-/:;<=>?@[]^_`{|}~";
                                            for (int i = 0; i < str.length(); i++) {
                                                char[] aux = {str.charAt(i)};
                                                especiales |= dom.contains(new String(aux));
                                            }
                                            if (!especiales) {
                                                if (!dom.startsWith(".")) {
                                                    if (!dom.contains("..")) {
                                                        tokens = new StringTokenizer(dom, ".");
                                                        str = tokens.nextToken();
                                                        if (str != null && !str.isEmpty() && tokens.hasMoreTokens()) {
                                                            while (tokens.hasMoreTokens()) {
                                                                str = tokens.nextToken();
                                                            }
                                                            if (str != null && !str.isEmpty()) {
                                                                boolean valido = false;
                                                                valido |= str.equals("com");
                                                                valido |= str.equals("es");
                                                                valido |= str.equals("ue");
                                                                valido |= str.equals("de");
                                                                valido |= str.equals("fr");
                                                                valido |= str.equals("it");
                                                                //...
                                                                if (valido) {
                                                                    Usuario u = usuarioFacade.findByEmail(email);
                                                                    if (u != null) {
                                                                        // email ya registrado
                                                                        err = 14;
                                                                    }
                                                                } else {
                                                                    // el ultimo campo del dominio no compatible
                                                                    err = 13;
                                                                }
                                                            } else {
                                                                // el ultimo campo del dominio vacio
                                                                err = 12;
                                                            }
                                                        } else {
                                                            // el primer campo del dominio vacio o unico elemento
                                                            err = 11;
                                                        }
                                                    } else {
                                                        // el campo dominio del email contiene ".."
                                                        err = 10;
                                                    }
                                                } else {
                                                    // el campo dominio del email comienza por "."
                                                    err = 9;
                                                }
                                            } else {
                                                // el campo dominio del email tiene caracter especial
                                                err = 8;
                                            }
                                        } else {
                                            // el campo usuario del email tiene caracter especial
                                            err = 7;
                                        }
                                    } else {
                                        // no hay campo dominio para el email
                                        err = 6;
                                    }
                                } else {
                                    // no hay campo dominio para el email
                                    err = 6;
                                }
                            } else {
                                // no hay campo usuario para el email
                                err = 5;
                            }
                        } else {
                            // no hay campo usuario para el email
                            err = 5;
                        }
                    } else {
                        // email no contiene "@@"
                        err = 4;
                    }
                } else {
                    // email no contiene "@"
                    err = 3;
                }
            } else {
                // email comienza por caracter especial
                err = 2;
            }
        } else {
            // email vacio
            err = 1;
        }
        return err;
    }
    
    public static String errorEmailToString(int error) {
        String str;
        switch (error) {
            case 1: str = "Error: email vacío"; break;
            case 2: str = "Error: email comienza por carácter especial"; break;
            case 3: str = "Error: email no contiene \"@\""; break;
            case 4: str = "Error: email contiene \"@@\""; break;
            case 5: str = "Error: email sin usuario (\".dominio\")"; break;
            case 6: str = "Error: email sin dominio (\"usuario.\")"; break;
            case 7: str = "Error: email con carácter especial en el usuario"; break;
            case 8: str = "Error: email con carácter especial en el dominio"; break;
            case 9: str = "Error: campo dominio del email comienza por \".\""; break;
            case 10: str = "Error: campo dominio del email contiene \"..\""; break;
            case 11: str = "Error: primer campo del dominio vacío o como único elemento"; break;
            case 12: str = "Error: ultimo campo del dominio vacío"; break;
            case 13: str = "Error: extensión del dominio no reconocida (compatibles: .com, .es, .ue, .de, .fr, .it)"; break;
            case 14: str = "Error: email ya registrado"; break;
            default: str = "";
        }
        return str;
    }
    
    protected static int errorTwitter(String twiter) {
        int err = 0;
        if (twiter != null && !twiter.isEmpty()) {
            if (twiter.startsWith("@")) {
                if (twiter.length() > 1) {
                    boolean especiales = false;
                    String t = twiter.substring(1);
                    String str = " !#$%&'()*+,-./:;<=>?@[]^_`{|}~";
                    for (int i = 0; i < str.length(); i++) {
                        char[] aux = {str.charAt(i)};
                        especiales |= t.contains(new String(aux));
                    }
                    if (especiales) {
                        // twiter contiene caracteres especiales
                        err = 4;
                    }
                } else {
                    // twiter solo contiene @
                    err = 3;
                }
            } else {
                // twiter no comienza por @
                err = 2;
            }
        } else {
            // twiter vacio
            err = 1;
        }
        return err;
    }
    
    public static String errorTwitterToString(int error) {
        String str;
        switch (error) {
            //error == 1 --> el campo twiter del formulario esta vacio, pero no es un campo requerido
            //case 1: str = ""; break;
            case 2: str = "Error: twiter no comienza por \"@\""; break;
            case 3: str = "Error: twiter sólo contiene \"@\""; break;
            case 4: str = "Error: twiter contiene carácteres especiales"; break;
            default: str = "";
        }
        return str;
    }
}

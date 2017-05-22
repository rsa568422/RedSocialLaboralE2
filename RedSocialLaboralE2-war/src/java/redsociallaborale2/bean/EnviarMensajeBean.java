/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package redsociallaborale2.bean;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import redsociallaborale2.ejb.MensajeFacade;
import redsociallaborale2.ejb.UsuarioFacade;
import redsociallaborale2.jpa.Mensaje;
import redsociallaborale2.jpa.Usuario;

/**
 *
 * @author anton
 */
@Named(value = "enviarMensajeBean")
@RequestScoped
public class EnviarMensajeBean {

    @EJB
    private MensajeFacade mensajeFacade;
    
    @Inject
    private UsuarioBean sesion;
    
    private List<Usuario> amigos;
    private Mensaje mensaje;
    private String contenidoSeleccionado;
    private Usuario amigoSeleccionado;
    
    /**
     * Creates a new instance of EnviarMensajeBean
     */
    public EnviarMensajeBean() {
    }
    
    @PostConstruct
    void init(){
        this.mensaje = new Mensaje();
        this.amigos = sesion.usuario.getAmigos();
    }
    
    public String goEnviarMensaje(){
        this.amigoSeleccionado = null;
        this.contenidoSeleccionado = "";
        return "enviarMensaje";
    }
    
    public String doEnvioMensaje(){
        this.mensaje.setMensaje(contenidoSeleccionado);
        this.mensaje.setReceptor(amigoSeleccionado);
        
        return "bandejaSalida";
    }
    
    public List<Usuario> getAmigos(){
        return this.amigos;
    }
    
    public String getContenidoSeleccionado(){
        return this.contenidoSeleccionado;
    }
    
    public Usuario getAmigoSeleccionado(){
        return this.amigoSeleccionado;
    }
    
}

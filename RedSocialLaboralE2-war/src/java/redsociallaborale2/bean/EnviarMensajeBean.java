/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package redsociallaborale2.bean;

import java.math.BigInteger;
import java.util.Date;
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
    private UsuarioFacade usuarioFacade;

    @EJB
    private MensajeFacade mensajeFacade;
    
    @Inject
    private UsuarioBean sesion;
    
    private List<Usuario> amigos;
    private Mensaje mensaje;
    private String contenidoSeleccionado;
    private Usuario amigoSeleccionado;
    private BigInteger idAmigo;
    
    /**
     * Creates a new instance of EnviarMensajeBean
     */
    public EnviarMensajeBean() {
    }
    
    @PostConstruct
    void init(){
        this.amigos = sesion.usuario.getAmigos();
    }
    
    public String goEnviarMensaje(){
        this.amigoSeleccionado = null;
        this.idAmigo = this.amigoSeleccionado.getId();
        this.contenidoSeleccionado = "";
        return "enviarMensaje";
    }
    
    public String doEnvioMensaje(){
        this.amigoSeleccionado = this.usuarioFacade.find(this.idAmigo);
        this.mensaje = new Mensaje();
        this.mensaje.setMensaje(contenidoSeleccionado);
        this.mensaje.setReceptor(amigoSeleccionado);
        this.mensaje.setEmisor(this.sesion.usuario);
        this.mensaje.setVisto('F');
        this.mensaje.setFecha(new Date());
        this.mensajeFacade.create(this.mensaje);
        
        this.sesion.usuario.getMensajesEmitidos().add(this.mensaje);
        this.usuarioFacade.edit(this.sesion.usuario);
        
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

    public BigInteger getIdAmigo() {
        return idAmigo;
    }

    public void setContenidoSeleccionado(String contenidoSeleccionado) {
        this.contenidoSeleccionado = contenidoSeleccionado;
    }

    public void setAmigoSeleccionado(Usuario amigoSeleccionado) {
        this.amigoSeleccionado = amigoSeleccionado;
    }

    public void setIdAmigo(BigInteger idAmigo) {
        this.idAmigo = idAmigo;
    }
    
}

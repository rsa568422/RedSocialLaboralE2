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
import javax.inject.Named;
import javax.inject.Inject;
import redsociallaborale2.ejb.MensajeFacade;
import redsociallaborale2.ejb.UsuarioFacade;
import redsociallaborale2.jpa.Mensaje;
import redsociallaborale2.jpa.Usuario;

/**
 *
 * @author anton
 */
@Named(value = "mensajesBean")
@RequestScoped
public class MensajesBean{

    @EJB
    private UsuarioFacade usuarioFacade;

    @EJB
    private MensajeFacade mensajeFacade;   
    
    @Inject
    private UsuarioBean sesion;
   
    protected List<Mensaje> mensajes;
    protected Mensaje mensaje;
    protected Mensaje mensajeSeleccionado;
    
    private Usuario u;
    
    /**
     * Creates a new instance of MensajesBean
     */
    public MensajesBean() {
        
    }
    
    @PostConstruct
    void init(){
        u = sesion.usuario;       
    }
    
    public List<Mensaje> getMensajesRecibidos(){
        mensajes = u.getMensajesRecibidos();
        return mensajes;
    }
    
    public List<Mensaje> getMensajesEnviados(){
        mensajes = u.getMensajesEmitidos();
        return mensajes; 
    }
    
    public List<Mensaje> getMensajesNoLeidos(){
        mensajes = mensajeFacade.findByVistoAndReceptor('F', u);
        return mensajes;
    }
    
    public String doBorrar(Mensaje m){
        this.mensajeFacade.remove(m);
        if(m.getReceptor().equals(sesion.usuario)){
            this.sesion.usuario.getMensajesRecibidos().remove(m);
        }else{
            this.sesion.usuario.getMensajesEmitidos().remove(m);
        }
        this.usuarioFacade.edit(sesion.usuario);
        this.init();
        return "bandejaEntrada";
    }
    
    public String establecerEstado(char i){
        String cadena = "Leído";
        if(i == 'F') cadena = "No leído";
        return cadena;
    }
    
    public String longitudMensaje(String m){
        String cadena = m;
        if(m.length()>20) cadena = m.substring(0, 18) + "...";
        return cadena;
    }
    
    public String goBandejaSalida(){
        return "bandejaSalida";
    }
    
    public String goBandejaEntrada(){
        return "bandejaEntrada";
    }
    
    public Mensaje getMensajeSeleccionado(){
        return this.mensajeSeleccionado;
    }
}

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
import redsociallaborale2.ejb.MensajeFacade;
import redsociallaborale2.jpa.Mensaje;

/**
 *
 * @author anton
 */
@Named(value = "leerMensajeBean")
@RequestScoped
public class LeerMensajeBean {

    @EJB
    private MensajeFacade mensajeFacade;
    
    @Inject
    private MensajesBean mensaje;
    
    @Inject
    private UsuarioBean sesion;
    
    protected boolean volEntrada, volSalida; 
    
    /**
     * Creates a new instance of LeerMensajeBean
     */
    public LeerMensajeBean() {
    }
    
    @PostConstruct
    void init(){
    }
    
    public String doLeerMensaje(Mensaje m){
        mensaje.mensajeSeleccionado = m;
        this.modificarEstado();
        this.volver();
        return "leerMensaje";
    } 
     
    private void modificarEstado(){
        if(mensaje.mensajeSeleccionado.getReceptor().equals(sesion.usuario)){
            mensaje.mensajeSeleccionado.setVisto('T');
            this.mensajeFacade.edit(mensaje.mensajeSeleccionado);
        }
    }
    
    private void volver(){
        if(mensaje.mensajeSeleccionado.getReceptor().equals(sesion.usuario)){
            this.volEntrada = true;
            this.volSalida = false;
        } else {
            this.volEntrada = false;
            this.volSalida = true;
        }
    }
    
    public boolean getVolEntrada(){
        return this.volEntrada;
    }
    
    public boolean getVolSalida(){
        return this.volSalida;
    }
    
}

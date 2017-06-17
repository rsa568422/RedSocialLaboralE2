/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package redsociallaborale2.bean;

import java.math.BigInteger;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import redsociallaborale2.ejb.SolicitudFacade;
import redsociallaborale2.ejb.UsuarioFacade;
import redsociallaborale2.jpa.Solicitud;
import redsociallaborale2.jpa.Usuario;

/**
 *
 * @author Inma
 */
@Named(value = "solicitudesBean")
@RequestScoped
public class SolicitudesBean 
{

    @EJB
    private SolicitudFacade solicitudFacade;

    @EJB
    private UsuarioFacade usuarioFacade;
    
    private Usuario usu;
    private List <Solicitud> listaSolicitudes;
    private Solicitud solicitud;
    @Inject
    private UsuarioBean sesion;
    
    

    /**
     * Creates a new instance of Solicitudes
     */
    public SolicitudesBean() 
    {
        
    }
    
    @PostConstruct
    void init()
    {
       // listaSolicitudes = sesion.getUsuario().getSolicitudesRecibidas();
       // listaSolicitudes = solicitudFacade.findByReceptor(sesion.usuario.getId());
        usu = sesion.usuario;
        listaSolicitudes = (List<Solicitud>)usu.getSolicitudesRecibidas();
        solicitud = null;
        
    }
    

 /*   public String doAceptar(Solicitud solicitud)
    {
        
        listaSolicitudes.remove(solicitud);
        
        
        return "main2.xhtml?faces-redirect=true";
    }
   */ 

    public Usuario getUsu() {
        return usu;
    }

    public void setUsu(Usuario usu) {
        this.usu = usu;
    }

    public UsuarioBean getSesion() {
        return sesion;
    }

   
    public void setSesion(UsuarioBean sesion) {
        this.sesion = sesion;
    }

    public List<Solicitud> getListaSolicitudes() 
    {   
        
       // listaSolicitudes = solicitudFacade.findByReceptor(usu.getId());
        
        return listaSolicitudes;
    }

    public void setListaSolicitudes(List<Solicitud> listaSolicitudes) {
        this.listaSolicitudes = listaSolicitudes;
    }

    public Solicitud getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(Solicitud solicitud) {
        this.solicitud = solicitud;
    }
    
    
    
    
    
    
}

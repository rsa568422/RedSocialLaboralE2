/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package redsociallaborale2.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.inject.Inject;
import redsociallaborale2.ejb.SolicitudFacade;
import redsociallaborale2.ejb.UsuarioFacade;
import redsociallaborale2.jpa.Solicitud;
import redsociallaborale2.jpa.SolicitudPK;
import redsociallaborale2.jpa.Usuario;

/**
 *
 * @author Inmaculada Sánchez
 */
@Named(value = "buscarUsuarioBean")
@RequestScoped
public class BuscarUsuarioBean implements Serializable
{

    @EJB
    private SolicitudFacade solicitudFacade;


    @EJB
    private UsuarioFacade usuarioFacade;
    
    
    @Inject
    private UsuarioBean sesion;
    
    private Usuario usu;
    protected List<Usuario> listaUsu;
    private String nomUs;
    protected Integer[] selecc;

    
    
    

    /**
     * Creates a new instance of BuscarUsuarioBean
     */
    public BuscarUsuarioBean() 
    {
        
    }
    
    @PostConstruct
    void init()
    {
        usu = sesion.usuario; 
        selecc = new Integer[] {1};
    }
    
    
   /* public String goMostrarUsuarios()
    {
        return "mostrarUsuarios";
    }
*/
    
    
    
    public String usuariosEncontrados ()
    {
        //Creo una lista con todos los amigos del usuario logueado
        //listaUsuAm = usu.getAmigos();
        
        if (nomUs != null)
        {
            //Busco usuarios por las letras que se han introducido en el campo
            
            listaUsu = usuarioFacade.busquedaEspecifica(nomUs, selecc);
        }

        if (listaUsu.size()>0)
        {
            sesion.seleccionado = listaUsu;
            return "mostrarUsuarios";
        }
        else
        {
            return "main";
        }
        
    }

    public String doSolicitud(Usuario usuEnc)
    {
        //Creo una nueva solicitud
        Solicitud solNueva = new Solicitud(new SolicitudPK(usu.getId(), usuEnc.getId()));
        
        
        solNueva.setEmisor(usu);
        solNueva.setReceptor(usuEnc);
        solNueva.setFecha(new Date());
        solNueva.setVisto('F');
        
        solicitudFacade.create(solNueva);
        
        usu.getSolicitudesEmitidas().add(solNueva);
        usuEnc.getSolicitudesRecibidas().add(solNueva);
        
        usuarioFacade.edit(usu);
        usuarioFacade.edit(usuEnc);
        
        return "mostrarUsuarios";
        
    }
    
    public boolean verAmigos (Usuario usuEnc)
    {
       List<Usuario> listaU = new ArrayList<>();
        
         for (Solicitud s :usu.getSolicitudesEmitidas())
         {
             //Lista de personas a las que envio solicitud
             listaU.add(s.getReceptor());
         }
        
        return !usu.getAmigos().contains(usuEnc) && !listaU.contains(usuEnc);
        
        
    }
    
    
    
    public UsuarioFacade getUsuarioFacade() {
        return usuarioFacade;
    }

    public void setUsuarioFacade(UsuarioFacade usuarioFacade) {
        this.usuarioFacade = usuarioFacade;
    }

    public UsuarioBean getSesion() {
        return sesion;
    }

    public void setSesion(UsuarioBean sesion) {
        this.sesion = sesion;
    }

    public Usuario getUsu() {
        return usu;
    }

    public void setUsu(Usuario usu) {
        this.usu = usu;
    }

    public List<Usuario> getListaUsu() {
        return listaUsu;
    }

    public void setListaUsu(List<Usuario> listaUsu) {
        this.listaUsu = listaUsu;
    }
    
    public String getNomUs() {
        return nomUs;
    }

    public void setNomUs(String nomUs) {
        this.nomUs = nomUs;
    }

    public Integer[] getSelecc() {
        return selecc;
    }

    public void setSelecc(Integer[] selecc) {
        this.selecc = selecc;
    }

   

   
}

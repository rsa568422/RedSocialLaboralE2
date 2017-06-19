/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package redsociallaborale2.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.inject.Inject;
import redsociallaborale2.ejb.SolicitudFacade;
import redsociallaborale2.ejb.UsuarioFacade;
import redsociallaborale2.jpa.Solicitud;
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
    private List<Integer>selecc;

    
    
    

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
        nomUs = "";
      //  selecc = new ArrayList<Integer>();
    }
    
    
    public String goMostrarUsuarios()
    {
        return "mostrarUsuarios";
    }
    
    
    
    public String usuariosEncontrados ()
    {
        //Creo una lista con todos los amigos del usuario logueado
        //listaUsuAm = usu.getAmigos();
        
        if ("".equals(nomUs))
        {
            
        } else {
            //Busco usuarios por las letras que se han introducido en el campo
            listaUsu = usuarioFacade.busquedaEspecifica(nomUs, selecc);
        }

        if (listaUsu.size()>0)
        {
            return "mostrarUsuarios";
        }
        else
        {
            return "main2";
        }
        
    }

    public String doAmigo(List<Usuario> usuEnc)
    {
        //Lista para coger para
        
        //listaSol = (List<Solicitud>) solicitudFacade.
        
        
        
        return "main2.xhtml?faces-redirect=true";
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

    public List<Integer> getSelecc() {
        return selecc;
    }

    public void setSelecc(List<Integer> selecc) {
        this.selecc = selecc;
    }


   
}

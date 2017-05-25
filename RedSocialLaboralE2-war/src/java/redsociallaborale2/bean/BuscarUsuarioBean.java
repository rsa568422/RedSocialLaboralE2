/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package redsociallaborale2.bean;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import redsociallaborale2.ejb.SolicitudFacade;
import redsociallaborale2.ejb.UsuarioFacade;
import redsociallaborale2.jpa.Solicitud;
import redsociallaborale2.jpa.Usuario;

/**
 *
 * @author Inma
 */
@Named(value = "buscarUsuarioBean")
@SessionScoped
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
    private List<Usuario> listaUsuAm;
    private String nomUs;
    private int [] selecc;
    private List<Usuario> usuEnc;
    private List<Solicitud> listaSol;
    
    
    

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
    }
    
    
    public String goMostrarUsuarios()
    {
        return "mostrarUsuarios.xhtml?faces-redirect=true";
    }
    
    
    
    public List<Usuario> usuariosEncontrados ()
    {
        //Recojo los amigos del usuario logueado
        listaUsuAm = (List<Usuario>)usu.getAmigoDe();
        
        //Busco los usuarios por el dato introducido
        listaUsu = usuarioFacade.busquedaEspecifica(nomUs, selecc);
        
        
        for (Usuario us: listaUsu)
         {
             for (Usuario usua: listaUsuAm)
             {
                 if (us.getId().equals(usua.getId()))
                 {
                     
                     listaUsu.remove(us);
                     break;
                 }
                 
             }
         }   
        return listaUsu;
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

    public List<Usuario> getListaUsuAm() {
        return listaUsuAm;
    }

    public void setListaUsuAm(List<Usuario> listaUsuAm) {
        this.listaUsuAm = listaUsuAm;
    }

    public String getNomUs() {
        return nomUs;
    }

    public void setNomUs(String nomUs) {
        this.nomUs = nomUs;
    }

    public int[] getSelecc() {
        return selecc;
    }

    public void setSelecc(int[] selecc) {
        this.selecc = selecc;
    }
    
    
    
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package redsociallaborale2.bean;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import redsociallaborale2.ejb.SolicitudFacade;
import redsociallaborale2.ejb.UsuarioFacade;
import redsociallaborale2.jpa.Usuario;

/**
 *
 * @author Inmaculada Sánchez
 */
@Named(value = "contactoBean")
@RequestScoped
public class ContactoBean {

    @EJB
    private UsuarioFacade usuarioFacade;

    @EJB
    private SolicitudFacade solicitudFacade;
    
    

    
    @Inject
    private UsuarioBean sesion;
    
    private List<Usuario> listaAmigos;
    protected Usuario usu;
    protected Usuario seleccionado;
    
    /**
     * Creates a new instance of ContactoBean
     */
    public ContactoBean() {
    }
    
    @PostConstruct
    public void init() 
    {
        listaAmigos = sesion.usuario.getAmigos();
        seleccionado = null;
    }
    
    public String goContactos ()
    {
        return "contactos.xhtml?faces-redirect=true";
    }
    
   /* public List<Usuario> muestraAmigos()
    {
        //Buscamos la lista de amigos
        //listaAmigos = sesion.usuario.getAmigos();

        return listaAmigos;
    }
  */  
    public String doBorrar(Usuario usu)
    {
        
        listaAmigos.remove(usu);
        
        init();
        return "contactos.xhtml?faces-redirect=true";
        
    }
    
    
    public String doVerPerfil(Usuario usu) 
    {
        seleccionado = usu;
        
        return "verPerfilContacto.xhtml";
    }

    public UsuarioBean getSesion() {
        return sesion;
    }

    public void setSesion(UsuarioBean sesion) {
        this.sesion = sesion;
    }

    public List<Usuario> getListaAmigos() {
        return listaAmigos;
    }

    public void setListaAmigos(List<Usuario> listaAmigos) {
        this.listaAmigos = listaAmigos;
    }

    public Usuario getSeleccionado() {
        return seleccionado;
    }

    public void setSeleccionado(Usuario seleccionado) {
        this.seleccionado = seleccionado;
    }

    public Usuario getUsu() {
        return usu;
    }

    public void setUsu(Usuario usu) {
        this.usu = usu;
    }

    
    
    
    
}

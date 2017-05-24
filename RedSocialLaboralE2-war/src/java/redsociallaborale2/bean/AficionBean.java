/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package redsociallaborale2.bean;

import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import redsociallaborale2.ejb.AficionFacade;
import redsociallaborale2.ejb.UsuarioFacade;
import redsociallaborale2.jpa.Aficion;

/**
 *
 * @author Roberto Sanchez
 */
@Named(value = "aficionBean")
@RequestScoped
public class AficionBean {

    @EJB
    private UsuarioFacade usuarioFacade;

    @EJB
    private AficionFacade aficionFacade;
    
    @Inject
    protected UsuarioBean sesion;

    /**
     * Creates a new instance of AficionBean
     */
    public AficionBean() {
    }

    public UsuarioBean getSesion() {
        return sesion;
    }

    public void setSesion(UsuarioBean sesion) {
        this.sesion = sesion;
    }
    
    public String doInsertar() {
        sesion.seleccionado = null;
        return "editarAficion.xhtml";
    }
    
    public String doEditar(Aficion aficion) {
        sesion.seleccionado = aficion;
        return "editarAficion.xhtml";
    }
    
    public String doEliminar(Aficion aficion) {
        aficionFacade.remove(aficion);
        sesion.usuario.getAficiones().remove(aficion);
        usuarioFacade.edit(sesion.usuario);
        return sesion.doVerPerfil();
    }
}

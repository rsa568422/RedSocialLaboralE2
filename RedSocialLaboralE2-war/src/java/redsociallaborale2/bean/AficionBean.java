/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package redsociallaborale2.bean;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
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
    
    protected boolean mostrarControles = false;
    
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

    public boolean isMostrarControles() {
        return mostrarControles;
    }

    public void setMostrarControles(boolean mostrarControles) {
        this.mostrarControles = mostrarControles;
    }
    
    public String doInsertar() {
        if (sesion != null && sesion.usuario != null) {
            sesion.seleccionado = null;
        } else {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("error.xhtml");
            } catch (IOException ex) {
                Logger.getLogger(AficionBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return "editarAficion.xhtml";
    }
    
    public String doEditar(Aficion aficion) {
        if (sesion != null && sesion.usuario != null && aficion != null) {
            sesion.seleccionado = aficion;
        } else {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("error.xhtml");
            } catch (IOException ex) {
                Logger.getLogger(AficionBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return "editarAficion.xhtml";
    }
    
    public String doEliminar(Aficion aficion) {
        if (sesion != null && sesion.usuario != null && aficion != null && aficionFacade.find(aficion.getAficionPK()) != null) {
            aficionFacade.remove(aficion);
            sesion.usuario.getAficiones().remove(aficion);
            usuarioFacade.edit(sesion.usuario);
        } else {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("error.xhtml");
            } catch (IOException ex) {
                Logger.getLogger(AficionBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return sesion.doVerPerfil();
    }
    
    public String doMostrarControles() {
        mostrarControles = true;
        return null;
    }
}

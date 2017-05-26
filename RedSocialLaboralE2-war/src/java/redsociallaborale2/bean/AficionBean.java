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
    
    private final String paginaError = "error.xhtml";

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
        if (sesion != null) {
            sesion.error = 0;
            if (sesion.usuario != null) {
                sesion.seleccionado = null;
            } else {
                sesion.error = 211; // No hay usuario en al sesion
            }
        }
        return sesion != null && sesion.error == 0 ? "editarAficion.xhtml" : paginaError;
    }
    
    public String doEditar(Aficion aficion) {
        sesion.error = 0;
        if (sesion.usuario != null) {
            if (aficion != null) {
                sesion.seleccionado = aficion;
            } else {
                sesion.error = 222; // La aficion no existe
            }
        } else {
            sesion.error = 221; // No hay usuario en al sesion
        }
        return sesion.error == 0 ? "editarAficion.xhtml" : paginaError;
    }
    
    public String doEliminar(Aficion aficion) {
        if (sesion == null) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect(paginaError);
            } catch (IOException ex) {
                Logger.getLogger(AficionBean.class.getName()).log(Level.SEVERE, null, ex);
            }
            //return null;
        } else {
            sesion.error = 0;
            if (sesion.usuario != null) {
                if (aficion != null) {
                    if (aficionFacade.find(aficion.getAficionPK()) != null) {
                        aficionFacade.remove(aficion);
                        sesion.usuario.getAficiones().remove(aficion);
                        usuarioFacade.edit(sesion.usuario);
                    } else {
                        sesion.error = 233; // La aficion no existe
                    }

                } else {
                    sesion.error = 232; // No se pasa aficion
                }
            } else {
                sesion.error = 231; // No hay usuario en al sesion
            }
        }
        return sesion != null && sesion.error == 0 ? sesion.doVerPerfil() : paginaError;
    }
}

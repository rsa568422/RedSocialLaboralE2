/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package redsociallaborale2.bean;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
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
@Named(value = "editarAficionBean")
@RequestScoped
public class EditarAficionBean {

    @EJB
    private UsuarioFacade usuarioFacade;

    @EJB
    private AficionFacade aficionFacade;
    
    @Inject
    UsuarioBean sesion;
    
    private String nombre;
    private boolean nueva;
    private final String paginaError = "error.xhtml";

    /**
     * Creates a new instance of EditarAficionBean
     */
    public EditarAficionBean() {
    }
    
    @PostConstruct
    void init() {
        if (sesion != null && sesion.usuario != null && (sesion.seleccionado == null || sesion.seleccionado instanceof Aficion)) {
            nueva = sesion.seleccionado == null;
            nombre = nueva ? "" : ((Aficion) sesion.seleccionado).getAficionPK().getNombre();
            sesion.error = 0;
        } else {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("error.xhtml");
            } catch (IOException ex) {
                Logger.getLogger(AficionBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isNueva() {
        return nueva;
    }

    public void setNueva(boolean nueva) {
        this.nueva = nueva;
    }
    
    public String doSave() {
        if (sesion != null && sesion.usuario != null) {
            sesion.error = nombre != null && !nombre.isEmpty() ? 0 : 1;
            if (sesion.error == 0) {
                Aficion aficion;
                if (nueva) {
                    aficion = aficionFacade.findByIdUsuarioAndNombreAficion(sesion.usuario.getId(), nombre);
                    if (aficion == null) {
                        aficion = new Aficion(nombre, sesion.usuario.getId());
                        aficion.setUsuario(sesion.usuario);
                        aficionFacade.create(aficion);
                        sesion.usuario.getAficiones().add(aficion);
                        usuarioFacade.edit(sesion.usuario);
                    } else {
                        sesion.error = 2;
                    }
                } else if (sesion.seleccionado instanceof Aficion && !((Aficion) sesion.seleccionado).getAficionPK().getNombre().equals(nombre)) {
                    Aficion existente = aficionFacade.findByIdUsuarioAndNombreAficion(sesion.usuario.getId(), nombre);
                    if (existente == null) {
                        aficion = aficionFacade.findByIdUsuarioAndNombreAficion(sesion.usuario.getId(), ((Aficion) sesion.seleccionado).getAficionPK().getNombre());
                        sesion.usuario.getAficiones().remove(aficion);
                        aficionFacade.remove(aficion);
                        aficion = new Aficion(nombre, sesion.usuario.getId());
                        sesion.usuario.getAficiones().add(aficion);
                        aficionFacade.create(aficion);
                        usuarioFacade.edit(sesion.usuario);
                    } else {
                        sesion.error = 3;
                    }
                }
            }
        } else {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("error.xhtml");
            } catch (IOException ex) {
                Logger.getLogger(AficionBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return sesion.error == 0 ? sesion.doVerPerfil() : null;
    }
    
    public String doShowErrorMsg() {
        String str;
        if (sesion != null) {
            switch (sesion.error) {
                case 1: str = "Error: introduzca nombre para la afición"; break;
                case 2: 
                case 3: str = "Error: ya existe una afición con ese nombre"; break;
                default: str = "";
            }
        } else {
            str = "";
        }
        return str;
    }
}

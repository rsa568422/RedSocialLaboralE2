/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package redsociallaborale2.bean;

import java.util.Date;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.inject.Inject;
import redsociallaborale2.ejb.ExperienciaFacade;
import redsociallaborale2.ejb.UsuarioFacade;
import redsociallaborale2.jpa.Experiencia;

/**
 *
 * @author Roberto Benitez
 *
 *
 */
@Named(value = "editarExperienciaBean")
@RequestScoped
public class EditarExperiencia {

    @EJB
    private ExperienciaFacade experienciaFacade;
    
    @Inject
    private ExperienciaBean experienciaBean;
    
    private Experiencia experiencia;
    
    /**
     * Creates a new instance of MensajesBean
     */
    
    public EditarExperiencia() {
    }

    @PostConstruct
    void init() {
       experiencia = experienciaBean.getExperienciaSeleccionada(); 
    }
    
    public String doSave() {
        String next = "editarExperiencia";
        int error = 0;
        
        if (error == 0) {
            experiencia.setEmpresa(experienciaBean.getExperienciaSeleccionada().getEmpresa());
            experiencia.setPuesto(experienciaBean.getExperienciaSeleccionada().getPuesto());
            experiencia.setWebempresa(experienciaBean.getExperienciaSeleccionada().getWebempresa());
               
            experienciaFacade.edit(experiencia);
            next = "listaExperiencias";
        } 
        return next ;
    }

    public ExperienciaFacade getExperienciaFacade() {
        return experienciaFacade;
    }

    public void setExperienciaFacade(ExperienciaFacade experienciaFacade) {
        this.experienciaFacade = experienciaFacade;
    }

    public ExperienciaBean getExperienciaBean() {
        return experienciaBean;
    }

    public void setExperienciaBean(ExperienciaBean experienciaBean) {
        this.experienciaBean = experienciaBean;
    }

    public Experiencia getExperiencia() {
        return experiencia;
    }

    public void setExperiencia(Experiencia experiencia) {
        this.experiencia = experiencia;
    }
}

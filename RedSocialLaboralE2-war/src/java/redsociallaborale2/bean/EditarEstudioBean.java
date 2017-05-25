/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package redsociallaborale2.bean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.inject.Inject;
import redsociallaborale2.ejb.EstudiosFacade;
import redsociallaborale2.jpa.Estudios;

/**
 *
 * @author Roberto Benitez
 *
 *
 */
@Named(value = "editarEstudioBean")
@RequestScoped
public class EditarEstudioBean {

    @EJB
    private EstudiosFacade estudiosFacade;

    @Inject
    private EstudiosBean estudio;

    protected Date fechainicio;
    protected Date fechafin;
    protected String descripcion;
    protected String ubicacion;

    /**
     * Creates a new instance of MensajesBean
     */
    
    public EditarEstudioBean() {
    }

    @PostConstruct
    void init() {
       /*descripcion = estudio..estudioSeleccionado.getDescripcion();
       ubicacion = estudio.estudioSeleccionado.getUbicacion();*/
    }

    public EstudiosBean getEstudio() {
        return estudio;
    }

    public void setEstudio(EstudiosBean estudio) {
        this.estudio = estudio;
    }

    public Date getFechainicio() {
        return fechainicio;
    }

    public void setFechainicio(Date fechainicio) {
        this.fechainicio = fechainicio;
    }

    public Date getFechafin() {
        return fechafin;
    }

    public void setFechafin(Date fechafin) {
        this.fechafin = fechafin;
    }

    public EstudiosFacade getEstudiosFacade() {
        return estudiosFacade;
    }

    public void setEstudiosFacade(EstudiosFacade estudiosFacade) {
        this.estudiosFacade = estudiosFacade;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }
    
    public String doSave() {
        String next = "editarEstudio";
        int error = 0;
        
        
        if (error == 0) {
            estudio.estudioSeleccionado.setDescripcion(descripcion);
            estudio.estudioSeleccionado.setUbicacion(ubicacion);
            
            estudio.estudio = estudio.estudioSeleccionado;
               
            estudiosFacade.edit(estudio.estudio);
            next = "listaEstudios";
        } 
        return next ;
    }
}

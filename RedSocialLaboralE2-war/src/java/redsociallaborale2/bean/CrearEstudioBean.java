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
import javax.inject.Inject;
import javax.inject.Named;
import redsociallaborale2.ejb.EstudiosFacade;
import redsociallaborale2.jpa.Estudios;

/**
 *
 * @author Roberto Benitez
 */
@Named(value = "crearEstudioBean")
@RequestScoped
public class CrearEstudioBean {

    @EJB
    private EstudiosFacade estudiosFacade;

    @Inject
    private UsuarioBean sesion;

    private Estudios estudio;

    private String fechainicio;
    private String fechafin;
    private String descripcion;
    private String ubicacion;

    public CrearEstudioBean() {
        estudio = new Estudios();
    }

    @PostConstruct
    void init() {
        fechainicio = "";
        fechafin = "";
        descripcion = "";
        ubicacion = "";
        sesion.error = 0;
    }

    public String doCrear() {
        String next = "anadirEstudio.xhtml";
        int error;

        error = fechainicio != null && !fechainicio.isEmpty() ? 0 : 1; 
        error = fechafin != null && !fechafin.isEmpty() ? error : error + 2; 
        error = descripcion != null && !descripcion.isEmpty() ? error : error + 4;
        if (error == 0) {
            if (fechasCorrectas(fechainicio,fechafin)){
                Estudios e = new Estudios();
                SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyy-MM-dd");
                 Date fechainicioD = null;
                Date fechafinD = null;
                try {
                    
                 fechainicioD = formatoDelTexto.parse(fechainicio);
                    fechafinD = formatoDelTexto.parse(fechafin);
                    
                } catch (ParseException ex) {
                }

                e.setFechaInicio(fechainicioD);
                e.setFechaFin(fechafinD);
                e.setDescripcion(descripcion);
                e.setUbicacion(ubicacion);
                e.setUsuario(sesion.usuario);
                estudiosFacade.create(e);
                next = "listaEstudios";
             } else {
                error = 15;
            }
        } else {
            error = 20;
        }
        sesion.error = error;
        return next;
    }
    
    public String doShowErrorMsg() {
        String str;
        switch(sesion.error){
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15: str="Error: la fecha fin debe ser posterior a la fecha inicio"; break;
            case 16:
            case 17:
            case 18:
            case 19:
            case 20: str="Error: los campos fechas y descripcion son obligatorios"; break;
            default: str="";
        }
        
        return str;
    }
    
    public boolean doShowErrorFechas() {
        return sesion.error == 15;
    }
    
    public boolean doShowErrorCampos() {
        return sesion.error == 20;
    }

    public String getFechainicio() {
        return fechainicio;
    }

    public void setFechainicio(String fechainicio) {
        this.fechainicio = fechainicio;
    }

    public String getFechafin() {
        return fechafin;
    }

    public void setFechafin(String fechafin) {
        this.fechafin = fechafin;
    }

    public EstudiosFacade getEstudiosFacade() {
        return estudiosFacade;
    }

    public void setEstudiosFacade(EstudiosFacade estudiosFacade) {
        this.estudiosFacade = estudiosFacade;
    }

    public UsuarioBean getSesion() {
        return sesion;
    }

    public void setSesion(UsuarioBean sesion) {
        this.sesion = sesion;
    }

    public Estudios getEstudio() {
        return estudio;
    }

    public void setEstudio(Estudios estudio) {
        this.estudio = estudio;
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

    private boolean fechasCorrectas(String fechainicio, String fechafin) {
        boolean res=false;
        
        SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyy-MM-dd");
        Date ini = null;
        Date fin = null;
        try {

                ini = formatoDelTexto.parse(fechainicio);
                fin = formatoDelTexto.parse(fechafin);

       } catch (ParseException ex) {
       }
        
       if (ini.before(fin)){
           res=true;
       } 
       return res;
    }

}

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
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import redsociallaborale2.ejb.EstudiosFacade;
import redsociallaborale2.ejb.UsuarioFacade;
import redsociallaborale2.jpa.Estudios;

/**
 *
 * @author Roberto Benitez
 */
@Named(value = "estudiosCrudModificarBean")
@RequestScoped
public class EstudiosCrudModificarBean {
    
    @EJB
    private EstudiosFacade estudiosfacade;
    
    @EJB
    private UsuarioFacade usuariofacade;

    @Inject
    UsuarioBean sesion;
    
    private Estudios estudioSeleccionado;
    /**
     * Creates a new instance of EstudiosCrudModificarBean
     */
    public EstudiosCrudModificarBean() {
    }
    
    @PostConstruct
    public void init(){
        if (sesion.seleccionado == null){ // Crear
            estudioSeleccionado = new Estudios();
        } else { // Editar
            estudioSeleccionado = (Estudios) sesion.seleccionado;
        }
        
    }

    public Estudios getEstudioSeleccionado() {
        return estudioSeleccionado;
    }

    public void setEstudioSeleccionado(Estudios estudioSeleccionado) {
        this.estudioSeleccionado = estudioSeleccionado;
    }
    
    public String doGuardar() {
        String next = "estudio";
        int error=0;
        if (estudioSeleccionado.getFechaInicio() == null){
            error = 1;
        } 
        
        if (estudioSeleccionado.getFechaFin() == null){
            error = error + 2;
        } 
        
        if (estudioSeleccionado.getDescripcion() == null || estudioSeleccionado.getDescripcion().isEmpty()){
            error = error +4;
        }
        
        
        if (error == 0) {
            
            Date fechainicio = estudioSeleccionado.getFechaInicio();
            Date fechafin = estudioSeleccionado.getFechaFin();
            
            // Crear estudio
            if (sesion.seleccionado == null) {
                if (fechasCorrectas(fechainicio, fechafin)) {
                    estudioSeleccionado.setUsuario(sesion.usuario);
                    estudiosfacade.create(estudioSeleccionado);
                    init();
                    next="estudiosLista";
                } else {
                    error = 15;
                }

            } 
            
            //Editar estudio
            else {
                if (fechasCorrectas(fechainicio, fechafin)) {
                    estudiosfacade.edit(estudioSeleccionado);
                    usuariofacade.edit(sesion.usuario);
                    init();
                    next="estudiosLista";
                } else {
                    error = 15;
                }
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
    
    protected void reset() {
        estudioSeleccionado = new Estudios();
        String inicio = "01/01/0001 01:00:00";
        String fin = "01/01/0001 01:00:00";
        
        SimpleDateFormat formatoDelTexto = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        Date ini = null;
        Date fini = null;
        try {

                ini = formatoDelTexto.parse(inicio);
                fini = formatoDelTexto.parse(fin);

       } catch (ParseException ex) {
       }
        
        
        estudioSeleccionado.setFechaInicio(ini);
        estudioSeleccionado.setFechaFin(fini);
        estudioSeleccionado.setDescripcion("");
        estudioSeleccionado.setUbicacion("");
    }

    public String doNuevo() {
        reset();
        return "estudio";
    }

    private boolean fechasCorrectas(Date fechainicio, Date fechafin) {
        boolean res = false;

        if (fechainicio.before(fechafin)) {
            res = true;
        }
        
        return res;
    }
    
}

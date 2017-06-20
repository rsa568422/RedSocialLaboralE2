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
import redsociallaborale2.ejb.ExperienciaFacade;
import redsociallaborale2.ejb.UsuarioFacade;
import redsociallaborale2.jpa.Experiencia;

/**
 *
 * @author Roberto Benitez
 */
@Named(value = "experienciasCrudModificarBean")
@RequestScoped
public class ExperienciasCrudModificarBean {
    
    @EJB
    private ExperienciaFacade experienciafacade;
    
    @EJB
    private UsuarioFacade usuariofacade;

    @Inject
    UsuarioBean sesion;
    
    private Experiencia experienciaSeleccionada;
    
    
    /**
     * Creates a new instance of ExperienciasCrudModificarBean
     */
    public ExperienciasCrudModificarBean() {
    }
    
    @PostConstruct
    public void init(){
        if (sesion.seleccionado == null){ // Crear
            experienciaSeleccionada = new Experiencia();
        } else { // Editar
            experienciaSeleccionada = (Experiencia) sesion.seleccionado;
        }
    }

    public Experiencia getExperienciaSeleccionada() {
        return experienciaSeleccionada;
    }

    public void setExperienciaSeleccionada(Experiencia experienciaSeleccionada) {
        this.experienciaSeleccionada = experienciaSeleccionada;
    }
    
    public String doGuardar() {
        String next = "experiencia";
        int error=0;
        if (experienciaSeleccionada.getFechaInicio() == null){
            error = 1;
        } 
        
        if (experienciaSeleccionada.getFechaFin() == null){
            error = error + 2;
        } 
        
        if (experienciaSeleccionada.getEmpresa() == null || experienciaSeleccionada.getEmpresa().isEmpty()){
            error = error +4;
        }
        
        if (error == 0) {
            Date fechainicio = experienciaSeleccionada.getFechaInicio();
            Date fechafin = experienciaSeleccionada.getFechaFin();
            
            // Crear experiencia
            if (sesion.seleccionado == null) {
                if (fechasCorrectas(fechainicio, fechafin)) {
                    experienciaSeleccionada.setUsuario(sesion.usuario);
                    experienciafacade.create(experienciaSeleccionada);
                    init();
                    next="experienciasLista";
                } else {
                    error = 15;
                }

            } 
            //Editar experiencia
            else {
                if (fechasCorrectas(fechainicio, fechafin)) {
                    experienciafacade.edit(experienciaSeleccionada);
                    usuariofacade.edit(sesion.usuario);
                    init();
                    next="experienciasLista";
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
            case 20: str="Error: los campos fechas y empresa son obligatorios"; break;
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
        experienciaSeleccionada = new Experiencia();
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
        
        
        experienciaSeleccionada.setFechaInicio(ini);
        experienciaSeleccionada.setFechaFin(fini);
        experienciaSeleccionada.setEmpresa("");
        experienciaSeleccionada.setPuesto("");
        experienciaSeleccionada.setWebempresa("");
    }

    public String doNuevo() {
        reset();
        return "experiencia";
    }

    private boolean fechasCorrectas(Date fechainicio, Date fechafin) {
        boolean res = false;

        if (fechainicio.before(fechafin)) {
            res = true;
        }
        
        return res;
    }
}

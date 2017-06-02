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
import redsociallaborale2.ejb.ExperienciaFacade;
import redsociallaborale2.jpa.Experiencia;

/**
 *
 * @author Roberto Benitez
 *
 *  
 */

@Named(value = "crearExperienciaBean")
@RequestScoped
public class CrearExperienciaBean {

    @EJB
    private ExperienciaFacade experienciaFacade;
    
    @Inject
    private UsuarioBean sesion;
    
    private Experiencia experiencia;
    
    private String fechainicio;
    private String fechafin;
    private String empresa;
    private String puesto;
    private String webempresa;

    
    public CrearExperienciaBean() {
        experiencia = new Experiencia();
    }
    
    @PostConstruct
    void init(){
        fechainicio="";
        fechafin="";
        empresa="";
        puesto="";
        webempresa="";
        sesion.error=0;
    }
    
    public String doCrear(){
        String next = "anadirExperiencia.xhtml";
        int error;

        error = fechainicio != null && !fechainicio.isEmpty() ? 0 : 1; 
        error = fechafin != null && !fechafin.isEmpty() ? error : error + 2; 
        error = empresa != null && !empresa.isEmpty() ? error : error + 4;
        if (error == 0) {
            if (fechasCorrectas(fechainicio,fechafin)){
                Experiencia e = new Experiencia();
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
                e.setEmpresa(empresa);
                e.setPuesto(puesto);
                e.setWebempresa(webempresa);
                e.setUsuario(sesion.usuario);
                experienciaFacade.create(e);
                next = "listaExperiencias";
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

    public UsuarioBean getSesion() {
        return sesion;
    }

    public void setSesion(UsuarioBean sesion) {
        this.sesion = sesion;
    }

    public Experiencia getExperiencia() {
        return experiencia;
    }

    public void setExperiencia(Experiencia experiencia) {
        this.experiencia = experiencia;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getPuesto() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    public String getWebempresa() {
        return webempresa;
    }

    public void setWebempresa(String webempresa) {
        this.webempresa = webempresa;
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

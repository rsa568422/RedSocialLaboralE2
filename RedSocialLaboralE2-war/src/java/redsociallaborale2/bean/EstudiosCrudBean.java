/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package redsociallaborale2.bean;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
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
 * @author Rober
 */
@Named(value = "estudiosCrudBean")
@RequestScoped
public class EstudiosCrudBean {

    @EJB
    private EstudiosFacade estudiosfacade;
    
    @EJB
    private UsuarioFacade usuariofacade;

    @Inject
    private UsuarioBean sesion;

    private List<Estudios> estudiosLista;
    private Estudios estudioSeleccionado;

    /**
     * Creates a new instance of EstudiosCrudBean
     */
    public EstudiosCrudBean() {
    }

    @PostConstruct
    public void init() {
        estudiosLista = estudiosfacade.findByIdUsuario(sesion.usuario.getId());
        estudioSeleccionado=new Estudios();
    }

    public List<Estudios> getEstudiosLista() {
        return estudiosLista;
    }

    public void setEstudiosLista(List<Estudios> estudiosLista) {
        this.estudiosLista = estudiosLista;
    }

    public Estudios getEstudioSeleccionado() {
        return estudioSeleccionado;
    }

    public void setEstudioSeleccionado(Estudios estudioSeleccionado) {
        this.estudioSeleccionado = estudioSeleccionado;
    }
    
    public String goListaEstudios(){
        return "estudiosLista";
    }

    public String doBorrar(Estudios estudio) {
        estudiosfacade.remove(estudio);
        init();
        return "estudiosLista";
    }

    public String doEditar(Estudios e) {
        estudioSeleccionado = e;
        return "estudio";
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
        /*
        error = fechainicioS != null && !fechainicioS.isEmpty() ? 0 : 1;
        error = fechafinS != null && !fechafinS.isEmpty() ? error : error + 2;
        error = descripcion != null && !descripcion.isEmpty() ? error : error + 4;
        */
        if (error == 0) {
            Date fechainicio = estudioSeleccionado.getFechaInicio();
            Date fechafin = estudioSeleccionado.getFechaFin();
            
            BigInteger usuario = sesion.usuario.getId();
            
            Estudios e = estudiosfacade.findByFechasYUsuario(fechainicio, fechafin, usuario);
            
            // Crear estudio
            if (e == null) {
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
                    estudioSeleccionado.setId(e.getId());
                    estudioSeleccionado.setUsuario(sesion.usuario);
                    estudiosfacade.edit(estudioSeleccionado);
                    
                    sesion.usuario.getEstudios().remove(e);
                    sesion.usuario.getEstudios().add(estudioSeleccionado);
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

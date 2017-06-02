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
import redsociallaborale2.ejb.UsuarioFacade;
import redsociallaborale2.jpa.Estudios;
import redsociallaborale2.jpa.Usuario;

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
    
    @EJB
    private UsuarioFacade usuarioFacade;

    @Inject
    private EstudiosBean estudio;

    @Inject
    private UsuarioBean sesion;

    
    private Usuario us;
    protected String fechainicio;
    protected String fechafin;
    protected String descripcion;
    protected String ubicacion;

    /**
     * Creates a new instance of MensajesBean
     */
    public EditarEstudioBean() {
    }

    @PostConstruct
    void init() {
        descripcion=estudio.estudioSeleccionado.getDescripcion();
        ubicacion=estudio.estudioSeleccionado.getUbicacion();
        us = sesion.usuario;
    }

    public EstudiosBean getEstudio() {
        return estudio;
    }

    public void setEstudio(EstudiosBean estudio) {
        this.estudio = estudio;
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
/*
   public String doSave() {
        String next = "editarEstudio";
        int error = 0;
        
        Estudios estudioViejo;
        Estudios estudioNuevo;
        
        String fechaStrIni;
        String fechaStrFin;
        
        if (error == 0) {
            estudioViejo = estudiosFacade.findByIdUsuarioAndIdEstudio(sesion.usuario.getId(), estudio.estudioSeleccionado.getId());
            fechaStrIni = estudioViejo.getFechaInicio().toString();
            fechaStrFin = estudioViejo.getFechaFin().toString();
            sesion.usuario.getEstudios().remove(estudioViejo);
            estudiosFacade.remove(estudioViejo);

            SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyy-MM-dd");
            Date fechainicioD = null;
            Date fechafinD = null;
            try {

                fechainicioD = formatoDelTexto.parse(fechaStrIni);
                fechafinD = formatoDelTexto.parse(fechaStrFin);

            } catch (ParseException ex) {
            }

            estudioNuevo = new Estudios();

            estudioNuevo.setFechaInicio(fechainicioD);
            estudioNuevo.setFechaFin(fechafinD);
            estudioNuevo.setDescripcion(descripcion);
            estudioNuevo.setUbicacion(ubicacion);
            estudioNuevo.setUsuario(sesion.usuario);
            sesion.usuario.getEstudios().add(estudioNuevo);
            estudiosFacade.create(estudioNuevo);
            usuarioFacade.edit(sesion.usuario);
            
            estudiosFacade.edit(estudio.estudioSeleccionado);
            next = "listaEstudios";
        }
        return next;
    }*/
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package redsociallaborale2.bean;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.inject.Inject;
import redsociallaborale2.ejb.ExperienciaFacade;
import redsociallaborale2.jpa.Experiencia;
import redsociallaborale2.jpa.Usuario;

/**
 *
 * @author Roberto Benitez
 */
@Named(value = "experienciaBean")
@RequestScoped
public class ExperienciaBean{

    @EJB
    private ExperienciaFacade experienciaFacade;   

    @Inject
    private UsuarioBean sesion;
   
    private List<Experiencia> experienciaLista;
    private Experiencia experienciaSeleccionada;
    private Experiencia experiencia;
    
    private Usuario u;
    
    /**
     * Creates a new instance of MensajesBean
     */
    public ExperienciaBean() {
        
    }
    
    @PostConstruct
    void init(){
        u = sesion.usuario;
        experiencia = new Experiencia();
        experienciaSeleccionada = null;
    }
    
    public List<Experiencia> getExperienciaLista(){
        experienciaLista = experienciaFacade.findByIdUsuario(u.getId());
        return experienciaLista;
    }
    
    public String doBorrar(Experiencia e){
        this.experienciaFacade.remove(e);
        this.init();
        return "listaExperiencia";
    }
    
    public String goListaExperiencias(){
        return "listaExperiencias";
    }
    
    public String goAnadirExperiencia(){
        return "anadirExperiencia";
    }
    
    public String doEditarExperiencia(Experiencia e){
        experienciaSeleccionada = e;
        return "editarExperiencia";
    }

    public UsuarioBean getSesion() {
        return sesion;
    }

    public void setSesion(UsuarioBean sesion) {
        this.sesion = sesion;
    }

    public Experiencia getExperienciaSeleccionada() {
        return experienciaSeleccionada;
    }

    public void setExperienciaSeleccionada(Experiencia experienciaSeleccionada) {
        this.experienciaSeleccionada = experienciaSeleccionada;
    }

    public Experiencia getExperiencia() {
        return experiencia;
    }

    public void setExperiencia(Experiencia experiencia) {
        this.experiencia = experiencia;
    }

    public Usuario getU() {
        return u;
    }

    public void setU(Usuario u) {
        this.u = u;
    }
    
}

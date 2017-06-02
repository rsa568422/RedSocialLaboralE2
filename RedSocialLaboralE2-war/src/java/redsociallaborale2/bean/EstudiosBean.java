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
import redsociallaborale2.ejb.EstudiosFacade;
import redsociallaborale2.jpa.Estudios;
import redsociallaborale2.jpa.Usuario;

/**
 *
 * @author Roberto Benitez
 */
@Named(value = "estudiosBean")
@RequestScoped
public class EstudiosBean{

    @EJB
    private EstudiosFacade estudiosFacade;   

    @Inject
    private UsuarioBean sesion;
   
    protected List<Estudios> estudios;
    protected Estudios estudioSeleccionado;
    protected Estudios estudio;
    
    private Usuario u;
    
    /**
     * Creates a new instance of MensajesBean
     */
    public EstudiosBean() {
        
    }
    
    @PostConstruct
    void init(){
        u = sesion.usuario;
        estudio = new Estudios();
        estudioSeleccionado=new Estudios();
    }

    public UsuarioBean getSesion() {
        return sesion;
    }

    public void setSesion(UsuarioBean sesion) {
        this.sesion = sesion;
    }

    public List<Estudios> getEstudios() {
        return estudios;
    }

    public void setEstudios(List<Estudios> estudios) {
        this.estudios = estudios;
    }

    public Estudios getEstudio() {
        return estudio;
    }

    public void setEstudio(Estudios estudio) {
        this.estudio = estudio;
    }

    public Usuario getU() {
        return u;
    }

    public void setU(Usuario u) {
        this.u = u;
    }
    
    public List<Estudios> getEstudiosLista(){
        estudios = estudiosFacade.findByIdUsuario(u.getId());
        return estudios;
    }
    
    public String doBorrar(Estudios e){
        this.estudiosFacade.remove(e);
        this.init();
        return "listaEstudios";
    }
    
    public String goListaEstudios(){
        return "listaEstudios";
    }
    
    public String goAnadirEstudio(){
        return "anadirEstudio";
    }
    
    public Estudios getEstudioSeleccionado(){
        return estudioSeleccionado;
    }
    
    public String doEditarEstudio(Estudios e){
        Estudios hola;
        hola = estudiosFacade.find(e.getId());
        estudioSeleccionado = hola;
        return "editarEstudio";
    }
    
     
}

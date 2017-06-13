/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package redsociallaborale2.bean;

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
 * @author Roberto Benitez
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
    }

    public List<Estudios> getEstudiosLista() {
        return estudiosLista;
    }

    public void setEstudiosLista(List<Estudios> estudiosLista) {
        this.estudiosLista = estudiosLista;
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
        sesion.seleccionado = e;
        return "estudio";
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package redsociallaborale2.bean;

import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import redsociallaborale2.jpa.Aficion;

/**
 *
 * @author Roberto
 */
@Named(value = "aficionBean")
@RequestScoped
public class AficionBean {

    /**
     * Creates a new instance of AficionBean
     */
    public AficionBean() {
    }
    
    public String doEditar(Aficion aficion) {
        return "verPerfil.xhtml";
    }
    
    public String doEliminar(Aficion aficion) {
        return "verPerfil.xhtml";
    }
}

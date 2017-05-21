/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package redsociallaborale2.bean;

import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

/**
 *
 * @author Roberto
 */
@Named(value = "editarUsuarioBean")
@RequestScoped
public class EditarUsuarioBean {
    
    @Inject
    UsuarioBean sesion;
    
    protected String rePass;

    /**
     * Creates a new instance of EditarUsuarioBean
     */
    public EditarUsuarioBean() {
    }
    
    @PostConstruct
    void init() {
        rePass = "";
        sesion.error = 0;
    }

    public String getRePass() {
        return rePass;
    }

    public void setRePass(String rePass) {
        this.rePass = rePass;
    }
    
    public String doSave() {
        return "";
    }
    
    public String doShowErrorMsg() {
        String str;
        switch (sesion.error) {
            default: str = "";
        }
        return str;
    }
}

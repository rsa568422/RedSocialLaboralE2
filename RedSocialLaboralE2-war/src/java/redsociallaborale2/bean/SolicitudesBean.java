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
import redsociallaborale2.ejb.SolicitudFacade;
import redsociallaborale2.ejb.UsuarioFacade;
import redsociallaborale2.jpa.Solicitud;
import redsociallaborale2.jpa.Usuario;

/**
 *
 * @author Inma
 */
@Named(value = "solicitudesBean")
@RequestScoped
public class SolicitudesBean 
{

    @EJB
    private SolicitudFacade solicitudFacade;

    @EJB
    private UsuarioFacade usuarioFacade;
    
    private Usuario usu;
    private Usuario usu2;
    private List <Solicitud> lista;
    @Inject
    private UsuarioBean sesion;
    
    

    /**
     * Creates a new instance of Solicitudes
     */
    public SolicitudesBean() 
    {
        
    }
    
    @PostConstruct
    void init()
    {
        usu = sesion.usuario;
        usu2 = null;
        lista = usu.getSolicitudesRecibidas(); 
    }
    

    public String doAceptar(Solicitud solicitud)
    {
        //Busco el usuario EMISOR de la solicitud
        usu2 = usuarioFacade.find(solicitud.getEmisor().getId());
        
        //Añado los usuarios a la coleccion
        usu.getAmigos().add(usu2);
        usu2.getAmigos().add(usu);
        
        //Actualizo usuarios
        usuarioFacade.edit(usu);
        usuarioFacade.edit(usu2);
        
        //Busco la solicitud para eliminarla
        Solicitud solElimina = new Solicitud();
        solElimina = solicitudFacade.findByEmisorAndReceptor(usu2.getId(), usu.getId());
       
        usu.getSolicitudesRecibidas().remove(solicitud);
        //Elimino la solicitud
        solicitudFacade.remove(solicitud);
        
        
        
        
        init();
        
       
        return "main2";
       
    }
    

    public Usuario getUsu() {
        return usu;
    }

    public void setUsu(Usuario usu) {
        this.usu = usu;
    }

    public UsuarioBean getSesion() {
        return sesion;
    }

   
    public void setSesion(UsuarioBean sesion) {
        this.sesion = sesion;
    }

    public List<Solicitud> getListaSolicitudes() 
    {   
       // lista = usu.getSolicitudesRecibidas(); 
    
        for (int i=0;i<lista.size();i++)
        {
            if (lista.get(i).getVisto()!='F')
            {
                lista.remove(i);
            }
        }
        return lista;
    }
        
}

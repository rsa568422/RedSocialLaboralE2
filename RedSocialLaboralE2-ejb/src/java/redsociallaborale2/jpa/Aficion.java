/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package redsociallaborale2.jpa;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author NetBeans
 */
@Entity
@Table(name = "AFICION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Aficion.findAll", query = "SELECT a FROM Aficion a"),
    @NamedQuery(name = "Aficion.findByNombre", query = "SELECT a FROM Aficion a WHERE a.aficionPK.nombre = :nombre"),
    @NamedQuery(name = "Aficion.findByUsuario", query = "SELECT a FROM Aficion a WHERE a.aficionPK.usuario = :usuario"),
    // CONSULTAS PERSONALIZADAS
    // author: Roberto Sanchez
    @NamedQuery(name = "Aficion.findByIdUsuario", query = "SELECT a FROM Aficion a WHERE a.aficionPK.usuario = :id"),
    @NamedQuery(name = "Aficion.findByIdUsuarioAndNombreAficion", query = "SELECT a FROM Aficion a WHERE a.aficionPK.usuario = :id AND a.aficionPK.nombre = :nombre")})
public class Aficion implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected AficionPK aficionPK;
    @JoinColumn(name = "USUARIO", referencedColumnName = "ID", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Usuario usuario;

    public Aficion() {
    }

    public Aficion(AficionPK aficionPK) {
        this.aficionPK = aficionPK;
    }

    public Aficion(String nombre, BigInteger usuario) {
        this.aficionPK = new AficionPK(nombre, usuario);
    }

    public AficionPK getAficionPK() {
        return aficionPK;
    }

    public void setAficionPK(AficionPK aficionPK) {
        this.aficionPK = aficionPK;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (aficionPK != null ? aficionPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Aficion)) {
            return false;
        }
        Aficion other = (Aficion) object;
        if ((this.aficionPK == null && other.aficionPK != null) || (this.aficionPK != null && !this.aficionPK.equals(other.aficionPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "redsociallaborale2.jpa.Aficion[ aficionPK=" + aficionPK + " ]";
    }
    
}

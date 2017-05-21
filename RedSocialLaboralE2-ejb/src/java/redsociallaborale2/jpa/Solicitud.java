/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package redsociallaborale2.jpa;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author NetBeans
 */
@Entity
@Table(name = "SOLICITUD")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Solicitud.findAll", query = "SELECT s FROM Solicitud s"),
    @NamedQuery(name = "Solicitud.findByEmisor", query = "SELECT s FROM Solicitud s WHERE s.solicitudPK.emisor = :emisor"),
    @NamedQuery(name = "Solicitud.findByReceptor", query = "SELECT s FROM Solicitud s WHERE s.solicitudPK.receptor = :receptor"),
    @NamedQuery(name = "Solicitud.findByFecha", query = "SELECT s FROM Solicitud s WHERE s.fecha = :fecha"),
    @NamedQuery(name = "Solicitud.findByVisto", query = "SELECT s FROM Solicitud s WHERE s.visto = :visto"),
    // CONSULTAS PERSONALIZADAS
    // author: Inmaculada Sanchez
    @NamedQuery(name = "Solicitud.findByEmisorAndReceptor", query = "SELECT s from Solicitud s WHERE s.emisor.id = :emisor AND s.receptor.id = :receptor")})
public class Solicitud implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SolicitudPK solicitudPK;
    @Column(name = "FECHA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Column(name = "VISTO")
    private Character visto;
    @JoinColumn(name = "EMISOR", referencedColumnName = "ID", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Usuario emisor;
    @JoinColumn(name = "RECEPTOR", referencedColumnName = "ID", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Usuario receptor;

    public Solicitud() {
    }

    public Solicitud(SolicitudPK solicitudPK) {
        this.solicitudPK = solicitudPK;
    }

    public Solicitud(BigInteger emisor, BigInteger receptor) {
        this.solicitudPK = new SolicitudPK(emisor, receptor);
    }

    public SolicitudPK getSolicitudPK() {
        return solicitudPK;
    }

    public void setSolicitudPK(SolicitudPK solicitudPK) {
        this.solicitudPK = solicitudPK;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Character getVisto() {
        return visto;
    }

    public void setVisto(Character visto) {
        this.visto = visto;
    }

    public Usuario getEmisor() {
        return emisor;
    }

    public void setEmisor(Usuario emisor) {
        this.emisor = emisor;
    }

    public Usuario getReceptor() {
        return receptor;
    }

    public void setReceptor(Usuario receptor) {
        this.receptor = receptor;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (solicitudPK != null ? solicitudPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Solicitud)) {
            return false;
        }
        Solicitud other = (Solicitud) object;
        if ((this.solicitudPK == null && other.solicitudPK != null) || (this.solicitudPK != null && !this.solicitudPK.equals(other.solicitudPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "redsociallaborale2.jpa.Solicitud[ solicitudPK=" + solicitudPK + " ]";
    }
    
}

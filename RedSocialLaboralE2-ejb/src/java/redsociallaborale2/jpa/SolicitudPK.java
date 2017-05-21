/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package redsociallaborale2.jpa;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author NetBeans
 */
@Embeddable
public class SolicitudPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "EMISOR")
    private BigInteger emisor;
    @Basic(optional = false)
    @NotNull
    @Column(name = "RECEPTOR")
    private BigInteger receptor;

    public SolicitudPK() {
    }

    public SolicitudPK(BigInteger emisor, BigInteger receptor) {
        this.emisor = emisor;
        this.receptor = receptor;
    }

    public BigInteger getEmisor() {
        return emisor;
    }

    public void setEmisor(BigInteger emisor) {
        this.emisor = emisor;
    }

    public BigInteger getReceptor() {
        return receptor;
    }

    public void setReceptor(BigInteger receptor) {
        this.receptor = receptor;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (emisor != null ? emisor.hashCode() : 0);
        hash += (receptor != null ? receptor.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SolicitudPK)) {
            return false;
        }
        SolicitudPK other = (SolicitudPK) object;
        if ((this.emisor == null && other.emisor != null) || (this.emisor != null && !this.emisor.equals(other.emisor))) {
            return false;
        }
        if ((this.receptor == null && other.receptor != null) || (this.receptor != null && !this.receptor.equals(other.receptor))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "redsociallaborale2.jpa.SolicitudPK[ emisor=" + emisor + ", receptor=" + receptor + " ]";
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package redsociallaborale2.jpa;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author NetBeans
 */
@Entity
@Table(name = "ESTUDIOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Estudios.findAll", query = "SELECT e FROM Estudios e"),
    @NamedQuery(name = "Estudios.findById", query = "SELECT e FROM Estudios e WHERE e.id = :id"),
    @NamedQuery(name = "Estudios.findByFechaInicio", query = "SELECT e FROM Estudios e WHERE e.fechaInicio = :fechaInicio"),
    @NamedQuery(name = "Estudios.findByFechaFin", query = "SELECT e FROM Estudios e WHERE e.fechaFin = :fechaFin"),
    @NamedQuery(name = "Estudios.findByUbicacion", query = "SELECT e FROM Estudios e WHERE e.ubicacion = :ubicacion"),
    @NamedQuery(name = "Estudios.findByDescripcion", query = "SELECT e FROM Estudios e WHERE e.descripcion = :descripcion"),
    // CONSULTAS PERSONALIZADAS
    // author: Roberto Sanchez
    @NamedQuery(name = "Estudios.findByIdUsuario", query = "SELECT e FROM Estudios e WHERE e.usuario.id = :id")})
public class Estudios implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "ESTUDIOS_SEQ")
    @SequenceGenerator(name="ESTUDIOS_SEQ", sequenceName = "ESTUDIOS_SEQ", allocationSize=1)
    private BigInteger id;
    @Column(name = "FECHAINICIO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInicio;
    @Column(name = "FECHAFIN")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaFin;
    @Size(max = 100)
    @Column(name = "UBICACION")
    private String ubicacion;
    @Size(max = 200)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @JoinColumn(name = "USUARIO", referencedColumnName = "ID")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Usuario usuario;

    public Estudios() {
    }

    public Estudios(BigInteger id) {
        this.id = id;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Estudios)) {
            return false;
        }
        Estudios other = (Estudios) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "redsociallaborale2.jpa.Estudios[ id=" + id + " ]";
    }
    
}

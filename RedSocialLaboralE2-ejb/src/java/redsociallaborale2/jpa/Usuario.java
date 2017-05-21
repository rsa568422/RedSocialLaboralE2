/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package redsociallaborale2.jpa;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author NetBeans
 */
@Entity
@Table(name = "USUARIO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usuario.findAll", query = "SELECT u FROM Usuario u"),
    @NamedQuery(name = "Usuario.findById", query = "SELECT u FROM Usuario u WHERE u.id = :id"),
    @NamedQuery(name = "Usuario.findByEmail", query = "SELECT u FROM Usuario u WHERE u.email = :email"),
    @NamedQuery(name = "Usuario.findByPass", query = "SELECT u FROM Usuario u WHERE u.pass = :pass"),
    @NamedQuery(name = "Usuario.findByNombre", query = "SELECT u FROM Usuario u WHERE u.nombre = :nombre"),
    @NamedQuery(name = "Usuario.findByApellidos", query = "SELECT u FROM Usuario u WHERE u.apellidos = :apellidos"),
    @NamedQuery(name = "Usuario.findByTwitter", query = "SELECT u FROM Usuario u WHERE u.twitter = :twitter"),
    @NamedQuery(name = "Usuario.findByInstagram", query = "SELECT u FROM Usuario u WHERE u.instagram = :instagram"),
    @NamedQuery(name = "Usuario.findByWeb", query = "SELECT u FROM Usuario u WHERE u.web = :web"),
    @NamedQuery(name = "Usuario.findByFoto", query = "SELECT u FROM Usuario u WHERE u.foto = :foto"),
    // CONSULTAS PERSONALIZADAS
    // author: Inmaculada Sanchez
    @NamedQuery(name = "Usuario.findBySimilarName", query = "SELECT u from Usuario u WHERE u.nombre LIKE :nombre OR u.apellidos LIKE :nombre")})
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USUARIO_SEQ")
    @SequenceGenerator(name = "USUARIO_SEQ", sequenceName = "USUARIO_SEQ", allocationSize = 1)
    private BigInteger id;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "EMAIL")
    private String email;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "PASS")
    private String pass;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "NOMBRE")
    private String nombre;
    @Size(max = 50)
    @Column(name = "APELLIDOS")
    private String apellidos;
    @Size(max = 50)
    @Column(name = "TWITTER")
    private String twitter;
    @Size(max = 50)
    @Column(name = "INSTAGRAM")
    private String instagram;
    @Size(max = 50)
    @Column(name = "WEB")
    private String web;
    @Size(max = 100)
    @Column(name = "FOTO")
    private String foto;
    @JoinTable(name = "CONTACTO", joinColumns = {
        @JoinColumn(name = "CONTACTO", referencedColumnName = "ID")}, inverseJoinColumns = {
        @JoinColumn(name = "USUARIO", referencedColumnName = "ID")})
    @ManyToMany(fetch = FetchType.LAZY)
    private List<Usuario> amigos;
    @ManyToMany(mappedBy = "amigos", fetch = FetchType.LAZY)
    private List<Usuario> amigoDe;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuario", fetch = FetchType.LAZY)
    private List<Estudios> estudios;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuario", fetch = FetchType.LAZY)
    private List<Aficion> aficiones;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuario", fetch = FetchType.LAZY)
    private List<Experiencia> experiencia;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "emisor", fetch = FetchType.LAZY)
    private List<Solicitud> solicitudesEmitidas;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "receptor", fetch = FetchType.LAZY)
    private List<Solicitud> solicitudesRecibidas;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "emisor", fetch = FetchType.LAZY)
    private List<Mensaje> mensajesEmitidos;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "receptor", fetch = FetchType.LAZY)
    private List<Mensaje> mensajesRecibidos;

    public Usuario() {
    }

    public Usuario(BigInteger id) {
        this.id = id;
    }

    public Usuario(BigInteger id, String email, String pass, String nombre) {
        this.id = id;
        this.email = email;
        this.pass = pass;
        this.nombre = nombre;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getInstagram() {
        return instagram;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    @XmlTransient
    public List<Usuario> getAmigos() {
        return amigos;
    }

    public void setAmigos(List<Usuario> amigos) {
        this.amigos = amigos;
    }

    @XmlTransient
    public List<Usuario> getAmigoDe() {
        return amigoDe;
    }

    public void setAmigoDe(List<Usuario> amigoDe) {
        this.amigoDe = amigoDe;
    }

    @XmlTransient
    public List<Estudios> getEstudios() {
        return estudios;
    }

    public void setEstudios(List<Estudios> estudios) {
        this.estudios = estudios;
    }

    @XmlTransient
    public List<Aficion> getAficiones() {
        return aficiones;
    }

    public void setAficiones(List<Aficion> aficiones) {
        this.aficiones = aficiones;
    }

    @XmlTransient
    public List<Experiencia> getExperiencia() {
        return experiencia;
    }

    public void setExperiencia(List<Experiencia> experiencia) {
        this.experiencia = experiencia;
    }

    @XmlTransient
    public List<Solicitud> getSolicitudesEmitidas() {
        return solicitudesEmitidas;
    }

    public void setSolicitudesEmitidas(List<Solicitud> solicitudesEmitidas) {
        this.solicitudesEmitidas = solicitudesEmitidas;
    }

    @XmlTransient
    public List<Solicitud> getSolicitudesRecibidas() {
        return solicitudesRecibidas;
    }

    public void setSolicitudesRecibidas(List<Solicitud> solicitudesRecibidas) {
        this.solicitudesRecibidas = solicitudesRecibidas;
    }

    @XmlTransient
    public List<Mensaje> getMensajesEmitidos() {
        return mensajesEmitidos;
    }

    public void setMensajesEmitidos(List<Mensaje> mensajesEmitidos) {
        this.mensajesEmitidos = mensajesEmitidos;
    }

    @XmlTransient
    public List<Mensaje> getMensajesRecibidos() {
        return mensajesRecibidos;
    }

    public void setMensajesRecibidos(List<Mensaje> mensajesRecibidos) {
        this.mensajesRecibidos = mensajesRecibidos;
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
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "redsociallaborale2.jpa.Usuario[ id=" + id + " ]";
    }

}

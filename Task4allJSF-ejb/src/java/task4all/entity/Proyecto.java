/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package task4all.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;


@Entity
@Table(name = "PROYECTO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Proyecto.findAll", query = "SELECT p FROM Proyecto p"),
    @NamedQuery(name = "Proyecto.findById", query = "SELECT p FROM Proyecto p WHERE p.id = :id"),
    @NamedQuery(name = "Proyecto.findByNombre", query = "SELECT p FROM Proyecto p WHERE p.nombre = :nombre"),
    @NamedQuery(name = "Proyecto.findByDescripcion", query = "SELECT p FROM Proyecto p WHERE p.descripcion = :descripcion"),
    @NamedQuery(name = "Proyecto.findByFechacreacion", query = "SELECT p FROM Proyecto p WHERE p.fechacreacion = :fechacreacion"),
    @NamedQuery(name = "Proyecto.findByFechaobjetivo", query = "SELECT p FROM Proyecto p WHERE p.fechaobjetivo = :fechaobjetivo"),
    @NamedQuery(name = "Proyecto.findByFechafin", query = "SELECT p FROM Proyecto p WHERE p.fechafin = :fechafin")})
public class Proyecto implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "SEQ_PROYECTO")
    @SequenceGenerator(name="SEQ_PROYECTO", sequenceName = "PROYECTO_id_SEQ", allocationSize=1)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "NOMBRE")
    private String nombre;
    @Size(max = 500)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHACREACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechacreacion;
    @Column(name = "FECHAOBJETIVO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaobjetivo;
    @Column(name = "FECHAFIN")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechafin;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "proyectoId")
    private Collection<Lista> listaCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "proyectoId")
    private Collection<Comentario> comentarioCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "proyectoId")
    private Collection<Actividad> actividadCollection;
    @JoinColumn(name = "FONDO_ID", referencedColumnName = "ID")
    @ManyToOne
    private Fondo fondoId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "proyectoId")
    private Collection<UsuarioProyecto> usuarioProyectoCollection;

    public Proyecto() {
    }

    public Proyecto(Integer id) {
        this.id = id;
    }

    public Proyecto(Integer id, String nombre, Date fechacreacion) {
        this.id = id;
        this.nombre = nombre;
        this.fechacreacion = fechacreacion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechacreacion() {
        return fechacreacion;
    }

    public void setFechacreacion(Date fechacreacion) {
        this.fechacreacion = fechacreacion;
    }

    public Date getFechaobjetivo() {
        return fechaobjetivo;
    }

    public void setFechaobjetivo(Date fechaobjetivo) {
        this.fechaobjetivo = fechaobjetivo;
    }

    public Date getFechafin() {
        return fechafin;
    }

    public void setFechafin(Date fechafin) {
        this.fechafin = fechafin;
    }

    @XmlTransient
    public Collection<Lista> getListaCollection() {
        return listaCollection;
    }

    public void setListaCollection(Collection<Lista> listaCollection) {
        this.listaCollection = listaCollection;
    }

    @XmlTransient
    public Collection<Comentario> getComentarioCollection() {
        return comentarioCollection;
    }

    public void setComentarioCollection(Collection<Comentario> comentarioCollection) {
        this.comentarioCollection = comentarioCollection;
    }

    @XmlTransient
    public Collection<Actividad> getActividadCollection() {
        return actividadCollection;
    }

    public void setActividadCollection(Collection<Actividad> actividadCollection) {
        this.actividadCollection = actividadCollection;
    }

    public Fondo getFondoId() {
        return fondoId;
    }

    public void setFondoId(Fondo fondoId) {
        this.fondoId = fondoId;
    }

    @XmlTransient
    public Collection<UsuarioProyecto> getUsuarioProyectoCollection() {
        return usuarioProyectoCollection;
    }

    public void setUsuarioProyectoCollection(Collection<UsuarioProyecto> usuarioProyectoCollection) {
        this.usuarioProyectoCollection = usuarioProyectoCollection;
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
        if (!(object instanceof Proyecto)) {
            return false;
        }
        Proyecto other = (Proyecto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "task4all.entity.Proyecto[ id=" + id + " ]";
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package task4all.entity;

import java.io.Serializable;
import java.math.BigInteger;
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

/**
 *
 * @author aidag
 */
@Entity
@Table(name = "TAREA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tarea.findAll", query = "SELECT t FROM Tarea t"),
    @NamedQuery(name = "Tarea.findById", query = "SELECT t FROM Tarea t WHERE t.id = :id"),
    @NamedQuery(name = "Tarea.findByNombre", query = "SELECT t FROM Tarea t WHERE t.nombre = :nombre"),
    @NamedQuery(name = "Tarea.findByDescripcion", query = "SELECT t FROM Tarea t WHERE t.descripcion = :descripcion"),
    @NamedQuery(name = "Tarea.findByFechacreacion", query = "SELECT t FROM Tarea t WHERE t.fechacreacion = :fechacreacion"),
    @NamedQuery(name = "Tarea.findByFechaobjetivo", query = "SELECT t FROM Tarea t WHERE t.fechaobjetivo = :fechaobjetivo"),
    @NamedQuery(name = "Tarea.findByFechafin", query = "SELECT t FROM Tarea t WHERE t.fechafin = :fechafin"),
    @NamedQuery(name = "Tarea.findByPrioridad", query = "SELECT t FROM Tarea t WHERE t.prioridad = :prioridad"),
    @NamedQuery(name = "Tarea.findByLista", query = "SELECT t FROM Tarea t WHERE t.listaId.id = :id")})
public class Tarea implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "SEQ_TAREA")
    @SequenceGenerator(name="SEQ_TAREA", sequenceName = "TAREA_id_SEQ", allocationSize=1)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "NOMBRE")
    private String nombre;
    @Size(max = 600)
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
    @Basic(optional = false)
    @NotNull
    @Column(name = "PRIORIDAD")
    private BigInteger prioridad;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tareaId")
    private Collection<Adjunto> adjuntoCollection;
    @OneToMany(mappedBy = "tareaId")
    private Collection<Actividad> actividadCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tareaId")
    private Collection<UsuarioTarea> usuarioTareaCollection;
    @JoinColumn(name = "LISTA_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Lista listaId;

    public Tarea() {
    }

    public Tarea(Integer id) {
        this.id = id;
    }

    public Tarea(Integer id, String nombre, Date fechacreacion, BigInteger prioridad) {
        this.id = id;
        this.nombre = nombre;
        this.fechacreacion = fechacreacion;
        this.prioridad = prioridad;
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

    public BigInteger getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(BigInteger prioridad) {
        this.prioridad = prioridad;
    }

    @XmlTransient
    public Collection<Adjunto> getAdjuntoCollection() {
        return adjuntoCollection;
    }

    public void setAdjuntoCollection(Collection<Adjunto> adjuntoCollection) {
        this.adjuntoCollection = adjuntoCollection;
    }

    @XmlTransient
    public Collection<Actividad> getActividadCollection() {
        return actividadCollection;
    }

    public void setActividadCollection(Collection<Actividad> actividadCollection) {
        this.actividadCollection = actividadCollection;
    }

    @XmlTransient
    public Collection<UsuarioTarea> getUsuarioTareaCollection() {
        return usuarioTareaCollection;
    }

    public void setUsuarioTareaCollection(Collection<UsuarioTarea> usuarioTareaCollection) {
        this.usuarioTareaCollection = usuarioTareaCollection;
    }

    public Lista getListaId() {
        return listaId;
    }

    public void setListaId(Lista listaId) {
        this.listaId = listaId;
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
        if (!(object instanceof Tarea)) {
            return false;
        }
        Tarea other = (Tarea) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "task4all.entity.Tarea[ id=" + id + " ]";
    }
    
}

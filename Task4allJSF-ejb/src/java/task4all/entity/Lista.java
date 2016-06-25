/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package task4all.entity;

import java.io.Serializable;
import java.util.Collection;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author aidag
 */
@Entity
@Table(name = "LISTA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Lista.findAll", query = "SELECT l FROM Lista l"),
    @NamedQuery(name = "Lista.findById", query = "SELECT l FROM Lista l WHERE l.id = :id"),
    @NamedQuery(name = "Lista.findByNombre", query = "SELECT l FROM Lista l WHERE l.nombre = :nombre"),
    @NamedQuery(name = "Lista.findByDescripcion", query = "SELECT l FROM Lista l WHERE l.descripcion = :descripcion"),
    @NamedQuery(name = "Lista.findByProyecto", query = "SELECT l FROM Lista l WHERE l.proyectoId.id = :id")})
public class Lista implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "SEQ_LISTA")
    @SequenceGenerator(name="SEQ_LISTA", sequenceName = "LISTA_id_SEQ", allocationSize=1)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "NOMBRE")
    private String nombre;
    @Size(max = 100)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @JoinColumn(name = "PROYECTO_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Proyecto proyectoId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "listaId")
    private Collection<Tarea> tareaCollection;

    public Lista() {
    }

    public Lista(Integer id) {
        this.id = id;
    }

    public Lista(Integer id, String nombre) {
        this.id = id;
        this.nombre = nombre;
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

    public Proyecto getProyectoId() {
        return proyectoId;
    }

    public void setProyectoId(Proyecto proyectoId) {
        this.proyectoId = proyectoId;
    }

    @XmlTransient
    public Collection<Tarea> getTareaCollection() {
        return tareaCollection;
    }

    public void setTareaCollection(Collection<Tarea> tareaCollection) {
        this.tareaCollection = tareaCollection;
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
        if (!(object instanceof Lista)) {
            return false;
        }
        Lista other = (Lista) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "task4all.entity.Lista[ id=" + id + " ]";
    }
    
}

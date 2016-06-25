/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package task4all.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author aidag
 */
@Entity
@Table(name = "FONDO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Fondo.findAll", query = "SELECT f FROM Fondo f"),
    @NamedQuery(name = "Fondo.findById", query = "SELECT f FROM Fondo f WHERE f.id = :id"),
    @NamedQuery(name = "Fondo.findByOscuro", query = "SELECT f FROM Fondo f WHERE f.oscuro = :oscuro")})
public class Fondo implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "SEQ_FONDO")
    @SequenceGenerator(name="SEQ_FONDO", sequenceName = "FONDO_id_SEQ", allocationSize=1)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Column(name = "FONDO")
    private Serializable fondo;
    @Column(name = "OSCURO")
    private Character oscuro;
    @OneToMany(mappedBy = "fondoId")
    private Collection<Proyecto> proyectoCollection;

    public Fondo() {
    }

    public Fondo(Integer id) {
        this.id = id;
    }

    public Fondo(Integer id, Serializable fondo) {
        this.id = id;
        this.fondo = fondo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Serializable getFondo() {
        return fondo;
    }

    public void setFondo(Serializable fondo) {
        this.fondo = fondo;
    }

    public Character getOscuro() {
        return oscuro;
    }

    public void setOscuro(Character oscuro) {
        this.oscuro = oscuro;
    }

    @XmlTransient
    public Collection<Proyecto> getProyectoCollection() {
        return proyectoCollection;
    }

    public void setProyectoCollection(Collection<Proyecto> proyectoCollection) {
        this.proyectoCollection = proyectoCollection;
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
        if (!(object instanceof Fondo)) {
            return false;
        }
        Fondo other = (Fondo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "task4all.entity.Fondo[ id=" + id + " ]";
    }
    
}

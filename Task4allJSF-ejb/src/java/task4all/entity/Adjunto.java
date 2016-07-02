/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package task4all.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;


@Entity
@Table(name = "ADJUNTO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Adjunto.findAll", query = "SELECT a FROM Adjunto a"),
    @NamedQuery(name = "Adjunto.findById", query = "SELECT a FROM Adjunto a WHERE a.id = :id"),
    @NamedQuery(name = "Adjunto.findByNombre", query = "SELECT a FROM Adjunto a WHERE a.nombre = :nombre"),
    @NamedQuery(name = "Adjunto.findByTipo", query = "SELECT a FROM Adjunto a WHERE a.tipo = :tipo"),
    @NamedQuery(name = "Adjunto.findByTamano", query = "SELECT a FROM Adjunto a WHERE a.tamano = :tamano"),
    @NamedQuery(name = "Adjunto.findByUrl", query = "SELECT a FROM Adjunto a WHERE a.url = :url")})
public class Adjunto implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "SEQ_ADJUNTO")
    @SequenceGenerator(name="SEQ_ADJUNTO", sequenceName = "ADJUNTO_id_SEQ", allocationSize=1)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 180)
    @Column(name = "NOMBRE")
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "TIPO")
    private String tipo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TAMANO")
    private Integer tamano;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3500)
    @Column(name = "URL")
    private String url;
    @JoinColumn(name = "TAREA_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Tarea tareaId;
    @JoinColumn(name = "USUARIO_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Usuario usuarioId;

    public Adjunto() {
    }

    public Adjunto(Integer id) {
        this.id = id;
    }

    public Adjunto(Integer id, String tipo, Integer tamano, String url) {
        this.id = id;
        this.tipo = tipo;
        this.tamano = tamano;
        this.url = url;
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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Integer getTamano() {
        return tamano;
    }

    public void setTamano(Integer tamano) {
        this.tamano = tamano;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Tarea getTareaId() {
        return tareaId;
    }

    public void setTareaId(Tarea tareaId) {
        this.tareaId = tareaId;
    }

    public Usuario getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Usuario usuarioId) {
        this.usuarioId = usuarioId;
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
        if (!(object instanceof Adjunto)) {
            return false;
        }
        Adjunto other = (Adjunto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "task4all.entity.Adjunto[ id=" + id + " ]";
    }
    
}

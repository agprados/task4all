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
@Table(name = "USUARIO_PROYECTO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UsuarioProyecto.findAll", query = "SELECT u FROM UsuarioProyecto u"),
    @NamedQuery(name = "UsuarioProyecto.findById", query = "SELECT u FROM UsuarioProyecto u WHERE u.id = :id"),
    @NamedQuery(name = "UsuarioProyecto.findByRol", query = "SELECT u FROM UsuarioProyecto u WHERE u.rol = :rol"),
    @NamedQuery(name = "UsuarioProyecto.findByUsuario", query = "SELECT u FROM UsuarioProyecto u WHERE u.usuarioUsuario.usuario = :u"),
    @NamedQuery(name = "UsuarioProyecto.findByProyecto", query = "SELECT u FROM UsuarioProyecto u WHERE u.proyectoId.id = :id"),
    @NamedQuery(name = "UsuarioProyecto.findByEmailAndProyecto", query = "SELECT u FROM UsuarioProyecto u WHERE u.usuarioUsuario.email = :email AND u.proyectoId.id = :id"),
    @NamedQuery(name = "UsuarioProyecto.findByUsuarioAndProyecto", query = "SELECT u FROM UsuarioProyecto u WHERE u.usuarioUsuario.usuario = :u AND u.proyectoId.id = :id")})
public class UsuarioProyecto implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "SEQ_USUARIO_PROYECTO")
    @SequenceGenerator(name="SEQ_USUARIO_PROYECTO", sequenceName = "USUARIO_PROYECTO_id_SEQ", allocationSize=1)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "ROL")
    private String rol;
    @JoinColumn(name = "PROYECTO_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Proyecto proyectoId;
    @JoinColumn(name = "USUARIO_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Usuario usuarioId;

    public UsuarioProyecto() {
    }

    public UsuarioProyecto(Integer id) {
        this.id = id;
    }

    public UsuarioProyecto(Integer id, String rol) {
        this.id = id;
        this.rol = rol;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public Proyecto getProyectoId() {
        return proyectoId;
    }

    public void setProyectoId(Proyecto proyectoId) {
        this.proyectoId = proyectoId;
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
        if (!(object instanceof UsuarioProyecto)) {
            return false;
        }
        UsuarioProyecto other = (UsuarioProyecto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "task4all.entity.UsuarioProyecto[ id=" + id + " ]";
    }
    
}

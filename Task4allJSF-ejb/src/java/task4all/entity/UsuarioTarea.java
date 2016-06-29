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
import javax.xml.bind.annotation.XmlRootElement;


@Entity
@Table(name = "USUARIO_TAREA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UsuarioTarea.findAll", query = "SELECT u FROM UsuarioTarea u"),
    @NamedQuery(name = "UsuarioTarea.findById", query = "SELECT u FROM UsuarioTarea u WHERE u.id = :id"),
    @NamedQuery(name = "UsuarioTarea.findByUsuarioUsuario", query = "SELECT u FROM UsuarioTarea u WHERE u.usuarioUsuario = :usuarioUsuario"),
    @NamedQuery(name = "UsuarioTarea.findByTarea", query = "SELECT u FROM UsuarioTarea u WHERE u.tareaId.id = :id"),
    @NamedQuery(name = "UsuarioTarea.findByTareaAndUsuario", query = "SELECT u FROM UsuarioTarea u WHERE u.tareaId.id = :id AND u.usuarioUsuario.usuario = :u")})
public class UsuarioTarea implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "SEQ_USUARIO_TAREA")
    @SequenceGenerator(name="SEQ_USUARIO_TAREA", sequenceName = "USUARIO_TAREA_id_SEQ", allocationSize=1)
    private Integer id;
    @JoinColumn(name = "TAREA_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Tarea tareaId;
    @JoinColumn(name = "USUARIO_USUARIO", referencedColumnName = "USUARIO")
    @ManyToOne(optional = false)
    private Usuario usuarioUsuario;

    public UsuarioTarea() {
    }

    public UsuarioTarea(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Tarea getTareaId() {
        return tareaId;
    }

    public void setTareaId(Tarea tareaId) {
        this.tareaId = tareaId;
    }

    public Usuario getUsuarioUsuario() {
        return usuarioUsuario;
    }

    public void setUsuarioUsuario(Usuario usuarioUsuario) {
        this.usuarioUsuario = usuarioUsuario;
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
        if (!(object instanceof UsuarioTarea)) {
            return false;
        }
        UsuarioTarea other = (UsuarioTarea) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "task4all.entity.UsuarioTarea[ id=" + id + " ]";
    }

}

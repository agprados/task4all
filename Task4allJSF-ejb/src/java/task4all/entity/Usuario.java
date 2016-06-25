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
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
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
@Table(name = "USUARIO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usuario.findAll", query = "SELECT u FROM Usuario u"),
    @NamedQuery(name = "Usuario.findByUsuario", query = "SELECT u FROM Usuario u WHERE u.usuario = :usuario"),
    @NamedQuery(name = "Usuario.findByContrasena", query = "SELECT u FROM Usuario u WHERE u.contrasena = :contrasena"),
    @NamedQuery(name = "Usuario.findByEmail", query = "SELECT u FROM Usuario u WHERE u.email = :email"),
    @NamedQuery(name = "Usuario.findByNombre", query = "SELECT u FROM Usuario u WHERE u.nombre = :nombre"),
    @NamedQuery(name = "Usuario.findByApellidos", query = "SELECT u FROM Usuario u WHERE u.apellidos = :apellidos"),
    @NamedQuery(name = "Usuario.findByVerificado", query = "SELECT u FROM Usuario u WHERE u.verificado = :verificado"),
    @NamedQuery(name = "Usuario.findByUsuarioAndContrasena", query = "SELECT u FROM Usuario u WHERE u.usuario = :usuario AND u.contrasena = :contrasena"),
    @NamedQuery(name = "Usuario.findByEmailAndContrasena", query = "SELECT u FROM Usuario u WHERE u.email = :email AND u.contrasena = :contrasena"),
    @NamedQuery(name = "Usuario.findByUsuarioOrEmail", query = "SELECT u FROM Usuario u WHERE u.usuario = :usuario OR u.email = :email")})
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "USUARIO")
    private String usuario;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 25)
    @Column(name = "CONTRASENA")
    private String contrasena;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 80)
    @Column(name = "EMAIL")
    private String email;
    @Size(max = 30)
    @Column(name = "NOMBRE")
    private String nombre;
    @Size(max = 80)
    @Column(name = "APELLIDOS")
    private String apellidos;
    @Lob
    @Column(name = "AVATAR")
    private Serializable avatar;
    @Basic(optional = false)
    @NotNull
    @Column(name = "VERIFICADO")
    private Character verificado;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuarioUsuario")
    private Collection<Comentario> comentarioCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuarioUsuario")
    private Collection<Actividad> actividadCollection;
    @OneToMany(mappedBy = "usuarioUsuariomencionado")
    private Collection<Actividad> actividadCollection1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuarioUsuario")
    private Collection<UsuarioTarea> usuarioTareaCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuarioUsuario")
    private Collection<UsuarioProyecto> usuarioProyectoCollection;

    public Usuario() {
    }

    public Usuario(String usuario) {
        this.usuario = usuario;
    }

    public Usuario(String usuario, String contrasena, String email, Character verificado) {
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.email = email;
        this.verificado = verificado;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public Serializable getAvatar() {
        return avatar;
    }

    public void setAvatar(Serializable avatar) {
        this.avatar = avatar;
    }

    public Character getVerificado() {
        return verificado;
    }

    public void setVerificado(Character verificado) {
        this.verificado = verificado;
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

    @XmlTransient
    public Collection<Actividad> getActividadCollection1() {
        return actividadCollection1;
    }

    public void setActividadCollection1(Collection<Actividad> actividadCollection1) {
        this.actividadCollection1 = actividadCollection1;
    }

    @XmlTransient
    public Collection<UsuarioTarea> getUsuarioTareaCollection() {
        return usuarioTareaCollection;
    }

    public void setUsuarioTareaCollection(Collection<UsuarioTarea> usuarioTareaCollection) {
        this.usuarioTareaCollection = usuarioTareaCollection;
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
        hash += (usuario != null ? usuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        if ((this.usuario == null && other.usuario != null) || (this.usuario != null && !this.usuario.equals(other.usuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "task4all.entity.Usuario[ usuario=" + usuario + " ]";
    }
    
}

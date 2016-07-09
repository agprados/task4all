/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package task4all.utils;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import task4all.ejb.ActividadFacade;
import task4all.ejb.AdjuntoFacade;
import task4all.ejb.ComentarioFacade;
import task4all.ejb.FondoFacade;
import task4all.ejb.ListaFacade;
import task4all.ejb.ProyectoFacade;
import task4all.ejb.TareaFacade;
import task4all.ejb.UsuarioFacade;
import task4all.ejb.UsuarioProyectoFacade;
import task4all.ejb.UsuarioTareaFacade;
import task4all.entity.Actividad;
import task4all.entity.Adjunto;
import task4all.entity.Comentario;
import task4all.entity.Fondo;
import task4all.entity.Lista;
import task4all.entity.Proyecto;
import task4all.entity.Tarea;
import task4all.entity.Usuario;
import task4all.entity.UsuarioProyecto;
import task4all.entity.UsuarioTarea;

@ManagedBean(eager = true)
@ApplicationScoped
public class ProyectoBienvenida {

    @EJB
    private UsuarioProyectoFacade usuarioProyectoFacade;
    @EJB
    private ProyectoFacade proyectoFacade;
    @EJB
    private FondoFacade fondoFacade;
    @EJB
    private UsuarioFacade usuarioFacade;
    @EJB
    private ListaFacade listaFacade;
    @EJB
    private TareaFacade tareaFacade;
    @EJB
    private ActividadFacade actividadFacade;
    @EJB
    private ComentarioFacade comentarioFacade;
    @EJB
    private AdjuntoFacade adjuntoFacade;
    @EJB
    private UsuarioTareaFacade usuarioTareaFacade;

    public ProyectoBienvenida() {

    }

    @PostConstruct
    public void init() {
        Usuario usuario = usuarioFacade.findUsuarioByUsuario("Invitado");
        if (usuario == null) {
            usuario = new Usuario();
            String uuid;
            boolean exists;
            do {
                uuid = UUID.randomUUID().toString();
                exists = usuarioFacade.findUsuarioByUUID(uuid) != null;
            } while (exists);
            usuario.setUsuario("Invitado");
            usuario.setUuid(uuid);
            usuario.setEmail("invitado.noreply@gmail.com");
            usuario.setContrasena("task4all4");
            usuario.setNombre("Invi");
            usuario.setApellidos("Tado");
            usuario.setVerificado('1');
            this.usuarioFacade.create(usuario);
        }

        usuario = usuarioFacade.findUsuarioByUsuario("Sr. Task4all");
        if (usuario == null) {
            usuario = new Usuario();
            String uuid;
            boolean exists;
            do {
                uuid = UUID.randomUUID().toString();
                exists = usuarioFacade.findUsuarioByUUID(uuid) != null;
            } while (exists);
            usuario.setUsuario("Sr. Task4all");
            usuario.setUuid(uuid);
            usuario.setEmail("task4all.noreply@gmail.com");
            usuario.setContrasena("task4all4");
            usuario.setNombre("Task");
            usuario.setApellidos("4 all");
            usuario.setVerificado('1');
            this.usuarioFacade.create(usuario);
        }
        
        List<Fondo> fondos = fondoFacade.findAll();
        if (fondos.isEmpty()) {
            cargarFondos();
        }
    }

    public void crearProyectoBienvenida(Usuario usuario) {

        // Primero se crea un proyecto
        Proyecto proyecto = new Proyecto();
        proyecto.setNombre("Proyecto de bienvenida");
        proyecto.setFechacreacion(new Date());
        proyecto.setDescripcion("Este es un proyecto en el que se muestran algunas funciones que podrás realizar en Task4all.\n Anímate a probarlo todo.");
        Fondo fondo = fondoFacade.findFondoByNombre("fondo_default.png");
        proyecto.setFondoId(fondo);
        proyectoFacade.create(proyecto);
        int claveProyecto = proyectoFacade.findMaxProyectoId();
        proyecto.setId(claveProyecto);

        // Luego se asigna al usuario
        UsuarioProyecto up = new UsuarioProyecto();
        up.setRol("líder");
        up.setProyectoId(proyecto);
        up.setUsuarioId(usuario);
        usuarioProyectoFacade.create(up);

        // Se añade el usuario invitado como invitado al proyecto
        Usuario u = usuarioFacade.findUsuarioByUsuario("Invitado");
        up = new UsuarioProyecto();
        up.setRol("invitado");
        up.setProyectoId(proyecto);
        up.setUsuarioId(u);
        usuarioProyectoFacade.create(up);

        // Se añade el usuario Sr. Task4all como miembro al proyecto (con avatar personalizado)
        u = usuarioFacade.findUsuarioByUsuario("Sr. Task4all");
        up = new UsuarioProyecto();
        up.setRol("miembro");
        up.setProyectoId(proyecto);
        up.setUsuarioId(u);
        usuarioProyectoFacade.create(up);

        // Ahora se crea un par de listas
        Lista lista = new Lista();
        lista.setNombre("Una lista con tareas");
        lista.setProyectoId(proyecto);
        listaFacade.create(lista);
        Integer clave = listaFacade.findMaxListaId();
        lista.setId(clave);

        Lista lista2 = new Lista();
        lista2.setNombre("Una lista vacía");
        lista2.setDescripcion("A las listas se les puede añadir una pequeña descripción");
        lista2.setProyectoId(proyecto);
        listaFacade.create(lista2);
        clave = listaFacade.findMaxListaId();
        lista2.setId(clave);

        // Se crea otra tarea con usuarios asignados
        Tarea tarea = new Tarea();
        tarea.setListaId(lista);
        tarea.setPrioridad(new BigInteger("1"));
        tarea.setFechacreacion(new Date());
        tarea.setNombre("Pulsa en la tarea para ver más información");
        tarea.setDescripcion("Como puedes ver más abajo a las tareas se les puede asignar usuarios que están dentro del proyecto");
        tareaFacade.create(tarea);
        clave = tareaFacade.findMaxTareaId();
        tarea.setId(clave);

        UsuarioTarea ut = new UsuarioTarea();
        ut.setTareaId(tarea);
        ut.setUsuarioId(usuario);
        usuarioTareaFacade.create(ut);

        // Se crea una tarea con fecha objetivo y prioridad alta
        tarea = new Tarea();
        tarea.setListaId(lista);
        tarea.setPrioridad(new BigInteger("2"));
        tarea.setFechacreacion(new Date());
        SimpleDateFormat formatter = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        String dateInString = "16-07-2016 09:00:00";
        Date date = new Date();
        try {
            date = formatter.parse(dateInString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        tarea.setFechaobjetivo(date);
        tarea.setNombre("Un tarea con prioridad alta y fecha objetivo");
        tarea.setDescripcion("");
        tareaFacade.create(tarea);
        clave = tareaFacade.findMaxTareaId();
        tarea.setId(clave);

        // Se crea otra tarea con adjunto
        tarea = new Tarea();
        tarea.setListaId(lista);
        tarea.setPrioridad(new BigInteger("0"));
        tarea.setFechacreacion(new Date());
        tarea.setNombre("Se pueden adjuntar ficheros a las tareas");
        tarea.setDescripcion("");
        tareaFacade.create(tarea);
        clave = tareaFacade.findMaxTareaId();
        tarea.setId(clave);

        Adjunto adjunto = new Adjunto();
        adjunto.setNombre("baby-seal-wallpaper.jpg");
        adjunto.setTamano(252007);
        adjunto.setTareaId(tarea);
        adjunto.setTipo(".jpg");
        adjunto.setUrl("/adjuntos/baby-seal-wallpaper.jpg");
        adjunto.setUsuarioId(u);
        adjuntoFacade.create(adjunto);
        clave = adjuntoFacade.findMaxAdjuntoId();
        adjunto.setId(clave);

        // Se añaden un par de comentarios al proyecto
        Comentario comentario = new Comentario();
        comentario.setContenido("Puedes escribir comentarios en los proyectos. Prueba a escribir uno.");
        comentario.setFecha(new Date());
        comentario.setProyectoId(proyecto);
        comentario.setUsuarioId(u);
        Actividad actividad = crearActividad(u, proyecto);
        comentario.setActividadId(actividad);
        comentarioFacade.create(comentario);
        clave = comentarioFacade.findMaxComentarioId();
        comentario.setId(clave);
        actividad.setComentario(comentario);
        actividadFacade.edit(actividad);
    }

    private Actividad crearActividad(Usuario u, Proyecto p) {
        Actividad actividad = new Actividad();

        actividad.setDescripcion("Ha comentado");
        actividad.setFecha(new Date());
        actividad.setProyectoId(p);
        actividad.setUsuarioId(u);

        actividadFacade.create(actividad);
        int clave = actividadFacade.findMaxActividadId();
        actividad.setId(clave);

        p.getActividadCollection().add(actividad);
        proyectoFacade.edit(p);

        return actividad;
    }
    
    private void cargarFondos() {
        
        // Fondo por defecto (blanco)
        Fondo f = new Fondo();
        f.setNombre("fondo_default.png");
        f.setOscuro('0');
        f.setUrl("/images/fondos/fondo_default.png");        
        fondoFacade.create(f);
        
        // Colores sólidos primero (primero claros, luego oscuros)
        
        // Fondo claro 1
        f = new Fondo();
        f.setNombre("fondo_claro_1.png");
        f.setOscuro('0');
        f.setUrl("/images/fondos/fondo_claro_1.png");        
        fondoFacade.create(f);
        
        // Fondo claro 2
        f = new Fondo();
        f.setNombre("fondo_claro_2.png");
        f.setOscuro('0');
        f.setUrl("/images/fondos/fondo_claro_2.png");        
        fondoFacade.create(f);
        
        // Fondo claro 3
        f = new Fondo();
        f.setNombre("fondo_claro_3.png");
        f.setOscuro('0');
        f.setUrl("/images/fondos/fondo_claro_3.png");        
        fondoFacade.create(f);
        
        // Fondo claro 4
        f = new Fondo();
        f.setNombre("fondo_claro_4.png");
        f.setOscuro('0');
        f.setUrl("/images/fondos/fondo_claro_4.png");        
        fondoFacade.create(f);
        
        // Fondo claro 5
        f = new Fondo();
        f.setNombre("fondo_claro_5.png");
        f.setOscuro('0');
        f.setUrl("/images/fondos/fondo_claro_5.png");        
        fondoFacade.create(f);
        
        // Fondo oscuro 1
        f = new Fondo();
        f.setNombre("fondo_oscuro_1.jpg");
        f.setOscuro('1');
        f.setUrl("/images/fondos/fondo_oscuro_1.jpg");        
        fondoFacade.create(f);
        
        // Fondo oscuro 2
        f = new Fondo();
        f.setNombre("fondo_oscuro_2.jpg");
        f.setOscuro('1');
        f.setUrl("/images/fondos/fondo_oscuro_2.jpg");        
        fondoFacade.create(f);
        
        // El resto de fondos (claros y oscuros intercalados)
        
        // Fondo claro 6
        f = new Fondo();
        f.setNombre("fondo_claro_6.png");
        f.setOscuro('0');
        f.setUrl("/images/fondos/fondo_claro_6.png");        
        fondoFacade.create(f);
        
        // Fondo oscuro 3
        f = new Fondo();
        f.setNombre("fondo_oscuro_3.jpg");
        f.setOscuro('1');
        f.setUrl("/images/fondos/fondo_oscuro_3.jpg");        
        fondoFacade.create(f);
        
        // Fondo claro 7
        f = new Fondo();
        f.setNombre("fondo_claro_7.png");
        f.setOscuro('0');
        f.setUrl("/images/fondos/fondo_claro_7.png");        
        fondoFacade.create(f);
        
        // Fondo oscuro 4
        f = new Fondo();
        f.setNombre("fondo_oscuro_4.jpg");
        f.setOscuro('1');
        f.setUrl("/images/fondos/fondo_oscuro_4.jpg");        
        fondoFacade.create(f);
        
        // Fondo claro 8
        f = new Fondo();
        f.setNombre("fondo_claro_8.jpg");
        f.setOscuro('0');
        f.setUrl("/images/fondos/fondo_claro_8.jpg");        
        fondoFacade.create(f);
        
        // Fondo oscuro 5
        f = new Fondo();
        f.setNombre("fondo_oscuro_5.jpg");
        f.setOscuro('1');
        f.setUrl("/images/fondos/fondo_oscuro_5.jpg");        
        fondoFacade.create(f);
        
        // Fondo claro 9
        f = new Fondo();
        f.setNombre("fondo_claro_9.jpg");
        f.setOscuro('0');
        f.setUrl("/images/fondos/fondo_claro_9.jpg");        
        fondoFacade.create(f);
        
        // Fondo oscuro 6
        f = new Fondo();
        f.setNombre("fondo_oscuro_6.gif");
        f.setOscuro('1');
        f.setUrl("/images/fondos/fondo_oscuro_6.gif");        
        fondoFacade.create(f);
        
        // Fondo claro 10
        f = new Fondo();
        f.setNombre("fondo_claro_10.gif");
        f.setOscuro('0');
        f.setUrl("/images/fondos/fondo_claro_10.gif");        
        fondoFacade.create(f);
        
        // Fondo oscuro 7
        f = new Fondo();
        f.setNombre("fondo_oscuro_7.png");
        f.setOscuro('1');
        f.setUrl("/images/fondos/fondo_oscuro_7.png");        
        fondoFacade.create(f);
        
        // Fondo claro 11
        f = new Fondo();
        f.setNombre("fondo_claro_11.jpg");
        f.setOscuro('0');
        f.setUrl("/images/fondos/fondo_claro_11.jpg");        
        fondoFacade.create(f);
        
        // Fondo oscuro 8
        f = new Fondo();
        f.setNombre("fondo_oscuro_8.jpg");
        f.setOscuro('1');
        f.setUrl("/images/fondos/fondo_oscuro_8.jpg");        
        fondoFacade.create(f);
        
        // Fondo claro 12
        f = new Fondo();
        f.setNombre("fondo_claro_12.png");
        f.setOscuro('0');
        f.setUrl("/images/fondos/fondo_claro_12.png");        
        fondoFacade.create(f);
        
        // Fondo oscuro 9
        f = new Fondo();
        f.setNombre("fondo_oscuro_9.png");
        f.setOscuro('1');
        f.setUrl("/images/fondos/fondo_oscuro_9.png");        
        fondoFacade.create(f);
        
        // Fondo oscuro 10
        f = new Fondo();
        f.setNombre("fondo_oscuro_10.png");
        f.setOscuro('1');
        f.setUrl("/images/fondos/fondo_oscuro_10.png");        
        fondoFacade.create(f);
        
        // Fondo oscuro 11
        f = new Fondo();
        f.setNombre("fondo_oscuro_11.jpg");
        f.setOscuro('1');
        f.setUrl("/images/fondos/fondo_oscuro_11.jpg");        
        fondoFacade.create(f);
    }
}

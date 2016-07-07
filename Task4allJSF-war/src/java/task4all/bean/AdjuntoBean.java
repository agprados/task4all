/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package task4all.bean;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import task4all.ejb.AdjuntoFacade;
import task4all.entity.Adjunto;

@ManagedBean
@RequestScoped
public class AdjuntoBean {

    @EJB
    private AdjuntoFacade adjuntoFacade;

    @ManagedProperty(value = "#{usuarioBean}")
    private UsuarioBean usuarioBean;
    @ManagedProperty(value = "#{tareaBean}")
    private TareaBean tareaBean;
    
    /**
     * Creates a new instance of AdjuntoBean
     */
    public AdjuntoBean() {
    }

    @PostConstruct
    public void init() {
    }

    public void setUsuarioBean(UsuarioBean usuarioBean) {
        this.usuarioBean = usuarioBean;
    }

    public void setTareaBean(TareaBean tareaBean) {
        this.tareaBean = tareaBean;
    }

    public void doSubirAdjunto(FileUploadEvent event) {
        InputStream in;
        String ruta;
        File f;
        FileOutputStream fos;

        UploadedFile adjunto = event.getFile();

        if (adjunto == null) {
            System.out.println("nulo");
        }

        if (adjunto != null && adjunto.getFileName() != null && !adjunto.getFileName().isEmpty()) {
            try {
                in = adjunto.getInputstream();

                ruta = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/")).getAbsolutePath();
                ruta = ruta.substring(0, ruta.lastIndexOf("dist"));
                ruta = ruta.concat("Task4allJSF-war" + File.separator + "web" + File.separator + "adjuntos" + File.separator);

                f = new File(ruta, adjunto.getFileName());
                fos = new FileOutputStream(f);

                int read;
                byte[] bytes = new byte[1024];

                try {
                    while ((read = in.read(bytes)) != -1) {
                        fos.write(bytes, 0, read);
                    }
                } finally {
                    in.close();
                    fos.close();
                }

                Adjunto a = new Adjunto();
                a.setNombre(adjunto.getFileName());
                a.setTamano(f.length());
                a.setTareaId(usuarioBean.getTareaSeleccionada());
                a.setTipo(adjunto.getFileName().substring(adjunto.getFileName().lastIndexOf("."), adjunto.getFileName().length()));
                a.setUrl(crearRutaAdjunto(f.getPath()));
                a.setUsuarioId(usuarioBean.getUsuario());
                this.adjuntoFacade.create(a);
                int clave = this.adjuntoFacade.findMaxAdjuntoId();
                a.setId(clave);
                
                this.tareaBean.cargarListaAdjuntos();
            } catch (IOException ex) {
                Logger.getLogger(UsuarioBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private String crearRutaAdjunto(String path) {
        String ruta = path.substring(path.lastIndexOf(File.separator + "adjuntos"), path.length());
        if (ruta.contains("\\")) {
            ruta = ruta.replaceAll("\\\\", "/");
        }
        return ruta;
    }

    public String doBorrar(Adjunto adjunto) {
        this.adjuntoFacade.remove(adjunto);
        this.tareaBean.getListaAdjuntos().remove(adjunto);

        String ruta = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/")).getAbsolutePath();
        ruta = ruta.substring(0, ruta.lastIndexOf("dist"));
        ruta = ruta.concat("Task4allJSF-war" + File.separator + "web" + adjunto.getUrl());
        ruta = crearRutaParaLocalizarArchivo(ruta);
        File f = new File(ruta);
        f.delete();

        return "tarea";
    }

    public StreamedContent getAdjuntoADescargar(Adjunto adjunto) {
        try {
            String ruta = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/")).getAbsolutePath();
            ruta = ruta.substring(0, ruta.lastIndexOf("dist"));
            ruta = ruta.concat("Task4allJSF-war" + File.separator + "web" + adjunto.getUrl());
            ruta = crearRutaParaLocalizarArchivo(ruta);

            InputStream stream = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream(adjunto.getUrl());
            return new DefaultStreamedContent(stream, Files.probeContentType(Paths.get(ruta)), adjunto.getNombre());
        } catch (IOException ex) {
            Logger.getLogger(AdjuntoBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private String crearRutaParaLocalizarArchivo(String path) {
        if (File.separator.equals("\\")) {
            path = path.replaceAll("/", "\\\\");
        }
        return path;
    }

}

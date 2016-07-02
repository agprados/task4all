/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package task4all.bean;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.json.JSONException;
import org.primefaces.json.JSONObject;
import org.primefaces.model.UploadedFile;
import task4all.ejb.UsuarioFacade;
import task4all.entity.Lista;
import task4all.entity.Proyecto;
import task4all.entity.Tarea;
import task4all.entity.Usuario;

@ManagedBean
@SessionScoped
public class UsuarioBean {

    @EJB
    private UsuarioFacade usuarioFacade;

    private Usuario usuario;
    private String identificador;
    private String usuarioRegistro;
    private String nombre;
    private String apellidos;
    private String email;
    private String contrasena;
    private String verificaContrasena;
    private String contrasenaActual;
    private UploadedFile avatar;
    private String errorLogin;
    private String errorRegistro;
    private String errorRecuperacion;
    private String errorConfiguracion;
    private String errorSocial;
    private String correctaConfiguracion;    
    private Proyecto proyectoSeleccionado;
    private Tarea tareaSeleccionada;
    private Lista listaSeleccionada;
    private String rolActual;
    private String emailRecuperacion;
    private String facebookID;
    private String googleID;
    private boolean okLogin;
    private final String FB_ID = "747075862097456";
    private final String FB_SECRET = "cb65b5382724343d60019074e274e058";
    private final String GOOGLE_ID = "720887194151-e2tbl9ti0v612god4l566mhe7bjocoa5.apps.googleusercontent.com";
    private final String GOOGLE_SECRET = "NVJFgMbDMt-ab3H4szSblHnr";
    
    /**
     * Creates a new instance of UsuarioBean
     */
    public UsuarioBean() {
    }

    @PostConstruct
    public void init() {
        okLogin = false;
        errorLogin = "";        
        errorRegistro = "";
        errorRecuperacion = "";
        errorConfiguracion = "";
        correctaConfiguracion = "";
        errorSocial = "";
        usuario = new Usuario();
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
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

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getVerificaContrasena() {
        return verificaContrasena;
    }

    public String getContrasenaActual() {
        return contrasenaActual;
    }

    public void setContrasenaActual(String contrasenaActual) {
        this.contrasenaActual = contrasenaActual;
    }

    public UploadedFile getAvatar() {
        return avatar;
    }

    public void setAvatar(UploadedFile avatar) {
        this.avatar = avatar;
    }

    public String getEmailRecuperacion() {
        return emailRecuperacion;
    }

    public void setEmailRecuperacion(String emailRecuperacion) {
        this.emailRecuperacion = emailRecuperacion;
    }

    public void setVerificaContrasena(String verificaContrasena) {
        this.verificaContrasena = verificaContrasena;
    }

    public String getErrorLogin() {
        return errorLogin;
    }

    public void setErrorLogin(String errorLogin) {
        this.errorLogin = errorLogin;
    }

    public String getErrorRegistro() {
        return errorRegistro;
    }

    public void setErrorRegistro(String errorRegistro) {
        this.errorRegistro = errorRegistro;
    }

    public String getErrorRecuperacion() {
        return errorRecuperacion;
    }

    public void setErrorRecuperacion(String errorRecuperacion) {
        this.errorRecuperacion = errorRecuperacion;
    }

    public String getErrorSocial() {
        return errorSocial;
    }

    public void setErrorSocial(String errorSocial) {
        this.errorSocial = errorSocial;
    }

    public Lista getListaSeleccionada() {
        return listaSeleccionada;
    }

    public void setListaSeleccionada(Lista listaSeleccionada) {
        this.listaSeleccionada = listaSeleccionada;
    }

    public Proyecto getProyectoSeleccionado() {
        return proyectoSeleccionado;
    }

    public void setProyectoSeleccionado(Proyecto proyectoSeleccionado) {
        this.proyectoSeleccionado = proyectoSeleccionado;
    }

    public Tarea getTareaSeleccionada() {
        return tareaSeleccionada;
    }

    public void setTareaSeleccionada(Tarea tareaSeleccionada) {
        this.tareaSeleccionada = tareaSeleccionada;
    }

    public String getRolActual() {
        return rolActual;
    }

    public void setRolActual(String rolActual) {
        this.rolActual = rolActual;
    }

    public String getUsuarioRegistro() {
        return usuarioRegistro;
    }

    public void setUsuarioRegistro(String usuarioRegistro) {
        this.usuarioRegistro = usuarioRegistro;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isOkLogin() {
        return okLogin;
    }

    public void setOkLogin(boolean okLogin) {
        this.okLogin = okLogin;
    }

    public String getErrorConfiguracion() {
        return errorConfiguracion;
    }

    public void setErrorConfiguracion(String errorConfiguracion) {
        this.errorConfiguracion = errorConfiguracion;
    }

    public String getCorrectaConfiguracion() {
        return correctaConfiguracion;
    }

    public void setCorrectaConfiguracion(String correctaConfiguracion) {
        this.correctaConfiguracion = correctaConfiguracion;
    }

    public String getFacebookID() {
        return facebookID;
    }

    public void setFacebookID(String facebookID) {
        this.facebookID = facebookID;
    }

    public String getGoogleID() {
        return googleID;
    }

    public void setGoogleID(String googleID) {
        this.googleID = googleID;
    }

    public String doEditar() {
        errorConfiguracion = "";
        correctaConfiguracion = "";

        if (email == null || email.isEmpty()) {
            errorConfiguracion = "El campo del email no puede estar vacío";
            return "configuracion";
        }

        if (isPasswordSet()
                && (contrasenaActual == null || contrasenaActual.equals(""))
                && (contrasena != null && !contrasena.equals(""))
                && (verificaContrasena != null && !verificaContrasena.equals(""))) {
            errorConfiguracion = "Debes introducir tu contraseña actual";
            return "configuracion";
        }
        
        if(isPasswordSet()
                && contrasenaActual != null && !contrasenaActual.equals("")
                && usuarioFacade.findUsuarioByUsuarioAndContrasena(usuario.getUsuario(), contrasenaActual) == null) {
            errorConfiguracion = "La contraseña actual no es correcta";
            return "configuracion";
        }
        
        if (!contrasena.equals(verificaContrasena)) {
            errorConfiguracion = "Las contraseñas no coinciden";
            return "configuracion";
        }

        if (!contrasena.equals("") && !contrasena.equals(usuario.getContrasena())) {
            correctaConfiguracion = "Cambios realizados satisfactoriamente";
            usuario.setContrasena(contrasena);
        }
        
        if(!nombre.equals(usuario.getNombre()) || !apellidos.equals(usuario.getApellidos())) {
            usuario.setNombre(nombre);
            usuario.setApellidos(apellidos);
            correctaConfiguracion = "Cambios realizados satisfactoriamente";
        }
        
        if (avatar != null) {
            try {
                InputStream in = avatar.getInputstream();
                String ruta = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/")).getAbsolutePath();
                ruta = ruta.substring(0, ruta.lastIndexOf("dist"));
                ruta = ruta.concat("Task4allJSF-war" + File.separator + "web" + File.separator + "images" + File.separator + "avatares" + File.separator);                
                
                File f = new File(ruta, avatar.getFileName());
                FileOutputStream fos = new FileOutputStream(f);

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
                
                correctaConfiguracion = "Cambios realizados satisfactoriamente. El avatar estará disponible en unos segundos";
                
                usuario.setAvatar(crearRutaAvatar(f.getPath()));    
                
            } catch (IOException ex) {
                Logger.getLogger(UsuarioBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        this.usuarioFacade.edit(usuario);       

        return "configuracion";
    }  

    private String crearRutaAvatar(String path) {
        String ruta = path.substring(path.lastIndexOf(File.separator + "images"), path.length());
        if(ruta.contains("\\")) {
            ruta = ruta.replaceAll("\\\\", "/");
        }
        return ruta;
    }

    public String doRecuperarContrasena() {
        if (emailRecuperacion == null || emailRecuperacion.isEmpty() || !isValidEmail(emailRecuperacion)) {
            errorRecuperacion = "El email introducido no es válido";
            return "recuperar";
        }

        Usuario u = usuarioFacade.findUsuarioByEmail(emailRecuperacion);
        
        if (u == null) {
            errorRecuperacion = "No hay ningún usuario registrado con ese email";
            return "recuperar";
        }
        mandarEmailContraseña(u);
        return "index?faces-redirect=true";
    }

    private void mandarEmailContraseña(Usuario u) {
        try {
            String remitente = "task4all.noreply@gmail.com";
            String contraseña = "task4all4";

            Properties props = new Properties();
            props.setProperty("mail.smtp.host", "smtp.gmail.com");
            props.setProperty("mail.smtp.starttls.enable", "true");
            props.setProperty("mail.smtp.port", "587");
            props.setProperty("mail.smtp.user", remitente);
            props.setProperty("mail.smtp.auth", "true");

            Session session = Session.getDefaultInstance(props);

            MimeMessage message = new MimeMessage(session);
            message.addRecipient(
                    Message.RecipientType.TO,
                    new InternetAddress(u.getEmail()));
            message.setSubject("Recuperación de contraseña de Task4all");
            message.setText(
                    "<p>Estos son sus datos de acceso:</p>"
                    + "<li>Usuario: " + u.getUsuario() + "</li> "
                    + "<li>Contraseña: " + u.getContrasena() + "</li> "
                    + "<p><a href=\"http://localhost:8080" + FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/login.do\">Acceder a Task4all</a></p>"
                    + "<p>No responder a este mensaje.<p/>",
                    "ISO-8859-1",
                    "html");

            Transport t = session.getTransport("smtp");
            t.connect(remitente, contraseña);
            t.sendMessage(message, message.getAllRecipients());
            t.close();
        } catch (Exception e) {
        }
    }
    
    public String doCompletarRegistro() {
        errorRegistro = "";
        
        if(usuarioRegistro == null || usuarioRegistro.equals("")) {
            errorRegistro = "Nombre de usuario vacío";
            email = null;
            return "loginSuccess";
        }
        
        if(usuarioFacade.findUsuarioByUsuario(usuarioRegistro) != null) {
            errorRegistro = "El usuario ya existe";
            email = null;
            return "loginSuccess";
        }
        
        if(email == null || email.isEmpty() || !isValidEmail(email)) {
            errorRegistro = "El email no es válido";
            email = null;
            return "loginSuccess";
        }
        
        if(usuarioFacade.findUsuarioByEmail(email) != null) {
            errorRegistro = "Ya existe un usuario con este email";
            email = null;
            return "loginSuccess";
        }
        
        if (((facebookID == null || facebookID.isEmpty())
                && (googleID == null || googleID.isEmpty()))
                || usuarioRegistro == null|| usuarioRegistro.isEmpty()
                || email == null || email.isEmpty()
                || usuario == null) {
            errorRegistro = "No se ha podido completar el registro";
            return "loginSuccess";
        }
        
        String uuid;
        boolean exists;
        do {
            uuid = UUID.randomUUID().toString();
            exists = usuarioFacade.findUsuarioByUUID(uuid) != null;
        } while(exists);
        
        usuario.setUsuario(usuarioRegistro);
        usuario.setUuid(uuid);
        usuario.setEmail(email);
        usuario.setContrasena(contrasena);
        if(facebookID != null && !facebookID.equals("")) {
            usuario.setFacebookid(facebookID);
        }
        if(googleID != null && !googleID.equals("")) {
            usuario.setGoogleid(googleID);
        }
        usuario.setVerificado('1');
        
        this.usuarioFacade.create(usuario);
        Integer clave = this.usuarioFacade.findMaxUsuarioId();
        usuario.setId(clave);
        okLogin = true;
        
        errorRegistro = "";
        return "principal?faces-redirect=true";
    }
    
    public String doNuevo() {
        errorRegistro = "";
        if(contrasena == null || contrasena.isEmpty() || usuarioRegistro == null || usuarioRegistro.isEmpty() || email == null || email.isEmpty()) {             
            errorRegistro = "Hay campos obligatorios vacíos";
            return "registrar";
        }
        if (!contrasena.equals(verificaContrasena)) {
            errorRegistro = "Las contraseñas no coinciden";
            return "registrar";
        }

        Usuario u = usuarioFacade.findUsuarioByUsuarioOrEmail(usuarioRegistro, email);

        if (u != null) {
            errorRegistro = "Ya existe un usuario con ese email o nombre de usuario";
            return "registrar";
        }
        
        String uuid;
        boolean exists;
        do {
            uuid = UUID.randomUUID().toString();
            exists = usuarioFacade.findUsuarioByUUID(uuid) != null;
        } while(exists);
                
        usuario.setUsuario(usuarioRegistro);
        usuario.setUuid(uuid);
        usuario.setEmail(email);
        usuario.setContrasena(contrasena);
        usuario.setVerificado('0');
        
        this.usuarioFacade.create(usuario);
        Integer clave = this.usuarioFacade.findMaxUsuarioId();
        usuario.setId(clave);

        errorRegistro = "";
        usuarioRegistro = "";
        email = "";
        return "principal?faces-redirect=true";
    }
    
    public String doLogin() {
        errorLogin = "";
        if (identificador == null || identificador.isEmpty() || contrasena == null || contrasena.isEmpty()) {
            errorLogin = "Hay campos vacíos";
            return "login";
        }
        
        usuario = usuarioFacade.findUsuarioByUsuarioAndContrasena(identificador, contrasena);

        if (usuario == null) {
            usuario = usuarioFacade.findUsuarioByEmailAndContrasena(identificador, contrasena);
            if (usuario == null) {
                errorLogin = "Login incorrecto";
                return "login";
            }
        }
        okLogin = true;
        return "loginSuccess?faces-redirect=true";
    }

    public void doFacebookLogin() {
        try {
            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();            
            String method = request.getParameter("method");
            
            if(method != null && !method.equals("fbPass") && !method.equals("glPass")) {
                doCheckLogout();
            }
            
            String code = request.getParameter("code");

            if (method != null && (method.equals("fb") || method.equals("fbPass"))) {
                if (code != null && !code.equals("")) {
                    URL url;
                    URLConnection con;
                    BufferedReader in;
                    String content, linea;
                    JSONObject tokenResult, infoResult;

                    url = new URL("https://graph.facebook.com/v2.3/oauth/access_token?client_id=" + FB_ID + "&redirect_uri=http%3A%2F%2Flocalhost%3A8080%2FTask4allJSF-war%2FloginSuccess.do%3Fmethod%3D"+method+"&client_secret=" + FB_SECRET + "&code=" + code);
                    con = url.openConnection();
                    in = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    content = "";
                    while ((linea = in.readLine()) != null) {
                        content += linea;
                    }
                    tokenResult = new JSONObject(content);

                    if (tokenResult.getString("access_token") != null && !tokenResult.getString("access_token").equals("")) {
                        url = new URL("https://graph.facebook.com/me?access_token=" + tokenResult.getString("access_token") + "&fields=first_name,last_name,email");
                        con = url.openConnection();
                        in = new BufferedReader(new InputStreamReader(con.getInputStream()));

                        content = "";
                        while ((linea = in.readLine()) != null) {
                            content += linea;
                        }
                        infoResult = new JSONObject(content);
                        
                        this.facebookID = infoResult.getString("id");
                        if(okLogin) {
                            usuario.setFacebookid(facebookID);
                            usuario.setFacebooktoken(tokenResult.getString("access_token"));
                            usuarioFacade.edit(usuario);
                        } else {
                            usuario = usuarioFacade.findUsuarioByFacebookId(facebookID);
                        
                            if (usuario != null) {
                                okLogin = true;
                            } else {
                                usuario = usuarioFacade.findUsuarioByEmail(infoResult.has("email") ? infoResult.getString("email") : "");
                                if(usuario != null && usuario.getVerificado()=='1') {
                                    usuario.setFacebookid(facebookID);
                                    usuario.setFacebooktoken(tokenResult.getString("access_token"));
                                    usuarioFacade.edit(usuario);
                                    okLogin = true;
                                } else if(usuario != null && usuario.getVerificado()=='0') {
                                    usuario.setFacebookid(facebookID);
                                    usuario.setFacebooktoken(tokenResult.getString("access_token"));
                                    usuario.setContrasena("");
                                    usuario.setVerificado('1');
                                    usuarioFacade.edit(usuario);
                                    okLogin = true;
                                } else {
                                    usuario = new Usuario();
                                    usuario.setNombre(infoResult.getString("first_name"));
                                    usuario.setApellidos(infoResult.getString("last_name"));
                                    usuario.setFacebooktoken(tokenResult.getString("access_token"));
                                    email = infoResult.has("email") ? infoResult.getString("email") : null;
                                    contrasena = "";
                                    verificaContrasena = contrasena;
                                }
                            }
                        }
                    } else {
                        try {
                            ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
                            context.redirect(context.getRequestContextPath() + "/login.do");
                        } catch (IOException ex) {
                            Logger.getLogger(UsuarioBean.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                } else if (!okLogin) {
                    ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
                    context.redirect(context.getRequestContextPath() + "/login.do");
                }
            }
        } catch (MalformedURLException ex) {
            Logger.getLogger(UsuarioBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | JSONException ex) {
            Logger.getLogger(UsuarioBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void doGoogleLogin() {
        try {
            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            String method = request.getParameter("method");
            
            if(method != null && !method.equals("fbPass") && !method.equals("glPass")) {
                doCheckLogout();
            }
            
            String code = request.getParameter("code");

            if (method != null && (method.equals("gl") || method.equals("glPass"))) {
                if (code != null && !code.equals("")) {
                    URL url;
                    URLConnection con;
                    BufferedReader in;
                    String content, linea;
                    JSONObject tokenResult, infoResult;

                    url = new URL("https://www.googleapis.com/oauth2/v4/token");
                    con = url.openConnection();
                    String urlParameters = "client_id=" + GOOGLE_ID + "&redirect_uri=http%3A%2F%2Flocalhost%3A8080%2FTask4allJSF-war%2FloginSuccess.do%3Fmethod%3D"+method+"&client_secret=" + GOOGLE_SECRET + "&grant_type=authorization_code&code=" + code;
                    byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
                    int postDataLength = postData.length;
                    
                    con.setDoOutput(true);
                    con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded"); 
                    con.setRequestProperty("charset", "utf-8");
                    con.setRequestProperty("Content-Length", Integer.toString(postDataLength));
                    con.setUseCaches(false);
                    
                    try(DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
                        wr.write(postData);
                    }
                    
                    in = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    content = "";
                    while ((linea = in.readLine()) != null) {
                        content += linea;
                    }
                    tokenResult = new JSONObject(content);
                    
                    if (tokenResult.getString("access_token") != null && !tokenResult.getString("access_token").equals("")) {
                        url = new URL("https://www.googleapis.com/oauth2/v1/userinfo?access_token=" + tokenResult.getString("access_token"));
                        con = url.openConnection();
                        in = new BufferedReader(new InputStreamReader(con.getInputStream()));

                        content = "";
                        while ((linea = in.readLine()) != null) {
                            content += linea;
                        }
                        infoResult = new JSONObject(content);
                        
                        this.googleID = infoResult.getString("id");
                        if(okLogin) {
                            usuario.setGoogleid(googleID);
                            usuario.setGoogletoken(tokenResult.getString("access_token"));
                            usuarioFacade.edit(usuario);
                        } else {
                            usuario = usuarioFacade.findUsuarioByGoogleId(googleID);

                            if (usuario != null) {
                                okLogin = true;
                            } else {
                                usuario = usuarioFacade.findUsuarioByEmail(infoResult.has("email") ? infoResult.getString("email") : "");
                                if(usuario != null && usuario.getVerificado()=='1') {
                                    usuario.setGoogleid(googleID);
                                    usuario.setGoogletoken(tokenResult.getString("access_token"));
                                    usuarioFacade.edit(usuario);
                                    okLogin = true;
                                } else if(usuario != null && usuario.getVerificado()=='0') {
                                    usuario.setGoogleid(googleID);
                                    usuario.setGoogletoken(tokenResult.getString("access_token"));
                                    usuario.setContrasena("");
                                    usuario.setVerificado('1');
                                    usuarioFacade.edit(usuario);
                                    okLogin = true;
                                } else {
                                    usuario = new Usuario();
                                    usuario.setNombre(infoResult.getString("given_name"));
                                    usuario.setApellidos(infoResult.getString("family_name"));
                                    usuario.setGoogletoken(tokenResult.getString("access_token"));
                                    email = infoResult.has("email") ? infoResult.getString("email") : null;
                                    contrasena = "";
                                    verificaContrasena = contrasena;
                                }
                            }
                        }
                    } else {
                        try {
                            ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
                            context.redirect(context.getRequestContextPath() + "/login.do");
                        } catch (IOException ex) {
                            Logger.getLogger(UsuarioBean.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                } else if (!okLogin) {
                    ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
                    context.redirect(context.getRequestContextPath() + "/login.do");
                }
            }
        } catch (MalformedURLException ex) {
            Logger.getLogger(UsuarioBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | JSONException | IllegalStateException ex) {
            Logger.getLogger(UsuarioBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String doLogout() {
        okLogin = false;
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "index?faces-redirect=true";
    }

    public void doCheckLogin() {
        try {
            if (!okLogin) {
                ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
                context.redirect(context.getRequestContextPath() + "/login.do");
            }
        } catch (IOException ex) {
            Logger.getLogger(UsuarioBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void doCheckLogout() {
        try {
            if (okLogin) {
                ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
                context.redirect(context.getRequestContextPath() + "/principal.do");
            }
        } catch (IOException ex) {                
            Logger.getLogger(UsuarioBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void doCheckSuccessLogin() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String method = request.getParameter("method");
        String url = "/principal.do";
        
        if(method != null && (method.equals("fbPass") || method.equals("glPass"))) {
            url = "/panel/configuracion.do";
        }
        
        try {
            if (okLogin) {
                ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
                context.redirect(context.getRequestContextPath() + url);
            }
        } catch (IOException ex) {                
            Logger.getLogger(UsuarioBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        return pattern.matcher(email).matches();
    }
    
    public boolean isFacebookConnected() {
        boolean connected = false;
        
        if(usuario != null && !usuario.getUsuario().equals("")) {
            connected = usuario.getFacebookid() != null && !usuario.getFacebookid().equals("");
        }
        
        return connected;
    }
    
    public boolean isGoogleConnected() {
        boolean connected = false;
        
        if(usuario != null && !usuario.getUsuario().equals("")) {
            connected = usuario.getGoogleid() != null && !usuario.getGoogleid().equals("");
        }
        
        return connected;
    }
    
    public boolean isPasswordSet() {
        return usuario.getContrasena() != null && !usuario.getContrasena().equals("");
    }
    
    public String doDesconectarFacebook() {
        if((usuario.getContrasena() == null || usuario.getContrasena().equals(""))
                && (usuario.getGoogleid() == null || usuario.getGoogleid().equals(""))) {
            errorSocial = "Debes establecer una contraseña antes de desvincular por completo tu cuenta";
            return "configuracion";
        }
        
        try {
            URL url;
            HttpURLConnection con;
            url = new URL("https://graph.facebook.com/"+usuario.getFacebookid()+"/permissions?access_token="+usuario.getFacebooktoken());
            con = (HttpURLConnection) url.openConnection();
            con.setDoOutput(true);
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            con.setRequestMethod("DELETE");
            con.connect();
            con.getResponseMessage();
            
            usuario.setFacebookid(null);
            usuario.setFacebooktoken(null);
            usuarioFacade.edit(usuario);
        } catch (MalformedURLException ex) {
            Logger.getLogger(UsuarioBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(UsuarioBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return "configuracion";
    }
    
    public String doDesconectarGoogle() {
        if ((usuario.getContrasena() == null || usuario.getContrasena().equals(""))
                && (usuario.getFacebookid() == null || usuario.getFacebookid().equals(""))) {
            errorSocial = "Debes establecer una contraseña antes de desvincular por completo tu cuenta";
            return "configuracion";
        }
        
        try {
            URL url;
            HttpURLConnection con;
            url = new URL("https://accounts.google.com/o/oauth2/revoke?token="+usuario.getGoogletoken());
            con = (HttpURLConnection) url.openConnection();
            con.setDoOutput(true);
            con.connect();
            con.getResponseCode();
            
            usuario.setGoogleid(null);
            usuario.setGoogletoken(null);
            usuarioFacade.edit(usuario);
        } catch (MalformedURLException ex) {
            Logger.getLogger(UsuarioBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(UsuarioBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return "configuracion";
    }
}

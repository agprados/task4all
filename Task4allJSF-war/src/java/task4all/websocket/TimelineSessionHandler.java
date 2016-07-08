/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package task4all.websocket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ApplicationScoped;
import javax.json.JsonObject;
import javax.json.spi.JsonProvider;
import javax.websocket.Session;
import task4all.entity.Comentario;

@ApplicationScoped
public class TimelineSessionHandler {
    
    private final Set<Session> sessions = new HashSet<>();
    private final Set<Comentario> comentarios = new HashSet<>();
    
    public void addSession(Session session) {
        sessions.add(session);
        for (Comentario comentario : comentarios) {
            JsonObject addMessage = createAddMessage(comentario);
            sendToSession(session, addMessage);
        }
    }

    public void removeSession(Session session) {
        sessions.remove(session);
    }
    
    public List<Comentario> getComentarios() {
        return new ArrayList<>(comentarios);
    }

    public void addComentario(Comentario comentario) {
        comentarios.add(comentario);
        JsonObject addMessage = createAddMessage(comentario);
        sendToAllConnectedSessions(addMessage);
    }

    public void removeComentario(int id) {
        Comentario comentario = getComentarioById(id);
        if (comentario != null) {
            comentarios.remove(comentario);
            JsonProvider provider = JsonProvider.provider();
            JsonObject removeMessage = provider.createObjectBuilder()
                    .add("action", "remove")
                    .add("id", id)
                    .build();
            sendToAllConnectedSessions(removeMessage);
        }
    }

    private Comentario getComentarioById(int id) {
       for (Comentario comentario : comentarios) {
            if (comentario.getId() == id) {
                return comentario;
            }
        }
        return null;
    }

    private JsonObject createAddMessage(Comentario comentario) {
       JsonProvider provider = JsonProvider.provider();
        JsonObject addMessage = provider.createObjectBuilder()
                .add("action", "add")
                .add("id", comentario.getId())
                .add("usuario", comentario.getUsuarioId().getUsuario())
                .add("fecha", comentario.getFecha().getTime())
                .add("proyecto", comentario.getProyectoId().getId())
                .add("contenido", comentario.getContenido())
                .build();
        return addMessage;
    }

    private void sendToAllConnectedSessions(JsonObject message) {
        for (Session session : sessions) {
            sendToSession(session, message);
        }
    }

    private void sendToSession(Session session, JsonObject message) {
        try {
            session.getBasicRemote().sendText(message.toString());
        } catch (IOException ex) {
            sessions.remove(session);
            Logger.getLogger(TimelineSessionHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

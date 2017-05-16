/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package REST.service;

import Entities.Contact;
import Entities.Tag;
import Exception.noTagsFoundException;
import Exception.unknowUserIdException;
import Metier.IgestionTag;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.json.*;

/**
 *
 * @author fez
 */
@javax.enterprise.context.RequestScoped
@Path("tags")
public class tagREST {

    @EJB
    private IgestionTag gT;

    @GET
    @Path("afficherTags")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getListesTags(@QueryParam("token") int id) {

        try {
            List<Tag> lesTags = gT.getListeTags(id);

            JSONArray tags = new JSONArray();
            JSONObject obj = new JSONObject();

            for (Tag t : lesTags) {
                JSONObject tempo = new JSONObject();
                tempo.put("id", t.getId());
                tempo.put("libelle", t.getLibelle());

                tags.put(tempo);
            }

            obj.put("tags", tags);

            return Response.ok(tags.toString(), MediaType.APPLICATION_JSON).build();
        } catch (noTagsFoundException | unknowUserIdException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    @Path("creerTag")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response CreerTag(@QueryParam("token") int id, String data) {

        try {
            JSONObject obj = new JSONObject(data);

            gT.creerTag(obj.getString("libelle"), id);

            return Response.ok(new JSONObject().put("statut", "ok").toString(), MediaType.APPLICATION_JSON).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

    }

    @PUT
    @Path("{idTag}/modifierTag")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response modifierTag(@QueryParam("token") int id, @PathParam("idTag") Integer idTag, String data) {
        try {

            gT.modifierTag(idTag, new JSONObject(data).getString("libelle"), id);
            return Response.ok(new JSONObject().put("statut", "ok").toString(), MediaType.APPLICATION_JSON).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    // @DELETE http://stackoverflow.com/questions/38284706/how-to-enable-disable-http-methods-for-restful-web-service
    @GET
    @Path("{idTag}/supprimerTag")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response supprimerTag(@QueryParam("token") int id, @PathParam("idTag") Integer idTag) {
        try {
            gT.supprimerTag(idTag, id);
            return Response.ok(new JSONObject().put("statut", "ok").toString(), MediaType.APPLICATION_JSON).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
    
    @POST 
    @Path("{idTag}/{idContact}/affecterTag")
    @Produces(MediaType.APPLICATION_JSON)
    public Response affecterTag(@QueryParam("token") int id, @PathParam("idTag") Integer idTag, @PathParam("idContact") Integer idContact) {
        try {
            gT.affecterTagAContact(idTag, id, idContact);
            return Response.ok(new JSONObject().put("statut", "ok").toString(), MediaType.APPLICATION_JSON).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
    
    @GET // à la place de delete parce que delete ne fonctionne pas 
    @Path("{idTag}/{idContact}/desaffecterTag")
    @Produces(MediaType.APPLICATION_JSON)
    public Response desaffecterTag(@QueryParam("token") int id, @PathParam("idTag") Integer idTag, @PathParam("idContact") Integer idContact) {
        try {
            gT.desaffecterTagAContact(idTag, id, idContact);
            return Response.ok(new JSONObject().put("statut", "ok").toString(), MediaType.APPLICATION_JSON).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
    
    @GET 
    @Path("{idTag}/getListContactByTag")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getListeContactsByTag(@QueryParam("token") int id, @PathParam("idTag") Integer idTag) {
        try {
            List<Contact> lesContacts = (List<Contact>) gT.getListeContactByTag(id,idTag);
           
            JSONArray contacts = new JSONArray();
            JSONObject obj = new JSONObject();

            for (Contact c : lesContacts) {
                JSONObject tempo = new JSONObject();
                tempo.put("id", c.getId());
                tempo.put("nom", c.getNom());
                tempo.put("prenom", c.getPrenom());
                contacts.put(tempo);
            }

            obj.put("contacts", contacts);
            
            return Response.ok(obj.toString(), MediaType.APPLICATION_JSON).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
    
    @GET 
    @Path("{idContact}/getListTagsByContact")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getListeTagsByContact(@QueryParam("token") int id, @PathParam("idContact") Integer idContact) {
        try {
            List<Tag> lesTags = gT.getListeTagByContact(id, idContact);
           
            JSONArray tags = new JSONArray();
            JSONObject obj = new JSONObject();

            for (Tag t : lesTags) {
                JSONObject tempo = new JSONObject();
                tempo.put("id", t.getId());
                tempo.put("libelle", t.getLibelle());
                tags.put(tempo);
            }

            obj.put("tags", tags);
                                // obj
            return Response.ok(tags.toString(), MediaType.APPLICATION_JSON).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
    
    @GET 
    @Path("{idContact}/getInvertListTagsByContact")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getInvertListeTagsByContact(@QueryParam("token") int id, @PathParam("idContact") Integer idContact) {
        try {
            List<Tag> lesTags = gT.getInvertListeTagByContact(id, idContact);
           
            JSONArray tags = new JSONArray();
            JSONObject obj = new JSONObject();

            for (Tag t : lesTags) {
                JSONObject tempo = new JSONObject();
                tempo.put("id", t.getId());
                tempo.put("libelle", t.getLibelle());
                tags.put(tempo);
            }

            obj.put("tags", tags);
                                // obj
            return Response.ok(tags.toString(), MediaType.APPLICATION_JSON).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

}

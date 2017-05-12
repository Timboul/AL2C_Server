package REST.service;

import Entities.Contact;
import Exception.noContactExistsException;
import Exception.unknowUserIdException;
import Metier.IgestionCanal;
import Metier.IgestionContact;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Contrôleur permettant la gestion des contacts de l'utilisateur
 * 
 * @author Alexandre Bertrand
 */
@javax.enterprise.context.RequestScoped
@Path("contacts")
public class ContactREST {
    
    @EJB
    private IgestionContact gestionContact;
    
    @EJB
    private IgestionCanal gestionCanal;
    
    @GET
    @Path("getContactsList")
    @Produces(MediaType.APPLICATION_JSON)
    public Response afficherListeContacts(@QueryParam("token") int id) {
        try {
            List<Contact> lesContacts = gestionContact.getListeContacts(id);
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

            return Response.ok(contacts.toString(), MediaType.APPLICATION_JSON)
                    .build();
        } catch (noContactExistsException | unknowUserIdException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
    
    @POST
    @Path("creerContact")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response creerContact(@QueryParam("token") int id, String data) {
        try {
            JSONObject obj = new JSONObject(data);
            gestionContact.ajouterContact(id, obj.getString("nom"),
                    obj.getString("prenom"));

            return Response.ok(new JSONObject().put("statut", "ok").toString(),
                    MediaType.APPLICATION_JSON).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
    
    @GET
    @Path("contacts")
    @Produces(MediaType.APPLICATION_JSON)
    public Response afficherContact(@QueryParam("token") int id,
            @PathParam("idContact") Integer idContact) {
        try {
            Contact leContact = gestionContact.afficherContact(idContact, id);
            JSONObject obj = new JSONObject();
            obj.put("id", leContact.getId());
            obj.put("nom", leContact.getNom());
            obj.put("prenom", leContact.getPrenom());
                    
            return Response.ok(obj.toString(), MediaType.APPLICATION_JSON)
                    .build();
        } catch (noContactExistsException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @PUT
    @Path("{idContact}/modifierContact")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response modifierContact(@QueryParam("token") int id,
            @PathParam("idContact") Integer idContact, String data) {
        try {
            JSONObject obj = new JSONObject(data);
            gestionContact.modifierContact(id, idContact, obj.getString("nom"),
                    obj.getString("prenom"));
            return Response.ok(new JSONObject().put("statut", "ok").toString(),
                    MediaType.APPLICATION_JSON).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
    
}

package REST.service;

import Entities.Article;
import Entities.Contact;
import Entities.Evenement;
import Entities.Invitation;
import Entities.Tag;
import Exception.notFoundEvenementException;
import Entities.util.EtatEvenement;
import Exception.noListeArticleFoundException;
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
import org.json.*;
import Metier.IGestionEvenement;
import Metier.IGestionInvitation;
import Metier.IGestionLieu;
import Metier.IGestionListe;
import java.util.ArrayList;

/**
 * Contrôleur permettant la gestion des contacts des évènements
 *
 * @author fez
 */
@javax.enterprise.context.RequestScoped
@Path("evenements")
public class EvenementFacadeREST {

    @EJB
    private IGestionEvenement gestionEvenement;
    
    @EJB
    private IGestionLieu gestionLieu;
    
    @EJB
    private IGestionInvitation gestionInvitation;
    
    @EJB
    private IGestionListe gestionListe;

    @GET
    @Path("getListeEvenementsPasse")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getListeEvenementsPasse(@QueryParam("token") int pid) {
        try {
            List<Evenement> lesEvents = gestionEvenement
                    .getListeEvenements(pid);
            JSONArray events = new JSONArray();
            JSONObject obj = new JSONObject();
            for (Evenement e : lesEvents) {
                if (e.getEtatEvenement().equals(EtatEvenement.PASSE.toString())) {
                    JSONObject tempo = new JSONObject();
                    tempo.put("id", e.getId());
                    tempo.put("etat", e.getEtatEvenement());
                    tempo.put("intitule", e.getIntitule());
                    tempo.put("dateDebut", e.getDateDebut().getTime());
                    if (e.getDateFin() != null)
                        tempo.put("dateFin", e.getDateFin().getTime());
                    tempo.put("nbInvites", e.getInvitationCollection().size());
                    tempo.put("nbPlaces", e.getNombreInvites());
                    int nbPresents = 0;
                    for (Invitation i : e.getInvitationCollection())
                        if (i.getPresence())
                            nbPresents++;
                    tempo.put("nbPresents", nbPresents);
                    tempo.put("adresse", e.getLieuId().getAdresse());
                    tempo.put("complement", e.getLieuId().getComplement());
                    tempo.put("codePostal", e.getLieuId().getCodePostal());
                    tempo.put("ville", e.getLieuId().getVille());
                    events.put(tempo);
                }
            }
            obj.put("Evenements", events);
            return Response.ok(events.toString(),
                    MediaType.APPLICATION_JSON).build();
        } catch (notFoundEvenementException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @GET
    @Path("getListeEvenementsAnnule")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getListeEvenementsAnnule(@QueryParam("token") int pid) {
        try {
            List<Evenement> lesEvents = gestionEvenement
                    .getListeEvenements(pid);
            JSONArray events = new JSONArray();
            JSONObject obj = new JSONObject();
            for (Evenement e : lesEvents) {
                if (e.getEtatEvenement().equals(EtatEvenement.ANNULE.toString())) {
                    JSONObject tempo = new JSONObject();
                    tempo.put("id", e.getId());
                    tempo.put("etat", e.getEtatEvenement());
                    tempo.put("intitule", e.getIntitule());
                    tempo.put("dateDebut", e.getDateDebut().getTime());
                    if (e.getDateFin() != null)
                        tempo.put("dateFin", e.getDateFin().getTime());
                    tempo.put("nbInvites", e.getInvitationCollection().size());
                    tempo.put("nbPlaces", e.getNombreInvites());
                    int nbPresents = 0;
                    for (Invitation i : e.getInvitationCollection())
                        if (i.getPresence())
                            nbPresents++;
                    tempo.put("nbPresents", nbPresents);
                    tempo.put("adresse", e.getLieuId().getAdresse());
                    tempo.put("complement", e.getLieuId().getComplement());
                    tempo.put("codePostal", e.getLieuId().getCodePostal());
                    tempo.put("ville", e.getLieuId().getVille());
                    events.put(tempo);
                }
            }
            obj.put("Evenements", events);
            return Response.ok(events.toString(),
                    MediaType.APPLICATION_JSON).build();
        } catch (notFoundEvenementException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
    
    @GET
    @Path("getListeEvenementsEnPreparation")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getListeEvenementsEnPreparation(@QueryParam("token") int pid) {
        try {
            List<Evenement> lesEvents = gestionEvenement
                    .getListeEvenements(pid);
            JSONArray events = new JSONArray();
            JSONObject obj = new JSONObject();
            for (Evenement e : lesEvents) {
                if (e.getEtatEvenement().equals(EtatEvenement.EN_PREPARATION.toString())) {
                    JSONObject tempo = new JSONObject();
                    tempo.put("id", e.getId());
                    tempo.put("etat", e.getEtatEvenement());
                    tempo.put("intitule", e.getIntitule());
                    tempo.put("dateDebut", e.getDateDebut().getTime());
                    if (e.getDateFin() != null)
                        tempo.put("dateFin", e.getDateFin().getTime());
                    tempo.put("nbInvites", e.getInvitationCollection().size());
                    tempo.put("nbPlaces", e.getNombreInvites());
                    int nbPresents = 0;
                    for (Invitation i : e.getInvitationCollection())
                        if (i.getPresence())
                            nbPresents++;
                    tempo.put("nbPresents", nbPresents);
                    tempo.put("adresse", e.getLieuId().getAdresse());
                    tempo.put("complement", e.getLieuId().getComplement());
                    tempo.put("codePostal", e.getLieuId().getCodePostal());
                    tempo.put("ville", e.getLieuId().getVille());
                    events.put(tempo);
                }
            }
            obj.put("Evenements", events);
            return Response.ok(events.toString(),
                    MediaType.APPLICATION_JSON).build();
        } catch (notFoundEvenementException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
    
    @GET
    @Path("getListeEvenementsEnCours")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getListeEvenementsEnCours(@QueryParam("token") int pid) {
        try {
            List<Evenement> lesEvents = gestionEvenement
                    .getListeEvenements(pid);
            JSONArray events = new JSONArray();
            JSONObject obj = new JSONObject();
            for (Evenement e : lesEvents) {
                if (e.getEtatEvenement().equals(EtatEvenement.EN_COURS.toString())) {
                    JSONObject tempo = new JSONObject();
                    tempo.put("id", e.getId());
                    tempo.put("etat", e.getEtatEvenement());
                    tempo.put("intitule", e.getIntitule());
                    tempo.put("dateDebut", e.getDateDebut().getTime());
                    if (e.getDateFin() != null)
                        tempo.put("dateFin", e.getDateFin().getTime());
                    tempo.put("nbInvites", e.getInvitationCollection().size());
                    tempo.put("nbPlaces", e.getNombreInvites());
                    int nbPresents = 0;
                    for (Invitation i : e.getInvitationCollection())
                        if (i.getPresence())
                            nbPresents++;
                    tempo.put("nbPresents", nbPresents);
                    tempo.put("adresse", e.getLieuId().getAdresse());
                    tempo.put("complement", e.getLieuId().getComplement());
                    tempo.put("codePostal", e.getLieuId().getCodePostal());
                    tempo.put("ville", e.getLieuId().getVille());
                    events.put(tempo);
                }
            }
            obj.put("Evenements", events);
            return Response.ok(events.toString(),
                    MediaType.APPLICATION_JSON).build();
        } catch (notFoundEvenementException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
    
    @GET
    @Path("getListeEvenementsAVenir")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getListeEvenementsAVenir(@QueryParam("token") int pid) {
        try {
            List<Evenement> lesEvents = gestionEvenement
                    .getListeEvenements(pid);
            JSONArray events = new JSONArray();
            JSONObject obj = new JSONObject();
            for (Evenement e : lesEvents) {
                if (e.getEtatEvenement().equals(EtatEvenement.A_VENIR.toString())) {
                    JSONObject tempo = new JSONObject();
                    tempo.put("id", e.getId());
                    tempo.put("etat", e.getEtatEvenement());
                    tempo.put("intitule", e.getIntitule());
                    tempo.put("dateDebut", e.getDateDebut().getTime());
                    if (e.getDateFin() != null)
                        tempo.put("dateFin", e.getDateFin().getTime());
                    tempo.put("nbInvites", e.getInvitationCollection().size());
                    tempo.put("nbPlaces", e.getNombreInvites());
                    int nbPresents = 0;
                    for (Invitation i : e.getInvitationCollection())
                        if (i.getPresence())
                            nbPresents++;
                    tempo.put("nbPresents", nbPresents);
                    tempo.put("adresse", e.getLieuId().getAdresse());
                    tempo.put("complement", e.getLieuId().getComplement());
                    tempo.put("codePostal", e.getLieuId().getCodePostal());
                    tempo.put("ville", e.getLieuId().getVille());
                    events.put(tempo);
                }
            }
            obj.put("Evenements", events);
            return Response.ok(events.toString(),
                    MediaType.APPLICATION_JSON).build();
        } catch (notFoundEvenementException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
    
    @GET
    @Path("{id}/getEvenement")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEvenement(@QueryParam("token") int id,
            @PathParam("id") Integer idEvenement) {
        try {
            Evenement e = gestionEvenement.afficherEvenement(idEvenement, id);
            JSONObject obj = new JSONObject();
            obj.put("id", e.getId());
            obj.put("etat", e.getEtatEvenement());
            obj.put("intitule", e.getIntitule());
            obj.put("description", e.getDescription());
            obj.put("dateDebut", e.getDateDebut().getTime());
            if (e.getDateFin() != null)
                obj.put("dateFin", e.getDateFin().getTime());
            obj.put("nbInvites", e.getInvitationCollection().size());
            obj.put("nbPlaces", e.getNombreInvites());
            obj.put("message", e.getMessageInvitation());
            int nbPresents = 0;
            for (Invitation i : e.getInvitationCollection()) 
                if (i.getPresence())
                    nbPresents++;
            obj.put("nbPresents", nbPresents);
            obj.put("adresse", e.getLieuId().getAdresse());
            obj.put("complement", e.getLieuId().getComplement());
            obj.put("codePostal", e.getLieuId().getCodePostal());
            obj.put("ville", e.getLieuId().getVille());
            return Response.ok(obj.toString(),
                    MediaType.APPLICATION_JSON).build();
        } catch (notFoundEvenementException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @POST
    @Path("creerEvenement")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response creerEvenement(@QueryParam("token") int pid, String data) {
        String complement = null;
        String dateFin = null;
        try {
            JSONObject obj = new JSONObject(data);
            if (obj.has("complement"))
                complement = obj.getString("complement");
            else
                complement="";
            
            if (obj.has("dateFin"))
                dateFin = obj.getString("dateFin");
            int idLieu = gestionLieu.ajouterLieu(obj.getString("adresse"),
                    complement, obj.getString("codePostal"),
                    obj.getString("ville"));
            int idEvenement = gestionEvenement.creationEvenement(pid, idLieu,
                    obj.getString("intitule"), obj.getString("description"),
                    obj.getString("dateDebut"), dateFin,
                    obj.getInt("nbPlaces"));
            JSONObject response = new JSONObject();
            response.put("id", idEvenement);
            return Response.ok(response.toString(),
                    MediaType.APPLICATION_JSON).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
    
    @POST
    @Path("{id}/modifierEvenement")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response modifierEvenement(@QueryParam("token") int pid,
            @PathParam("id") Integer idEvent, String data) {
        String complement = null;
        String dateFin = null;
        try {
            JSONObject obj = new JSONObject(data);
            if (obj.has("complement"))
                complement = obj.getString("complement");
            if (obj.has("dateFin"))
                dateFin = obj.getString("dateFin");
            if (gestionEvenement.isEvenementEnPreparation(idEvent))
                gestionLieu.modifierLieu(idEvent, pid, obj.getString("adresse"),
                        complement, obj.getString("codePostal"),
                        obj.getString("ville"));
            gestionEvenement.modifierEvenement(idEvent, pid,
                    obj.getString("intitule"), obj.getString("description"),
                    obj.getString("dateDebut"), dateFin,
                    obj.getInt("nbPlaces"));      
            return Response.ok(new JSONObject().put("Statut", "ok").toString(),
                    MediaType.APPLICATION_JSON).build();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
    
    @GET
    @Path("{id}/annulerEvenement")
    @Produces(MediaType.APPLICATION_JSON)
    public Response annulerEvenement(@QueryParam("token") int pid, 
            @PathParam("id") Integer idEvent) {
        try {
            //TODO voir si on vérifie la valider du token ici
            gestionEvenement.annulerEvenement(idEvent, pid);
            return Response.ok(new JSONObject().put("Statut", "ok").toString(),
                    MediaType.APPLICATION_JSON).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
    
    @PUT
    @Path("{id}/validerEvenement")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response validerEvenement(@QueryParam("token") int id,
            @PathParam("id") Integer idEvenement) {
        try {
            gestionEvenement.validerEvenement(idEvenement, id);
            return Response.ok(new JSONObject().put("Statut", "ok").toString(),
                    MediaType.APPLICATION_JSON).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
    
    @GET
    @Path("{id}/getListePresents")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getListePresents(@QueryParam("token") int id,
            @PathParam("id") Integer idEvenement) {
        try {
            List<Contact> contactsInvites = gestionInvitation
                    .getInvitesPresents(idEvenement, id);
            JSONArray array = new JSONArray();
            for (Contact contact: contactsInvites) {
                JSONObject tempo = new JSONObject();
                tempo.put("id", contact.getId());
                tempo.put("nom", contact.getNom());
                tempo.put("prenom", contact.getPrenom());
                array.put(tempo);
            }
            return Response.ok(array.toString(),
                    MediaType.APPLICATION_JSON).build();
        } catch (notFoundEvenementException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
    
    @GET
    @Path("{id}/getListeNonPresents")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getListeNonPresents(@QueryParam("token") int id,
            @PathParam("id") Integer idEvenement) {
        try {
            List<Contact> contactsInvites = gestionInvitation
                    .getInvitesNonPresents(idEvenement, id);
            JSONArray array = new JSONArray();
            for (Contact contact: contactsInvites) {
                JSONObject tempo = new JSONObject();
                tempo.put("id", contact.getId());
                tempo.put("nom", contact.getNom());
                tempo.put("prenom", contact.getPrenom());
                array.put(tempo);
            }
            return Response.ok(array.toString(),
                    MediaType.APPLICATION_JSON).build();
        } catch (notFoundEvenementException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
    
    @GET
    @Path("{id}/getListeSansReponse")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getListeSansReponse(@QueryParam("token") int id,
            @PathParam("id") Integer idEvenement) {
        try {
            List<Contact> contactsInvites = gestionInvitation
                    .getInvitesSansReponse(idEvenement, id);
            JSONArray array = new JSONArray();
            for (Contact contact: contactsInvites) {
                JSONObject tempo = new JSONObject();
                tempo.put("id", contact.getId());
                tempo.put("nom", contact.getNom());
                tempo.put("prenom", contact.getPrenom());
                array.put(tempo);
            }
            return Response.ok(array.toString(),
                    MediaType.APPLICATION_JSON).build();
        } catch (notFoundEvenementException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
    
    @GET
    @Path("{id}/getListeInvites")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getListeInvites(@QueryParam("token") int id,
            @PathParam("id") Integer idEvenement) {
        try {
            List<Contact> contactsInvites = gestionInvitation
                    .getContactsInvites(idEvenement, id);
            JSONArray array = new JSONArray();
            for (Contact contact: contactsInvites) {
                JSONObject tempo = new JSONObject();
                tempo.put("id", contact.getId());
                tempo.put("nom", contact.getNom());
                tempo.put("prenom", contact.getPrenom());
                array.put(tempo);
            }
            return Response.ok(array.toString(),
                    MediaType.APPLICATION_JSON).build();
        } catch (notFoundEvenementException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
           
    @GET
    @Path("{id}/getListeNonInvites")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getListeNonInvites(@QueryParam("token") int id,
            @PathParam("id") Integer idEvenement) {
        try {
            List<Contact> contactsNonInvites = gestionInvitation
                    .getContactsNonInvites(idEvenement, id);
            JSONArray array = new JSONArray();
            for (Contact contact: contactsNonInvites) {
                JSONObject tempo = new JSONObject();
                tempo.put("id", contact.getId());
                tempo.put("nom", contact.getNom());
                tempo.put("prenom", contact.getPrenom());
                array.put(tempo);
            }
            return Response.ok(array.toString(),
                    MediaType.APPLICATION_JSON).build();
        } catch (notFoundEvenementException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
    
    @GET
    @Path("{id}/getListeTagNonInvites")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getListeTagNonInvites(@QueryParam("token") int id,
            @PathParam("id") Integer idEvenement) {
        try {
            List<Tag> tagsNotInvites = gestionInvitation
                    .getTagsNonInvites(idEvenement, id);
            JSONArray array = new JSONArray();
            for (Tag tag: tagsNotInvites) {
                JSONObject tempo = new JSONObject();
                tempo.put("id", tag.getId());
                tempo.put("libelle", tag.getLibelle());
                array.put(tempo);
            }
            return Response.ok(array.toString(),
                    MediaType.APPLICATION_JSON).build();
        } catch (notFoundEvenementException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
    
    @PUT
    @Path("{id}/modifierMessage")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response modifierMessage(@QueryParam("token") int id,
            @PathParam("id") Integer idEvenement, String data) {
        try {
            JSONObject obj = new JSONObject(data);
            gestionEvenement.modifierMessageInvitation(idEvenement, id,
                    obj.getString("message"));
            return Response.ok(new JSONObject().put("Statut", "ok").toString(),
                    MediaType.APPLICATION_JSON).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
    
    @POST
    @Path("{idEvenement}/inviterContacts")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response inviterContacts(@QueryParam("token") int id,
            @PathParam("idEvenement") Integer idEvenement, String data) {
        try {
            JSONArray contacts = new JSONArray(data);
            ArrayList<Integer> liste = new ArrayList<Integer>();
            for (int i = 0; i < contacts.length(); i++) {
                JSONObject contact = contacts.getJSONObject(i);
                liste.add(contact.getInt("id"));
            }
            gestionInvitation.inviterContacts(idEvenement, id, liste);
            return Response.ok(new JSONObject().put("Statut", "ok").toString(),
                    MediaType.APPLICATION_JSON).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
    
    @POST
    @Path("{idEvenement}/supprimerInvitationContacts")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response supprimerInvitationContacts(@QueryParam("token") int id,
            @PathParam("idEvenement") Integer idEvenement, String data) {
        try {
            JSONArray contacts = new JSONArray(data);
            ArrayList<Integer> liste = new ArrayList<Integer>();
            for (int i = 0; i < contacts.length(); i++) {
                JSONObject contact = contacts.getJSONObject(i);
                liste.add(contact.getInt("id"));
            }
            gestionInvitation.supprimerInvitationContacts(idEvenement, id, liste);
            return Response.ok(new JSONObject().put("Statut", "ok").toString(),
                    MediaType.APPLICATION_JSON).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
    
    @POST
    @Path("{idEvenement}/inviterTags")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response inviterTags(@QueryParam("token") int id,
            @PathParam("idEvenement") Integer idEvenement, String data) {
        try {
            JSONArray tags = new JSONArray(data);
            ArrayList<Integer> liste = new ArrayList<Integer>();
            for (int i = 0; i < tags.length(); i++) {
                JSONObject tag = tags.getJSONObject(i);
                liste.add(tag.getInt("id"));
            }
            gestionInvitation.inviterTags(idEvenement, id, liste);
            return Response.ok(new JSONObject().put("Statut", "ok").toString(),
                    MediaType.APPLICATION_JSON).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
    
    @POST
    @Path("{idEvenement}/supprimerInvitationTags")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response supprimerInvitationTags(@QueryParam("token") int id,
            @PathParam("idEvenement") Integer idEvenement, String data) {
        try {
            JSONArray tags = new JSONArray(data);
            ArrayList<Integer> liste = new ArrayList<Integer>();
            for (int i = 0; i < tags.length(); i++) {
                JSONObject tag = tags.getJSONObject(i);
                liste.add(tag.getInt("id"));
            }
            gestionInvitation.supprimerInvitationTags(idEvenement, id, liste);
            return Response.ok(new JSONObject().put("Statut", "ok").toString(),
                    MediaType.APPLICATION_JSON).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
    
    @POST
    @Path("validerInvitation")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response validerInvitation(String data) {
        try {
            JSONObject response = new JSONObject(data);
            JSONArray array = response.getJSONArray("messages");
            JSONObject obj = array.getJSONObject(0);
            gestionInvitation.validerReponseInvitation(obj.getString("conversationId"),
                    Boolean.parseBoolean(obj.getString("presence")));
            return Response.ok(new JSONObject().put("Statut", "ok").toString(),
                    MediaType.APPLICATION_JSON).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
    
    @POST
    @Path("creerInvitationPremierContact")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response creerInvitationPremierContact(String data) {
        try {
            JSONObject response = new JSONObject(data);
            JSONArray array = response.getJSONArray("messages");
            JSONObject obj = array.getJSONObject(0);
            gestionInvitation.creerInvitationPremierContact(obj.toString());
            return Response.ok(new JSONObject().put("Statut", "ok").toString(),
                    MediaType.APPLICATION_JSON).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
    
    @GET
    @Path("{id}/getMessagesInvitation")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMessagesInvitation(@QueryParam("token") int id,
            @PathParam("id") Integer idEvenement) {
        try {
            return Response.ok(gestionInvitation.creerListeMessagesInvitations(idEvenement),
                    MediaType.APPLICATION_JSON).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
    
    @POST
    @Path("{id}/creerListeArticle")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response creerListeArticle(@PathParam("id") Integer idEvenement, String data) {
        try {
            JSONArray array = new JSONArray(data);
            ArrayList<Article> articles = new ArrayList<Article>();
            for (int i = 0; i < array.length(); i++) {
                JSONObject produit = array.getJSONObject(i);
                Article article = new Article();
                article.setLibelle(produit.getString("produit"));
                article.setQuantite(produit.getInt("quantite"));
                articles.add(article);
            }
            gestionListe.ajouterListe(idEvenement, articles);
            return Response.ok(new JSONObject().put("Statut", "ok").toString(),
                    MediaType.APPLICATION_JSON).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
    
    @GET
    @Path("{id}/getListeArticles")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getListeArticles(@PathParam("id") Integer idEvenement) {
        try {
            String retour = "[]";
            JSONArray array = new JSONArray();
            List<Article> articles = (List<Article>) 
                    gestionListe.recupererListe(idEvenement).getArticleCollection();
            if (articles != null) {
                if (articles.size() > 0) {
                    for (Article article: articles) {
                        JSONObject tempo = new JSONObject();
                        tempo.put("produit", article.getLibelle());
                        tempo.put("quantite", article.getQuantite());
                        array.put(tempo);
                    }
                    retour = array.toString();
                }
            }
            return Response.ok(retour,
                    MediaType.APPLICATION_JSON).build();
        } catch (noListeArticleFoundException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
        
}

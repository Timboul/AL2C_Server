/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package REST.service;

import Entities.Utilisateur;
import Exception.failAuthentificationException;
import Exception.mailAlreadyUsedException;
import Exception.notFoundUtilisateurException;
import Metier.IgestionUtilisateur;
import java.util.List;
import javax.ejb.EJB;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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

/**
 *
 * @author fez
 */
//@Stateless => http://stackoverflow.com/questions/25879898/glassfish-4-1-cant-run-restful-service-when-using-ear-ejb-web-module
@javax.enterprise.context.RequestScoped
@Path("utilisateur")
public class UtilisateurFacadeREST /*extends AbstractFacade<Utilisateur>*/ {

    @EJB//(name="Metier.IgestionUtilisateur")
    private IgestionUtilisateur gU;

    /*
    @PersistenceContext(unitName = "Al2cServer-warPU")
    private EntityManager em;
    public UtilisateurFacadeREST() {
        super(Utilisateur.class);
    }
  
    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Utilisateur entity) {
        super.create(entity);
    }
    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Utilisateur entity) {
        super.edit(entity);
    }
    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }
    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Utilisateur find(@PathParam("id") Integer id) {
        return super.find(id);
    }
    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Utilisateur> findAll() {
        return super.findAll();
    }
    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Utilisateur> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }
  @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }*/
 /*
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
     */
    @POST
    @Path("inscription")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String inscription(String data) {

        JSONObject obj = new JSONObject(data);

        String nom = obj.getString("nom");
        String prenom = obj.getString("prenom");
        String mail = obj.getString("mail");
        String mdp = obj.getString("mdp");

        try {
            gU.inscriptionClient(nom, prenom, mail, mdp);
        } catch (mailAlreadyUsedException e) {
            return new JSONObject().put("err", "001").toString();
        }

        return new JSONObject().put("Statut", "ok").toString();
    }

    @GET
    @Path("authentification")
    @Produces(MediaType.APPLICATION_JSON) // 
    public Response authentification(@QueryParam("mail") String p_mail, @QueryParam("mdp") String p_mdp) {

        String mail = p_mail;
        String mdp = p_mdp;

        try {
            int id = gU.authentificationClient(mail, mdp);
            return Response.ok(new JSONObject().put("token", id).toString(), MediaType.APPLICATION_JSON).build();
        } catch (failAuthentificationException ex) {
           return   Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("afficherParametres")
    @Produces(MediaType.APPLICATION_JSON)
    public Response afficherInformationUtilisateur(@QueryParam("token") int pid) {

        try {
            Utilisateur u = gU.afficherInformationsCompteUtilisateur(pid);
            JSONObject obj = new JSONObject();
            obj.put("nom", u.getNom());
            obj.put("prenom", u.getPrenom());
            obj.put("mail", u.getAdresseMail());

            return Response.ok(obj.toString(), MediaType.APPLICATION_JSON).build();
        } catch (notFoundUtilisateurException ex) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @PUT
    @Path("modifierParametres")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response modifierInformationUtilisateur(@QueryParam("token") int pid, String data) {

        try {
           
            JSONObject obj = new JSONObject(data);

            // TODO cette ligne est à modifier si on met un token en place
            // il faudra alors recherche l'id avec le token correspondant 
            String nom = obj.getString("nom");
            String prenom = obj.getString("prenom");
            String mail = obj.getString("mail");
            String mdp = obj.getString("mdp");
            System.out.println(pid);
           gU.modifierInformationsCompteUtilisateur(pid, nom, prenom, mail, mdp); 
            return Response.ok(new JSONObject().put("Statut", "ok").toString() , MediaType.APPLICATION_JSON).build();
        } catch (notFoundUtilisateurException ex) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

}


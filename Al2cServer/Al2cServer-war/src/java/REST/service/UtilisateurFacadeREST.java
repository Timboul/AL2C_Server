/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package REST.service;

import Entities.Utilisateur;
import Exception.failAuthentificationException;
import Metier.IgestionUtilisateur;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.json.*;

/**
 *
 * @author fez
 */
@Stateless
@Path("utilisateur")
public class UtilisateurFacadeREST extends AbstractFacade<Utilisateur> {

    @EJB(name="Metier.IgestionUtilisateur")
    private IgestionUtilisateur gU;

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
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @POST
    @Path("inscription")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject inscription(String data) {

        JSONObject obj = new JSONObject(data);
        String nom = obj.getString("nom");
        System.out.println(nom);
        String prenom = obj.getString("prenom");
        String mail = obj.getString("mail");
        String mdp = obj.getString("mdp");

        //TODO gérer echec avec exception et retour => { "err":"001"}
        gU.inscriptionClient(nom, prenom, mail, mdp);

        return new JSONObject("{\"statut\":\"ok\"}");
    }

    @POST
    @Path("authentification")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject authentification(String data) {

        JSONObject obj = new JSONObject(data);

        String mail = obj.getString(data);
        String mdp = obj.getString(data);

        try {
            gU.authentificationClient(mail, mdp);
            return new JSONObject("{\"token\":\"ok\"}");
        } catch (failAuthentificationException ex) {
            return new JSONObject("{ \"err\":\"002\"}");
        }
    }

}

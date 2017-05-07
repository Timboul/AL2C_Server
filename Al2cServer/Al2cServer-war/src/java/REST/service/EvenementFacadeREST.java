/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package REST.service;

import Entities.Evenement;
import Exception.notFoundEvenementException;
import Metier.IgestionEvenement;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.json.Json;
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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.json.*;
/**
 *
 * @author fez
 */
@javax.enterprise.context.RequestScoped
@Path("evenements")
public class EvenementFacadeREST{
    
      @EJB
    private IgestionEvenement gE;
/*
    @PersistenceContext(unitName = "Al2cServer-warPU")
    private EntityManager em;

    public EvenementFacadeREST() {
        super(Evenement.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Evenement entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Evenement entity) {
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
    public Evenement find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Evenement> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Evenement> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    */
  
    
    @GET
    @Path("getListeEvenements")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getListeEvenements(@QueryParam("token") int pid) {
        
        try{
           List<Evenement> lesEvents = gE.getListeEvenements(pid);
           
           JSONArray events = new JSONArray();
           JSONObject obj = new JSONObject();
           
           for(Evenement e: lesEvents){
               JSONObject tempo = new JSONObject();
               tempo.put("id", e.getId());
               tempo.put("intitule", e.getIntitule());
               tempo.put("description", e.getDescription());
               tempo.put("dateDebut", e.getDateDebut());
               tempo.put("datefin", e.getDateFin());
               tempo.put("etat",e.getEtatEvenement());
               tempo.put("lieu", e.getLieu());
               tempo.put("nombreInvite", e.getNombreInvites());
               tempo.put("message", e.getMessageInvitation());
               
               events.put(tempo);
           }
           
          obj.put("Evenements", events);
               
           return Response.ok(events.toString(), MediaType.APPLICATION_JSON).build();
        }catch(notFoundEvenementException e){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        
        
    }
    
    
    
    
    
    
    
}

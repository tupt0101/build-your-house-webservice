/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tupt.entities.service;

import java.util.List;
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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import tupt.entities.Favorite;

/**
 *
 * @author sherl
 */
@Stateless
@Path("tupt.entities.favorite")
public class FavoriteFacadeREST extends AbstractFacade<Favorite> {

    @PersistenceContext(unitName = "BuildYourHouse_WSPU")
    private EntityManager em;

    public FavoriteFacadeREST() {
        super(Favorite.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Favorite entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Favorite entity) {
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
    public Favorite find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Favorite> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Favorite> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    @Path("insert")
    @Consumes(MediaType.APPLICATION_XML)
    public Favorite insert(Favorite favorite) {
        super.create(favorite);
        return favorite;
    }
    
    @GET
    @Path("findToRemove")
    @Produces(MediaType.APPLICATION_XML)
    public String findToRemove(@QueryParam("productID") int productID, @QueryParam("accountID") int accountID) {
        String sql = "SELECT f.ID FROM Favorite f WHERE f.AccountID = ? AND f.ProductID = ?";
        return getEntityManager().createNativeQuery(sql).setParameter(1, accountID).setParameter(2, productID).getSingleResult().toString();
    }
}

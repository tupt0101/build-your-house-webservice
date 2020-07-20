/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tupt.entities.service;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
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
import tupt.entities.Product;

/**
 *
 * @author sherl
 */
@Stateless
@Path("tupt.entities.product")
public class ProductFacadeREST extends AbstractFacade<Product> {

    @PersistenceContext(unitName = "BuildYourHouse_WSPU")
    private EntityManager em;

    public ProductFacadeREST() {
        super(Product.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Product entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Product entity) {
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
    public Product find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Product> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Product> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    public Product insert(Product product) {
        super.create(product);
        return product;
    }

    @GET
    @Path("findCement")
    @Produces(MediaType.APPLICATION_XML)
    public List<Product> findCement() {
        TypedQuery query = em.createNamedQuery("Product.findCement", Product.class);
        return (List<Product>) query.getResultList();
    }

    @GET
    @Path("findRock")
    @Produces(MediaType.APPLICATION_XML)
    public List<Product> findRock() {
        TypedQuery query = em.createNamedQuery("Product.findRock", Product.class);
        return (List<Product>) query.getResultList();
    }

    @GET
    @Path("findSand")
    @Produces(MediaType.APPLICATION_XML)
    public List<Product> findSand() {
        TypedQuery query = em.createNamedQuery("Product.findSand", Product.class);
        return (List<Product>) query.getResultList();
    }

    @GET
    @Path("findSteel")
    @Produces(MediaType.APPLICATION_XML)
    public List<Product> findSteel() {
        TypedQuery query = em.createNamedQuery("Product.findSteel", Product.class);
        return (List<Product>) query.getResultList();
    }

    @GET
    @Path("findBrick")
    @Produces(MediaType.APPLICATION_XML)
    public List<Product> findBrick() {
        TypedQuery query = em.createNamedQuery("Product.findBrick", Product.class);
        return (List<Product>) query.getResultList();
    }

    @GET
    @Path("findTile")
    @Produces(MediaType.APPLICATION_XML)
    public List<Product> findTile() {
        TypedQuery query = em.createNamedQuery("Product.findTile", Product.class);
        return (List<Product>) query.getResultList();
    }

    @GET
    @Path("findProductByTag")
    @Produces(MediaType.APPLICATION_XML)
    public List<Product> findProductByTag(@QueryParam("tag1") String tag1, @QueryParam("tag2") String tag2,
            @QueryParam("tag3") String tag3, @QueryParam("tag4") String tag4, @QueryParam("tag5") String tag5) {

        String sql = "SELECT a.ID, a.Num FROM " +
                        "(SELECT p.ID, COUNT(*) as Num " +
                        "FROM Product p " +
                        "JOIN TagProduct tp ON p.ID = tp.productID " +
                        "JOIN Tag t ON t.ID = tp.tagID " +
                        "WHERE t.Name = ? or t.Name = ? or t.Name = ? or t.Name = ? or t.Name = ? " +
                        "GROUP BY p.ID) a " +
                        "WHERE a.Num = 5";
    
        TypedQuery query = (TypedQuery) getEntityManager().createNativeQuery(sql);
        query.setParameter(1, tag1);
        query.setParameter(2, tag2);
        query.setParameter(3, tag3);
        query.setParameter(4, tag4);
        query.setParameter(5, tag5);

        List<Object[]> objects = query.getResultList();
        List<Product> result = new ArrayList<>();
        for (Object[] row : objects) {
            Product product = getEntityManager().find(Product.class, row[0]);
            result.add(product);
        }
        System.out.println("result test: " + result.size());
        return result;
    }
    
    @GET
    @Path("findFavoriteProduct")
    @Produces(MediaType.APPLICATION_XML)
    public String findFavoriteProduct(@QueryParam("accountID") int accountID) {
        Object result = em.createNamedQuery("findFavoriteProduct").setParameter(1, accountID).getSingleResult();
        if (result != null) {
            return result.toString();
        }
        return "";
    }
    
    @PUT
    @Path("updatePC/{id}")
    @Consumes({MediaType.APPLICATION_XML})
    public Product updateProductOccurrence(@PathParam("id") Long id, Product entity) {
        super.edit(entity);
        return entity;
    }
    
    @GET
    @Path("findTrendingProduct")
    @Produces(MediaType.APPLICATION_XML)
    public String findTrendingProduct() {
        Object result = em.createNamedQuery("findTrendingProduct").getSingleResult();
        if (result != null) {
            return result.toString();
        }
        return "";
    }
}

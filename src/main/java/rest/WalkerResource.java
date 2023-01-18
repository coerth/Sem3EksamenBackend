package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entities.User;
import facades.WalkerFacade;
import utils.EMF_Creator;

import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.*;
import java.util.List;

@Path("walker")
public class WalkerResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final WalkerFacade FACADE = WalkerFacade.getInstance(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    @Context
    private UriInfo context;

    @Context
    SecurityContext securityContext;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response allWalkers()
    {
         return Response.ok(GSON.toJson(FACADE.getAllWalkers())).build();
    }

    @GET
    @Path("dog/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response allWalkers(@PathParam("id") int id)
    {
        return Response.ok(GSON.toJson(FACADE.getAllWalkersByDogId(id))).build();
    }


}

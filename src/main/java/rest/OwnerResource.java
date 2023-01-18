package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import facades.OwnerFacade;
import facades.WalkerFacade;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.*;
@Path("owner")
public class OwnerResource
{
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final OwnerFacade FACADE = OwnerFacade.getInstance(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    @Context
    private UriInfo context;

    @Context
    SecurityContext securityContext;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllOwners()
    {
        return Response.ok(GSON.toJson(FACADE.getAllOwners())).build();
    }

    @GET
    @Path("dogs/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllDogsByOwnerId(@PathParam("id") int id)
    {
        return Response.ok(GSON.toJson(FACADE.getAllDogsFromOwner(id))).build();
    }
}

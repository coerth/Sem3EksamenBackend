package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import facades.DogFacade;
import facades.OwnerFacade;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.*;

public class DogResource
{

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final DogFacade FACADE = DogFacade.getInstance(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    @Context
    private UriInfo context;

    @Context
    SecurityContext securityContext;

    @GET
    @Path("owner/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllDogsByOwnerId(@PathParam("id") int id)
    {
        return Response.ok(GSON.toJson(FACADE.getAllDogsFromOwner(id))).build();
    }
}

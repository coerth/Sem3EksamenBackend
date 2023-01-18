package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.DogDto;
import facades.DogFacade;
import facades.OwnerFacade;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

@Path("dog")
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
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllDogs()
    {
        return Response.ok(GSON.toJson(FACADE.getAllDogs())).build();
    }
    @GET
    @Path("owner/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllDogsByOwnerId(@PathParam("id") int id)
    {
        return Response.ok(GSON.toJson(FACADE.getAllDogsFromOwner(id))).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createADog(String content)
    {
        DogDto dogDto = GSON.fromJson(content, DogDto.class);

        return Response.ok(GSON.toJson(FACADE.createDog(dogDto))).build();
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateADog(String content)
    {
        DogDto dogDto = GSON.fromJson(content, DogDto.class);

        return Response.ok(GSON.toJson(FACADE.updateDog(dogDto))).build();
    }

    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteADog(@PathParam("id") int id)
    {
        return Response.ok(GSON.toJson(FACADE.deleteADog(id))).build();
    }
}

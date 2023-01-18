package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.DogDto;
import dtos.WalkerDto;
import entities.Dog;
import entities.Owner;
import entities.Walker;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class DogResourceTest
{
    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();

    Walker w1, w2;
    Dog d1, d2;
    Owner o1, o2;

    private static HttpServer httpServer;
    private static EntityManagerFactory emf;

    static HttpServer startServer() {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }

    @BeforeAll
    public static void setUpClass() {
        //This method must be called before you request the EntityManagerFactory
        EMF_Creator.startREST_TestWithDB();
        emf = EMF_Creator.createEntityManagerFactoryForTest();

        httpServer = startServer();
        //Setup RestAssured
        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;
        RestAssured.defaultParser = Parser.JSON;
    }

    @AfterAll
    public static void closeTestServer() {
        //System.in.read();

        //Don't forget this, if you called its counterpart in @BeforeAll
        EMF_Creator.endREST_TestWithDB();
        httpServer.shutdownNow();
    }

    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();

        w1 = new Walker("John", "Johnvej", "20102010");
        w2 = new Walker("Mary", "Maryvej", "40302010");
        o1 = new Owner("Bob", "Bobvej", "50601020");
        o2 = new Owner("Lise", "Lisevej", "99886655");
        d1 = new Dog("King","Pitbull", "Male", LocalDate.now(), o1);
        d2 = new Dog("Rook", "Chihuahua", "Male", LocalDate.now(), o2);
        w1.addDog(d1);
        w1.addDog(d2);
        w2.addDog(d1);

        try {
            em.getTransaction().begin();
            em.createNamedQuery("Walker.deleteAllRows").executeUpdate();
            em.createNamedQuery("Dog.deleteAllRows").executeUpdate();
            em.createNamedQuery("Owner.deleteAllRows").executeUpdate();
            em.persist(w1);
            em.persist(w2);
            em.persist(d1);
            em.persist(d2);

            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }


    @Test
    public void testServerIsUp() {
        System.out.println("Testing is server UP");
        given().when().get("/dog").then().statusCode(200);
    }
    @Test
    public void testLogRequest() {
        System.out.println("Testing logging request details");
        given().log().all()
                .when().get("/dog")
                .then().statusCode(200);
    }
    @Test
    public void testLogResponse() {
        System.out.println("Testing logging response details");
        given()
                .when().get("/dog")
                .then().log().body().statusCode(200);
    }

    @Test
    public void getAllDogsByOwnerId(){
        List<DogDto> dogDtoList;

        dogDtoList = given()
                .contentType("application/json")
                .when()
                .get("/dog/owner/{id}", o1.getId())
                .then()
                .extract().body().jsonPath().getList("",DogDto.class);

        DogDto dogDto1 = new DogDto(d1);

        assertThat(dogDtoList, containsInAnyOrder(dogDto1));
    }

    @Test
    public void getAllDogs(){
        List<DogDto> dogDtoList;

        dogDtoList = given()
                .contentType("application/json")
                .when()
                .get("/dog")
                .then()
                .extract().body().jsonPath().getList("",DogDto.class);

        DogDto dogDto1 = new DogDto(d1);
        DogDto dogDto2 = new DogDto(d2);

        assertThat(dogDtoList, containsInAnyOrder(dogDto1,dogDto2));
    }

    @Test
    public void deleteDog() {
        given()
                .contentType(ContentType.JSON)
                .delete("/dog/" + d1.getId())
                .then()
                .statusCode(200)
                .extract().response().as(Boolean.class);
    }

    @Test
    public void updateTest() {
        d1.setName("Oswald");
        DogDto dogDto = new DogDto(d1);
        String requestBody = GSON.toJson(dogDto);

        given()
                .header("Content-type", ContentType.JSON)
                .body(requestBody)
                .when()
                .put("/dog/" + d1.getId())
                .then()
                .assertThat()
                .statusCode(200)
                .body("id", equalTo(d1.getId()))
                .body("name", equalTo("Oswald"))
                .body("breed", equalTo("Pitbull"));
    }

    @Test
    public void create() {
        Dog d3 = new Dog("Oswald", "Hund", "Male", LocalDate.now(), o1);
        DogDto dogDto = new DogDto(d3);

        String requestBody = GSON.toJson(dogDto);

        given()
                .header("Content-type", ContentType.JSON)
                .and()
                .body(requestBody)
                .when()
                .post("/dog")
                .then()
                .assertThat()
                .statusCode(200)
                .body("id", notNullValue())
                .body("name", equalTo(d3.getName()))
                .body("breed", equalTo(d3.getBreed()));
        //.body("userName", equalTo(profileDto.getUser().getUserName()));
    }
}

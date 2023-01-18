package facades;

import dtos.DogDto;
import dtos.OwnerDto;
import dtos.WalkerDto;
import entities.Dog;
import entities.Owner;
import entities.Walker;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DogFacadeTest
{
    private static EntityManagerFactory emf;
    private static DogFacade dogFacade;

    Walker w1, w2;
    Dog d1, d2;
    Owner o1, o2;

    public DogFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        dogFacade = DogFacade.getInstance(emf);
    }

    @BeforeEach
    public void setup() {
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
    void getAllDogsTest()
    {
        List<DogDto> actual = dogFacade.getAllDogs();
        assertEquals(2, actual.size());
    }

    @Test
    void getAllDogsFromOwnerIdTest()
    {
        List<DogDto> actual = dogFacade.getAllDogsFromOwner(o1.getId());
        assertEquals(1, actual.size());
    }

    @Test
    void createDogTest()
    {
        Dog dog = new Dog("NewDog", "TestDog", "Female", LocalDate.now(), o2);
        DogDto actual = dogFacade.createDog(new DogDto(dog));

        assertNotNull(actual);

        List<DogDto> dogDtoList = dogFacade.getAllDogs();
        assertEquals(3, dogDtoList.size());
    }

    @Test
    void updateDogTest()
    {
        d1.setOwner(o2);
        DogDto dogDto = new DogDto(d1);

        DogDto actual = dogFacade.updateDog(dogDto);

        assertEquals(o2.getId(), actual.getOwner().getId());
    }

    @Disabled
    @Test
    void deleteADogTest()
    {

        boolean actual = dogFacade.deleteADog(d1.getId());

        assertEquals(true, actual);

        assertEquals(0, o1.getDogs().size());
    }
}

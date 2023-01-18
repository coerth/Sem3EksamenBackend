package facades;

import dtos.OwnerDto;
import dtos.WalkerDto;
import entities.Dog;
import entities.Owner;
import entities.Walker;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OwnerFacadeTest
{
    private static EntityManagerFactory emf;
    private static OwnerFacade ownerFacade;

    Walker w1, w2;
    Dog d1, d2;
    Owner o1, o2;

    public OwnerFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        ownerFacade = OwnerFacade.getInstance(emf);
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
    void getAllOwnersTest()
    {
        List<OwnerDto> actual = ownerFacade.getAllOwners();
        assertEquals(2, actual.size());
    }
}

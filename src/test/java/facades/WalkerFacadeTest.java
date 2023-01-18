package facades;

import dtos.WalkerDto;
import entities.Dog;
import entities.Owner;
import entities.User;
import entities.Walker;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;
import security.errorhandling.AuthenticationException;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WalkerFacadeTest
{
    private static EntityManagerFactory emf;
    private static WalkerFacade walkerFacade;

    Walker w1, w2;
    Dog d1, d2;
    Owner o1, o2;

    public WalkerFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        walkerFacade = WalkerFacade.getInstance(emf);
    }

    @BeforeEach
    public void setup() {
        EntityManager em = emf.createEntityManager();

        w1 = new Walker("John", "Johnvej", "20102010");
        w2 = new Walker("Mary", "Maryvej", "40302010");
        o1 = new Owner("Bob", "Bobvej", "50601020");
        o2 = new Owner("Lise", "Lisevej", "99886655");
        d1 = new Dog("King", "Male", LocalDate.now(), o1);
        d2 = new Dog("Rook", "Male", LocalDate.now(), o2);
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
    void getAllWalkers()
    {
        List<WalkerDto> actual = walkerFacade.getAllWalkers();
        assertEquals(2, actual.size());
    }

}

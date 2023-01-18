package facades;

import dtos.DogDto;
import entities.Dog;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;

public class DogFacade
{
    private static EntityManagerFactory emf;
    private static DogFacade instance;

    private DogFacade() {
    }

    public static DogFacade getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new DogFacade();
        }
        return instance;
    }


    public List<DogDto> getAllDogsFromOwner(int id)
    {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Dog> query = em.createQuery("SELECT d FROM Dog d WHERE d.owner.id = :id", Dog.class);
        query.setParameter("id", id);
        List<Dog> dogList = query.getResultList();

        return DogDto.getDTOS(dogList);

    }
}

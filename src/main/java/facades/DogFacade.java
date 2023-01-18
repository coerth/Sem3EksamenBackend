package facades;

import dtos.DogDto;
import dtos.WalkerDto;
import entities.Dog;
import entities.Owner;
import entities.Walker;

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


    public List<DogDto> getAllDogs()
    {
        EntityManager em = emf.createEntityManager();

        TypedQuery<Dog> query = em.createQuery("SELECT d FROM Dog d", Dog.class);
        List<Dog> dogList = query.getResultList();

        return DogDto.getDTOS(dogList);
    }

    public List<DogDto> getAllDogsFromOwner(int id)
    {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Dog> query = em.createQuery("SELECT d FROM Dog d WHERE d.owner.id = :id", Dog.class);
        query.setParameter("id", id);
        List<Dog> dogList = query.getResultList();

        return DogDto.getDTOS(dogList);
    }

    public DogDto createDog(DogDto dogDto)
    {
        Dog dog = new Dog(dogDto);

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        if(dog.getOwner().getId() != null)
        {
            em.merge(dog.getOwner());
        }
        else{
        em.persist(dog);
        }
        em.getTransaction().commit();

        return new DogDto(dog);
    }

    public DogDto updateDog(DogDto dogDto)
    {
        EntityManager em = emf.createEntityManager();

        Dog dog = new Dog(dogDto);
        Owner owner;
        if(dogDto.getOwner().getId() != null)
        {
            owner = em.find(Owner.class, dogDto.getOwner().getId());
            dog.setOwner(owner);
        }

        em.getTransaction().begin();
        em.merge(dog);
        em.getTransaction().commit();

        return new DogDto(dog);
    }
}

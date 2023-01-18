package facades;

import dtos.DogDto;
import dtos.WalkerDto;
import entities.Dog;
import entities.Owner;
import entities.Walker;
import handlers.DogHandler;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

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
        EntityManager em = emf.createEntityManager();
        Dog dog = new Dog(dogDto);
        Owner owner = em.find(Owner.class, dog.getOwner().getId());
        owner.addDog(dog);

        em.getTransaction().begin();
        if(dog.getOwner().getId() != null)
        {
            em.merge(owner);
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
        Set<Walker> updatedWalkerSet = new LinkedHashSet<>();

        Dog dog = em.find(Dog.class, dogDto.getId());
        Owner owner;
        if(dogDto.getOwner().getId() != null && !dogDto.getOwner().getId().equals(dog.getOwner().getId()))
        {
            owner = em.find(Owner.class, dogDto.getOwner().getId());
            dog.setOwner(owner);
        }
        Dog updatedDog = DogHandler.dogUpdateHandler(dog, dogDto);

        for(DogDto.InnerWalkerDto walkerDto : dogDto.getWalkers())
        {
            Walker walker = em.find(Walker.class, walkerDto.getId());
            updatedWalkerSet.add(walker);
        }
        if(!dog.getWalkers().equals(updatedWalkerSet))
        {
            dog.setWalkers(updatedWalkerSet);
        }


        em.getTransaction().begin();
        em.merge(updatedDog);
        em.getTransaction().commit();

        return new DogDto(dog);
    }

    public boolean deleteADog(int id)
    {
        EntityManager em = emf.createEntityManager();
        Dog dog = em.find(Dog.class, id);
        dog.getOwner().removeDog(dog);

        em.getTransaction().begin();
        em.remove(dog);
        em.getTransaction().commit();

        dog = em.find(Dog.class, id);
        if(dog == null)
        {
            return true;
        }
        return false;
    }
}

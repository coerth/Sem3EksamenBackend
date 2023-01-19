package facades;

import dtos.DogDto;
import dtos.WalkerDto;
import entities.Dog;
import entities.Owner;
import entities.Walker;
import errorhandling.GenericExceptionMapper;
import handlers.DogHandler;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.ws.rs.NotFoundException;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        em.persist(dog);
        em.getTransaction().commit();

        em.close();
        System.out.println(dog);
        return new DogDto(dog);
    }

    public DogDto updateDog(DogDto dogDto) throws NotFoundException
    {
        EntityManager em = emf.createEntityManager();
        Set<Walker> updatedWalkerSet = new LinkedHashSet<>();

        try{

        Dog dogFromDb = em.find(Dog.class, dogDto.getId());
        Owner owner;
        if(dogDto.getOwner().getId() != null && !dogDto.getOwner().getId().equals(dogFromDb.getOwner().getId()))
        {
            owner = em.find(Owner.class, dogDto.getOwner().getId());
            dogFromDb.setOwner(owner);
        }
        Dog updatedDog = DogHandler.dogUpdateHandler(dogFromDb, dogDto);

        for(DogDto.InnerWalkerDto walkerDto : dogDto.getWalkers())
        {
            Walker walker = em.find(Walker.class, walkerDto.getId());
            //walker.addDog(updatedDog);
            updatedWalkerSet.add(walker);

        }

            System.out.println(updatedWalkerSet);
            if(!dogFromDb.getWalkers().equals(updatedWalkerSet))
        {
            dogFromDb.setWalkers(updatedWalkerSet);
        }


        em.getTransaction().begin();
        em.merge(updatedDog);
        em.getTransaction().commit();

        return new DogDto(dogFromDb);

        }catch (
                NullPointerException ex)
        {
            Logger.getLogger(GenericExceptionMapper.class.getName()).log(Level.SEVERE, null, ex);
            throw new NotFoundException("No Dog found with that Id");
        }
    }

    public boolean deleteADog(int id) throws NotFoundException
    {
        EntityManager em = emf.createEntityManager();
        Dog dog;
        try {
        dog = em.find(Dog.class, id);
        dog.getOwner().removeDog(dog);

        em.getTransaction().begin();
        em.remove(dog);
        em.getTransaction().commit();

        }catch (
                NullPointerException ex)
        {
            Logger.getLogger(GenericExceptionMapper.class.getName()).log(Level.SEVERE, null, ex);
            throw new NotFoundException("No Dog found with that Id");
        }

        dog = em.find(Dog.class, id);
        if(dog == null)
        {
            return true;
        }
        return false;


    }
}

package facades;

import dtos.DogDto;
import dtos.OwnerDto;
import entities.Dog;
import entities.Owner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.ws.rs.QueryParam;
import java.util.List;

public class OwnerFacade
{
    private static EntityManagerFactory emf;
    private static OwnerFacade instance;

    private OwnerFacade() {
    }

    public static OwnerFacade getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new OwnerFacade();
        }
        return instance;
    }

    public List<OwnerDto> getAllOwners()
    {
        EntityManager em = emf.createEntityManager();

        TypedQuery<Owner> query = em.createQuery("SELECT o FROM Owner o", Owner.class);
        List<Owner> ownerList = query.getResultList();

        return OwnerDto.getDTOS(ownerList);
    }

}

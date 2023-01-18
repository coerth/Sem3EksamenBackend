package facades;

import dtos.WalkerDto;
import entities.Walker;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

public class WalkerFacade
{
    private static EntityManagerFactory emf;
    private static WalkerFacade instance;

    private WalkerFacade() {
    }

    public static WalkerFacade getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new WalkerFacade();
        }
        return instance;
    }

    public List<WalkerDto> getAllWalkers()
    {
        EntityManager em = emf.createEntityManager();

        TypedQuery<Walker> query = em.createQuery("SELECT w FROM Walker w", Walker.class);
        List<Walker> walkerList = query.getResultList();

        return WalkerDto.getDTOS(walkerList);
    }

    public List<WalkerDto> getAllWalkersByDogId(int id)
    {
        EntityManager em = emf.createEntityManager();

        TypedQuery<Walker> query = em.createQuery("SELECT w FROM Walker w join w.dogs d Where d.id = :id", Walker.class);
        query.setParameter("id", id);
        List<Walker> walkerList = query.getResultList();

        return WalkerDto.getDTOS(walkerList);
    }
}

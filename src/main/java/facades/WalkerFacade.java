package facades;

import javax.persistence.EntityManagerFactory;

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

}

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class App {
    private static final String GRINGOTTS_PU= "Gringotts";
    private static final String SALES_PU = "sales";
    private static final String UNIVERSITYSYSTEM_PU = "universitySystem";
    private static final String hospital_PU = "hospital";
    private static final String bills_payment_system_PU = "bills_payment_system";
    public static void main(String[] args) {

        EntityManagerFactory entityManagerFactory=
                Persistence.createEntityManagerFactory(bills_payment_system_PU);
     //  EntityManager entityManager = entityManagerFactory.createEntityManager();
     //  Engine engine = new Engine(entityManager);

     //  engine.run();
    }
}

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Map;

public class App {
    private static final String GRINGOTTS_PU = "gringotts";
    private static final String SALES_PU = "sales";
    private static final String UNIVERSITY_PU = "university";
    private static final String HOSPITAL_PU = "hospital";
    private static final String BILLS_PU = "bills";
    private static final String FOOTBALL_PU = "football";
    public static void main(String[] args) {

        EntityManagerFactory entityManagerFactory =
                Persistence.createEntityManagerFactory(FOOTBALL_PU);

//        EntityManager entityManager = entityManagerFactory.createEntityManager();
//
//        Engine engine = new Engine(entityManager);
//
//        engine.run();
    }
}

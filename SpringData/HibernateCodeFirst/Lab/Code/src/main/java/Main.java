import entities.Car;
import entities.Truck;
import entities.Vehicle;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("mydb");
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

        Vehicle car = new Car(4);
        Vehicle truck = new Truck(15, 4);

        em.persist(car);
        em.persist(truck);

        em.getTransaction().commit();

    }
}

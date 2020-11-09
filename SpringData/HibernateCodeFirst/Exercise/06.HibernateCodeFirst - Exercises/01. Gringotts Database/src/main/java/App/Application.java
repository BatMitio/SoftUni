package App;

import App.Entities.WizardDeposits;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Application {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("wizard_deposits");
        EntityManager entityManager = emf.createEntityManager();

        entityManager.getTransaction().begin();

        WizardDeposits wizardDeposits = new WizardDeposits("Pesho", "Peshev", 25);
        entityManager.persist(wizardDeposits);

        entityManager.getTransaction().commit();

    }
}

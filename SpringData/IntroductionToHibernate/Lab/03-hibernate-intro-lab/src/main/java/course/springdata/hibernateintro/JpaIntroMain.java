package course.springdata.hibernateintro;

import course.springdata.hibernateintro.entity.Student;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JpaIntroMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("school");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Student student = new Student("Teo");
        em.persist(student);
        em.getTransaction().commit();
    }
}

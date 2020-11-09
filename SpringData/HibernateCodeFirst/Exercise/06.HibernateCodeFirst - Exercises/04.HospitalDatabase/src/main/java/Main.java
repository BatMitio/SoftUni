package App;

import Entities.*;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        EntityManager entityManager = Persistence.createEntityManagerFactory("hospital_db")
                .createEntityManager();

        Scanner scanner = new Scanner(System.in);

        entityManager.getTransaction().begin();

        System.out.println("Welcome to Hospital Database!");

        System.out.println("Enter a number 1-9");
        System.out.println("1. Get all patients");
        System.out.println("2. Get all diagnosis");
        System.out.println("3. Add new patient");
        System.out.println("4. Get Patient name, diagnosis and medicaments by patient id");
        int choice =  scanner.nextInt();

        switch(choice){
            case 1:
                printAllPatients(entityManager);
                break;
            case 2:
                printAllDiagnosis(entityManager);
                break;
            case 3:
                addNewPatient(entityManager);
                break;
            case 4:
                getPatientById(entityManager);
            default:
                break;
        }

        entityManager.getTransaction().commit();
    }

    private static void printAllPatients(EntityManager entityManager){
        List<Patient> patients = entityManager.createQuery("FROM Patient", Patient.class).getResultList();

        patients.forEach(p -> {
            System.out.println(p.getFirstName() + " " + p.getLastName());
        });
    }

    private static void printAllDiagnosis(EntityManager entityManager){
        List<Visit> visits = entityManager.createQuery("FROM Visit v WHERE v.diagnose.name is not null", Visit.class).getResultList();

        visits.forEach(v -> {
            System.out.println("Diagnose name: " + v.getDiagnose().getName() + ", Patient Name: " + v.getPatient().getFirstName() + " " + v.getPatient().getLastName());
        });
    }

    private static void addNewPatient(EntityManager entityManager){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a patient name: [First name Last name]");
        String[] patientInfo = scanner.nextLine().split(" ");
        Patient patient = new Patient(patientInfo[0], patientInfo[1]);

        System.out.println("Enter a Diagnose name: [name]");
        Diagnose diagnose = new Diagnose(scanner.nextLine());

        System.out.println("Enter a diagnose comment");
        DiagnoseComment diagnoseComment = new DiagnoseComment(scanner.nextLine());
        diagnoseComment.setDiagnose(diagnose);
        diagnose.getComments().add(diagnoseComment);

        System.out.println("Enter a medicament name: ");
        Medicament medicament = new Medicament(scanner.nextLine());

        System.out.println("Enter a visit comment: ");
        VisitComment visitComment = new VisitComment(scanner.nextLine());
        Visit visit = new Visit(patient, diagnose, medicament);
        visitComment.setVisit(visit);

        entityManager.persist(visitComment);
        entityManager.persist(diagnoseComment);
        entityManager.persist(visit);
        entityManager.flush();


    }
    private static void getPatientById(EntityManager entityManager){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a patient id: ");
        int id = scanner.nextInt();
        Patient patient = entityManager.createQuery("FROM Patient p where p.id = " + id, Patient.class).getSingleResult();

        System.out.println("Patient name: " + patient.getFirstName() + " " + patient.getLastName());
        System.out.println("Patient medicaments:");
        patient.getVisits().forEach(v -> {
            System.out.println(v.getMedicament().getName());
        });
        System.out.println("Patient Diagnosis: ");
        patient.getVisits().forEach(v -> System.out.println(v.getDiagnose().getName()));

    }
}

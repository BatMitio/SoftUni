import model.*;

import javax.persistence.EntityManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.List;

public class Engine implements Runnable {

    private final EntityManager entityManager;
    private final BufferedReader reader;


    public Engine(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.reader = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public void run() {
        // EX 2
//        changeCasingEx2();

        // EX 3
//        try {
//            containsEmployeeEX3();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        // EX 4
//        employeesWithSalaryOver50000EX4();

        // EX 5
//        getEmployeesFromDepartmentEX5();

        // EX 6
//        try {
//            addAddressAndUpdateEmployeeEx6();//TODO: BE CAREFUL WHEN RUNNING THIS METHOD (APPLIES CHANGES)
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        // EX 7
//        addressesWithEmployeesCountEX7();

        // EX 8
//        try {
//            getEmployeeWithProjectsEX8();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        // EX 9
//        findLatest10ProjectsEX9();

        // EX 10
//        increaseSalariesEX10();

        // EX 11
//        findEmployeesByFirstNameEX11(); //TODO: RUN BEFORE EX 10 FOR RIGHT RESULT!!!

        // EX 12
//        employeeMaximumSalariesEX12();  //TODO: RUN BEFORE EX 10 FOR RIGHT RESULT!!!

        // EX 13
//        try {
//            removeTownsEX13();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }


    private void changeCasingEx2() {
        List<Town> towns = entityManager
                .createQuery("SELECT t FROM Town t " +
                        "WHERE length(t.name) <= 5", Town.class)
                .getResultList();

        entityManager.getTransaction().begin();
        towns.forEach(entityManager::detach);

        for (Town town : towns) {
            town.setName(town.getName().toLowerCase());
        }
        towns.forEach(entityManager::merge);
        entityManager.flush();
        entityManager.getTransaction().commit();
    }

    private void containsEmployeeEX3() throws IOException {
        System.out.println("Enter employee full name:");
        String fullName = reader.readLine();

        List<Employee> employees = entityManager
                .createQuery("SELECT e FROM Employee e " +
                        "WHERE concat(e.firstName, ' ', e.lastName) = :name", Employee.class)
                .setParameter("name", fullName)
                .getResultList();

        System.out.println(employees.size() == 0 ? "No" : "Yes");
    }

    private void employeesWithSalaryOver50000EX4() {
        entityManager
                .createQuery("SELECT e FROM Employee e " +
                        "WHERE e.salary > 50000", Employee.class)
                .getResultStream()
                .map(Employee::getFirstName)
                .forEach(System.out::println);

    }

    private void getEmployeesFromDepartmentEX5() {
        entityManager
                .createQuery("SELECT e FROM Employee e " +
                        "WHERE e.department.name = 'Research and Development' " +
                        "ORDER BY e.salary, e.id", Employee.class)
                .getResultStream()
                .forEach(employee -> {
                    System.out.printf("%s %s from Research and Development - $%.2f%n",
                            employee.getFirstName(), employee.getLastName(),
                            employee.getSalary());
                });


    }

    private void addAddressAndUpdateEmployeeEx6() throws IOException {
        Address address = createAddress("Vitoshka 15");

        System.out.println("Enter employee last name:");
        String lastName = reader.readLine();

        entityManager
                .createQuery("SELECT e FROM Employee e " +
                        "WHERE e.lastName = :name", Employee.class)
                .setParameter("name", lastName)
                .getSingleResult();
    }

    private void addressesWithEmployeesCountEX7() {
        List<Address> addresses = entityManager
                .createQuery("SELECT a FROM Address a " +
                        "ORDER BY a.employees.size DESC", Address.class)
                .setMaxResults(10)
                .getResultList();
        addresses.forEach(address -> {
            System.out.printf("%s, %s - %d employees%n", address.getText(),
                    address.getTown().getName(), address.getEmployees().size());
        });
    }

    private void getEmployeeWithProjectsEX8() throws IOException {
        System.out.println("Enter valid employee id:");
        int id = Integer.parseInt(reader.readLine());

        Employee employee = entityManager
                .find(Employee.class, id);

        System.out.printf("%s %s - %s%n",
                employee.getFirstName(),
                employee.getLastName(),
                employee.getJobTitle());

        employee.getProjects()
                .stream()
                .sorted(Comparator.comparing(Project::getName))
                .forEach(project -> {
                    System.out.printf("\t%s%n", project.getName());
                });
    }

    private void findLatest10ProjectsEX9() {
        List<Project> projects = entityManager
                .createQuery("SELECT p FROM Project p " +
                        "ORDER BY p.startDate DESC ", Project.class)
                .setMaxResults(10)
                .getResultList();
        projects
                .stream()
                .sorted(Comparator.comparing(Project::getName))
                .forEach(project -> {
                    System.out.printf("Project name: %s%n\t" +
                                    "Project Description: %s%n\t" +
                                    "Project Start Date:%s%n\t" +
                                    "Project End Date: %s%n",
                            project.getName(),
                            project.getDescription(),
                            project.getStartDate(),
                            project.getEndDate());
                });
    }

    private void increaseSalariesEX10() {
        entityManager.getTransaction().begin();
        int affectedRows = entityManager
                .createQuery("UPDATE Employee e " +
                        "SET e.salary = e.salary * 1.12 " +
                        "WHERE e.department.id IN (1, 2, 4, 11)")
                .executeUpdate();
        entityManager.getTransaction().commit();

//        System.out.println("Affected rows: " + affectedRows); extra

        entityManager.createQuery("SELECT e FROM Employee e " +
                "WHERE e.department.id IN (1, 2, 4, 11)", Employee.class)
                .getResultStream()
                .forEach(employee -> {
                    System.out.printf("%s %s ($%.2f)%n",
                            employee.getFirstName(),
                            employee.getLastName(),
                            employee.getSalary());
                });
    }

    private void findEmployeesByFirstNameEX11() {
        List<Employee> employees = entityManager
                .createQuery("SELECT e FROM Employee e " +
                        "WHERE e.firstName LIKE 'SA%'", Employee.class)
                .getResultList();

        employees
                .forEach(employee -> {
                    System.out.printf("%s %s - %s - ($%.2f)%n",
                            employee.getFirstName(), employee.getLastName(),
                            employee.getJobTitle(),
                            employee.getSalary());
                });
    }

    private void employeeMaximumSalariesEX12() {

        List<Department> departments = entityManager
                .createQuery("SELECT d FROM Department d " +
                        "WHERE d.manager.salary NOT BETWEEN 30000 AND 70000 " +
                        "GROUP BY d.name", Department.class)
                .getResultList();
        departments
                .forEach(department -> {
                    System.out.printf("%s %.2f%n", department.getName(), department.getManager().getSalary());
                });


    }

    private void removeTownsEX13() throws IOException {
        System.out.println("Enter town name:");
        String townName = reader.readLine();
        Integer townId = entityManager
                .createQuery("SELECT t.id FROM Town t " +
                        "WHERE t.name = :name", Integer.class)
                .setParameter("name", townName)
                .getSingleResult();
        entityManager.getTransaction().begin();
        entityManager
                .createQuery("UPDATE Employee e " +
                        "SET e.address = NULL " +
                        "WHERE e.address.id IN (SELECT a FROM Address a " +
                        "WHERE a.town.id = :townId)")
                .setParameter("townId", townId)
                .executeUpdate();
        entityManager.flush();
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        int affectedRows = entityManager
                .createQuery("DELETE FROM Address a " +
                        "WHERE a.town.id = :townId")
                .setParameter("townId", townId)
                .executeUpdate();
        entityManager.flush();
        entityManager.getTransaction().commit();
        if (affectedRows == 1) {
            System.out.println("1 address in " + townName + " deleted");
        } else {
            System.out.printf("%d addresses in %s deleted", affectedRows, townName);
        }
        entityManager.getTransaction().begin();
        entityManager
                .createQuery("DELETE FROM Town t " +
                        "WHERE t.id = :townId")
                .setParameter("townId", townId)
                .executeUpdate();
        entityManager.flush();
        entityManager.getTransaction().commit();
    }

    private Address createAddress(String addressText) {
        Address address = new Address();
        address.setText(addressText);

        entityManager.getTransaction().begin();
        entityManager.persist(address);
        entityManager.getTransaction().commit();
        return address;
    }


}

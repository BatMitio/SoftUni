import entities.*;

import javax.persistence.EntityManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Engine implements Runnable {

    private final EntityManager entityManager;
    private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public Engine(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void run() {
        System.out.println("Enter exercise number: [2-13] or 0 for exit");

        try {
            int choice = Integer.parseInt(reader.readLine());
            outer:
            while (choice >= 2 && choice <= 13) {
                switch (choice) {
                    case 2:
                        changeCasingEx2();
                        break;
                    case 3:
                        containsEmployeeEx3();
                        break;
                    case 4:
                        employeesWithSalaryOver5000Ex4();

                        break;
                    case 5:
                        employeesFromDepartmentEx5();
                        break;
                    case 6:
                        addingNewAddressAndUpdatingEmployeeEx6();
                        break;
                    case 7:
                        addressesWithEmployeeCountEx7();
                        break;
                    case 8:
                        getEmployeeWithProjectEx8();
                        break;
                    case 9:
                        findLatest10ProjectsEx9();
                        break;
                    case 10:
                        increaseSalariesEx10();
                        break;
                    case 11:
                        findEmployeesByFirstNamePatternEx11();
                        break;
                    case 12:
                        employeesMaximumSalariesEx12();
                        break;
                    case 13:
                        removeTownsEx13();
                        break;
                    default:
                        break outer;

                }
                System.out.println("Enter exercise number: [2-13] or 0 for exit");
                choice = Integer.parseInt(reader.readLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void removeTownsEx13() throws IOException {
        System.out.println("Enter town name:");
        String town = reader.readLine();
        List<Address> addressesList = entityManager.createQuery("select a from Address a " +
                "where a.town.name = :name", Address.class).setParameter("name", town).getResultList();
        entityManager.getTransaction().begin();
        for (Address a : addressesList) {
            for (Employee e : a.getEmployees()) {
                e.setAddress(null);
            }
        }
        for (Address a : addressesList) {
            entityManager.remove(a);
        }
        Town t = entityManager.createQuery("select t from Town t where t.name = :name", Town.class)
                .setParameter("name", town).getSingleResult();
        entityManager.remove(t);
        entityManager.getTransaction().commit();

        if (addressesList.size() == 0 || addressesList.size() == 1) {
            System.out.printf("%d address in %s deleted.%n", addressesList.size(), town);
        } else {
            System.out.printf("%d addresses in %s deleted.%n", addressesList.size(), town);
        }
    }

    private void employeesMaximumSalariesEx12() {

        List<BigDecimal> salaries = entityManager.createQuery("select max(e.salary) from Employee e " +
                "group by e.department having max(e.salary) not between 30000 and 70000", BigDecimal.class)
                .getResultList();

        List<String> departments = entityManager.createQuery("select e.department.name from Employee e " +
                "group by e.department having max(e.salary) not between 30000 and 70000", String.class)
                .getResultList();

        for (int i = 0; i < salaries.size(); i++) {
            String depName = departments.get(i);
            BigDecimal salary = salaries.get(i);
            System.out.printf("%s %.2f%n", depName, salary);
        }


    }

    private void findEmployeesByFirstNamePatternEx11() throws IOException {
        System.out.println("Provide name pattern:");
        String pattern = reader.readLine() + "%";
        entityManager.createQuery("select e from Employee e " +
                "where e.firstName like :name ", Employee.class).setParameter("name", pattern).getResultList()
                .forEach(e -> System.out.printf("%s %s - %s - ($%.2f)%n"
                        , e.getFirstName()
                        , e.getLastName()
                        , e.getJobTitle()
                        , e.getSalary()));
    }

    private void increaseSalariesEx10() {
        entityManager.getTransaction().begin();
        entityManager.createQuery("UPDATE Employee e " +
                "set e.salary = e.salary * 1.12 " +
                "where e.department.id in (1, 2, 4, 11)").executeUpdate();
        entityManager.getTransaction().commit();

        entityManager.createQuery("select e from Employee e " +
                "where e.department.id in (1, 2, 4, 11)", Employee.class).getResultList()
                .forEach(e -> System.out.printf("%s %s (%.2f)%n"
                        , e.getFirstName()
                        , e.getLastName()
                        , e.getSalary()));
    }


    private String getDateTime(LocalDateTime localDateTime) {

        return localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
    }

    private void findLatest10ProjectsEx9() {
        List<Project> projectList = entityManager.createQuery("select p FROM Project p " +
                "order by p.startDate DESC ", Project.class).setMaxResults(10).getResultList();
        projectList.sort(Comparator.comparing(Project::getName));
        projectList
                .forEach(p -> {
                    System.out.printf("Project name: %s%n", p.getName());
                    System.out.printf("\tProject Description: %s%n", p.getDescription());
                    System.out.printf("\tProject Start Date: %s%n", p.getStartDate() == null ? "null" : getDateTime(p.getStartDate()));
                    System.out.printf("\tProject End Date: %s%n", p.getEndDate() == null ? "null" : getDateTime(p.getEndDate()));

                });
    }

    private void getEmployeeWithProjectEx8() throws IOException {
        System.out.println("Enter employee`s id:");

        int employeeId = Integer.parseInt(reader.readLine());

        Employee employee = entityManager.find(Employee.class, employeeId);
        System.out.printf("%s %s - %s%n"
                , employee.getFirstName()
                , employee.getLastName()
                , employee.getJobTitle());

        employee.getProjects().stream()
                .sorted(Comparator.comparing(Project::getName)).forEach(p -> System.out.println("\t" + p.getName()));

    }

    private void addressesWithEmployeeCountEx7() {
        entityManager.createQuery("select a from Address a " +
                "ORDER BY a.employees.size desc ", Address.class).setMaxResults(10).getResultList()
                .forEach(a -> System.out.printf("%s, %s - %d employees%n",
                        a.getText(), a.getTown().getName(), a.getEmployees().size()
                ));
    }

    private void addingNewAddressAndUpdatingEmployeeEx6() throws IOException {
        Address address = createAddress();
        System.out.println("Enter employee`s last name:");
        String lastName;

        lastName = reader.readLine();

        Employee employee = entityManager.createQuery("select e from Employee e " +
                "where e.lastName = :lastName ", Employee.class).setParameter("lastName", lastName)
                .setMaxResults(1).getSingleResult();

        entityManager.getTransaction().begin();
        employee.setAddress(address);
        entityManager.getTransaction().commit();


    }

    private Address createAddress() {
        Address address = new Address();
        address.setText("Vitoshka 15");
        address.setId(32);
        entityManager.getTransaction().begin();
        entityManager.persist(address);
        entityManager.getTransaction().commit();
        return address;
    }

    private void employeesFromDepartmentEx5() {
        entityManager.createQuery("select e from Employee e " +
                "where e.department.name = 'Research and Development' " +
                "order by e.salary, e.id", Employee.class).getResultList()
                .forEach(e -> System.out.printf("%s %s from Research and Development - $%.2f%n",
                        e.getFirstName(), e.getLastName(), e.getSalary()));
    }

    private void employeesWithSalaryOver5000Ex4() {
        entityManager.createQuery("SELECT e from Employee e " +
                "WHERE e.salary > 50000", Employee.class)
                .getResultList().forEach(e -> System.out.println(e.getFirstName()));
    }

    private void containsEmployeeEx3() throws IOException {
        System.out.println("Enter employee`s full name:");
        String fullName;

        fullName = reader.readLine();

        List<Employee> employees = entityManager.createQuery("SELECT e from Employee e " +
                "where concat(e.firstName, ' ', e.lastName) = :name", Employee.class)
                .setParameter("name", fullName).getResultList();

        System.out.println(employees.isEmpty() ? "No" : "Yes");
    }

    private void changeCasingEx2() {
        List<Town> towns = entityManager.createQuery("SELECT t from Town t " +
                "WHERE length(t.name) <= 5 ", Town.class).getResultList();

        entityManager.getTransaction().begin();
        towns.forEach(entityManager::merge);
        towns.forEach(t -> t.setName(t.getName().toLowerCase()));
        entityManager.flush();
        entityManager.getTransaction().commit();
    }
}

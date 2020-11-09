import model.*;

import javax.persistence.EntityManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;

import static java.lang.Integer.parseInt;

public class Engine implements Runnable {

    private final EntityManager entityManager;
    private  final BufferedReader reader ;

    public Engine(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.reader= new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public void run() {
            changeCasingEx2();
       
        try {
            containsEmployeeEx3();
        } catch (IOException e) {
            e.printStackTrace();
        }
        employeeWithSalaryOver50000EX4();
        employeesFromDepartmentEx5();
        try {
            addingNewAddressAndUpdatingEmployeeEx6();
        } catch (IOException e) {
            e.printStackTrace();
        }
         addressesWithEmployeeCountEx7();
        try {
            getEmployeeWithProjectEx8();
        } catch (IOException e) {
            e.printStackTrace();
        }

        findLatest10ProjectsEx9();

        increaseSalariesEx10();
        try {
            findEmployeesByFirstNameEx11();
        } catch (IOException e) {
            e.printStackTrace();
        }




    }

    private void findEmployeesByFirstNameEx11() throws IOException {

        System.out.println("Enter part of name");

        String partOfName = reader.readLine();
        String pn= partOfName+"%";

        List<Employee> employees = entityManager.createQuery("SELECT e FROM Employee e WHERE e.firstName like :pn ",Employee.class)
                .setParameter("pn",pn)
                .getResultList();

        employees.forEach(employee ->
                        System.out.printf("%s %s - %s - ($%.2f)%n"
                                ,employee.getFirstName()
                                ,employee.getLastName()
                                ,employee.getJobTitle()
                                ,employee.getSalary())
                );


    }

    private void increaseSalariesEx10() {

        entityManager.getTransaction().begin();
        int detectedRows = entityManager
                .createQuery("UPDATE  Employee e " +
                        "SET e.salary = e.salary*1.12 " +
                        "where e.department.id IN (1,2,4,11)")
                .executeUpdate();
        entityManager.getTransaction().commit();

        List<Employee> employees = entityManager.createQuery("SELECT e FROM Employee e where e.department.id IN (1,2,4,11)",Employee.class).getResultList();

        employees.forEach(employee ->
                        System.out.printf("%s %s ($%.2f)%n"
                                ,employee.getFirstName()
                                ,employee.getLastName()
                                ,employee.getSalary())
                );
    }

    private void findLatest10ProjectsEx9() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.s");
        List<Project> projects = entityManager.createQuery("SELECT p FROM Project p order by p.startDate desc ",Project.class)
                .setMaxResults(10)
                .getResultList();

        projects
                .stream()
                .sorted(Comparator.comparing(Project::getName))
                .forEach(project ->
                                System.out.printf("Project name: %s\n" +
                                        " \tProject Description: %s\n" +
                                        " \tProject Start Date:%s\n" +
                                        " \tProject End Date: %s\n "
                                        ,project.getName(),
                                        project.getDescription()
                                        ,project.getStartDate().format(formatter)
                                        ,project.getEndDate())
                        );



    }

    private void getEmployeeWithProjectEx8() throws IOException {

        System.out.println("Enter ID please");
        int id = parseInt(reader.readLine());

    Employee employee = entityManager.createQuery("SELECT e FROM Employee e WHERE e.id= :id",Employee.class)
            .setParameter("id",id).getSingleResult();

        System.out.printf("%s %s - %s%n",employee.getFirstName()
        ,employee.getLastName()
        ,employee.getJobTitle());

       employee.getProjects()
               .stream()
               .sorted(Comparator.comparing(Project::getName))
               .forEach(project ->
                               System.out.printf("%s%n",project.getName())
                       );

    }

    private void addressesWithEmployeeCountEx7() {

        List<Address> addresses = entityManager.createQuery("select a FROM Address a " +
                "ORDER BY a.employees.size DESC",Address.class)
                .setMaxResults(10)
                .getResultList();
        System.out.println();
        System.out.println("If you have a different result, maybe you're base is not clear".toUpperCase());
        System.out.println();
        addresses.forEach(a-> System.out.printf("%s, %s - %d%n",a.getText()
                ,a.getTown().getName()
                ,a.getEmployees().size()));

    }

    private void addingNewAddressAndUpdatingEmployeeEx6() throws IOException {
        Address address = createNewAddress("Vitoshka 15");

        System.out.println("Enter valid last name");

        String name = reader.readLine();

        Employee employee = entityManager
                .createQuery("select e from Employee e " +
                        "where e.lastName = :name",Employee.class)
                .setParameter("name",name)
                .getSingleResult();
        entityManager.getTransaction().begin();
        employee.setAddress(address);
        entityManager.getTransaction().commit();


    }

    private void employeesFromDepartmentEx5() {
        List<Employee> employees = entityManager
                .createQuery("select e from Employee e " +
                        "where e.department.name = 'Research and Development' " +
                        "order by e.salary,e.id",Employee.class)
                .getResultList();
        for (Employee employee : employees) {
            System.out.printf("%s %s from %s - %.2f%n"
                    ,employee.getFirstName()
                    ,employee.getLastName()
                    ,employee.getDepartment().getName()
                    ,employee.getSalary());
        }
    }

    private void employeeWithSalaryOver50000EX4() {
          entityManager
                .createQuery("select e FROM Employee  e " +
                        "where e.salary>50000 ",Employee.class)
                .getResultStream()
                 .map(Employee::getFirstName)
                 .forEach(System.out::println);
    }

    private void containsEmployeeEx3() throws IOException {
        System.out.println("Enter employee full name please");
        String fullName = reader.readLine();
        List<Employee> employees = entityManager
                .createQuery("SELECT e FROM Employee e " +
                        "where concat(e.firstName,' ',e.lastName) = :name ",Employee.class)
                .setParameter("name",fullName)
                .getResultList();
        System.out.println(employees.size()==0 ? "NO" : "YES");
    }

    private void changeCasingEx2() {
        List<Town> towns = entityManager
                .createQuery("SELECT t FROM Town t " +
                        "where length(t.name)<=5 ",Town.class)
                .getResultList();
entityManager.getTransaction().begin();
towns.forEach(entityManager::detach);
for (Town town:towns){
    town.setName(town.getName().toLowerCase());
}
towns.forEach(entityManager::merge);
entityManager.flush();
entityManager.getTransaction().commit();

    }

    private Address createNewAddress(String addressName) {

        Address address = new Address();
        address.setText(addressName);
        entityManager.getTransaction().begin();
        entityManager.persist(address);
        entityManager.getTransaction().commit();

        return address;

    }
}

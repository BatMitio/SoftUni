import entities.*;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class Engine implements Runnable{
    private static EntityManager em;
    private final static String DB = "soft_uni";

    public Engine(EntityManager em) {
        this.em = em;
    }

    @Override
    public void run(){
        Scanner sc = new Scanner(System.in);
        int number = 0;
        boolean running = true;
        while (running){
            System.out.print("Please enter the number of the exercise (2 - 13) you want to check or 0 for exit: ");
            number = Integer.parseInt(sc.nextLine());
            switch (number){
                case 2: ex2ChangeCasing.run(); break;
                case 3: ex3ContainsEmployee.run(); break;
                case 4: ex4EmployeesWithSalaryOver50000.run(); break;
                case 5: ex5EmployeesFromDepartment.run(); break;
                case 6: ex6AddingANewAddressAndUpdatingEmployee.run(); break;
                case 7: ex7AddressesWithEmployeeCount.run(); break;
                case 8: ex8GetEmployeeWithProject.run(); break;
                case 9: ex9FindLatest10Projects.run(); break;
                case 10: ex10IncreaseSalaries.run(); break;
                case 11: ex11FindEmployeesByFirstName.run(); break;
                case 12: ex12EmployeesMaximumSalaries.run(); break;
                case 13: ex13RemoveTowns.run(); break;
                case 0:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid number!");
                    break;
            }
        }
        System.out.println("Bye!");
    }

    //Tuka uslovieto e mnogo neqsno taka che mislq che ne trqbva da se schita tazi zadacha za ocenqvane
    public static class ex2ChangeCasing{
        public static void run(){

            List<Town> towns = em
                    .createQuery("SELECT t FROM Town t " +
                            "WHERE length(t.name) <= 5", Town.class)
                    .getResultList();
            em.getTransaction().begin();
            towns.forEach(em::detach);

            for(Town town : towns){
                town.setName(town.getName().toLowerCase());
            }

            towns.forEach(em::merge);
            em.flush();
            em.getTransaction().commit();
            towns.forEach(t -> System.out.println(t.getName()));
        }
    }

    public static class ex3ContainsEmployee{
        public static void run() {
            Scanner sc = new Scanner(System.in);
            String name = sc.nextLine();
            List<Employee> employees = em.createQuery("SELECT e FROM Employee e " +
                    "WHERE concat(e.firstName, ' ', e.lastName) = :name", Employee.class)
                    .setParameter("name", name)
                    .setMaxResults(1)
                    .getResultList();
            System.out.println(employees.size() == 0 ? "No" : "Yes");
        }
    }

    public static class ex4EmployeesWithSalaryOver50000{
        public static void run(){
            em.createQuery("SELECT e FROM Employee e " +
                    "WHERE e.salary > 50000", Employee.class)
                    .getResultStream()
            .forEach(e -> System.out.println(e.getFirstName()));
        }
    }

    public static class ex5EmployeesFromDepartment{
        public static void run(){
            em.createQuery("SELECT e FROM Employee e " +
                    "WHERE e.department.name = :departmentName " +
                    "ORDER BY e.salary ASC, e.id ASC", Employee.class)
                    .setParameter("departmentName", "Research and Development")
                    .getResultStream()
            .forEach(e -> System.out.printf("%s %s from %s - $%.2f%n",
                    e.getFirstName(), e.getLastName(), e.getDepartment().getName(), e.getSalary()));
        }
    }

    public static class ex6AddingANewAddressAndUpdatingEmployee{
        public static void run(){
            Scanner sc = new Scanner(System.in);

            Address address = em.createQuery("SELECT a FROM Address a " +
                    "WHERE a.text = :text", Address.class)
                    .setParameter("text", "Vitoshka 15")
                    .getResultStream()
                    .findFirst().orElse(null);

            if(address == null){
                address= new Address();
                address.setText("Vitoshka 15");
                em.getTransaction().begin();

                em.persist(address);

                em.getTransaction().commit();
            }

            String employeeLastName = sc.nextLine();

            Employee employee = em.createQuery("SELECT e FROM Employee e " +
                    "WHERE e.lastName = :lastName", Employee.class)
                    .setParameter("lastName", employeeLastName)
                    .getResultStream().findFirst().orElse(null);

            if(employee == null)
                return;

            em.getTransaction().begin();




            employee.setAddress(address);

            em.getTransaction().commit();
            System.out.println("Make sure to refresh your db after this exercise!");
        }
    }

    public static class ex7AddressesWithEmployeeCount{
        public static void run(){
            em.createQuery("SELECT a FROM Address a " +
                    "ORDER BY a.employees.size DESC", Address.class)
                    .setMaxResults(10)
                    .getResultStream()
            .forEach(a -> System.out.printf("%s, %s - %d employees%n", a.getText(), a.getTown().getName(), a.getEmployees().size()));
        }
    }

    public static class ex8GetEmployeeWithProject{
        public static void run(){
            Scanner sc = new Scanner(System.in);
            int id = Integer.parseInt(sc.nextLine());

            Employee employee = em.find(Employee.class, id);

            if(employee == null)
                return;

            System.out.printf("%s %s - %s%n",
                    employee.getFirstName(), employee.getLastName(), employee.getJobTitle());
            employee.getProjects().stream().sorted((a, b) -> a.getName().compareTo(b.getName()))
                    .forEach(p -> System.out.println("\t  " + p.getName()));
        }
    }

    public static class ex9FindLatest10Projects{
        public static void run(){
            em.createQuery("SELECT p FROM Project p " +
                    "WHERE p.endDate IS NULL " +
                    "ORDER BY p.startDate DESC", Project.class)
                    .setMaxResults(10)
            .getResultStream().sorted((a, b) -> a.getName().compareTo(b.getName())).forEach(p -> {
                System.out.printf("Project name: %s%n", p.getName());
                System.out.printf("\t\tProject Description: %s%n", p.getDescription());
                System.out.printf("\t\tProject Start Date: %s%n",
                        p.getStartDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                System.out.printf("\t\tProject End Date: null%n");
            });

        }
    }

    public static class ex10IncreaseSalaries{
        public static void run(){
            String query = "SELECT e FROM Employee e " +
                    "WHERE e.department.name IN " +
                    "('Engineering', 'Tool Design', 'Marketing', 'Information Services')";

            List<Employee> employees = em.createQuery(query, Employee.class).getResultList();

            em.getTransaction().begin();

            for (Employee employee : employees) {
                em.detach(employee);
                employee.setSalary(employee.getSalary().multiply(new BigDecimal(1.12)));
                em.merge(employee);
            }
            em.flush();

            em.getTransaction().commit();

            em.createQuery(query, Employee.class).getResultStream()
            .forEach(e -> System.out.printf("%s %s ($%.2f)%n", e.getFirstName(), e.getLastName(), e.getSalary()));
        }
    }

    public static class ex11FindEmployeesByFirstName{
        public static void run(){
            Scanner sc = new Scanner(System.in);
            String startsWith = sc.nextLine();
            em.createQuery("SELECT e FROM Employee e " +
                    "WHERE e.firstName LIKE :pattern", Employee.class)
                    .setParameter("pattern", startsWith+"%")
                    .getResultStream()
            .forEach(e -> System.out.printf("%s %s - %s - ($%.2f)%n",
                    e.getFirstName(), e.getLastName(), e.getJobTitle(), e.getSalary()));
        }
    }

    public static class ex12EmployeesMaximumSalaries{
        public static void run(){
            em.createQuery("SELECT d FROM Department d", Department.class).getResultStream()
                    .forEach(d -> {
                        BigDecimal max = d.getEmployees().stream()
                                .map(e -> e.getSalary()).max(BigDecimal::compareTo)
                                .orElse(null);
                        if(max.compareTo(new BigDecimal(70000)) > 0 ||
                                max.compareTo(new BigDecimal(30000)) < 0)
                            System.out.printf("%s %.2f%n", d.getName(), max);

                    });
        }
    }

    public static class ex13RemoveTowns{
        public static void run(){
            Scanner sc = new Scanner(System.in);
            String townName = sc.nextLine();

            List<Address> addresses = em.createQuery("SELECT a FROM Address a " +
                    "WHERE a.town.name = :townName", Address.class)
                    .setParameter("townName", townName).getResultList();
            int size = addresses.size();

            em.getTransaction().begin();

            for (Address address : addresses) {
                address.getEmployees().forEach(e -> e.setAddress(null));
                em.remove(address);
            }

            Town town = em.createQuery("SELECT t FROM Town t " +
                    "WHERE t.name = :townName", Town.class)
                    .setParameter("townName", townName)
                    .getResultStream()
                    .findFirst()
                    .orElse(null);
            em.remove(town);
            System.out.printf("%d address%s in %s deleted%n", size, size == 1 ? "" : "es", town.getName());
            em.getTransaction().commit();



        }
    }
}

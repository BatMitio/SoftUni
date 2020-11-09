package models.tasks;

import constants.Const;
import core.Engine;
import models.entities.Employee;

import java.util.List;
import java.util.Scanner;

public class Ex11FindEmployeesByFirstName extends Task {
    @Override
    public void run() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter pattern, please: ");
        String pattern = sc.nextLine();
        List<Employee> employees = Engine
                .getEm()
                .createQuery("select e from Employee e where lower(e.firstName) like :pattern", Employee.class)
                .setParameter("pattern", pattern.toLowerCase() + "%")
                .getResultList();
        System.out.println(Const.DELIMITER);
        employees.forEach(employee -> System.out.printf(Const.EMPLOYEE_INFO_PRINTABLE,
                employee.getFirstName(), employee.getLastName(), employee.getJobTitle(), employee.getSalary()).println());
        System.out.println(Const.DELIMITER);
    }
}
package models.tasks;

import constants.Const;
import core.Engine;
import models.entities.Employee;
import models.entities.Project;

import java.util.Comparator;
import java.util.Scanner;

public class Ex08GetEmployeeWithProject extends Task {
    @Override
    public void run() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter employee ID, please: ");
        int id = Integer.parseInt(sc.nextLine());
        Employee employee = Engine
                .getEm()
                .createQuery("select e from Employee e where e.id = :id", Employee.class)
                .setParameter("id", id)
                .getResultStream()
                .findFirst()
                .orElse(null);
        System.out.println(Const.DELIMITER);
        if(employee == null) {
            System.out.printf(Const.NULL_EMPLOYEE_MESSAGE, "ID " + id).println();
            System.out.println(Const.DELIMITER);
            return;
        }
        System.out.printf(Const.SINGLE_EMPLOYEE_PRINTABLE,
                employee.getFirstName(), employee.getLastName(), employee.getJobTitle()).println();
        employee
                .getProjects()
                .stream()
                .sorted(Comparator.comparing(Project::getName))
                .forEach(project -> System.out.printf(Const.SINGLE_PROJECT_PRINTABLE, project.getName()).println());
        System.out.println(Const.DELIMITER);
    }
}
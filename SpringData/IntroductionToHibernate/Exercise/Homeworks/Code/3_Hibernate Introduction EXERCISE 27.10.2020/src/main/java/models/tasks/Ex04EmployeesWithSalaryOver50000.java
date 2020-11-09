package models.tasks;

import constants.Const;
import core.Engine;
import models.entities.Employee;

import java.util.List;

public class Ex04EmployeesWithSalaryOver50000 extends Task {
    @Override
    public void run() {
        List<Employee> employees = Engine
                .getEm()
                .createQuery("select e from Employee e where e.salary > 50000", Employee.class)
                .getResultList();
        System.out.println(Const.DELIMITER);
        System.out.println(Const.EMPLOYEES_PRINTABLE);
        employees.forEach(employee -> System.out.println(employee.getFirstName()));
        System.out.println(Const.DELIMITER);
    }
}
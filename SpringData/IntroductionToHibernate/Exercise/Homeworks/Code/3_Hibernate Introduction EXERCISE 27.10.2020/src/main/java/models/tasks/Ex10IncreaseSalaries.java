package models.tasks;

import constants.Const;
import core.Engine;
import models.entities.Employee;

import java.math.BigDecimal;
import java.util.List;

public class Ex10IncreaseSalaries extends Task {
    @Override
    public void run() {
        List<Employee> employees = Engine
                .getEm()
                .createQuery("select e from Employee e " +
                        "where e.department.name " +
                        "in ('Engineering', 'Tool Design', 'Marketing', 'Information Services')", Employee.class)
                .getResultList();
        System.out.println(Const.DELIMITER);
        Engine.getEm().getTransaction().begin();
        employees.forEach(employee -> {
            employee.setSalary(employee.getSalary().multiply(new BigDecimal("1.12")));
            Engine.getEm().merge(employee);
        });
        Engine.getEm().flush();
        Engine.getEm().getTransaction().commit();
        employees.forEach(employee -> System.out.printf(Const.EMPLOYEE_SALARY_PRINTABLE,
                employee.getFirstName(), employee.getLastName(), employee.getSalary()).println());
        System.out.println();
        System.out.println("\u2757".repeat(3) + " DO NOT FORGET TO RESET UP THE DB FOR THE NEXT TASKS");
        System.out.println(Const.DELIMITER);
    }
}
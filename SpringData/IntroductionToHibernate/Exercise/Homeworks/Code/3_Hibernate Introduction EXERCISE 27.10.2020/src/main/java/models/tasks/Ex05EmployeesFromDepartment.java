package models.tasks;

import constants.Const;
import core.Engine;
import models.entities.Employee;

import java.util.List;

public class Ex05EmployeesFromDepartment extends Task {
    @Override
    public void run() {
        List<Employee> employees = Engine
                .getEm()
                .createQuery("select e from Employee e where e.department.name = 'Research and Development'" +
                        "order by e.salary asc, e.id asc", Employee.class)
                .getResultList();
        System.out.println(Const.DELIMITER);
        employees.forEach(employee -> System.out.printf(Const.EMPLOYEES_DEPARTMENTS_SALARIES_PRINTABLE,
                employee.getFirstName(), employee.getLastName(), employee.getSalary()).println());
        System.out.println(Const.DELIMITER);
    }
}
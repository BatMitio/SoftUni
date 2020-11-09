package models.tasks;

import constants.Const;
import core.Engine;
import models.entities.Department;
import models.entities.Employee;

import java.math.BigDecimal;
import java.util.List;

public class Ex12EmployeesMaximumSalaries extends Task {
    @Override
    public void run() {
        List<Department> departments = Engine
                .getEm()
                .createQuery("select d from Department d", Department.class)
                .getResultList();
        System.out.println(Const.DELIMITER);
        departments.forEach(department -> {
            BigDecimal max = department
                    .getEmployees()
                    .stream()
                    .map(Employee::getSalary).max(BigDecimal::compareTo)
                    .orElse(null);
            assert max != null;
            if(max.compareTo(new BigDecimal(30000)) < 0 || max.compareTo(new BigDecimal(70000)) > 0)
                System.out.printf(Const.DEPARTMENT_SALARY_PRINTABLE, department.getName(), max).println();
        });
        System.out.println(Const.DELIMITER);
    }
}
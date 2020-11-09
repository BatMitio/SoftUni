package models.tasks;

import constants.Const;
import core.Engine;
import models.entities.Employee;
import java.util.List;
import java.util.Scanner;

public class Ex03ContainsEmployee extends Task {
    @Override
    public void run() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter employee name, please: ");
        String name = sc.nextLine();
        List<Employee> employees = Engine
                .getEm()
                .createQuery("select e from Employee e where concat(e.firstName, ' ', e.lastName) = :name", Employee.class)
                .setParameter("name", name)
                .getResultList();
        System.out.println(Const.DELIMITER);
        System.out.print(Const.IS_NAME_CONTAINED_PRINTABLE + " ");
        System.out.println(employees.isEmpty() ? "No" : "Yes");
        System.out.println(Const.DELIMITER);
    }
}
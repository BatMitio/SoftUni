package models.tasks;

import constants.Const;
import core.Engine;
import models.entities.Address;
import models.entities.Employee;

import java.util.Scanner;

public class Ex06AddingNewAddressAndUpdatingEmployee extends Task {
    @Override
    public void run() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter employee last name, please: ");
        String lastName = sc.nextLine();
        Address address = Engine
                .getEm()
                .createQuery("select a from Address a where a.text = 'Vitoshka 15'", Address.class)
                .getResultStream()
                .findFirst()
                .orElse(null);
        System.out.println(Const.DELIMITER);
        if(address == null) {
            address = new Address();
            address.setText("Vitoshka 15");
            Engine.getEm().getTransaction().begin();
            Engine.getEm().persist(address);
            Engine.getEm().getTransaction().commit();
            System.out.println(Const.ADDED_ADDRESS_MESSAGE);
        }
        else System.out.println(Const.NOT_ADDED_ADDRESS_MESSAGE);
        Employee employee = Engine
                .getEm()
                .createQuery("select e from Employee e where e.lastName = :lastName", Employee.class)
                .setParameter("lastName", lastName)
                .getResultStream()
                .findFirst()
                .orElse(null);
        if(employee == null) {
            System.out.printf(Const.NULL_EMPLOYEE_MESSAGE, "last name '" + lastName + '\'').println();
            System.out.println(Const.DELIMITER);
            return;
        }
        Engine.getEm().getTransaction().begin();
        Engine.getEm().detach(employee);
        employee.setAddress(address);
        Engine.getEm().merge(employee);
        Engine.getEm().flush();
        Engine.getEm().getTransaction().commit();
        System.out.printf(Const.NOT_NULL_EMPLOYEE_MESSAGE, employee.getFirstName(), employee.getLastName(), employee.getId()).println();
        System.out.println();
        System.out.println("\u2757".repeat(3) + " DO NOT FORGET TO RESET UP THE DB FOR THE NEXT TASKS");
        System.out.println(Const.DELIMITER);
    }
}
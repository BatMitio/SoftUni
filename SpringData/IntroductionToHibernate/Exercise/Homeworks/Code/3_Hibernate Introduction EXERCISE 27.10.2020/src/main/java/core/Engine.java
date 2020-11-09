package core;

import constants.Const;
import models.tasks.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.Serializable;
import java.util.Scanner;

public class Engine implements Serializable {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory(Const.DATABASE_NAME);
    private static final EntityManager em = Engine.emf.createEntityManager();

    private Engine() {
    }

    public static EntityManager getEm() { return Engine.em; }

    public static void initSetUp() {
        System.out.printf(Const.LOAD_DATABASE_DRIVER_MESSAGE, Const.DATABASE_DRIVER).println();
        System.out.printf(Const.CREATE_DATABASE_MESSAGE, Const.CONNECTION_URL, Const.DATABASE_NAME).println();
        System.out.printf(Const.SET_DATABASE_CONNECTION_MESSAGE, Const.CONNECTION_URL, Const.DATABASE_NAME).println();
    }

    public static void run() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.print(Const.TASKS);
            String command = sc.nextLine();
            if(command.toLowerCase().equals(Const.QUIT)) {
                System.out.println();
                System.out.println("\u001B[45m" + "\u001B[1m" + "\u001B[30m" + Const.QUIT_MESSAGE + "\u001B[0m");
                Engine.em.close();
                return;
            }
            Engine.executeCommand(command);
        }
    }

    private static void executeCommand(String command) {
        Task task = null;
        switch (command) {
            case "2", "02" -> task = new Ex02ChangeCasing();
            case "3", "03" -> task = new Ex03ContainsEmployee();
            case "4", "04" -> task = new Ex04EmployeesWithSalaryOver50000();
            case "5", "05" -> task = new Ex05EmployeesFromDepartment();
            case "6", "06" -> task = new Ex06AddingNewAddressAndUpdatingEmployee();
            case "7", "07" -> task = new Ex07AddressesWithEmployeeCount();
            case "8", "08" -> task = new Ex08GetEmployeeWithProject();
            case "9", "09" -> task = new Ex09FindLatest10Projects();
            case "10" -> task = new Ex10IncreaseSalaries();
            case "11" -> task = new Ex11FindEmployeesByFirstName();
            case "12" -> task = new Ex12EmployeesMaximumSalaries();
            case "13" -> task = new Ex13RemoveTowns();
            default -> {
                System.out.println(Const.DELIMITER);
                System.out.println(Const.INVALID_TASK_NUMBER);
                System.out.println(Const.DELIMITER);
            }
        }
        if(task != null) task.run();
    }
}
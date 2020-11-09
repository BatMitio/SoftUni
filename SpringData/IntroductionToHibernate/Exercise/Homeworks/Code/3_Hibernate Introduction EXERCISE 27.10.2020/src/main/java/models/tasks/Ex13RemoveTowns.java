package models.tasks;

import constants.Const;
import core.Engine;
import models.entities.Address;
import models.entities.Town;

import java.util.List;
import java.util.Scanner;

public class Ex13RemoveTowns extends Task {
    @Override
    public void run() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter town name, please: ");
        String townName = sc.nextLine();
        Town town = Engine
                .getEm()
                .createQuery("select t from Town t where t.name = :townName", Town.class)
                .setParameter("townName", townName)
                .getResultStream()
                .findFirst()
                .orElse(null);
        if(town == null) {
            System.out.println(Const.DELIMITER);
            System.out.printf(Const.NULL_TOWN_MESSAGE, townName).println();
            System.out.println(Const.DELIMITER);
            return;
        }
        Engine.getEm().remove(town);
        List<Address> addresses = Engine
                .getEm()
                .createQuery("select a from Address a where a.town.name = :townName", Address.class)
                .setParameter("townName", townName)
                .getResultList();
        int count = addresses.size();
        Engine.getEm().getTransaction().begin();
        for (Address address : addresses) {
            address.getEmployees().forEach(employee -> employee.setAddress(null));
            Engine.getEm().remove(address);
        }
        System.out.println(Const.DELIMITER);
        System.out.printf(Const.ADDRESSES_DELETED_PRINTABLE, count, count == 1 ? "" : "es", town.getName()).println();
        Engine.getEm().getTransaction().commit();
        System.out.println(Const.DELIMITER);
    }
}
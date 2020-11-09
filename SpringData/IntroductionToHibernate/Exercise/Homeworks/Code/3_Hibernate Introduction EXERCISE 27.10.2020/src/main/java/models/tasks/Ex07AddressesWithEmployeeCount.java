package models.tasks;

import constants.Const;
import core.Engine;
import models.entities.Address;

import java.util.List;

public class Ex07AddressesWithEmployeeCount extends Task {
    @Override
    public void run() {
        List<Address> addresses =  Engine
                .getEm()
                .createQuery("select a from Address a order by a.employees.size desc", Address.class)
                .setMaxResults(10)
                .getResultList();
        System.out.println(Const.DELIMITER);
        addresses.forEach(address -> System.out.printf(Const.ADDRESSES_PRINTABLE,
                address.getText(), address.getTown().getName(), address.getEmployees().size()).println());
        System.out.println(Const.DELIMITER);
    }
}
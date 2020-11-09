package models.tasks;

import constants.Const;
import core.Engine;
import models.entities.Town;

import java.util.List;

public class Ex02ChangeCasing extends Task {
    @Override
    public void run() {
        List<Town> towns = Engine
                .getEm()
                .createQuery("select t from Town t where length(t.name) <= 5", Town.class)
                .getResultList();
        Engine.getEm().getTransaction().begin();
        towns.forEach(town -> {
            Engine.getEm().detach(town);
            town.setName(town.getName().toLowerCase());
            Engine.getEm().merge(town);
        });
        Engine.getEm().flush();
        Engine.getEm().getTransaction().commit();
        System.out.println(Const.DELIMITER);
        System.out.println(Const.TOWNS_PRINTABLE);
        towns.forEach(System.out::println);
        System.out.println(Const.DELIMITER);
    }
}
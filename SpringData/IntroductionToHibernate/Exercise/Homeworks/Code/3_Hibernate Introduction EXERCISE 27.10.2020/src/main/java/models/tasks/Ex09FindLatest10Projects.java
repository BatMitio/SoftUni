package models.tasks;

import constants.Const;
import core.Engine;
import models.entities.Project;

import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Ex09FindLatest10Projects extends Task {
    @Override
    public void run() {
        List<Project> projects = Engine
                .getEm()
                .createQuery("select p from Project p order by p.startDate desc", Project.class)
                .setMaxResults(10)
                .getResultStream()
                .sorted(Comparator.comparing(Project::getName))
                .collect(Collectors.toList());
        System.out.println(Const.DELIMITER);
        projects.forEach(project -> {
            System.out.printf(Const.PROJECT_NAME_PRINTABLE, project.getName()).println();
            System.out.printf(Const.PROJECT_DESCRIPTION_PRINTABLE, project.getDescription()).println();
            System.out.printf(Const.PROJECT_START_DATE_PRINTABLE, project.getStartDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.0"))).println();
            System.out.printf(Const.PROJECT_END_DATE_PRINTABLE, project.getEndDate()).println();
        });
        System.out.println(Const.DELIMITER);
    }
}
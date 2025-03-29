package br.com.ada.t1322.tecnicasprogramacao.projeto.service;

import br.com.ada.t1322.tecnicasprogramacao.projeto.model.Task;

import java.util.Comparator;

public class TaskComparators {

    public static final Comparator<Task> BY_DEADLINE = Comparator.comparing(Task::getDeadline);

    public static final Comparator<Task> BY_TITLE = Comparator.comparing(Task::getTitle);

    public static final Comparator<Task> BY_STATUS = Comparator.comparing(Task::getStatus);

    public static final Comparator<Task> BY_ID = Comparator.comparing(Task::getId);

    public static Comparator<Task> getComparator(String criteria, boolean reversed) {
        Comparator<Task> comparator = switch (criteria.toLowerCase()) {
            case "title" -> BY_TITLE;
            case "status" -> BY_STATUS;
            case "id" -> BY_ID;
            default -> BY_DEADLINE;
        };
        return reversed ? comparator.reversed() : comparator;
    }
}

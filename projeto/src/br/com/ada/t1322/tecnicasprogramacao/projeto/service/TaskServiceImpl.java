package br.com.ada.t1322.tecnicasprogramacao.projeto.service;

import br.com.ada.t1322.tecnicasprogramacao.projeto.model.Task;
import br.com.ada.t1322.tecnicasprogramacao.projeto.repository.TaskRepository;
import br.com.ada.t1322.tecnicasprogramacao.projeto.service.notification.Notifier;
import br.com.ada.t1322.tecnicasprogramacao.projeto.service.validation.TaskValidator;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class TaskServiceImpl extends AbstractTaskService {

    public static final Comparator<Task> DEFAULT_TASK_SORT = Comparator.comparing(Task::getDeadline);
    private static TaskServiceImpl INSTANCE;

    private TaskServiceImpl(TaskRepository taskRepository, TaskValidator taskValidator, Notifier notifier) {
        super(taskRepository, taskValidator, notifier);
    }

    public static TaskServiceImpl create(TaskRepository taskRepository, TaskValidator taskValidator, Notifier notifier) {
        if (INSTANCE == null) {
            synchronized (TaskServiceImpl.class) {
                if (INSTANCE == null) {
                    INSTANCE = new TaskServiceImpl(taskRepository, taskValidator, notifier);
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public List<Task> findAll(Optional<Comparator<Task>> orderBy) {
        List<Task> tasks = taskRepository.findAll();
        return orderBy.map(comparator ->
                tasks.stream()
                        .sorted(comparator.thenComparing(Task::getId))
                        .collect(Collectors.toList())
        ).orElse(tasks);
    }

    @Override
    public List<Task> findByStatus(Task.Status status, Optional<Comparator<Task>> orderBy) {
        List<Task> tasks = taskRepository.findByStatus(status);
        return orderBy.map(comparator ->
                tasks.stream()
                        .sorted(comparator.thenComparing(Task::getId))
                        .collect(Collectors.toList())
        ).orElse(tasks);
    }

    @Override
    public List<Task> findBy(Predicate<Task> predicate, Optional<Comparator<Task>> orderBy) {
        List<Task> tasks = taskRepository.findBy(predicate);
        return orderBy.map(comparator ->
                tasks.stream()
                        .sorted(comparator.thenComparing(Task::getId))
                        .collect(Collectors.toList())
        ).orElse(tasks);
    }
}
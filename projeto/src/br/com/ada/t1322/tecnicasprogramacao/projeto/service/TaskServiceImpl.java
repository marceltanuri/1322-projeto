package br.com.ada.t1322.tecnicasprogramacao.projeto.service;

import br.com.ada.t1322.tecnicasprogramacao.projeto.model.Task;
import br.com.ada.t1322.tecnicasprogramacao.projeto.repository.TaskRepository;
import br.com.ada.t1322.tecnicasprogramacao.projeto.service.notification.Notifier;
import br.com.ada.t1322.tecnicasprogramacao.projeto.service.validation.TaskValidator;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

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
        // Você deve usar o repository que já está disponível via heranca.
        // Por exemplo return taskRepository.findAll();
        // Mas lembre que precisa aplicar o ordenador (orderBy) antes de retornar a lista
        return null;
    }

    @Override
    public List<Task> findByStatus(Task.Status status, Optional<Comparator<Task>> orderBy) {
        return null;
    }

    @Override
    public List<Task> findBy(Predicate<Task> predicate, Optional<Comparator<Task>> orderBy) {
        return null;
    }

}

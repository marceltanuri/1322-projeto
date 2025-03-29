package br.com.ada.t1322.tecnicasprogramacao.projeto.service;

import br.com.ada.t1322.tecnicasprogramacao.projeto.model.Task;
import br.com.ada.t1322.tecnicasprogramacao.projeto.repository.TaskRepository;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class TaskServiceImpl extends AbstractTaskService {

    public static final Comparator<Task> DEFAULT_TASK_SORT = Comparator.comparing(Task::getDeadline);
    private static final int MIN_TITLE_LENGTH = 3;

    public TaskServiceImpl(TaskRepository taskRepository) {
        super(taskRepository);
    }

    private static void validateTitle(String title) {
        if (title == null || title.isBlank() || title.length() < MIN_TITLE_LENGTH) {
            throw new IllegalArgumentException("Título inválido: '" + title + "'. Deve ter pelo menos " + MIN_TITLE_LENGTH + " caracteres.");
        }
    }

    private static void validateDeadline(LocalDate deadline) {
        if (deadline == null || deadline.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Data inválida: '" + deadline + "'. Deve ser maior ou igual à data atual.");
        }
    }

    private static void validateStatus(Task.Status status) {
        if (status == null) {
            throw new IllegalArgumentException("Status inválido: não pode ser nulo.");
        }
    }

    @Override
    public List<Task> findAll(Optional<Comparator<Task>> orderBy) {
        return taskRepository.findAll().stream().sorted(orderBy.orElse(DEFAULT_TASK_SORT)).toList();
    }

    @Override
    public List<Task> findByStatus(Task.Status status, Optional<Comparator<Task>> orderBy) {
        return taskRepository.findByStatus(status).stream().sorted(orderBy.orElse(DEFAULT_TASK_SORT)).toList();
    }

    @Override
    public List<Task> findBy(Predicate<Task> predicate, Optional<Comparator<Task>> orderBy) {
        var stream = taskRepository.findBy(predicate).stream();

        if (orderBy.isPresent()) {
            stream = stream.sorted(orderBy.get());
        }

        return stream.toList();
    }

    @Override
    public void validate(Task task) {
        if (task == null) {
            throw new IllegalArgumentException("Task não pode ser nula");
        }

        validateTitle(task.getTitle());
        validateDeadline(task.getDeadline());
        validateStatus(task.getStatus());
    }
}

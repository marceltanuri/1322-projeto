package br.com.ada.t1322.tecnicasprogramacao.projeto.controller;

import br.com.ada.t1322.tecnicasprogramacao.projeto.dto.TaskUpdateRequest;
import br.com.ada.t1322.tecnicasprogramacao.projeto.model.Task;
import br.com.ada.t1322.tecnicasprogramacao.projeto.service.TaskService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public abstract class AbstractTaskController implements TaskController {

    private final static DateTimeFormatter DATE_TIME_FORMATTER = new DateTimeFormatterBuilder()
            .appendOptional(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
            .appendOptional(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
            .toFormatter();

    protected final TaskService taskService;

    public AbstractTaskController(TaskService taskService) {
        if (taskService == null) {
            throw new IllegalArgumentException("TaskService não pode ser nulo.");
        }
        this.taskService = taskService;
    }

    @Override
    public final Task createTask(String title, String description, String deadline, Task.Status status) {
        validateTitle(title);
        validateDeadline(deadline);
        validateStatus(status);

        Task task = new Task(null, title, description, LocalDate.parse(deadline, DATE_TIME_FORMATTER), status);
        return taskService.save(task);
    }

    protected abstract void validateTitle(String title);

    protected abstract void validateDeadline(String deadline);

    protected abstract void validateStatus(Task.Status status);

    @Override
    public List<Task> getAllTasks(Optional<Comparator<Task>> orderBy) {
        return taskService.findAll(orderBy);
    }

    @Override
    public List<Task> getTasksByStatus(Task.Status status, Optional<Comparator<Task>> orderBy) {
        return taskService.findByStatus(status, orderBy);
    }

    @Override
    public List<Task> getTasksBy(Predicate<Task> predicate, Optional<Comparator<Task>> orderBy) {
        return taskService.findBy(predicate, orderBy);
    }

    @Override
    public Task updateTask(Long id, String title, String description, String deadline, Task.Status status) {
        Task existingTask = taskService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tarefa com o ID " + id + " não encontrada."));

        TaskUpdateRequest taskUpdateRequest = TaskUpdateRequest.builder(id)
                .title(title != null ? title : existingTask.getTitle())
                .description(description != null ? description : existingTask.getDescription())
                .deadline(deadline != null ? LocalDate.parse(deadline, DATE_TIME_FORMATTER) : existingTask.getDeadline())
                .status(status != null ? status : existingTask.getStatus())
                .build();

        return taskService.updateTask(taskUpdateRequest);
    }

    @Override
    public Task updateTaskStatus(Long id, Task.Status newStatus) {
        return taskService.updateStatus(id, newStatus);
    }
}

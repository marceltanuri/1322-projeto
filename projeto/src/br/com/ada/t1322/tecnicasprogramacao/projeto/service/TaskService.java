package br.com.ada.t1322.tecnicasprogramacao.projeto.service;

import br.com.ada.t1322.tecnicasprogramacao.projeto.dto.TaskUpdateRequest;
import br.com.ada.t1322.tecnicasprogramacao.projeto.model.Task;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public interface TaskService {

    Task save(Task task);

    List<Task> findAll(Optional<Comparator<Task>> orderBy);

    List<Task> findByStatus(Task.Status status, Optional<Comparator<Task>> orderBy);

    List<Task> findBy(Predicate<Task> predicate, Optional<Comparator<Task>> orderBy);

    Optional<Task> findById(Long id);

    default Task getById(Long id) {
        return findById(id).orElseThrow(() -> new IllegalArgumentException("Tarefa não encontrada"));
    }


    boolean deleteById(Long id);

    void clearAll();

    void notifyUpcomingDeadlines(int daysBefore);

    Task updateTask(TaskUpdateRequest updateRequest);

    Task updateStatus(Long id, Task.Status newStatus);
}

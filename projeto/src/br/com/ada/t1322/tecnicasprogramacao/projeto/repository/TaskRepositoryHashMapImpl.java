package br.com.ada.t1322.tecnicasprogramacao.projeto.repository;

import br.com.ada.t1322.tecnicasprogramacao.projeto.model.Task;

import java.util.*;
import java.util.function.Predicate;

public class TaskRepositoryHashMapImpl implements TaskRepository {

    private static final TaskRepositoryHashMapImpl INSTANCE = new TaskRepositoryHashMapImpl();
    private static Long idCounter = 1L;
    private final Map<Long, Task> tasks = new HashMap<>();

    private TaskRepositoryHashMapImpl() {

    }

    public static TaskRepositoryHashMapImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public Task save(Task task) {
        if (task.getId() == null) {
            task.setId(idCounter++);
            tasks.put(task.getId(), task);
        } else {
            tasks.compute(task.getId(), (id, existingTask) -> {
                if (existingTask == null) {
                    throw new IllegalArgumentException("Tentativa de salvar uma nova tarefa com um ID inexistente.");
                }
                return task;
            });
        }
        return task;
    }

    @Override
    public List<Task> findAll() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public List<Task> findByStatus(String status) {
        return tasks.values().stream()
                .filter(task -> task.getStatus().getDescricao().equalsIgnoreCase(status))
                .toList();
    }

    @Override
    public List<Task> findByStatus(Task.Status status) {
        return tasks.values().stream()
                .filter(task -> task.getStatus() == status)
                .toList();
    }

    @Override
    public List<Task> findBy(Predicate<Task> predicate) {
        return tasks.values().stream()
                .filter(predicate)
                .toList();
    }

    @Override
    public Optional<Task> findById(Long id) {
        return Optional.ofNullable(tasks.get(id));
    }

    @Override
    public boolean deleteById(Long id) {
        return tasks.remove(id) != null;
    }

    @Override
    public void deleteAll() {
        tasks.clear();
        idCounter = 1L;
    }
}

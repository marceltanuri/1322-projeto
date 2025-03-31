package br.com.ada.t1322.tecnicasprogramacao.projeto.repository;

import br.com.ada.t1322.tecnicasprogramacao.projeto.model.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class TaskRepositoryImpl implements TaskRepository {

    private static final TaskRepositoryImpl INSTANCE = new TaskRepositoryImpl();
    private final List<Task> tasks = new ArrayList<>();

    private static Long idCounter = 1L;

    private TaskRepositoryImpl() {

    }

    public static TaskRepositoryImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public Task save(Task task) {
        if (task.getId() == null) {

            task.setId(getIncrementalId());
            tasks.add(task);
        } else {

            Optional<Task> existingTask = findById(task.getId());

            if (existingTask.isPresent()) {
                Task currentTask = existingTask.get();
                currentTask.setTitle(task.getTitle());
                currentTask.setDescription(task.getDescription());
                currentTask.setDeadline(task.getDeadline());
                currentTask.setStatus(task.getStatus());
            } else {
                throw new IllegalArgumentException("Tentativa de salvar uma nova tarefa com um ID inexistente.");
            }
        }
        return task;
    }

    private static Long getIncrementalId() {
        return idCounter++;
    }

    @Override
    public List<Task> findAll() {
        return new ArrayList<>(tasks);
    }


    @Override
    public List<Task> findByStatus(String status) {
        return tasks.stream()
                .filter(task -> task.getStatus().toString().equalsIgnoreCase(status))
                .collect(Collectors.toList());
    }

    @Override
    public List<Task> findByStatus(Task.Status status) {
        return tasks.stream()
                .filter(task -> task.getStatus() == status)
                .toList();
    }

    @Override
    public List<Task> findBy(Predicate<Task> predicate) {
        return tasks.stream()
                .filter(predicate)
                .toList();
    }

    @Override
    public Optional<Task> findById(Long id) {
        return tasks.stream()
                .filter(task -> task.getId().equals(id))
                .findFirst();
    }

    @Override
    public boolean deleteById(Long id) {
        return tasks.removeIf(task -> task.getId().equals(id));
    }

    @Override
    public void deleteAll() {
        tasks.clear();
        idCounter = 1L;
    }
}
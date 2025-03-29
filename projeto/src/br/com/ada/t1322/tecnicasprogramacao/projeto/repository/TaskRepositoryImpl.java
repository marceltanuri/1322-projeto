package br.com.ada.t1322.tecnicasprogramacao.projeto.repository;

import br.com.ada.t1322.tecnicasprogramacao.projeto.model.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.IntStream;

public class TaskRepositoryImpl implements TaskRepository {

    private static final TaskRepositoryImpl INSTANCE = new TaskRepositoryImpl();
    private static final List<Task> tasks = new ArrayList<>();
    private static Long idCounter = 1L;

    private TaskRepositoryImpl() {
        // Construtor privado para impedir instanciação externa
    }

    public static TaskRepositoryImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public Task save(Task task) {
        if (task.getId() == null) {
            task.setId(idCounter++);
            tasks.add(task);
        } else {
            IntStream.range(0, tasks.size())
                    .filter(i -> tasks.get(i).getId().equals(task.getId()))
                    .findFirst()
                    .ifPresentOrElse(
                            index -> tasks.set(index, task),
                            () -> tasks.add(task)
                    );
        }
        return task;
    }

    @Override
    public List<Task> findAll() {
        return new ArrayList<>(tasks);
    }

    @Override
    public List<Task> findByStatus(String status) {
        return tasks.stream()
                .filter(task -> task.getStatus().getDescricao().equalsIgnoreCase(status))
                .toList();
    }

    @Override
    public List<Task> findByStatus(Task.Status status) {
        return tasks.stream()
                .filter(task -> task.getStatus() == status)
                .toList();
    }

    @Override
    public List<Task> findBy(Predicate<Task> predicate) {
        return tasks.stream().filter(predicate).toList();
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

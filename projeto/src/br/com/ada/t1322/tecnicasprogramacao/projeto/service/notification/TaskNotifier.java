package br.com.ada.t1322.tecnicasprogramacao.projeto.service.notification;

import br.com.ada.t1322.tecnicasprogramacao.projeto.config.ConfigConstants;
import br.com.ada.t1322.tecnicasprogramacao.projeto.model.Task;
import br.com.ada.t1322.tecnicasprogramacao.projeto.repository.TaskRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

public class TaskNotifier implements Notifier {

    private final TaskRepository taskRepository;
    private final int thresholdDays;
    private final int notificationCooldownMinutes;
    private final Map<Long, LocalDateTime> lastNotifications = new ConcurrentHashMap<>(); // 🔥 Controle de notificações enviadas

    private volatile boolean running = true;

    public TaskNotifier(TaskRepository taskRepository, int thresholdDays, int notificationCooldownMinutes) {
        if (taskRepository == null) {
            throw new IllegalArgumentException("TaskRepository não pode ser nulo.");
        }
        if (thresholdDays <= 0) {
            throw new IllegalArgumentException("thresholdDays deve ser maior que zero.");
        }
        if (notificationCooldownMinutes <= 0) {
            throw new IllegalArgumentException("notificationCooldownMinutes deve ser maior que zero.");
        }

        this.taskRepository = taskRepository;
        this.thresholdDays = thresholdDays;
        this.notificationCooldownMinutes = notificationCooldownMinutes;
    }

    public void start() {
        CompletableFuture.runAsync(() -> {
            while (running) {
                checkForExpiringTasks();
                try {
                    Thread.sleep(ConfigConstants.TASK_CHECK_INTERVAL_MS);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });
    }

    public void stop() {
        running = false;
    }

    private void checkForExpiringTasks() {
        LocalDate today = LocalDate.now();
        LocalDate thresholdDate = today.plusDays(thresholdDays);
        LocalDateTime now = LocalDateTime.now();

        List<Task> expiringTasks = taskRepository.findBy(task ->
                (task.getDeadline().isBefore(thresholdDate) || task.getDeadline().isEqual(thresholdDate)) &&
                        (task.getDeadline().isEqual(today) || task.getDeadline().isAfter(today)) &&
                        task.getStatus() != Task.Status.CONCLUIDO
        );

        expiringTasks.forEach(task -> {
            if (shouldNotify(task.getId(), now)) {
                String message = "🔔 Tarefa \"" + task.getTitle() + "\" expira em: " + task.getDeadline();
                NotificationLogger.logNotification(message);
                lastNotifications.put(task.getId(), now);
            }
        });
    }

    private boolean shouldNotify(Long taskId, LocalDateTime now) {
        return lastNotifications.getOrDefault(taskId, now.minusMinutes(notificationCooldownMinutes + 1))
                .plusMinutes(notificationCooldownMinutes).isBefore(now);
    }
}

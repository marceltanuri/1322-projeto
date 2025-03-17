package br.com.ada.t1322.tecnicasprogramacao.projeto.service.notification;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class NotificationLogger {

    private static final Path LOG_FILE_PATH = Path.of("notifications.log");
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static void logNotification(String message) {
        String timestamp = LocalDateTime.now().format(FORMATTER);
        String logEntry = timestamp + " - " + message + System.lineSeparator();

        try {
            Files.writeString(LOG_FILE_PATH, logEntry,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND
            );
        } catch (IOException e) {
            System.err.println("❌ Erro ao escrever no arquivo de log: " + e.getMessage());
        }
    }
}

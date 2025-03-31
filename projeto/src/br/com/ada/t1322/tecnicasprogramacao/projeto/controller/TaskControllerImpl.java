package br.com.ada.t1322.tecnicasprogramacao.projeto.controller;

import br.com.ada.t1322.tecnicasprogramacao.projeto.model.Task;
import br.com.ada.t1322.tecnicasprogramacao.projeto.service.TaskService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;

public class TaskControllerImpl extends AbstractTaskController {

    private static final int MIN_TITLE_LENGTH = 3;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public TaskControllerImpl(TaskService taskService) {
        super(taskService);
    }

    @Override
    protected void validateTitle(String title) {
        Optional.ofNullable(title)
                .filter(t -> !t.isBlank() && t.length() >= MIN_TITLE_LENGTH)
                .orElseThrow(() -> new IllegalArgumentException("O título deve ter pelo menos " + MIN_TITLE_LENGTH + " caracteres e não pode ser vazio."));
    }

    @Override
    protected void validateDeadline(String deadline) {
        Optional.ofNullable(deadline)
                .map(date -> {
                    try {
                        return LocalDate.parse(date, DATE_FORMATTER);
                    } catch (DateTimeParseException e) {
                        throw new IllegalArgumentException("Formato de data inválido. Use 'dd/MM/yyyy'.");
                    }
                })
                .filter(parsedDate -> !parsedDate.isBefore(LocalDate.now()))
                .orElseThrow(() -> new IllegalArgumentException("A data deve ser igual ou posterior à data atual."));
    }

    @Override
    protected void validateStatus(Task.Status status) {
        Optional.ofNullable(status)
                .orElseThrow(() -> new IllegalArgumentException("O status não pode ser nulo."));
    }
}
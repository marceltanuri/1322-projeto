package br.com.ada.t1322.tecnicasprogramacao.projeto.controller;

import br.com.ada.t1322.tecnicasprogramacao.projeto.model.Task;
import br.com.ada.t1322.tecnicasprogramacao.projeto.service.TaskService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;
import java.util.function.Predicate;

public class TaskControllerImpl extends AbstractTaskController {

    private static final int TAMANHO_MINIMO_TITULO = 3;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private static final Predicate<String> IS_INVALID_TITLE =
            s -> s == null || s.isBlank() || s.length() < TAMANHO_MINIMO_TITULO;

    private static final Predicate<String> IS_BLANK = s -> s == null || s.isBlank();

    public static final int MIN_DESCRIPTION_LENGTH = 5;
    private static final Predicate<String> IS_INVALID_DESCRIPTION_TEXT = s -> s != null && !s.isBlank() && s.length() < MIN_DESCRIPTION_LENGTH;

    private static final Predicate<LocalDate> IS_BEFORE_TODAY =
            date -> date.isBefore(LocalDate.now());

    public TaskControllerImpl(TaskService taskService) {
        super(taskService);
    }

    @Override
    protected void validateTitle(String title) {
        Optional.ofNullable(title)
                .filter(IS_INVALID_TITLE.negate())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Título deve ter no mínimo " + TAMANHO_MINIMO_TITULO + " caracteres"
                ));
    }

    @Override
    protected void validateDeadline(String deadline) {
        Optional.ofNullable(deadline)
                .map(date -> {
                    try {
                        return LocalDate.parse(date, FORMATTER);
                    } catch (DateTimeParseException e) {
                        throw new IllegalArgumentException("Formato de data inválido. Use dd/MM/yyyy.");
                    }
                })
                .filter(IS_BEFORE_TODAY.negate())
                .orElseThrow(() -> new IllegalArgumentException("Data deve ser igual ou superior à data atual."));
    }

    @Override
    protected void validateStatus(Task.Status status) {
        if (status == null) {
            throw new IllegalArgumentException("Status não pode ser nulo.");
        }
    }

}

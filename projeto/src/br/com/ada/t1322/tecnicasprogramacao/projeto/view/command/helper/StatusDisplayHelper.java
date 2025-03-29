package br.com.ada.t1322.tecnicasprogramacao.projeto.view.command.helper;

import br.com.ada.t1322.tecnicasprogramacao.projeto.model.Task;

import java.util.Arrays;
import java.util.stream.Collectors;

public class StatusDisplayHelper {
    public static String getStatusOptions() {
        return Arrays.stream(Task.Status.values())
                .map(status -> status.getDescricao())
                .collect(Collectors.joining(", ", "(", ")"));
    }
}

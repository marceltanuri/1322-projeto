package br.com.ada.t1322.tecnicasprogramacao.projeto.view.command.task_sorting;

import br.com.ada.t1322.tecnicasprogramacao.projeto.model.Task;
import br.com.ada.t1322.tecnicasprogramacao.projeto.service.TaskComparators;
import br.com.ada.t1322.tecnicasprogramacao.projeto.view.View;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Collectors;

public class SortingSelectionTaskHandler {

    public static Optional<Comparator<Task>> getSortingMethod(View view) {
        view.showMessage("Escolha o critério de ordenação:");

        String options = Arrays.stream(SortingTaskOption.values())
                .map(option -> option.getOption() + " - " + option.getDisplayName())
                .collect(Collectors.joining("\n"));

        view.showMessage(options);

        int option = view.getIntInput("Digite o número da opção");

        SortingTaskOption sortingOption = SortingTaskOption.fromOption(option);
        if (sortingOption == SortingTaskOption.NONE) {
            return Optional.empty();
        }

        view.showMessage("Deseja ordem reversa? (S/N)");
        boolean reversed = view.getInput("").trim().equalsIgnoreCase("S");

        return Optional.of(TaskComparators.getComparator(sortingOption.getCriteria(), reversed));
    }
}

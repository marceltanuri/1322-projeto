package br.com.ada.t1322.tecnicasprogramacao.projeto.view.command;

import br.com.ada.t1322.tecnicasprogramacao.projeto.controller.TaskController;
import br.com.ada.t1322.tecnicasprogramacao.projeto.model.Task;
import br.com.ada.t1322.tecnicasprogramacao.projeto.view.View;
import br.com.ada.t1322.tecnicasprogramacao.projeto.view.command.task_sorting.SortingSelectionTaskHandler;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class FilterTasksByCustomPredicateCommand implements Command {

    private final View view;
    private final TaskController taskController;

    public FilterTasksByCustomPredicateCommand(View view, TaskController taskController) {
        this.view = view;
        this.taskController = taskController;
    }

    @Override
    public void execute() {
        do {
            String keyword = view.getInput("🔎 Digite uma palavra-chave para buscar no título ou descrição");
            Optional<Comparator<Task>> orderBy = SortingSelectionTaskHandler.getSortingMethod(view);

            Predicate<Task> predicate = task -> task.getTitle().contains(keyword) || task.getDescription().contains(keyword);
            List<Task> tasks = taskController.getTasksBy(predicate, orderBy);

            if (tasks.isEmpty()) {
                view.showMessage("📭 Nenhuma tarefa encontrada.");
            } else {
                tasks.forEach(task -> view.showMessage(task.toString()));
            }
        } while (view.getInput("Deseja listar novamente? (S/N)").trim().equalsIgnoreCase("S"));
    }
}

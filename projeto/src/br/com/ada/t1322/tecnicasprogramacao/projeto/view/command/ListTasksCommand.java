package br.com.ada.t1322.tecnicasprogramacao.projeto.view.command;

import br.com.ada.t1322.tecnicasprogramacao.projeto.controller.TaskController;
import br.com.ada.t1322.tecnicasprogramacao.projeto.model.Task;
import br.com.ada.t1322.tecnicasprogramacao.projeto.view.View;
import br.com.ada.t1322.tecnicasprogramacao.projeto.view.command.task_sorting.SortingSelectionTaskHandler;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class ListTasksCommand implements Command {

    private final View view;
    private final TaskController taskController;

    public ListTasksCommand(View view, TaskController taskController) {
        this.view = view;
        this.taskController = taskController;
    }

    @Override
    public void execute() {
        do {
            Optional<Comparator<Task>> orderBy = SortingSelectionTaskHandler.getSortingMethod(view);
            List<Task> tasks = taskController.getAllTasks(orderBy);

            if (tasks.isEmpty()) {
                view.showMessage("📭 Nenhuma tarefa encontrada.");
            } else {
                tasks.forEach(task -> view.showMessage(task.toString()));
            }
        } while (view.getInput("Deseja listar novamente? (S/N)").trim().equalsIgnoreCase("S"));
    }

}

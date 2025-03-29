package br.com.ada.t1322.tecnicasprogramacao.projeto.view.command;

import br.com.ada.t1322.tecnicasprogramacao.projeto.controller.TaskController;
import br.com.ada.t1322.tecnicasprogramacao.projeto.model.Task;
import br.com.ada.t1322.tecnicasprogramacao.projeto.view.View;
import br.com.ada.t1322.tecnicasprogramacao.projeto.view.command.helper.StatusDisplayHelper;

public class UpdateTaskCommand implements Command {

    private final View view;
    private final TaskController taskController;

    public UpdateTaskCommand(View view, TaskController taskController) {
        this.view = view;
        this.taskController = taskController;
    }

    @Override
    public void execute() {
        do {
            Long id = view.getIntInput("📌 Informe o ID da tarefa para atualizar").longValue();
            String title = view.getInput("📌 Novo título (ou pressione Enter para manter)");
            String description = view.getInput("📝 Nova descrição (ou pressione Enter para manter)");
            String deadline = view.getInput("📅 Nova data limite (YYYY-MM-DD) (ou pressione Enter para manter)");
            String status = view.getInput("🔄 Novo status " + StatusDisplayHelper.getStatusOptions() + " (ou pressione Enter para manter)");

            try {
                Task updatedTask = taskController.updateTask(
                        id,
                        title.isBlank() ? null : title,
                        description.isBlank() ? null : description,
                        deadline.isBlank() ? null : deadline,
                        status.isBlank() ? null : Task.Status.fromString(status)
                );
                view.showMessage("✅ Tarefa atualizada com sucesso!");
                view.showMessage(updatedTask.toString());
            } catch (IllegalArgumentException e) {
                view.showMessage("❌ Erro: " + e.getMessage());
            }
        } while (view.getInput("Deseja atualizar outra tarefa? (S/N)").trim().equalsIgnoreCase("S"));
    }
}

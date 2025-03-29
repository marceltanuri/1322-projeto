package br.com.ada.t1322.tecnicasprogramacao.projeto.view.command;

import br.com.ada.t1322.tecnicasprogramacao.projeto.controller.TaskController;
import br.com.ada.t1322.tecnicasprogramacao.projeto.model.Task;
import br.com.ada.t1322.tecnicasprogramacao.projeto.view.View;

public class UpdateTaskStatusCommand implements Command {

    private final View view;
    private final TaskController taskController;

    public UpdateTaskStatusCommand(View view, TaskController taskController) {
        this.view = view;
        this.taskController = taskController;
    }

    @Override
    public void execute() {
        Long id = view.getIntInput("📌 Informe o ID da tarefa para atualizar o status").longValue();
        String status = view.getInput("🔄 Novo status (Pendente, Em andamento,Bloqueado, Concluído)");

        try {
            Task updatedTask = taskController.updateTaskStatus(id, Task.Status.fromString(status));
            view.showMessage("✅ Status atualizado com sucesso!");
            view.showMessage(updatedTask.toString());
        } catch (IllegalArgumentException e) {
            view.showMessage("❌ Erro: " + e.getMessage());
        }
    }
}

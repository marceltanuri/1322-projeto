package br.com.ada.t1322.tecnicasprogramacao.projeto.view.command;

import br.com.ada.t1322.tecnicasprogramacao.projeto.controller.TaskController;
import br.com.ada.t1322.tecnicasprogramacao.projeto.model.Task;
import br.com.ada.t1322.tecnicasprogramacao.projeto.view.View;

import java.util.Optional;

public class CreateTaskCommand implements Command {

    private final View view;
    private final TaskController taskController;

    public CreateTaskCommand(View view, TaskController taskController) {
        this.view = view;
        this.taskController = taskController;
    }

    @Override
    public void execute() {
        String title = view.getInput("📌 Informe o título da tarefa");
        String description = view.getInput("📝 Informe a descrição (opcional)");
        String deadline = view.getInput("📅 Informe a data limite (DD/MM/YYYY)");
        String statusStr = view.getInput("🔄 Informe o status (Pendente, Em andamento,Bloqueado, Concluído) (ou deixe em branco para 'Pendente')");

        try {
            Task.Status status = Optional.ofNullable(statusStr)
                    .filter(s -> !s.isBlank())
                    .map(Task.Status::fromString)
                    .orElse(Task.Status.PENDENTE);

            Task task = taskController.createTask(title, description, deadline, status);
            view.showMessage("✅ Tarefa criada com sucesso!");
            view.showMessage(task.toString());
        } catch (IllegalArgumentException e) {
            view.showMessage("❌ Erro: " + e.getMessage());
        }
    }
}

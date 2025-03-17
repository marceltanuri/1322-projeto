package br.com.ada.t1322.tecnicasprogramacao.projeto.view;

import br.com.ada.t1322.tecnicasprogramacao.projeto.controller.TaskController;
import br.com.ada.t1322.tecnicasprogramacao.projeto.view.command.*;

import java.util.HashMap;
import java.util.Map;

public class ConsoleApp {

    private final View view;
    private final Map<Integer, Command> commands = new HashMap<>();

    public ConsoleApp(View view, TaskController taskController) {
        this.view = view;

        commands.put(1, new CreateTaskCommand(view, taskController));
        commands.put(2, new UpdateTaskCommand(view, taskController));
        commands.put(3, new UpdateTaskStatusCommand(view, taskController));
        commands.put(4, new ListTasksCommand(view, taskController));
        commands.put(5, new FilterTasksByStatusCommand(view, taskController));
        commands.put(6, new FilterTasksByCustomPredicateCommand(view, taskController));
        commands.put(7, new ShowNotificationsCommand(view));
        commands.put(8, new ExitCommand(view));
    }

    public void run() {
        while (true) {
            showMenu();
            int option = view.getIntInput("Escolha uma opção");
            commands.getOrDefault(option, () -> view.showMessage("❌ Opção inválida. Tente novamente.")).execute();
        }
    }

    private void showMenu() {
        view.showMessage("\n📌 Gerenciador de Tarefas Inteligente");
        view.showMessage("1 - Criar Tarefa");
        view.showMessage("2 - Atualizar Tarefa");
        view.showMessage("3 - Atualizar Status");
        view.showMessage("4 - Listar Tarefas Ordenadas");
        view.showMessage("5 - Filtrar por Status");
        view.showMessage("6 - Buscar por Palavra-chave");
        view.showMessage("7 - Exibir Notificações 🔔");
        view.showMessage("8 - Sair");
    }
}

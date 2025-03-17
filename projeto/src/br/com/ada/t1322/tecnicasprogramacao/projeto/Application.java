package br.com.ada.t1322.tecnicasprogramacao.projeto;

import br.com.ada.t1322.tecnicasprogramacao.projeto.controller.TaskController;
import br.com.ada.t1322.tecnicasprogramacao.projeto.controller.TaskControllerImpl;
import br.com.ada.t1322.tecnicasprogramacao.projeto.model.Task;
import br.com.ada.t1322.tecnicasprogramacao.projeto.service.TaskServiceFactory;
import br.com.ada.t1322.tecnicasprogramacao.projeto.view.ConsoleApp;
import br.com.ada.t1322.tecnicasprogramacao.projeto.view.ConsoleView;
import br.com.ada.t1322.tecnicasprogramacao.projeto.view.View;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Application {
    public static void main(String[] args) {
        try (View view = new ConsoleView()) {
            TaskController controller = new TaskControllerImpl(TaskServiceFactory.createTaskService());

            initializeSampleTasks(controller);

            ConsoleApp app = new ConsoleApp(view, controller);
            app.run();
        } catch (Exception e) {
            System.err.println("Erro durante a execução: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void initializeSampleTasks(TaskController controller) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        controller.createTask("Reunião de equipe", "Discutir metas da semana", LocalDate.now().format(formatter), Task.Status.PENDENTE);

        Task taskRelatorio = controller.createTask("Entrega do relatório", "Relatório financeiro mensal", LocalDate.now().plusDays(1).format(formatter), Task.Status.EM_ANDAMENTO);
        controller.updateTaskStatus(taskRelatorio.getId(), Task.Status.CONCLUIDO);

        controller.createTask("Atualizar backlog", "Revisar e priorizar tarefas", LocalDate.now().plusDays(2).format(formatter), Task.Status.PENDENTE);
        controller.createTask("Call com cliente", "Apresentação de progresso do projeto", LocalDate.now().plusDays(3).format(formatter), Task.Status.EM_ANDAMENTO);
        controller.createTask("Planejamento sprint", "Definição das atividades para a próxima sprint", LocalDate.now().plusDays(4).format(formatter), Task.Status.PENDENTE);

        controller.createTask("Revisar pull requests", "Revisão de pull requests da sprint atual", LocalDate.now().plusDays(1).format(formatter), Task.Status.EM_ANDAMENTO);

        System.out.println("✅ 5 tarefas de exemplo foram adicionadas!");
    }
}

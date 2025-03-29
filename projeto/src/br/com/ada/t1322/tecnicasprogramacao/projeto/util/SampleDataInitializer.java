package br.com.ada.t1322.tecnicasprogramacao.projeto.util;

import br.com.ada.t1322.tecnicasprogramacao.projeto.controller.TaskController;
import br.com.ada.t1322.tecnicasprogramacao.projeto.model.Task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class SampleDataInitializer {

    public static void initializeSampleTasks(TaskController controller) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        Task taskRelatorio = controller.createTask("Entrega do relatório", "Relatório financeiro mensal", LocalDate.now().plusDays(1).format(formatter), Task.Status.EM_ANDAMENTO);
        controller.updateTaskStatus(taskRelatorio.getId(), Task.Status.CONCLUIDO);

        Task taskBloqueada = controller.createTask("Tarefa Bloqueada", "Tarefa bloqueada por alguma razão", LocalDate.now().format(formatter), Task.Status.PENDENTE);
        controller.updateTaskStatus(taskBloqueada.getId(), Task.Status.BLOQUEADO);

        controller.createTask("Reunião de equipe", "Discutir metas da semana", LocalDate.now().format(formatter), Task.Status.PENDENTE);
        controller.createTask("Atualizar backlog", "Revisar e priorizar tarefas", LocalDate.now().plusDays(2).format(formatter), Task.Status.PENDENTE);
        controller.createTask("Call com cliente", "Apresentação de progresso do projeto", LocalDate.now().plusDays(3).format(formatter), Task.Status.EM_ANDAMENTO);
        controller.createTask("Planejamento sprint", "Definição das atividades para a próxima sprint", LocalDate.now().plusDays(4).format(formatter), Task.Status.PENDENTE);

        System.out.println("✅ 5 tarefas de exemplo foram adicionadas!");
    }

}

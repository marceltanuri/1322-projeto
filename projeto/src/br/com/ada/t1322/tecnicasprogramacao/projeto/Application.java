package br.com.ada.t1322.tecnicasprogramacao.projeto;

import br.com.ada.t1322.tecnicasprogramacao.projeto.controller.TaskController;
import br.com.ada.t1322.tecnicasprogramacao.projeto.controller.TaskControllerImpl;
import br.com.ada.t1322.tecnicasprogramacao.projeto.repository.TaskRepositoryImpl;
import br.com.ada.t1322.tecnicasprogramacao.projeto.service.TaskServiceImpl;
import br.com.ada.t1322.tecnicasprogramacao.projeto.util.SampleDataInitializer;
import br.com.ada.t1322.tecnicasprogramacao.projeto.view.ConsoleApp;
import br.com.ada.t1322.tecnicasprogramacao.projeto.view.ConsoleView;
import br.com.ada.t1322.tecnicasprogramacao.projeto.view.View;

public class Application {
    public static void main(String[] args) {
        try (View view = new ConsoleView()) {
            TaskController controller = new TaskControllerImpl(new TaskServiceImpl(TaskRepositoryImpl.getInstance()));

            SampleDataInitializer.initializeSampleTasks(controller);

            ConsoleApp app = new ConsoleApp(view, controller);
            app.run();
        } catch (Exception e) {
            System.err.println("Erro durante a execução: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

package br.com.ada.t1322.tecnicasprogramacao.projeto.view.command;

import br.com.ada.t1322.tecnicasprogramacao.projeto.view.View;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ShowNotificationsCommand implements Command {

    private static final Path LOG_FILE_PATH = Path.of("notifications.log");
    private final View view;

    public ShowNotificationsCommand(View view) {
        this.view = view;
    }

    @Override
    public void execute() {
        try {
            if (Files.exists(LOG_FILE_PATH)) {
                view.showMessage("\n📜 Histórico de Notificações:");
                Files.lines(LOG_FILE_PATH).forEach(view::showMessage);
            } else {
                view.showMessage("\n📭 Nenhuma notificação registrada.");
            }
        } catch (IOException e) {
            view.showMessage("❌ Erro ao ler o arquivo de notificações: " + e.getMessage());
        }
    }
}

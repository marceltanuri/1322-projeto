[Voltar ao README](README.md)
# Mudanças e Melhorias Realizadas

A seguir, detalho as mudanças implementadas no projeto para adequá-lo aos requisitos e melhorar sua robustez, legibilidade e manutenção. Essas alterações foram realizadas ao longo do desenvolvimento, integrando melhorias propostas nas atividades do curso.

---

## 1. **AbstractTaskController**

- **Formatador de Data com Múltiplos Padrões:**  
  Para resolver erros na atualização de tarefas relacionados à data, foi criado um `DateTimeFormatter` que aceita dois padrões de entrada:
  ```java
  private final static DateTimeFormatter DATE_TIME_FORMATTER = new DateTimeFormatterBuilder()
          .appendOptional(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
          .appendOptional(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
          .toFormatter();
  ```
- **Tratamento de Valores Nulos na Atualização:**  
  Na função `updateTask`, foi adicionado o uso de `existingTask` e verificadores de `null`. Essa mudança permite que, ao atualizar uma tarefa, o usuário possa simplesmente apertar Enter para manter os dados originais, evitando que a atualização mantenha dados nulos e quebre o código:
  ```java
  @Override
  public Task updateTask(Long id, String title, String description, String deadline, Task.Status status) {
      Task existingTask = taskService.findById(id)
              .orElseThrow(() -> new IllegalArgumentException("Tarefa com o ID " + id + " não encontrada."));

      TaskUpdateRequest taskUpdateRequest = TaskUpdateRequest.builder(id)
              .title(title != null ? title : existingTask.getTitle())
              .description(description != null ? description : existingTask.getDescription())
              .deadline(deadline != null ? LocalDate.parse(deadline, DATE_TIME_FORMATTER) : existingTask.getDeadline())
              .status(status != null ? status : existingTask.getStatus())
              .build();

      return taskService.updateTask(taskUpdateRequest);
  }
  ```
  Essa abordagem foi adotada para solucionar um problema que ocorria ao atualizar tarefas mantendo os dados originais.

---

## 2. **TaskControllerImpl**

- **Alteração na Função `validateStatus`:**  
  A função `validateStatus` foi modificada para permitir a criação de uma tarefa com status **Concluído**. Na concepção adotada, se o usuário esquecer de anotar no gerenciador de tarefas, é possível criar a tarefa já com status concluído para que o histórico reflita essa anotação.

---

## 3. **Task**

- **Formatação do `toString`:**  
  O método `toString` foi alterado para formatar a data de deadline para o padrão brasileiro (`dd/MM/yyyy`), em vez do padrão americano.  
  ```java
  @Override
  public String toString() {
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
      String deadLineFormated = deadline.format(formatter);
      return String.format(
              "📌 Tarefa #%d%nTítulo: %s%nDescrição: %s%n📅 Prazo: %s%n🔄 Status: %s%n",
              id, title, description, deadLineFormated, status.getDescricao()
      );
  }
  ```

---

## 4. **Enum Task.Status**

- **Inclusão do Novo Status "Bloqueado":**  
  Exemplo:
  ```java
  public enum Status {
      PENDENTE("Pendente"),
      EM_ANDAMENTO("Em Andamento"),
      CONCLUIDO("Concluído"),
      BLOQUEADO("Bloqueado");
      // ... restante do código
  }
  ```

---

## 5. **TaskComparators**

- **Novo Comparator por ID:**  
  Foi adicionado um comparador para ordenar tarefas por ID:
  ```java
  public static final Comparator<Task> BY_ID = Comparator.comparing(Task::getId);
  ```
- **Atualização do Método `getComparator`:**  
  O método agora inclui a opção "id" para ordenação:
  ```java
  public static Comparator<Task> getComparator(String criteria, boolean reversed) {
      Comparator<Task> comparator = switch (criteria.toLowerCase()) {
          case "title" -> BY_TITLE;
          case "status" -> BY_STATUS;
          case "id" -> BY_ID;
          default -> BY_DEADLINE;
      };
      return reversed ? comparator.reversed() : comparator;
  }
  ```

---

## 6. **SampleDataInitializer**

- **Adição de Nova Tarefa com Status Bloqueado:**  
  Ao inicializar o programa, foi incluída uma nova tarefa com o status **Bloqueado**. Foram feitos ajustes também na ordenação das tarefas.

---

## 7. Classes em view.command

- **CreateTaskCommand, FilterTasksByCustomPredicateCommand, FilterTasksByStatusCommand, ListTasksCommand, UpdateTaskCommand, UpdateTaskStatusCommand**  
  - **Função:** Cada classe foi modificada para incluir um laço `do-while` que pergunta:  
    ```java
    view.getInput("Deseja criar outra tarefa? (S/N)")
    ```  
    Isso permite que o usuário repita a ação (criar, atualizar ou listar tarefas) sem precisar voltar ao menu principal.

## 8. Pacote view.command.task_sorting

- **SortingSelectionTaskHandler (classe)**  
  - **Função estática:**  
    ```java
    public static Optional<Comparator<Task>> getSortingMethod(View view) {
        view.showMessage("Escolha o critério de ordenação:");

        String options = Arrays.stream(SortingTaskOption.values())
                .map(option -> option.getOption() + " - " + option.getDisplayName())
                .collect(Collectors.joining("\n"));

        view.showMessage(options);

        int option = view.getIntInput("Digite o número da opção");

        SortingTaskOption sortingOption = SortingTaskOption.fromOption(option);
        if (sortingOption == SortingTaskOption.NONE) {
            return Optional.empty();
        }

        view.showMessage("Deseja ordem reversa? (S/N)");
        boolean reversed = view.getInput("").trim().equalsIgnoreCase("S");

        return Optional.of(TaskComparators.getComparator(sortingOption.getCriteria(), reversed));
    }
    ```

- **SortingTaskOption (enum)**  
  - **Função estática:**  
    ```java
    public enum SortingTaskOption {
        DEADLINE(1, "Data Limite", "deadline"),
        TITLE(2, "Título", "title"),
        STATUS(3, "Status", "status"),
        ID(4, "Id", "id"),
        NONE(5, "Sem ordenação", "");

## 9. Pacote view.command.helper

- **StatusDisplayHelper (classe)**  
  - **Função estática:**  
    ```java
    public static String getStatusOptions() {
        return Arrays.stream(Task.Status.values())
                     .map(status -> status.getDescricao())
                     .collect(Collectors.joining(", ", "(", ")"));
    }
    ```  
    Exibe dinamicamente os status disponíveis, evitando a necessidade de atualizar manualmente em várias classes.

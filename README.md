<a id="top"></a>
# Gerenciador de Tarefas Inteligente 📋✨

Este projeto é a base de um **Gerenciador de Tarefas Inteligente** desenvolvido para auxiliar profissionais a organizar suas atividades diárias. Foi criado e aprimorado durante o curso de Técnicas de Programação do programa Desenvolva+ da Ada e Mercado Livre, onde nossa base sólida foi expandida com melhorias e adaptações utilizando conceitos de SOLID e programação funcional.

---

## 📖 Documentação e Enunciado

- **Enunciado do Projeto:**  
  Para mais informações sobre o contexto, os requisitos e as diretrizes do projeto, consulte o [Enunciado do Projeto](ENUNCIADO.md).

- **Estrutura do Projeto:**  
  Entenda melhor a organização das pastas e arquivos acessando o [ESTRUTURA.md](ESTRUTURA.md).

- **Mudanças Realizadas:**  
  Detalhes completos sobre as melhorias feitas no projeto podem ser encontrados no [MUDANÇAS.md](MUDANÇAS.md).


---

## 🚀 Funcionalidades

- **Cadastro de Tarefas:**  
  Registre novas tarefas com título, descrição, data limite e status (Pendente, Em andamento, Concluído, Bloqueado).

- **Listagem e Filtragem:**  
  Exiba todas as tarefas cadastradas, com opções de filtragem por status e ordenação por data, título, status ou ID.

- **Atualização de Tarefas:**  
  Atualize os dados das tarefas; se algum campo for deixado em branco, o valor existente é mantido.

- **Ordenação Dinâmica:**  
  Utilize comparadores customizados para ordenar as tarefas conforme os critérios definidos, eliminando código repetido.

---

## 🔍 Mudanças e Melhorias Realizadas

Foram realizadas diversas melhorias no projeto, incluindo:

- Formatação da data para o padrão brasileiro.
- Inclusão de um novo status **Bloqueado** no enum `Task.Status`.
- Refatoração de comandos para reduzir duplicação e melhorar a reutilização de código.
- Adição de loops para repetição de ações no menu.
- Tratamento de campos nulos na atualização de tarefas.

Para detalhes completos, consulte o [arquivo de mudanças](MUDANÇAS.md).

---

## 🚀 Implementações Futuras

As seguintes funcionalidades serão adicionadas em versões futuras do projeto:

-  **Notificações Inteligentes com CompletableFuture:**  
  O sistema alertará automaticamente o usuário quando uma tarefa estiver próxima do prazo de conclusão, utilizando notificações assíncronas para melhorar a performance da aplicação.

---

## 💻 Tecnologias Utilizadas

- **Java**
- **Programação Funcional e Orientada a Objetos (SOLID)**
- **API java.time** para manipulação de datas
- **Streams e Optionals** para processamento e validação de dados
- **Collections (Comparator, List, HashMap, Map)** para manipulação de dados e ordenação
- **Scanner** para leitura de entradas do usuário no console

---

[Voltar ao topo](#top)



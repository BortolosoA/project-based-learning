# Exercício 19 — Gerenciador de Tarefas com Persistência

## Conceito
Aprender **I/O de arquivos** (`java.nio.file.Files`, `Path`) e serialização **JSON** com uma biblioteca externa (Gson ou Jackson), compilando com classpath (`-cp`) — o passo anterior ao Maven.

## Enunciado
Um gerenciador de tarefas de console que **não perde os dados ao fechar**: tudo é salvo em disco em formato JSON.

Classes esperadas:

- **`Tarefa`** — atributos: `titulo` (String), `descricao` (String), `prioridade` (enum `Prioridade`: `BAIXA`, `MEDIA`, `ALTA`), `dataLimite` (`java.time.LocalDate`), `concluida` (boolean). Construtor, getters/setters e `toString` legível.
- **`RepositorioTarefas`** — responsável pela persistência:
  - `List<Tarefa> carregar()` — lê o arquivo JSON; se o arquivo não existir, retorna lista vazia (não pode lançar erro na primeira execução).
  - `void salvar(List<Tarefa> tarefas)` — grava a lista inteira em JSON (pretty-print, se a lib permitir).
- **`GerenciadorTarefas`** — lógica do CRUD: adicionar, listar (com filtro opcional: todas / pendentes / concluídas), concluir, remover.
- **`Main`** — menu interativo com `Scanner`. Ao iniciar, carrega as tarefas do arquivo; a cada operação que altera dados, salva imediatamente.

**Abordagem esperada de dependência:** baixe o jar do **Gson** (ou Jackson) e compile com `-cp`:

```bash
# Exemplo com Gson (adapte a versão do arquivo baixado):
# Baixe gson-2.x.x.jar de https://repo1.maven.org/maven2/com/google/code/gson/gson/ e coloque na pasta do exercício
```

## Requisitos
1. Persistência em **JSON legível** (não serialização Java binária nem CSV). Campo `dataLimite` como texto ISO (`2026-10-01`) — com Gson, registrar um adaptador para `LocalDate`.
2. Primeira execução (arquivo inexistente) deve funcionar sem erro, começando com lista vazia.
3. Arquivo corrompido/ausente tratado com `try/catch` de `IOException` (e de erro de parse), exibindo mensagem amigável.
4. Menu com opções: 1) Adicionar, 2) Listar (com subfiltro), 3) Concluir tarefa, 4) Remover, 0) Sair — sempre salvando após alterações.
5. Leitura de `LocalDate` no formato `AAAA-MM-DD` com tratamento de `DateTimeParseException`.
6. Toda entrada inválida do menu deve ser tratada (reaproveite o que aprendeu no exercício 16).
7. Ao fechar e reabrir o programa, as tarefas devem estar lá — é o teste principal.

## Exemplo de fluxo
```
=== Gerenciador de Tarefas ===
1) Adicionar  2) Listar  3) Concluir  4) Remover  0) Sair
> 1
Título: Estudar Streams
Descrição: Terminar exercício 18
Prioridade (BAIXA/MEDIA/ALTA): ALTA
Data limite (AAAA-MM-DD): 2026-09-30
Tarefa adicionada e salva.
> 2
Filtrar: 1) Todas  2) Pendentes  3) Concluídas
> 2
[PENDENTE] Estudar Streams | ALTA | até 2026-09-30
> 0
Até logo! (1 tarefa salva em tarefas.json)
```

## Como executar
```bash
# Com o jar do Gson na pasta do exercício (nome pode variar conforme a versão):
javac -cp .:gson-2.11.0.jar *.java
java -cp .:gson-2.11.0.jar Main
```

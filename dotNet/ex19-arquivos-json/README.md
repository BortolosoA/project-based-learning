# Exercício 19 — Gerenciador de Tarefas com Persistência (Arquivos + JSON)

## Conceito
Ler e escrever arquivos (`File.ReadAllText` / `File.WriteAllText`), serializar JSON com
`System.Text.Json` (`JsonSerializer`) e trabalhar com datas (`DateTime`).

## Enunciado
Um gerenciador de tarefas no console que **sobrevive entre execuções**, salvando tudo em `tarefas.json`:

- `Tarefa` — `Id` (int), `Titulo` (string), `Concluida` (bool), `CriadaEm` (DateTime).
- `GerenciadorTarefas` — carrega a lista do arquivo no construtor e salva depois de cada alteração:
  - `void Adicionar(string titulo)` — gera o próximo `Id` (máximo existente + 1) e `CriadaEm = DateTime.Now`.
  - `bool Concluir(int id)` — marca como concluída; retorna false se não existir.
  - `bool Remover(int id)` — remove; retorna false se não existir.
  - `void Listar(bool? concluidas = null)` — lista todas ou filtra por status; pendentes primeiro.
  - `void Estatisticas()` — total, pendentes, concluídas e % concluída.
- `Program` — menu:
  ```
  1 - Adicionar
  2 - Listar
  3 - Concluir
  4 - Remover
  0 - Sair
  ```

## Requisitos
1. Serialização com `JsonSerializer.Serialize` / `Deserialize` (nunca montar JSON "na mão").
2. Se `tarefas.json` não existir ou estiver vazio, comece com lista vazia — a **primeira
   execução não pode quebrar**.
3. Datas em formato legível no console: `ToString("dd/MM/yyyy HH:mm")`.
4. Ao reiniciar o programa, as tarefas da execução anterior continuam lá — esse é o teste final.

## Exemplo
```
1
Digite o título: Estudar LINQ
Tarefa criada.
2
[Pendente] #1 - Estudar LINQ (criada em 28/09/2026 10:00)
3
Id da tarefa: 1
Tarefa concluída!
```

## Como executar
```bash
dotnet run
# adicione tarefas, encerre o programa, rode de novo e liste — elas continuam lá!
```

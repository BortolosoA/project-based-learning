# AGENTS.md — Correção do Exercício 19 (Arquivos + JSON)

## Critérios de correção
1. Persistência real: o arquivo `tarefas.json` é criado/atualizado após cada operação.
2. Primeira execução (arquivo inexistente) não quebra — começa com lista vazia.
3. Serialização via `System.Text.Json` — sem JSON escrito na mão.
4. Ids incrementais sem colisão; concluir/remover tratam id inexistente (retornam false + mensagem).
5. Datas impressas em `dd/MM/yyyy HH:mm`.

## Como verificar
```bash
printf "1\nEstudar LINQ\n0\n" | dotnet run
cat tarefas.json        # deve conter a tarefa com id 1 e data
printf "2\n0\n" | dotnet run     # a tarefa ainda está lá
printf "3\n1\n2\n0\n" | dotnet run  # concluída aparece como [Concluída]
```

## Erros comuns a apontar
- `File.ReadAllText` estourando `FileNotFoundException` na primeira execução — usar `File.Exists` antes.
- Deserializar para `List<Tarefa>` com null check ausente (`JsonSerializer.Deserialize` pode retornar null).
- Propriedades sem `get; set;` (ou sem construtor sem parâmetros) — System.Text.Json não serializa/deserializa.
- Esquecer de salvar após alterar (mudança só em memória).
- Id "incremental" com contador em memória que reinicia a cada execução e gera duplicado — o certo é `Max(Id) + 1` a partir do arquivo.

## Padrão de feedback
O teste decisivo é fechar e abrir o programa. Se as tarefas sobreviverem, o objetivo
foi atingido. Comentar que esse padrão (carregar no construtor, salvar em cada
mutação) é um "repositório" primitivo — no ex23 o EF Core fará isso por ele.

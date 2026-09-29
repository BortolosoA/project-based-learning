# AGENTS.md — Correção do Exercício 4

## Critérios de correção
1. Lê nome, idade e altura na ordem pedida.
2. Usa `TryParse` (não `Parse`/`Convert` sem proteção) para número; entrada inválida mostra `Idade inválida!` sem quebrar.
3. Imprime a frase final com os dados informados.

## Como verificar
```bash
printf "Ana\n25\n1.65\n" | dotnet run
printf "Ana\nabc\n" | dotnet run   # esperado: Idade inválida! (sem stack trace)
```

## Erros comuns a apontar
- `int.Parse` direto: digitar "abc" derruba o programa com `FormatException` — apontar como erro.
- Problema de cultura: `1.65` falhar no `double.TryParse` porque o sistema espera vírgula —
  sugerir `CultureInfo.InvariantCulture`.
- `ReadLine` retornando null (fim de input) sem tratamento — aceitar, mas mencionar nullable.

## Padrão de feedback
Aprovar se ler os 3 dados, imprimir a frase e sobreviver à entrada inválida.
Explicar erros de forma didática e reforçar: no .NET, `TryParse` é a forma idiomática.

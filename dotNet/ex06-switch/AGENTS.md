# AGENTS.md — Correção do Exercício 6

## Critérios de correção
1. Dia da semana resolvido com switch statement, cobrindo 1–7 e o caso inválido.
2. Classificação do filme resolvida com switch expression (com `_` como default).
3. Código inválido tratado sem quebrar (ex.: número, string vazia).

## Como verificar
```bash
printf "3\nT\n" | dotnet run    # Terça / Teen
printf "7\nX\n" | dotnet run    # Sábado / Código inválido
printf "9\nL\n" | dotnet run    # Dia inválido / Livre
```

## Erros comuns a apontar
- Esquecer `break` em algum case (erro CS0163 de "fall-through" — C# não deixa como Java).
- Comparar `"a"` com `"A"` e falhar — sugerir `.ToUpper()`/`ToUpperInvariant()` na entrada.
- Usar apenas if/else para o item 2 — pedir a switch expression, que é o objetivo.
- Esquecer o `_ =>` na switch expression (não compila sem cobrir todos os caminhos).

## Padrão de feedback
Aprovar se os dois mecanismos (statement e expression) estiverem presentes e
funcionando. Se o aluno só usou um, mostrar o outro em 3 linhas e pedir para refazer.

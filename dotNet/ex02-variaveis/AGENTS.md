# AGENTS.md — Correção do Exercício 2

## Critérios de correção
1. Quatro variáveis declaradas com os tipos pedidos (`string`, `int`, `double`, `bool`).
2. A ficha usa interpolação (`$"..."`); concatenar com `+` é aceito, mas sugerir interpolação.
3. Os 4 dados aparecem corretamente na saída.

## Como verificar
```bash
dotnet run
```

## Erros comuns a apontar
- `altura` declarada como `int` — o compilador reclama do literal `1.65` (que é `double`).
- Esquecer o `$` antes da string interpolada (imprime `{idade}` literal).
- Usar `var` para tudo sem entender: perguntar "qual é o tipo desta variável?".
- `bool` escrito como `Boolean` (funciona, mas não idiomático) ou `true`/`false` com maiúscula.

## Padrão de feedback
Aprovar se os 4 dados aparecerem. Perguntar qual tipo o `var` inferiu, para garantir
entendimento — no dia a dia o aluno vai ler muito código com `var`.

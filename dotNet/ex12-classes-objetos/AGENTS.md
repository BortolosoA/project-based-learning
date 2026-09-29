# AGENTS.md — Correção do Exercício 12

## Critérios de correção
1. Dois arquivos (`Conta.cs`, `Program.cs`) e o projeto roda.
2. `Conta` tem construtor e os três métodos pedidos.
3. O saque valida saldo suficiente (o saque de 2000 é recusado).
4. Saldo final impresso é 1200.

## Como verificar
```bash
dotnet run
```

## Erros comuns a apontar
- `Sacar` subtraindo sem verificar saldo.
- Setter de `Saldo` público e saldo alterado direto no Program (`conta.Saldo = 999`)
  — falar de encapsulamento; `private set` é o objetivo aqui.
- Construtor que não atribui os valores (esqueceu os parâmetros).
- Métodos `static` na Conta (não faz sentido: cada conta tem seu próprio saldo).

## Padrão de feedback
O saldo final 1200 é o teste principal. Apontar problemas de encapsulamento como
melhoria de estilo — e elogiar se o aluno já usou `private set` ou campo privado.

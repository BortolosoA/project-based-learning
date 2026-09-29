# Exercício 16 — Caixa Eletrônico Seguro (Exceções)

## Conceito
Tratamento de erros do jeito certo: `try/catch/finally`, `throw`, exceções do framework
(`FormatException`, `ArgumentException`) e **exceções customizadas**.

## Enunciado
Um caixa eletrônico com menu:
```
1 - Depositar
2 - Sacar
3 - Ver saldo
0 - Sair
```

Regras que devem gerar **exceções** (não basta `Console.WriteLine` na regra):
- Sacar valor maior que o saldo → `SaldoInsuficienteException`.
- Depositar ou sacar valor negativo/zero → `ValorInvalidoException`.

Estrutura:
- `SaldoInsuficienteException`, `ValorInvalidoException` — exceções criadas por você,
  herdando de `Exception`, com mensagem clara (a de saldo pode informar o disponível).
- `Conta` — propriedade `Saldo`, `Depositar(double)` e `Sacar(double)` que fazem `throw`
  das exceções acima quando as regras são violadas.
- `Program` — menu em laço; cada operação dentro de `try/catch` que captura as exceções
  e imprime `ERRO: <mensagem>`; o programa **nunca deve encerrar por erro de entrada**
  (opção inválida, texto no lugar de número etc.).

## Exemplo
```
1
Digite o valor: 50
Depósito realizado. Saldo: 50
2
Digite o valor: 100
ERRO: Saldo insuficiente. Disponível: 50
2
Digite o valor: -10
ERRO: Valor inválido.
0
```

## Como executar
```bash
dotnet run
```

## Dica
`catch (SaldoInsuficienteException ex)` → `ex.Message` carrega a mensagem que você
passou no `throw new SaldoInsuficienteException("...")`.

# Exercício 16 — Caixa Eletrônico Seguro

## Conceito
Aprender a tratar erros de forma profissional em Java: criar exceções customizadas, usar `try/catch/finally`, `throw`/`throws` e entender a diferença entre exceções *checked* e *unchecked*.

## Introdução ao tema

Erros **vão** acontecer — o que separa um programa amador de um profissional
é o que acontece quando eles ocorrem. Em vez de deixar tudo quebrar com um
*stack trace*, Java oferece **exceções**: o `throw` "joga" o problema, o
`try/catch` captura e trata, e o `finally` roda sempre. Exceções
**customizadas** dão nomes claros aos problemas do seu domínio
("SaldoInsuficiente" diz muito mais que um número de erro).

## Exemplo simples

```java
static int dividir(int a, int b) {
    if (b == 0) {
        throw new IllegalArgumentException("Divisor não pode ser zero!");
    }
    return a / b;
}

public static void main(String[] args) {
    try {
        System.out.println(dividir(10, 0));   // lança a exceção!
    } catch (IllegalArgumentException e) {
        System.out.println("ERRO: " + e.getMessage());   // trata, não quebra
    } finally {
        System.out.println("Fim da operação.");          // roda SEMPRE
    }
}
```

## Enunciado
Você vai construir um caixa eletrônico de console que nunca "quebra" por erro do usuário. O sistema gerencia uma conta bancária simples com menu interativo.

Classes esperadas:

- **`ContaBancaria`** — atributos: `titular` (String), `saldo` (double). Métodos:
  - `void depositar(double valor)` — lança `ValorInvalidoException` se valor ≤ 0.
  - `void sacar(double valor)` — lança `ValorInvalidoException` se valor ≤ 0 e `SaldoInsuficienteException` se valor > saldo.
  - `void transferir(ContaBancaria destino, double valor)` — aplica as mesmas validações; debita desta conta e credita na destino.
  - `double getSaldo()` e `String getTitular()`.
- **`SaldoInsuficienteException`** — exceção **checked** (extends `Exception`), com mensagem informando saldo atual e valor tentado.
- **`ValorInvalidoException`** — exceção **unchecked** (extends `IllegalArgumentException`).
- **`Main`** — menu interativo com `Scanner`: 1) Depositar, 2) Sacar, 3) Transferir, 4) Ver saldo, 0) Sair.

Comportamento: o programa nunca deve terminar com stack trace na cara do usuário. Toda entrada inválida (texto onde se espera número, valor negativo, saldo insuficiente) deve ser capturada e exibir uma mensagem amigável. Ao encerrar, exiba o saldo final em um bloco `finally` (ou após o loop).

## Requisitos
1. `SaldoInsuficienteException` deve ser **checked** (`extends Exception`) — os métodos que a lançam devem declarar `throws`.
2. `ValorInvalidoException` deve ser **unchecked** (`extends IllegalArgumentException`).
3. Usar `try/catch` com **múltiplos catch** (ou multi-catch) tratando separadamente: `InputMismatchException` (entrada não numérica), `ValorInvalidoException` e `SaldoInsuficienteException`.
4. Usar `finally` em pelo menos um ponto relevante (ex.: log da operação ou encerramento do Scanner).
5. Nenhuma operação inválida pode alterar o saldo.
6. Mensagens de erro claras, em português, sem stack trace para o usuário.
7. O menu deve repetir até o usuário escolher sair.

## Exemplo de fluxo
```
=== Caixa Eletrônico ===
Titular: Ana | Saldo: R$ 500.00
1) Depositar  2) Sacar  3) Transferir  4) Saldo  0) Sair
> 2
Valor do saque: 800
Erro: Saldo insuficiente. Saldo atual: R$ 500.00, valor tentado: R$ 800.00
> 2
Valor do saque: abc
Erro: digite um número válido.
> 1
Valor do depósito: -50
Erro: o valor deve ser maior que zero.
> 1
Valor do depósito: 200
Depósito realizado. Novo saldo: R$ 700.00
> 0
Encerrando... Saldo final: R$ 700.00
```

## Como executar
```bash
javac *.java
java Main
```

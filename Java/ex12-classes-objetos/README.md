# Exercício 12 — Classes e Objetos

## Conceito
Criação de classes, atributos, construtores e métodos.

## Introdução ao tema

Aqui começa a **Orientação a Objetos**. Uma **classe** é a "planta" (o molde);
um **objeto** é a "casa" construída a partir dela com `new`. A classe define
**atributos** (os dados) e **métodos** (os comportamentos); o **construtor**
roda na criação e inicializa os atributos. Cada objeto guarda **seus próprios
valores** — duas contas da mesma classe têm saldos independentes.

## Exemplo simples

```java
class Cachorro {
    String nome;

    Cachorro(String nome) {        // construtor: mesmo nome da classe
        this.nome = nome;           // this = "deste objeto"
    }

    void latir() {
        System.out.println(nome + ": Au au!");
    }
}

// em outro lugar:
Cachorro rex = new Cachorro("Rex");   // um objeto nascendo
rex.latir();                          // Rex: Au au!
```

## Enunciado
Crie dois arquivos:

### `Conta.java`
Uma classe `Conta` (conta bancária) com:
- Atributos: `titular` (String) e `saldo` (double).
- Construtor que recebe o titular e o saldo inicial.
- Métodos:
  - `depositar(double valor)` — soma ao saldo.
  - `sacar(double valor)` — subtrai do saldo apenas se houver saldo suficiente; caso contrário imprime `Saldo insuficiente`.
  - `exibirSaldo()` — imprime titular e saldo.

### `Main.java`
Programa que:
1. Cria uma conta para "Maria" com saldo inicial 1000.
2. Deposita 500.
3. Saca 2000 (deve ser recusado).
4. Saca 300.
5. Exibe o saldo final (deve ser 1200).

## Como executar
```bash
javac Main.java Conta.java
java Main
```

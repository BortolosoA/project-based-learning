# Exercício 3 — Operadores Aritméticos

## Conceito
Operadores `+`, `-`, `*`, `/`, `%` e conversão de tipos (casting).

## Introdução ao tema

Operadores são os símbolos que transformam valores: `+`, `-`, `*`, `/` e `%`
(o resto da divisão). O detalhe que pega muita gente: **a operação segue o tipo
dos operandos** — `int / int` resulta em `int`, e a parte decimal é jogada fora.
Para uma divisão "de verdade", um dos lados precisa ser `double` — e é aí que
entra o **casting**: `(double) a / b`.

## Exemplo simples

```java
int a = 17, b = 5;

System.out.println(a / b);          // 3   — divisão inteira (trunca!)
System.out.println((double) a / b); // 3.4 — casting: divisão real
System.out.println(a % b);          // 2   — resto: 17 = 5*3 + 2
```

## Enunciado
Crie um arquivo `Calculadora.java` que:
1. Declare duas variáveis inteiras, por exemplo `a = 17` e `b = 5`.
2. Imprima os resultados de soma, subtração, multiplicação e resto (`%`).
3. Imprima a divisão inteira (`a / b`) e também a divisão com casas decimais (converta para `double`).

## Saída esperada (exemplo)
```
Soma: 22
Subtração: 12
Multiplicação: 85
Divisão inteira: 3
Divisão real: 3.4
Resto: 2
```

## Como executar
```bash
javac Calculadora.java
java Calculadora
```

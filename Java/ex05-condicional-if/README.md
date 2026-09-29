# Exercício 5 — Condicionais (if/else)

## Conceito
Estruturas de decisão: `if`, `else if`, `else`, operadores de comparação e lógicos.

## Introdução ao tema

O `if/else` é o coração da **lógica**: o programa toma decisões a partir de
condições (`if` = "se", `else` = "senão"). As condições combinam operadores de
comparação (`==`, `!=`, `>`, `<`, `>=`, `<=`) e lógicos (`&&` = e, `||` = ou,
`!` = não). E um detalhe que decide este exercício: a **ordem** importa — o
primeiro `if` verdadeiro "vence" e os demais nem rodam.

## Exemplo simples

```java
int idade = 20;

if (idade >= 18) {
    System.out.println("Maior de idade");
} else {
    System.out.println("Menor de idade");
}

// comparações devolvem boolean — dá pra guardar em uma variável:
boolean podeDirigir = idade >= 18;   // true
```

## Enunciado
Crie um arquivo `Notas.java` que:
1. Leia a nota de um aluno (0 a 10) usando `Scanner`.
2. Classifique e imprima:
   - Nota >= 7: `Aprovado`
   - Nota >= 5 e < 7: `Recuperação`
   - Nota < 5: `Reprovado`
3. Se a nota for menor que 0 ou maior que 10, imprima: `Nota inválida`

## Exemplos
```
Digite a nota: 8.5
Aprovado
```
```
Digite a nota: 12
Nota inválida
```

## Como executar
```bash
javac Notas.java
java Notas
```

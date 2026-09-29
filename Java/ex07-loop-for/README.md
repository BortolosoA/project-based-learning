# Exercício 7 — Laço for

## Conceito
Repetição com `for`.

## Introdução ao tema

O laço `for` **repete um bloco** de código. A cabeça do laço tem três partes
separadas por `;`: **inicialização** (`int i = 1`), **condição de parada**
(`i <= 10`) e **incremento** (`i++`). É o companheiro perfeito para "faça isso
N vezes" — e o erro clássico é usar `<` onde o enunciado pede "até 10", parando no 9.

## Exemplo simples

```java
for (int i = 1; i <= 5; i++) {
    System.out.println("Volta número " + i);
}
// Saída: Volta número 1 ... Volta número 5
// Troque i <= 5 por i < 5 e veja a última volta sumir!
```

## Enunciado
Crie um arquivo `Tabuada.java` que:
1. Leia um número inteiro do usuário.
2. Imprima a tabuada desse número de 1 a 10 usando `for`.

## Exemplo
```
Digite um número: 7
7 x 1 = 7
7 x 2 = 14
...
7 x 10 = 70
```

## Extra (opcional)
Imprima também a soma de todos os resultados da tabuada.

## Como executar
```bash
javac Tabuada.java
java Tabuada
```

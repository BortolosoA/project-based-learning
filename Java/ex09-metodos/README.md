# Exercício 9 — Métodos

## Conceito
Criação e chamada de métodos estáticos, parâmetros e valor de retorno.

## Introdução ao tema

Um **método** é um bloco de código com **nome**, que recebe **parâmetros**,
pode **retornar** um valor e pode ser reutilizado no programa inteiro — a
versão em código de "escreva uma vez, use sempre". Um método Java declara: o
tipo de retorno, o nome e a lista de parâmetros. E métodos chamados direto do
`main` precisam ser `static`.

## Exemplo simples

```java
static int dobro(int n) {
    return n * 2;   // devolve o valor para quem chamou
}

public static void main(String[] args) {
    int resultado = dobro(21);
    System.out.println(resultado);   // 42
}
```

## Enunciado
Crie um arquivo `Utilidades.java` com:
1. Um método `soma(int a, int b)` que retorna a soma.
2. Um método `ehPar(int n)` que retorna `true` se o número for par.
3. Um método `maior(int a, int b, int c)` que retorna o maior dos três números.
4. No `main`, chame os três métodos com valores de exemplo e imprima os resultados.

## Exemplo de saída
```
Soma de 4 e 9: 13
8 é par? true
Maior entre 3, 12 e 7: 12
```

## Como executar
```bash
javac Utilidades.java
java Utilidades
```

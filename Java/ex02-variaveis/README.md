# Exercício 2 — Variáveis e Tipos Primitivos

## Conceito
Tipos primitivos: `int`, `double`, `boolean`, `char`, e a classe `String`.

## Introdução ao tema

Uma **variável** é uma "caixinha com rótulo" na memória que guarda um valor.
Java é **estaticamente tipado**: toda variável tem um tipo fixo declarado —
`int` para inteiros, `double` para decimais, `boolean` para verdadeiro/falso,
`char` para um caractere e `String` (que é uma classe) para textos. Escolher
o tipo certo evita erros e deixa a intenção do código clara.

## Exemplo simples

```java
int idade = 25;           // número inteiro
double altura = 1.65;     // número com casas decimais
boolean estudante = true; // verdadeiro ou falso
char inicial = 'A';       // um único caractere (aspas simples)
String nome = "Ana";      // texto (aspas duplas)

System.out.println("Meu nome é " + nome + " e tenho " + idade + " anos.");
```

## Enunciado
Crie um arquivo `Variaveis.java` que declare variáveis com seus dados pessoais fictícios:
1. `nome` (String)
2. `idade` (int)
3. `altura` (double, em metros)
4. `estudante` (boolean)

Imprima uma frase usando todas elas, por exemplo:
```
Meu nome é Ana, tenho 25 anos, 1.65m de altura. Estudante: true
```

## Como executar
```bash
javac Variaveis.java
java Variaveis
```

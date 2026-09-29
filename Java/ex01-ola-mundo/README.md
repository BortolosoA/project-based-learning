# Exercício 1 — Olá, Mundo!

## Conceito
Estrutura básica de um programa Java, `main` e `System.out.println`.

## Introdução ao tema

Todo programa Java começa com uma **classe** e um método `main` — é a porta de
entrada que a JVM procura ao executar. Java é uma linguagem **compilada**: o
`javac` transforma seu `.java` em **bytecode** (`.class`) e o comando `java`
executa esse bytecode. Esse ciclo *compilar → executar* vai acompanhar você
por toda a trilha.

## Exemplo simples

```java
public class OlaMundo {                        // o nome da classe = nome do arquivo
    public static void main(String[] args) {   // ponto de entrada do programa
        System.out.println("Olá, Mundo!");     // imprime e pula a linha
    }
}
```

Repare na "receita" de `System.out.println`: `System` é uma classe, `out` é a
saída padrão e `println` é o método que imprime.

## Enunciado
Crie um arquivo `OlaMundo.java` que:
1. Contenha uma classe pública chamada `OlaMundo`.
2. Imprima na tela a mensagem: `Olá, Mundo!`
3. Na linha seguinte, imprima seu nome.

## Saída esperada
```
Olá, Mundo!
Seu Nome
```

## Como executar
```bash
javac OlaMundo.java
java OlaMundo
```

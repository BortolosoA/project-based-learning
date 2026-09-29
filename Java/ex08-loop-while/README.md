# Exercício 8 — Laço while

## Conceito
Repetição com `while` e controle por condição de parada.

## Introdução ao tema

O `while` repete **enquanto uma condição for verdadeira** — diferente do
`for`, você nem sempre sabe de antemão quantas voltas ele dará. É ideal para
"repita até o usuário acertar". O perigo é o **laço infinito**: a condição
precisa, em algum momento, virar falsa — ou seja, a variável testada tem que
mudar dentro do laço.

## Exemplo simples

```java
int contador = 3;

while (contador > 0) {
    System.out.println(contador);
    contador--;   // sem esta linha, o laço NUNCA terminaria!
}
System.out.println("Fim!");
```

## Enunciado
Crie um arquivo `Adivinhe.java` que:
1. Defina um número secreto fixo no código (ex.: `int secreto = 42;`).
2. Peça ao usuário para adivinhar o número repetidamente com `while`, até ele acertar.
3. A cada tentativa errada, diga se o palpite é `Maior` ou `Menor` que o número secreto.
4. Ao acertar, imprima `Parabéns! Você acertou em X tentativas.`

## Exemplo
```
Tente adivinhar (1 a 100): 50
Menor
Tente adivinhar (1 a 100): 30
Maior
Tente adivinhar (1 a 100): 42
Parabéns! Você acertou em 3 tentativas.
```

## Como executar
```bash
javac Adivinhe.java
java Adivinhe
```

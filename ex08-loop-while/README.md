# Exercício 8 — Laço while

## Conceito
Repetição com `while` e controle por condição de parada.

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

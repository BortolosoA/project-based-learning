# AGENTS.md — Correção do Exercício 11

## Critérios de correção
1. Arquivo `AnalisaTexto.java` compila e lê a frase com `nextLine()`.
2. As 5 análises funcionam corretamente.
3. A busca por "java" é case-insensitive (ex.: usando `toLowerCase().contains("java")`).

## Como verificar
```bash
javac AnalisaTexto.java && printf "Eu amo Java\n" | java AnalisaTexto
```
Conferir cada linha da saída com o exemplo do README.

## Erros comuns a apontar
- Usar `==` para comparar Strings em vez de `equals` — reforçar esse conceito se aparecer.
- `charAt(frase.length())` causa `StringIndexOutOfBoundsException` — o índice correto é `length() - 1`.
- Esquecer de converter para minúsculas antes do `contains("java")`.

## Padrão de feedback
Aprovar se as 5 análises estiverem corretas. O ponto pedagógico mais importante deste exercício é `equals` vs `==`.

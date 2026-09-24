# AGENTS.md — Correção do Exercício 7

## Critérios de correção
1. Arquivo `Tabuada.java` compila e usa um laço `for`.
2. Imprime exatamente 10 linhas no formato `<n> x <i> = <resultado>`.
3. Os cálculos estão corretos.

## Como verificar
```bash
javac Tabuada.java && printf "7\n" | java Tabuada
```
Conferir que vai de 1 até 10 e que os produtos estão certos (ex.: última linha `7 x 10 = 70`).

## Erros comuns a apontar
- Condição do laço com `< 10` em vez de `<= 10` (imprime só até 9).
- Começar o contador em 0.

## Padrão de feedback
Aprovar se as 10 linhas estiverem corretas. O extra é bônus — elogiar se feito, não exigir.

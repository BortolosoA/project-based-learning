# AGENTS.md — Correção do Exercício 8

## Critérios de correção
1. Arquivo `Adivinhe.java` compila e usa `while` (ou `do-while`).
2. Dá dicas "Maior"/"Menor" corretas: se o palpite é MAIOR que o secreto, imprime "Menor" (o número é menor) — verifique se o aluno não inverteu.
3. Conta as tentativas e imprime a mensagem final correta.

## Como verificar (assumindo secreto = 42; ajuste conforme o código)
```bash
javac Adivinhe.java && printf "50\n30\n42\n" | java Adivinhe
```
Saída esperada: Menor, Maior, Parabéns em 3 tentativas.

## Erros comuns a apontar
- Dicas invertidas.
- Contador começando em 1 (não conta a primeira tentativa) ou em 0 sem incrementar.
- Loop infinito por não ler nova tentativa dentro do while.

## Padrão de feedback
Aprovar se o jogo funciona de ponta a ponta. Erros de dicas invertidas são comuns — apontar com exemplo.

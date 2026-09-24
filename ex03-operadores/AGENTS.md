# AGENTS.md — Correção do Exercício 3

## Critérios de correção
1. Arquivo `Calculadora.java` compila e executa.
2. Usa corretamente `+`, `-`, `*`, `/`, `%`.
3. Mostra a diferença entre divisão inteira e divisão real (com cast para `double`).

## Como verificar
```bash
javac Calculadora.java && java Calculadora
```
Com a=17 e b=5 (ou valores equivalentes), a divisão inteira e a real devem dar resultados diferentes.

## Erros comuns a apontar
- Não fazer o cast: `(double) a / b`. Explicar por que `a / b` com dois inteiros trunca.
- Confundir `%` com porcentagem (é resto da divisão).

## Padrão de feedback
Aprovar se as 6 operações aparecem corretamente. Aponte sutilmente onde há truncamento se faltar o cast.

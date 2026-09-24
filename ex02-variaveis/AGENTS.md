# AGENTS.md — Correção do Exercício 2

## Critérios de correção
1. Arquivo `Variaveis.java` compila sem erros.
2. Usa pelo menos os 4 tipos: `String`, `int`, `double`, `boolean`.
3. A saída contém os valores de todas as variáveis em uma frase legível.

## Como verificar
```bash
javac Variaveis.java && java Variaveis
```

## Erros comuns a apontar
- Concatenação de String com `+` faltando.
- Uso incorreto de tipos (ex.: altura como `int`).
- Variáveis declaradas fora do `main` sem `static`.

## Padrão de feedback
Aprovar se todos os tipos estiverem corretos e a saída for legível. Explicar erros de forma didática sem reescrever o código inteiro.

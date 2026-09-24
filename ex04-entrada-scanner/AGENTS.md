# AGENTS.md — Correção do Exercício 4

## Critérios de correção
1. Arquivo `Perfil.java` compila e importa `java.util.Scanner`.
2. Lê nome, idade e altura na ordem pedida, usando os métodos corretos.
3. Imprime a frase final com os dados informados.

## Como verificar
```bash
javac Perfil.java && printf "Ana\n25\n1.65\n" | java Perfil
```

## Erros comuns a apontar
- Não fechar o Scanner (`scanner.close()`) — avisar como boa prática, não reprovar por isso.
- Confundir `next()` com `nextLine()` (nomes com espaço quebram com `next()`).
- Locale: `nextDouble()` pode exigir vírgula dependendo do sistema. Sugerir `Locale.US` se necessário.

## Padrão de feedback
Aprovar se ler os 3 dados e imprimir a frase correta. Explicar erros de forma didática.

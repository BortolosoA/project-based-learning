# AGENTS.md — Correção do Exercício 5

## Critérios de correção
1. Arquivo `Notas.java` compila e lê a nota com `Scanner`.
2. Lógica correta com os 3 intervalos (Aprovado / Recuperação / Reprovado).
3. Validação de nota fora do intervalo 0–10.

## Como verificar
```bash
javac Notas.java
printf "8\n" | java Notas    # Aprovado
printf "6\n" | java Notas    # Recuperação
printf "3\n" | java Notas    # Reprovado
printf "15\n" | java Notas   # Nota inválida
```

## Erros comuns a apontar
- Ordem das condições (checar `>= 7` depois de `>= 5` não funciona).
- Usar `=` em vez de `==` nas comparações.

## Padrão de feedback
Testar os 4 casos acima. Aprovar somente se todos passarem. Explicar a ordem das condições se esse for o erro.

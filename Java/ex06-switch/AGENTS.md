# AGENTS.md — Correção do Exercício 6

## Critérios de correção
1. Arquivo `DiaSemana.java` compila e usa `switch` (não apenas if/else encadeados).
2. Todos os 7 dias mapeados corretamente.
3. Caso `default` imprime `Dia inválido`.

## Como verificar
```bash
javac DiaSemana.java
printf "3\n" | java DiaSemana   # Terça
printf "7\n" | java DiaSemana   # Sábado
printf "9\n" | java DiaSemana   # Dia inválido
```

## Erros comuns a apontar
- Esquecer `break` em cada case (com switch tradicional), causando fall-through.
- Aceitar soluções com switch expressions modernas (`->`), desde que corretas — elogiar.

## Padrão de feedback
Testar os 3 casos acima. Se houver fall-through, mostrar ao aluno com um exemplo concreto o que acontece.

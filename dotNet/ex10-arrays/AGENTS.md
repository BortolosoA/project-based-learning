# AGENTS.md — Correção do Exercício 10

## Critérios de correção
1. Array de 5 doubles declarado e preenchido.
2. Impressão com `foreach`; cálculos (média, maior, menor, aprovados) com `for`.
3. Os quatro resultados estão matematicamente corretos para os dados escolhidos.

## Como verificar
```bash
dotnet run
```
Com os dados do enunciado: média 7.2, maior 9.0, menor 5.5, aprovados 4.
Recalcular à mão caso o aluno mude os valores.

## Erros comuns a apontar
- `i <= notas.Length` — `IndexOutOfRangeException` no último passo.
- Acumular a maior nota inicializando com 0 (quebra se todas as notas forem negativas — aqui não quebra, mas comentar).
- Recalcular média somando e dividindo dentro do laço (imprime resultados parciais).

## Padrão de feedback
Aprovar se os 4 números baterem. Desafio incremental: "agora imprima a posição
da maior nota" — força o aluno a guardar o índice, não só o valor.

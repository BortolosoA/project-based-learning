# Exercício 10 — Arrays

## Conceito
Arrays de tamanho fixo: declaração, índice, `Length`, iteração com `for` e `foreach`.

## Enunciado
1. Crie um `double[]` com as notas de 5 alunos (hardcoded, ex.: `7.5, 8.0, 5.5, 9.0, 6.0`).
2. Imprima todas as notas com `foreach` (na mesma linha, separadas por espaço).
3. Calcule e imprima com um laço `for`:
   - a média das notas,
   - a maior nota,
   - a menor nota,
   - quantos alunos passaram (nota >= 6).

## Exemplo
```
Notas: 7.5 8.0 5.5 9.0 6.0
Média: 7.2
Maior nota: 9.0
Menor nota: 5.5
Aprovados: 4
```

## Como executar
```bash
dotnet run
```

## Dica
Percorrer com índice: `for (int i = 0; i < notas.Length; i++)`.
Percorrer sem índice: `foreach (double nota in notas)`.
Cada um serve a um propósito — aqui você usa os dois.

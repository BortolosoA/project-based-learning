# AGENTS.md — Correção do Exercício 10

## Critérios de correção
1. Arquivo `Notas.java` compila e usa um array de `double` com tamanho definido pelo usuário.
2. Calcula corretamente média, maior e menor.
3. Usa laços para leitura e cálculo (não código repetido manualmente).

## Como verificar
```bash
javac Notas.java && printf "3\n7.5\n4.0\n9.0\n" | java Notas
```
Média ≈ 6.83, Maior 9.0, Menor 4.0.

## Erros comuns a apontar
- Inicializar maior/menor com 0 (quebra se todas as notas forem negativas) — ensinar a iniciar com `notas[0]`.
- `ArrayIndexOutOfBoundsException` por laço com `<=` no limite.
- Dividir soma por constante em vez de `notas.length`.

## Padrão de feedback
Aprovar se os 3 cálculos estiverem corretos com o teste acima. Testar também com 1 nota para verificar edge case.

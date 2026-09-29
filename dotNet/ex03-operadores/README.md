# Exercício 3 — Operadores

## Conceito
Operadores aritméticos (`+`, `-`, `*`, `/`, `%`), de comparação (`==`, `!=`, `>`, `<`, `>=`, `<=`) e lógicos (`&&`, `||`, `!`).

## Enunciado
Com `a = 17` e `b = 5` (hardcoded), imprima:
1. `a + b`, `a - b`, `a * b`
2. `a / b` (divisão inteira) e `a % b` (resto)
3. `a / 5.0` (agora com double — compare o resultado com o item 2!)
4. O resultado de: `a > b`, `a == b`, `a != b`
5. O resultado de: `a > 10 && b < 10` e `a > 100 || b < 10`

## Saída esperada
```
17 + 5 = 22
17 - 5 = 12
17 * 5 = 85
17 / 5 = 3
17 % 5 = 2
17 / 5.0 = 3.4
a > b: True
a == b: False
a != b: True
a > 10 e b < 10: True
a > 100 ou b < 10: True
```

## Como executar
```bash
dotnet run
```

## Dica
`17 / 5` é `3` porque **int dividido por int é int** (trunca).
Aí está uma pegadinha clássica que aparece em cálculo de médias e percentuais.

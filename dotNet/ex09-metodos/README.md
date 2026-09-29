# Exercício 9 — Métodos

## Conceito
Métodos estáticos: parâmetros, `return`, valores padrão e sobrecarga.

## Enunciado
Crie uma mini-calculadora com métodos estáticos no `Program.cs`:

- `static double Somar(double a, double b)`
- `static double Subtrair(double a, double b)`
- `static double Multiplicar(double a, double b)`
- `static double Dividir(double a, double b)` — se o divisor for 0, retorne `double.NaN`.
- `static double Media(double a, double b, double c)` — média de 3 notas
- `static double Media(double a, double b)` — **sobrecarga** com 2 notas

No programa principal:
1. Leia dois números (TryParse).
2. Imprima todos os resultados chamando os métodos.
3. Imprima também a média dos dois números (sobrecarga de 2) e de 3 números fixos: 7, 8 e 9.

## Exemplo
```
Digite o primeiro número: 10
Digite o segundo número: 4
Soma: 14
Subtração: 6
Multiplicação: 40
Divisão: 2.5
Média (2 números): 7
Média (7, 8, 9): 8
```

## Como executar
```bash
dotnet run
```

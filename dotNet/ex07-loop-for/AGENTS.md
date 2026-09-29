# AGENTS.md — Correção do Exercício 7

## Critérios de correção
1. Tabuada do 7 completa (1 a 10) com o formato `7 x N = resultado`.
2. Soma de 1 a 100 calculada com `for` (resultado 5050) — não hardcoded, não fórmula fechada.
3. Contagem regressiva na mesma linha (`Console.Write`) seguida de `Lançar!`.

## Como verificar
```bash
dotnet run
```
Conferir: 10 linhas de tabuada (última `7 x 10 = 70`), soma 5050 e contagem em linha única.

## Erros comuns a apontar
- Laço `for (int i = 1; i <= 10; i++)` escrito com `< 10` (para no 9).
- Usar `Console.WriteLine` na contagem e quebrar em várias linhas.
- Acumulador declarado dentro do laço (zera a cada iteração).

## Padrão de feedback
Aprovar se os três blocos estiverem corretos. Como extra, propor: "e se a tabuada
fosse do número que o usuário digitar?" — prepara o terreno para métodos e reuso.

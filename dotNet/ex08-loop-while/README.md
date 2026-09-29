# Exercício 8 — Laços while e do-while

## Conceito
Laços condicionais: `while` (0 ou mais execuções) e `do-while` (1 ou mais execuções).

## Enunciado
Jogo de adivinhação:
1. Sorteie um número de 1 a 100 com `Random.Shared.Next(1, 101)`.
2. Enquanto o usuário não acertar: leia um palpite e diga `Maior`, `Menor` ou
   `Acertou em X tentativas!`.
3. Depois pergunte `Jogar de novo? (s/n)` e use `do-while` para repetir o jogo
   enquanto a resposta for `s`.

## Exemplo
```
Adivinhe o número (1-100): 50
Maior
Adivinhe: 75
Menor
Adivinhe: 60
Acertou em 3 tentativas!
Jogar de novo? (s/n) n
```

## Como executar
```bash
dotnet run
```

## Dica
`Next(1, 101)` — o valor máximo é **exclusivo**. `Next(1, 100)` sortearia até 99.
E lembrar: no `do-while`, o `while (condicao);` termina com ponto e vírgula.

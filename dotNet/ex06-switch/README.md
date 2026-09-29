# Exercício 6 — switch e switch expression

## Conceito
O clássico `switch` statement e a **switch expression** (C# 8+), mais concisa e moderna.

## Enunciado
1. Leia um número de 1 a 7 (dia da semana) e use um **switch statement** para
   imprimir o nome do dia (`1 = Domingo`, ..., `7 = Sábado`). Fora do intervalo → `Dia inválido`.
2. Leia um código de filme (`A`, `L`, `T`) e use uma **switch expression** para
   devolver a classificação:
   - `A` → `Adulto`
   - `L` → `Livre`
   - `T` → `Teen`
   - qualquer outro → `Código inválido`

## Exemplo
```
Digite o dia (1-7): 3
Terça
Digite o código do filme (A/L/T): T
Classificação: Teen
```

## Como executar
```bash
dotnet run
```

## Dica
Switch expression em uma linha:
```csharp
string classe = codigo switch
{
    "A" => "Adulto",
    "L" => "Livre",
    "T" => "Teen",
    _ => "Código inválido"
};
```
O `_` é o "default". E atenção: `switch` statement sem `break`/`return` não compila!

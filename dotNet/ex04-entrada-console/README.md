# Exercício 4 — Entrada de Dados com Console

## Conceito
Leitura de dados com `Console.ReadLine()` e conversão idiomática com `int.TryParse` / `double.TryParse`.

## Enunciado
Crie um programa `Perfil` que:
1. Peça o nome do usuário (`Console.ReadLine()`).
2. Peça a idade — use `int.TryParse`; se falhar, mostre `Idade inválida!` e encerre.
3. Peça a altura em metros com `double.TryParse` (aceite ponto como separador — dica abaixo).
4. Imprima: `Olá, <nome>! Você tem <idade> anos e <altura>m de altura.`

## Exemplo
```
Digite seu nome: Ana
Digite sua idade: 25
Digite sua altura: 1.65
Olá, Ana! Você tem 25 anos e 1.65m de altura.
```

## Como executar
```bash
dotnet run
```

## Dica
`Console.ReadLine()` **sempre** devolve `string?`. O jeito idiomático do C# é:
```csharp
if (!int.TryParse(Console.ReadLine(), out int idade))
{
    Console.WriteLine("Idade inválida!");
    return;
}
```
Para a altura aceitar `1.65` mesmo em sistema com vírgula:
`double.TryParse(Console.ReadLine(), CultureInfo.InvariantCulture, out double altura);`
(import `System.Globalization`.)

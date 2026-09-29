# Exercício 20 — Conversor de Moedas com Testes (xUnit + TDD)

## Conceito
Testes automatizados com **xUnit** (`dotnet test`), TDD (vermelho → verde → refatora)
e consumo de API HTTP com `HttpClient`.

## Enunciado
Um conversor de moedas testável:

1. `ConversorMoedas` — classe **pura** com `double Converter(double valor, string de, string para)`.
   Taxas fixas: `BRL→USD 0.19`, `USD→BRL 5.26`, `BRL→EUR 0.18`, `EUR→BRL 5.54`.
   - Moeda desconhecida → lança `ArgumentException` com mensagem clara.
   - Valor negativo → lança `ArgumentOutOfRangeException`.
2. `ServidorTaxasHttp` (extra) — busca taxas reais em `https://open.er-api.com/v6/latest/BRL`
   com `HttpClient` (cache de 1h opcional) e injeta as taxas no conversor.
3. Projeto de testes xUnit:
   - `Converter_BrlParaUsd_DeveRetornarValorCorreto` — 100 BRL → 19 USD.
   - `[Theory]` + `[InlineData]` com pelo menos 3 casos.
   - `Converter_MoedaInvalida_DeveLancarExcecao`.
   - `Converter_ValorNegativo_DeveLancarExcecao`.
4. Pratique **TDD**: escreva os testes antes da implementação — faça falhar (vermelho),
   depois passe (verde), depois melhore (refatora).

## Requisitos
- `dotnet test` verde com no mínimo 4 testes.
- Lógica de conversão separada da interface de console (a classe é testável sem input de usuário).
- Mensagens de erro das exceções dizem qual moeda/valor é o problema.

## Como executar
```bash
dotnet new console -n ConversorMoedas
dotnet new xunit -n ConversorMoedas.Tests
dotnet add ConversorMoedas.Tests reference ConversorMoedas
dotnet test
```

## Exemplo de teste
```csharp
[Fact]
public void Converter_BrlParaUsd_DeveRetornarValorCorreto()
{
    var conversor = new ConversorMoedas();
    var resultado = conversor.Converter(100, "BRL", "USD");
    Assert.Equal(19, resultado, precision: 2);
}
```

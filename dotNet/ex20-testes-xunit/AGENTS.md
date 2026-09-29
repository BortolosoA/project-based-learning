# AGENTS.md — Correção do Exercício 20 (xUnit + TDD)

## Critérios de correção
1. Solução com 2 projetos: console + testes, com referência correta entre eles.
2. Pelo menos 4 testes xUnit passando (`dotnet test` verde), incluindo `[Theory]`/`[InlineData]`.
3. Exceções para moeda inválida e valor negativo — e testes que provam isso (`Assert.Throws`).
4. `ConversorMoedas` é classe pura: sem `Console.ReadLine/WriteLine` dentro dela.

## Como verificar
```bash
dotnet test
# esperado: Passed! - total >= 4, Failed: 0
```
Verificações estáticas:
```bash
grep -n "Assert.Throws" ConversorMoedas.Tests/*.cs    # teste de exceção presente
grep -n "Theory\|InlineData" ConversorMoedas.Tests/*.cs # caso parametrizado presente
grep -n "Console.ReadLine" ConversorMoedas/ConversorMoedas.cs   # não deve existir
```

## Erros comuns a apontar
- Testar com `Assert.True(resultado == 19)` — ensinar `Assert.Equal(19, resultado, precision: 2)` (doubles!).
- `Assert.Throws` genérico demais (`Exception`) — testar o tipo específico.
- Comparar double com precisão zero e falhar por arredondamento (0.19 * 100).
- Lógica + interface misturadas (impossível testar sem digitar) — separar é o objetivo.
- Testes que dependem de internet (ServidorTaxasHttp) rodando no CI e falhando aleatoriamente — taxas fixas no teste.

## Padrão de feedback
Celebre o primeiro `dotnet test` verde — é o momento "TDD" da trilha. Verificar se o
aluno escreveu os testes antes (perguntar como foi o vermelho). Reforçar: de agora
em diante, todo exercício de API da trilha pode (e deve) ser testado.

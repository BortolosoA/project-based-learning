# Exercício 18 — Analisador de Vendas (LINQ e Lambdas)

## Conceito
Dominar o **LINQ** e as expressões **lambda**: `Where`, `Select`, `Sum`, `Average`,
`GroupBy`, `OrderByDescending`, `MaxBy` — e o tratamento de nullable types — para
transformar uma lista de dados em um relatório.

## Enunciado
Uma loja quer analisar suas vendas do mês. Você receberá uma lista fixa (hardcoded)
de vendas e deverá gerar um relatório completo no console — **sem usar nenhum
`for`/`foreach` tradicional nas análises**.

Classes esperadas:

- **`Venda`** — `Produto` (string), `Categoria` (string), `Valor` (double, preço unitário),
  `Quantidade` (int), `Data` (DateTime). O faturamento de uma venda é `Valor * Quantidade`.
- **`AnalisadorVendas`** — recebe uma `List<Venda>` e expõe métodos (todos com LINQ):
  - `double FaturamentoTotal()`
  - `Dictionary<string, double> FaturamentoPorCategoria()`
  - `Venda? VendaMaisValiosa()` (maior faturamento — retorna nullable!)
  - `string? ProdutoMaisVendido()` (maior soma de quantidades)
  - `double TicketMedio()` (faturamento total / número de vendas)
  - `List<Venda> VendasAcimaDe(double minimo)` — ordenadas da maior para a menor
- **`Program`** — monta uma lista com pelo menos 10 vendas de 3+ categorias
  (dados hardcoded) e imprime o relatório formatado.

## Requisitos
1. Todas as análises com LINQ/lambdas — proibido `for`/`foreach` nos métodos do `AnalisadorVendas`.
2. `FaturamentoPorCategoria` usa `GroupBy` (ou `ToLookup`) + `Sum` — não monta o dicionário na mão.
3. `VendaMaisValiosa` retorna `Venda?` e o `Program` trata o null com `?.` ou `?? "..."`.
4. Usar `OrderByDescending` com lambda na lista de vendas acima do corte.
5. Ticket médio usa o **faturamento** (valor × quantidade), não só o valor unitário.
6. `ProdutoMaisVendido` soma as quantidades por produto (GroupBy), não pega a venda isolada com mais unidades.

## Exemplo de fluxo
```
=== Relatório de Vendas ===
Faturamento total: R$ 12.480,50
Ticket médio: R$ 1.248,05

Faturamento por categoria:
  Eletrônicos: R$ 7.900,00
  Livros:      R$ 1.580,50
  Cozinha:     R$ 3.000,00

Venda mais valiosa: Notebook Dell (R$ 4.500,00)
Produto mais vendido (unidades): Caneca Térmica (37 un.)

Top vendas acima de R$ 1.000,00:
  1. Notebook Dell — R$ 4.500,00
  2. Fone Bluetooth — R$ 1.800,00
  3. Panela de Pressão — R$ 1.200,00
```

## Como executar
```bash
dotnet run
```

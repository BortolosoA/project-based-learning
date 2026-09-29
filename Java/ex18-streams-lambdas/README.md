# Exercício 18 — Analisador de Vendas

## Conceito
Dominar a API de **Streams** e expressões **lambda** do Java: `filter`, `map`, `reduce`, `sorted`, `Collectors.groupingBy`, `Optional` e *method references* para transformar uma lista de dados em um relatório.

## Enunciado
Uma loja quer analisar suas vendas do mês. Você receberá uma lista fixa (hardcoded) de vendas e deverá gerar um relatório completo no console — **sem usar nenhum `for`/`while` tradicional nas análises**.

Classes esperadas:

- **`Venda`** — atributos: `produto` (String), `categoria` (String), `valor` (double, preço unitário), `quantidade` (int), `data` (`java.time.LocalDate`). Com construtor, getters e `toString`. O faturamento de uma venda é `valor * quantidade`.
- **`AnalisadorVendas`** — recebe um `List<Venda>` e expõe métodos de análise (todos com Streams):
  - `double faturamentoTotal()`
  - `Map<String, Double> faturamentoPorCategoria()`
  - `Optional<Venda> vendaMaisValiosa()` (maior faturamento unitário)
  - `String produtoMaisVendido()` (maior soma de quantidades)
  - `double ticketMedio()` (faturamento total / número de vendas)
  - `List<Venda> vendasAcimaDe(double faturamentoMinimo)` — ordenadas da maior para a menor
- **`Main`** — monta uma lista com pelo menos 10 vendas de 3+ categorias diferentes (dados hardcoded) e imprime o relatório formatado.

## Requisitos
1. Todas as análises devem usar Streams/lambdas — proibido `for`/`while` nos métodos do `AnalisadorVendas`.
2. Usar `Collectors.groupingBy` com downstream (ex.: `summingDouble`) no faturamento por categoria.
3. Usar `Optional` no retorno de `vendaMaisValiosa` e tratá-lo corretamente no `Main` (ex.: `ifPresent` ou `orElse`), nunca `get()` sem verificação.
4. Usar pelo menos **um method reference** (ex.: `Venda::getProduto`, `System.out::println`).
5. Ordenação com `sorted(Comparator.comparing(...).reversed())` ou equivalente.
6. Relatório deve mostrar: faturamento total, faturamento por categoria, venda mais valiosa, produto mais vendido, ticket médio (2 casas decimais) e top vendas acima de um corte definido no código.
7. Código deve compilar com Java 11+ sem dependências externas.

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
javac *.java
java Main
```

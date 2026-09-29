# Exercício 17 — Sistema de Pagamentos (Interfaces, Abstração e Generics)

## Conceito
Interfaces (`interface`), classes abstratas (`abstract`), generics (`<T>`) e polimorfismo —
a base para entender os frameworks que virão nas próximas fases.

## Enunciado
Um sistema de processamento de pagamentos:

- `IPagamento` (interface): `void Pagar(double valor)` e `string Descricao { get; }`.
- `PagamentoCartao` — implementa `IPagamento`: paga em 1x e imprime log com valor.
- `PagamentoPix` — implementa `IPagamento`: imprime um "código copia e cola" fake
  (use `Guid.NewGuid()`).
- `PagamentoBase` (classe **abstrata**) — com propriedade `Valor` e método
  `void Registrar(string mensagem)` que imprime `[LOG] mensagem`; as classes concretas
  herdam dela **e** implementam `IPagamento`.
- `RepositorioPagamentos<T>` (classe **genérica**) — com `Adicionar(T item)`,
  `List<T> ObterTodos()` e `int Total { get; }` — funciona com qualquer tipo.
- `ProcessadorPagamentos` — recebe uma `List<IPagamento>` e chama `Pagar()` de todos
  num `foreach` (**polimorfismo**: não conhece as classes concretas).

No `Program`:
1. Monte uma lista com um Pix e um cartão e processe todos.
2. Guarde os pagamentos num `RepositorioPagamentos<IPagamento>` e prove que o generic
   funciona, listando o total guardado.

## Exemplo
```
[Pix] Pago R$ 250 — código copia e cola: f3b5...e0fk
[Cartão] Pago R$ 100 em 1x
Pagamentos processados: 2
Total no repositório: 2
```

## Como executar
```bash
dotnet run
```

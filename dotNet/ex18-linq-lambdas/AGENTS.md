# AGENTS.md — Correção do Exercício 18 (LINQ e Lambdas)

## Critérios de correção
1. Classe `Venda` tem os 5 campos pedidos, incluindo `DateTime`, e um jeito de calcular o faturamento da venda.
2. Nenhum `for`/`foreach` dentro do `AnalisadorVendas` — apenas LINQ.
3. `FaturamentoPorCategoria` usa `GroupBy` + `Sum` (ou `ToDictionary` a partir de agrupamento), não dicionário montado na mão.
4. `VendaMaisValiosa` retorna nullable (`Venda?`) e o `Program` trata com `?.` / `??` — nunca assume que existe.
5. Há `OrderByDescending`, `Where`, `Sum`/`Average` e `Select` em uso.
6. `ProdutoMaisVendido` soma quantidades por produto (GroupBy), não pega a venda com maior `Quantidade` isolada — aceitar a segunda se justificar, mas apontar a diferença.
7. Relatório contém todos os itens e valores matematicamente corretos (recalcular o faturamento total à mão a partir dos dados hardcoded).
8. Ticket médio usa faturamento (valor×quantidade), não valor unitário.

## Como verificar
```bash
dotnet run
```
Verificações estáticas (nos arquivos do aluno):
```bash
grep -nE "for\s*\(|foreach" AnalisadorVendas.cs   # não deve retornar nada
grep -n "GroupBy" *.cs                            # agrupamento presente
grep -n "??\|\?\." Program.cs                     # tratamento de null presente
grep -n "OrderByDescending" *.cs                  # ordenação LINQ presente
```

## Erros comuns a apontar
- LINQ "falso": `foreach` com acumulador externo em vez de `Sum()`.
- Esquecer `.ToList()` e iterar a consulta várias vezes (execução deferida — cada `foreach` refaz o cálculo).
- Não tratar null de `VendaMaisValiosa` / `ProdutoMaisVendido` quando a lista é vazia.
- `GroupBy` seguido de um segundo loop para somar — dá para fazer em uma passada (`g.Sum(...)`).
- Imprimir o Dictionary direto (`[Eletrônicos, 7900]`) em vez de formatar o relatório.

## Padrão de feedback
Reconhecer que LINQ é o maior salto de paradigma da trilha até aqui — se o aluno
usou `GroupBy` corretamente, celebrar explicitamente. Corrigir mostrando o
antes/depois em 3-4 linhas ("este foreach com acumulador vira este `Sum(v => v.Valor * v.Quantidade)`").
Encerrar com desafio: "adicione um método que retorna a categoria campeã usando
`MaxBy` sobre o dicionário".

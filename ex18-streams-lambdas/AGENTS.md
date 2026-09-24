# AGENTS.md — Correção do Exercício 18 (Streams e Lambdas)

## Critérios de correção
1. Compila com `javac *.java` (Java 11+) sem dependências externas.
2. Classe `Venda` tem os 5 atributos pedidos, incluindo `LocalDate`, e modo de calcular faturamento da venda.
3. Nenhum `for`/`while` tradicional dentro de `AnalisadorVendas` — apenas streams (no máximo `forEach` terminal em impressão).
4. `faturamentoPorCategoria` usa `Collectors.groupingBy` com downstream de soma (`summingDouble`/`reducing`), não mapa montado na mão.
5. `vendaMaisValiosa` retorna `Optional<Venda>` e o `Main` trata sem `get()` direto.
6. Há pelo menos um method reference (`Classe::metodo`) no código.
7. Há uso de `sorted` com `Comparator`, de `filter` com predicado e de agregação via `reduce`/`*ingDouble`/`average`.
8. `produtoMaisVendido` soma quantidades por produto (groupingBy), não simplesmente pega a venda com maior `quantidade` isolada — aceitar as duas se o README do aluno justificar, mas apontar a diferença.
9. Relatório impresso contém todos os itens exigidos e valores matematicamente corretos (conferir o faturamento total somando os dados).
10. Ticket médio usa o faturamento (valor×quantidade), não só o valor unitário.

## Como verificar
```bash
javac *.java
java Main
```
Verificações estáticas:
```bash
grep -n "for\s*(\|while\s*(" AnalisadorVendas.java     # não deve retornar nada
grep -n "groupingBy" *.java                            # agrupamento presente
grep -n "::" *.java                                    # method reference presente
grep -n "Optional" *.java                              # Optional em uso
grep -n "\.get()" Main.java AnalisadorVendas.java      # get() sem verificação: apontar
```
Recalcular à mão (ou com calculadora) o faturamento total a partir dos dados hardcoded no `Main` e comparar com a saída.

## Erros comuns a apontar
- Usar stream só para esconder um `forEach` com acumulador externo (variável mutável capturada) em vez de `reduce`/collect.
- `Optional.get()` sem `isPresent`/`orElse`.
- Confundir `map` com `mapToDouble` e quebrar a soma.
- `groupingBy` sem downstream e depois um segundo loop para somar — dá para fazer em uma passada.
- Imprimir o Map direto (`{A=1.0, B=2.0}`) em vez de formatar o relatório.
- Usar `parallelStream()` sem necessidade.

## Padrão de feedback
Reconheça que streams é o maior salto de paradigma da trilha até aqui — se o aluno usou `groupingBy` com downstream corretamente, celebre explicitamente. Corrija mostrando o antes/depois em 3-4 linhas ("este forEach com acumulador vira este `mapToDouble(...).sum()`"), no máximo 2-3 pontos. Encerre com um desafio incremental: "adicione um método que retorna a categoria campeã de faturamento usando `max(Comparator.comparing(...))` sobre o `Map`".

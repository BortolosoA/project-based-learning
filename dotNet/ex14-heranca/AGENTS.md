# AGENTS.md — Correção do Exercício 14

## Critérios de correção
1. `Funcionario` é a base com método `virtual`; `Gerente` e `Desenvolvedor` usam `override`.
2. `List<Funcionario>` contém os três tipos e o `foreach` chama a versão correta de cada um.
3. Cálculos corretos: gerente +20%, dev +10% por projeto.
4. Total da folha bate com a soma dos salários calculados.

## Como verificar
```bash
dotnet run
```
Com os dados do exemplo: 6000 + 5500 + 3000 = 14500.
Se o aluno usou outros números, recalcular à mão.

## Erros comuns a apontar
- Método `new` em vez de `override` (esconde, não substitui — polimorfismo quebra silenciosamente).
- Esquecer `base` no construtor das filhas (dados do pai ficam zerados).
- `List<Gerente>` + `List<Desenvolvedor>` separadas em vez de uma lista polimórfica — funciona, mas perde o objetivo do exercício.
- `virtual` faltando na base (erro CS0506 ao tentar `override`).

## Padrão de feedback
Pedir o experimento do `override` removido: se o aluno souber explicar por que o
resultado mudou, o polimorfismo foi entendido. Aprovar com o total da folha correto.

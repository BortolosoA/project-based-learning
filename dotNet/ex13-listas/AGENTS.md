# AGENTS.md — Correção do Exercício 13

## Critérios de correção
1. Menu funciona em laço e as 3 operações + saída estão implementadas.
2. Nome duplicado é bloqueado com `Contains`.
3. Listagem ordenada (`Sort`) e numerada; remoção trata nome inexistente.
4. Total de convidados impresso ao sair.

## Como verificar
```bash
printf "1\nAna\n1\nAna\n1\nBruno\n2\n3\nAna\n2\n0\n" | dotnet run
```
Fluxo: adiciona Ana, tenta repetir (erro), adiciona Bruno, lista, remove Ana, lista, sai.

## Erros comuns a apontar
- `Remove` sem verificar o retorno (bool) — remover inexistente fica mudo.
- `Contains` case sensitive demais ("ana" != "Ana") — aceitar, mas mencionar `StringComparison` como extra.
- Modificar a lista dentro do `foreach` (exceção `InvalidOperationException`) — remover depois do laço.
- Esquecer o `Sort()` (ou ordenar a lista original sem querer, tudo bem aqui).

## Padrão de feedback
Aprovar se o fluxo completo rodar. Desafio: mudar de `List<string>` para
`List<Convidado>` (classe com nome e idade) — prepara o ex15.

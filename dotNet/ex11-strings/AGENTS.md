# AGENTS.md — Correção do Exercício 11

## Critérios de correção
1. Os 6 itens do enunciado funcionam com a frase de exemplo (incluindo espaços extras).
2. Trim é usado antes de contar caracteres.
3. Contagem de palavras não explode com espaços duplicados (RemoveEmptyEntries).

## Como verificar
```bash
printf "  Estou aprendendo python agora  \n" | dotnet run
```
Esperado: 30 caracteres, 4 palavras, contém "c#" False, substituição e 3 primeiras palavras corretas.

## Erros comuns a apontar
- Chamar `Replace` e ignorar o retorno (imutabilidade!): `frase.Replace(...)` sozinho não altera nada.
- `Contains` com case sensitive — usar `frase.Contains("c#", StringComparison.OrdinalIgnoreCase)`.
- `Split(' ')` com espaços duplicados gerando strings vazias na contagem.
- `Substring` estourando (`ArgumentOutOfRangeException`) quando a frase tem menos de N caracteres — apontar como melhoria.

## Padrão de feedback
Aprovar se os 6 itens funcionarem. A imutabilidade é o conceito-chave: pedir ao
aluno para explicar por que precisou guardar o retorno do `Replace`.

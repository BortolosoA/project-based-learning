# AGENTS.md — Correção do Exercício 3

## Critérios de correção
1. Os 5 primeiros cálculos (soma, subtração, multiplicação, divisão inteira e resto) corretos.
2. Divisão inteira resulta `3` e divisão com double resulta `3.4` — o aluno precisa mostrar as duas.
3. Comparações e expressões lógicas imprimem `True`/`False` corretamente.

## Como verificar
```bash
dotnet run
```
Resultados esperados: 22, 12, 85, 3, 2, 3.4, True, False, True, True, True.

## Erros comuns a apontar
- `17 / 5` dando 3.4 — o aluno misturou tipos; reforçar int/int = int.
- Trocar `&&` por `&` — funciona mas sempre avalia os dois lados; apontar como curiosidade, não erro.
- Imprimir texto em vez do resultado booleano (ex.: escrever "verdadeiro" hardcoded).

## Padrão de feedback
A divisão inteira é o teste principal deste exercício. Se acertou, elogiar; se não,
explicar a regra de tipos e pedir para rodar de novo com `5.0`.

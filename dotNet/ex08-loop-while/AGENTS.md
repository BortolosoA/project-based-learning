# AGENTS.md — Correção do Exercício 8

## Critérios de correção
1. Sorteio com `Random.Shared` (ou instância de `Random`) no intervalo 1–100.
2. Laço principal (`while`) dá as dicas `Maior`/`Menor` e conta as tentativas.
3. `do-while` repete o jogo enquanto a resposta for `s` (aceitar maiúscula `S`).

## Como verificar
```bash
printf "50\n75\n60\nn\n" | dotnet run
```
Saída deve conter dicas coerentes com o número sorteado e a contagem de tentativas.
Rodar mais de uma vez para ver o número mudar.

## Erros comuns a apontar
- `Next(1, 100)` — nunca sorteia 100 (limite exclusivo).
- Laço infinito quando a entrada falha e `ReadLine` retorna null/vazio — sugerir TryParse + valor sentinela.
- Usar `while` onde o jogo precisa rodar ao menos uma vez (ok tecnicamente, mas apontar o `do-while` como mais idiomático aqui).
- Comparar resposta com `== "s"` sem tratar `S` maiúsculo.

## Padrão de feedback
Aprovar se der para jogar uma partida completa e sair. Desafio incremental:
limitar o número de tentativas a 7 (aí o laço precisa de mais uma condição de parada).

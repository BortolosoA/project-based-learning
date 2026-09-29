# AGENTS.md — Correção do Exercício 9

## Critérios de correção
1. Arquivo `Utilidades.java` compila.
2. Os três métodos existem com assinaturas coerentes: `soma(int,int)` retorna int, `ehPar(int)` retorna boolean, `maior(int,int,int)` retorna int.
3. O `main` chama os três métodos e usa seus retornos (não faz tudo inline).

## Como verificar
```bash
javac Utilidades.java && java Utilidades
```
Conferir os valores impressos de acordo com os argumentos usados no código.

## Erros comuns a apontar
- Métodos sem `static` sendo chamados do `main`.
- `ehPar` escrito com if/else verboso (`if (n%2==0) return true; else return false;`) — mostrar que dá pra retornar a expressão diretamente (observação de estilo, não reprovação).
- Método com `void` imprimindo em vez de retornar (fogem do enunciado, mas aceitar com observação se a lógica estiver correta).

## Padrão de feedback
Aprovar se os 3 métodos funcionam e são usados no main. Dar dicas de estilo como comentário extra.

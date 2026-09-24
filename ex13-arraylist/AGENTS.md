# AGENTS.md — Correção do Exercício 13

## Critérios de correção
1. Arquivo `ListaCompras.java` compila e usa `ArrayList<String>`.
2. As 4 opções do menu funcionam; o laço principal só termina com a opção 0.
3. Remoção informa quando o item não existe; listagem mostra numeração ou "Lista vazia".

## Como verificar
```bash
javac ListaCompras.java && printf "1\nArroz\n1\nFeijao\n3\n2\nArroz\n3\n2\nArroz\n0\n" | java ListaCompras
```
Fluxo: adiciona 2 itens, lista, remove Arroz, lista (só Feijao), tenta remover Arroz de novo (não encontrado), sai.

## Erros comuns a apontar
- Problema clássico: `nextInt()` seguido de `nextLine()` — o ENTER pendente faz o `nextLine` ler uma linha vazia. Apontar a solução (`scanner.nextLine()` extra após `nextInt`).
- Usar `for-each` para remover enquanto itera (ConcurrentModificationException).

## Padrão de feedback
Testar o fluxo acima. O bug do nextInt/nextLine é o ponto pedagógico central — explicá-lo bem se ocorrer.

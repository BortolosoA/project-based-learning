# AGENTS.md — Correção do Exercício 15 (Projeto Final)

## Critérios de correção
1. Três arquivos (`Livro`, `Biblioteca`, `Main`) compilam com `javac *.java`.
2. Separação de responsabilidades: `Livro` só trata do livro; `Biblioteca` gerencia a lista; `Main` só cuida do menu/entrada.
3. Todas as 5 operações do menu funcionam, incluindo tratamento de erros (livro inexistente, já emprestado).
4. Código usa encapsulamento (atributos privados + getters/métodos).

## Como verificar
```bash
javac *.java && printf "1\nDom Casmurro\nMachado de Assis\n2\n3\nDom Casmurro\n2\n3\nDom Casmurro\n4\nDom Casmurro\n5\nDom Casmurro\n0\n" | java Main
```
Fluxo: adiciona, lista, empresta, lista (vazio), tenta emprestar de novo (erro), devolve, busca, sai.

## Erros comuns a apontar
- Comparação de Strings com `==` em vez de `equals`.
- Lógica de negócio dentro do Main em vez da classe Biblioteca.
- Não validar se o livro já está emprestado.
- Remover livro da lista ao emprestar (ele deve permanecer, só mudar o estado).

## Padrão de feedback
Este é o exercício de consolidação. Além de corrigir bugs, avalie a organização geral do código e dê um resumo final do progresso do aluno nos 15 exercícios. Seja encorajador: completar este projeto significa dominar o básico de Java e POO.

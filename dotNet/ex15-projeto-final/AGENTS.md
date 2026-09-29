# AGENTS.md — Correção do Exercício 15 (Projeto Final)

## Critérios de correção
1. Três arquivos (`Livro.cs`, `Biblioteca.cs`, `Program.cs`) e o projeto roda com `dotnet run`.
2. Separação de responsabilidades: `Livro` só trata do livro; `Biblioteca` gerencia a lista; `Program` só cuida do menu/entrada.
3. As 5 operações do menu funcionam, incluindo tratamento de erros (livro inexistente, já emprestado).
4. Código usa encapsulamento (propriedades com `private set` onde fizer sentido).
5. Busca por título ignora maiúsculas/minúsculas.

## Como verificar
```bash
printf "1\nDom Casmurro\nMachado de Assis\n2\n3\nDom Casmurro\n2\n3\nDom Casmurro\n4\nDom Casmurro\n5\ndom casmurro\n0\n" | dotnet run
```
Fluxo: adiciona, lista, empresta, lista (vazio), tenta emprestar de novo (erro), devolve, busca com caixa diferente, sai.

## Erros comuns a apontar
- Lógica de negócio dentro do `Program` em vez da classe `Biblioteca`.
- Não validar se o livro já está emprestado.
- Remover o livro da lista ao emprestar (ele deve permanecer, só muda de estado).
- Comparação de títulos case sensitive (o teste busca `dom casmurro` em minúsculas de propósito).

## Padrão de feedback
Este é o exercício de consolidação da fase 1. Além de corrigir bugs, avalie a
organização geral do código e dê um resumo do progresso do aluno nos 15 exercícios.
Seja encorajador: completar este projeto significa dominar o básico de C# e POO.

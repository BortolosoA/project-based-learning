# Exercício 15 — Projeto Final: Sistema de Biblioteca

## Conceito
Colocando tudo junto: classes, herança/composição, coleções, métodos, laços, condicionais, entrada de dados e organização em vários arquivos.

## Enunciado
Crie um sistema de biblioteca simples com os arquivos:

### `Livro.cs`
- Propriedades: `Titulo`, `Autor`, `Emprestado` (bool).
- Construtor e métodos `Emprestar()` e `Devolver()`.

### `Biblioteca.cs`
- Contém uma `List<Livro>`.
- Métodos:
  - `void AdicionarLivro(Livro livro)`
  - `void ListarDisponiveis()` — mostra só os não emprestados (ou `Nenhum livro disponível`).
  - `void EmprestarLivro(string titulo)` — marca como emprestado se existir e estiver disponível; senão, mensagem de erro.
  - `void DevolverLivro(string titulo)` — marca como disponível.
  - `void BuscarPorTitulo(string titulo)` — imprime os dados do livro ou `Livro não encontrado`.

### `Program.cs`
Menu interativo:
```
1 - Adicionar livro
2 - Listar livros disponíveis
3 - Emprestar livro
4 - Devolver livro
5 - Buscar livro
0 - Sair
```

## Requisitos
- Trate os casos de erro (livro inexistente, já emprestado etc.).
- Compare títulos **sem diferenciar maiúsculas** (`StringComparison.OrdinalIgnoreCase`).
- Use ao menos um laço, condicionais e métodos em cada classe principal.
- Separação de responsabilidades: `Livro` só sabe de si; `Biblioteca` gerencia a
  lista; `Program` só cuida do menu/entrada.

## Exemplo de fluxo
```
> Adicionar "Dom Casmurro" de Machado de Assis
> Listar: Dom Casmurro - Machado de Assis
> Emprestar "Dom Casmurro"
> Listar: Nenhum livro disponível
> Devolver "Dom Casmurro"
> Listar: Dom Casmurro - Machado de Assis
```

## Como executar
```bash
dotnet run
```

# Exercício 15 — Projeto Final: Sistema de Biblioteca

## Conceito
Colocando tudo junto: classes, herança/composição, coleções, métodos, laços, condicionais, entrada de dados e organização em vários arquivos.

## Introdução ao tema

Aqui não há conceito novo — o desafio é **organização**: transformar tudo o
que você aprendeu num sistema com várias classes trabalhando juntas. A
habilidade que separa iniciante de dev é a **separação de responsabilidades**:
cada classe faz uma coisa só, e as demais apenas *pedem* a ela. Menos "copia e
cola", mais desenho de arquitetura.

## Exemplo simples

```java
// Esboço da arquitetura — cada classe com um único papel:

class Livro {
    // guarda título, autor, status (emprestado ou não)
}

class Biblioteca {
    // DETÉM a List<Livro> e TODA a regra de negócio:
    // adicionar, emprestar (valida disponibilidade), devolver, buscar
}

class Main {
    // SÓ interage com o usuário: menu, entrada, impressão
    // e DELEGA o trabalho para a Biblioteca
}
```

Teste mental: "se eu trocar o console por uma interface web, o que muda?"
Resposta certa: só o `Main`.

## Enunciado
Crie um sistema de biblioteca simples com os arquivos:

### `Livro.java`
- Atributos: `titulo`, `autor`, `emprestado` (boolean).
- Construtor, getters e métodos `emprestar()` e `devolver()`.

### `Biblioteca.java`
- Contém um `ArrayList<Livro>`.
- Métodos:
  - `adicionarLivro(Livro livro)`
  - `listarDisponiveis()` — mostra só os não emprestados.
  - `emprestarLivro(String titulo)` — marca como emprestado se existir e estiver disponível; senão, mensagem de erro.
  - `devolverLivro(String titulo)` — marca como disponível.
  - `buscarPorTitulo(String titulo)` — imprime os dados do livro ou `Livro não encontrado`.

### `Main.java`
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
- O programa só deve rodar após compilar **todas** as classes juntas.
- Trate os casos de erro (livro inexistente, já emprestado etc.).
- Use ao menos um laço, condicionais e métodos em cada classe principal.

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
javac *.java
java Main
```

# AGENTS.md — Correção do Exercício 1

## Critérios de correção
1. Existe um projeto console válido (`.csproj` com `net8.0`) e o código está no `Program.cs`.
2. O programa imprime `Olá, Mundo!` e o nome do aluno, em duas linhas.

## Como verificar
```bash
dotnet run
```
A saída deve ter **duas linhas**: a mensagem e o nome.

## Erros comuns a apontar
- Esquecer o ponto e vírgula no fim do `Console.WriteLine`.
- Escrever `console.writeline` em minúsculas — C# diferencia maiúsculas de minúsculas.
- Aspas "inteligentes" (`"”`) coladas de editor de texto rico — o compilador rejeita; deve ser aspas retas (`"`).
- Declarar um método `Main` junto com top-level statements (erro CS0017) — escolher um dos dois.

## Padrão de feedback
- Se compilar e imprimir corretamente: aprovar e parabenizar.
- Caso contrário: explicar o erro de forma didática e apontar a linha do problema. Não reescrever o código inteiro pelo aluno.

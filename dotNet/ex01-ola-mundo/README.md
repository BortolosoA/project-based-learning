# Exercício 1 — Olá, Mundo!

## Conceito
Estrutura básica de um programa C#: projeto console, `Program.cs` e `Console.WriteLine`.

## Enunciado
1. Crie um projeto de console:
   ```bash
   dotnet new console -n OlaMundo
   ```
2. Edite o `Program.cs` para imprimir na tela: `Olá, Mundo!`
3. Na linha seguinte, imprima seu nome.

## Saída esperada
```
Olá, Mundo!
Seu Nome
```

## Como executar
```bash
dotnet new console -n OlaMundo
cd OlaMundo
# edite o Program.cs
dotnet run
```

## Dica
No .NET 6 ou superior, o `Program.cs` **não precisa** de classe nem método `Main` —
as *top-level statements* já são o corpo do programa. Basta escrever os
`Console.WriteLine` diretamente no arquivo.

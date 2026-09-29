# Exercício 2 — Variáveis e Tipos

## Conceito
Tipos básicos do C#: `int`, `double`, `string`, `bool`, `char`, inferência com `var` e `const`.

## Enunciado
Crie um programa (`dotnet new console -n Perfil`) que:
1. Declare uma variável `nome` (string), `idade` (int), `altura` (double) e `estudante` (bool), com seus dados.
2. Imprima a ficha usando **interpolação de string** (`$"..."`).

## Saída esperada
```
=== Ficha do Aluno ===
Nome: Ana
Idade: 25 anos
Altura: 1.65m
Estudante: True
```

## Extra (não obrigatório, mas recomendado)
- Troque `string nome = "Ana";` por `var nome = "Ana";` e confirme que funciona igual.
  Você sabe dizer qual é o tipo real de `nome`?
- Crie uma `const int IdadeMaxima = 130;` e tente alterá-la (`IdadeMaxima = 131;`).
  Leia a mensagem de erro do compilador — ela explica por que `const` não pode mudar.

## Como executar
```bash
dotnet run
```

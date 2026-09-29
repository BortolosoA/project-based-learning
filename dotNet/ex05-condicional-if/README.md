# Exercício 5 — Condicionais com if/else

## Conceito
`if`, `else if`, `else` e operadores de comparação e lógicos na prática.

## Enunciado
Crie um programa que:
1. Leia uma idade e classifique:
   - `0–12` → `Criança`
   - `13–17` → `Adolescente`
   - `18–59` → `Adulto`
   - `60+` → `Idoso`
   - Negativa ou acima de 130 → `Idade inválida`
2. Leia uma nota de 0 a 10 e informe a situação:
   - `>= 7` → `Aprovado`
   - `>= 5` → `Recuperação`
   - `< 5` → `Reprovado`
   - Fora de 0–10 → `Nota inválida`

## Exemplo
```
Digite a idade: 15
Adolescente
Digite a nota: 8.5
Aprovado
```

## Como executar
```bash
dotnet run
```

## Dica
Cuidado com a ordem dos `if`: se testar `idade >= 0` antes de tudo, os casos
negativos já estão resolvidos. Pensar na ordem é parte da lógica.

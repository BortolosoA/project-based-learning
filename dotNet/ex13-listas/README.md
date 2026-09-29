# Exercício 13 — Listas (List<T>)

## Conceito
A coleção mais usada do C#: `List<T>` — `Add`, `Remove`, `Contains`, `Count`, `Sort` e indexador.

## Enunciado
Um gerenciador de convidados de festa. Crie um menu interativo:
```
1 - Adicionar convidado
2 - Listar convidados
3 - Remover convidado
0 - Sair
```
Regras:
- Não deixar adicionar nome repetido (use `Contains`) — avise `Convidado já existe!`.
- Listar sempre em ordem alfabética (`Sort()` antes de listar) com numeração.
- Remover por nome exato; se não existir, avise.
- Ao sair, mostre o total de convidados confirmados.

## Exemplo
```
> 1, Ana
> 1, Ana
Convidado já existe!
> 1, Bruno
> 2
1. Ana
2. Bruno
```

## Como executar
```bash
dotnet run
```

## Dica
`List<string>` cresce sozinha — diferente do array do ex10, você não precisa
saber o tamanho antes. Compare: `convidados.Add(...)` vs `array[i] = ...`.

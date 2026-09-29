# Exercício 11 — Strings

## Conceito
Métodos de string (`ToUpper`, `ToLower`, `Trim`, `Contains`, `Substring`, `Split`, `Replace`, `Length`), interpolação e imutabilidade.

## Enunciado
Leia uma frase (podendo ter espaços extras nas pontas) e:
1. Imprima quantos caracteres ela tem **depois de remover espaços das pontas** (`Trim`).
2. Imprima em MAIÚSCULAS e em minúsculas.
3. Conte quantas palavras existem (use `Split(' ')` — cuidado com espaços duplicados; dica: `Split(' ', StringSplitOptions.RemoveEmptyEntries)`).
4. Verifique se contém a palavra `c#` (ignorando maiúsculas).
5. Substitua `python` por `c#` na frase, se houver.
6. Imprima só as 3 primeiras palavras da frase, usando `Substring` **ou** range (`frase[..n]`).

## Exemplo
```
Digite uma frase:   Estou aprendendo python agora
Caracteres (sem espaços extras): 30
Maiúsculas: ESTOU APRENDENDO PYTHON AGORA
Minúsculas: estou aprendendo python agora
Palavras: 4
Contém "c#"? False
Substituindo python por c#: Estou aprendendo c# agora
3 primeiras palavras: Estou aprendendo c#
```

## Como executar
```bash
dotnet run
```

## Dica
Strings em C# são **imutáveis**: `frase.Replace(...)` retorna uma *nova* string;
a original nunca muda. `var nova = frase.Replace("python", "c#");`

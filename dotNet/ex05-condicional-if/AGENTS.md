# AGENTS.md — Correção do Exercício 5

## Critérios de correção
1. Classificação de idade cobre todos os intervalos, incluindo inválidos (negativa e > 130).
2. Situação da nota cobre aprovado/recuperação/reprovado e nota fora de 0–10.
3. Entradas inválidas (texto) não derrubam o programa (reusar TryParse do ex04).

## Como verificar
```bash
printf "15\n8.5\n" | dotnet run   # Adolescente / Aprovado
printf "-1\n3\n" | dotnet run     # Idade inválida / Reprovado
printf "70\n5\n" | dotnet run     # Idoso / Recuperação
```

## Erros comuns a apontar
- Esquecer algum intervalo limite (12 vs 13, 59 vs 60, exatamente 7 ou 5).
- Ordem dos `if` que faz um caso "engolir" outro (ex.: `>= 13` antes de tratar `<= 12`).
- Comparar double com `==` — funciona aqui, mas comentar sobre precisão se surgirem dúvidas.

## Padrão de feedback
Testar os limites (12, 13, 17, 18, 59, 60, exatamente 5 e 7). Aprovar se todos
baterem; caso contrário, apontar qual limite falhou e pedir para rastrear a lógica.

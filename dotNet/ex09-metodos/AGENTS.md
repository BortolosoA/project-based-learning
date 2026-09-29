# AGENTS.md — Correção do Exercício 9

## Critérios de correção
1. Os 6 métodos existem com as assinaturas pedidas (`static` + tipos corretos).
2. `Dividir` trata divisor 0 retornando `NaN` (não lança, não quebra).
3. As duas `Media` coexistem como sobrecarga (mesmo nome, parâmetros diferentes).
4. O programa chama os métodos — a lógica não está inline no corpo principal.

## Como verificar
```bash
printf "10\n4\n" | dotnet run
# Soma: 14, Subtração: 6, Multiplicação: 40, Divisão: 2.5, Média (2): 7
printf "10\n0\n" | dotnet run
# Divisão: NaN (ou mensagem explicando)
```

## Erros comuns a apontar
- Calcular a divisão direto no `Main` em vez de chamar o método.
- Sobrecarga com nomes diferentes (`Media2`) — sobrecarga é mesmo nome.
- `return` faltando em algum caminho do método (erro CS0161).
- Divisão por zero com `int` (gera exceção) em vez de `double` (gera NaN/Infinity).

## Padrão de feedback
Aprovar se as operações vierem de métodos e as sobrecargas funcionarem.
Desafio: adicionar `static double Somar(params double[] valores)` e perguntar
por que agora `Somar(1, 2, 3)` também compila.

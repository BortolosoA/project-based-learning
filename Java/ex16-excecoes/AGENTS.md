# AGENTS.md — Correção do Exercício 16 (Exceções)

## Critérios de correção
1. Compila com `javac *.java` sem erros nem warnings graves.
2. Existe `SaldoInsuficienteException` estendendo `Exception` (checked) — verificar o `extends` no código.
3. Existe `ValorInvalidoException` estendendo `IllegalArgumentException` (unchecked).
4. Métodos `sacar` e `transferir` declaram `throws SaldoInsuficienteException` e usam `throw` corretamente.
5. Saque maior que o saldo **não** altera o saldo e exibe mensagem amigável (sem stack trace).
6. Depósito/saque com valor ≤ 0 lança `ValorInvalidoException`, capturada no menu.
7. Entrada não numérica (ex.: "abc") é tratada com catch de `InputMismatchException` (ou tratamento equivalente do Scanner), sem encerrar o programa.
8. Há uso de `finally` em pelo menos um ponto coerente.
9. Menu funciona em loop até a opção 0 e exibe saldo final ao encerrar.
10. Sem `catch (Exception e)` genérico engolindo tudo silenciosamente; cada tipo de erro tem mensagem específica.

## Como verificar
```bash
javac *.java
# Entrada simulada: saque acima do saldo, entrada inválida, valor negativo, depósito ok, sair
printf '2\n800\n2\nabc\n1\n-50\n1\n200\n0\n' | java Main
```
Verificar que: o programa não quebra em nenhuma entrada, saldo final é R$ 700.00 (assumindo saldo inicial 500), e nenhuma linha de stack trace aparece na saída:
```bash
printf '2\n800\n0\n' | java Main 2>&1 | grep -i "at .*\.java" && echo "FALHOU: stack trace visível" || echo "OK"
```

## Erros comuns a apontar
- Declarar `SaldoInsuficienteException` como `RuntimeException` (perde o objetivo didático de checked).
- Capturar exceção e não fazer nada (`catch` vazio).
- Corrigir o saldo antes de validar (debitar e depois lançar exceção, deixando estado inconsistente).
- Não consumir a linha inválida do `Scanner` após `InputMismatchException`, causando loop infinito.
- Usar exceções para fluxo normal (ex.: lançar exceção para sair do menu).

## Padrão de feedback
Comece reconhecendo o que funciona (compilação, fluxo principal, tratamento correto de algum erro). Depois aponte **no máximo 2 ou 3 problemas**, em ordem de gravidade, sempre citando o comportamento observado ("ao digitar 'abc', o programa encerrou com stack trace") em vez de só o código. Termine com um desafio pequeno de melhoria (ex.: adicionar limite diário de saque com nova exceção customizada) para manter o aluno motivado.

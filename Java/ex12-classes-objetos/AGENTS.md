# AGENTS.md — Correção do Exercício 12

## Critérios de correção
1. Existem os dois arquivos `Conta.java` e `Main.java`, e o programa compila e roda.
2. `Conta` tem construtor e os três métodos pedidos.
3. O saque valida saldo suficiente (saque de 2000 deve ser recusado).
4. Saldo final impresso é 1200.0.

## Como verificar
```bash
javac Main.java Conta.java && java Main
```

## Erros comuns a apontar
- `sacar` subtraindo sem verificar saldo.
- Atributos públicos sendo modificados diretamente no Main em vez de usar os métodos — falar de encapsulamento (ideal: atributos `private`, mas não reprovar se funcionar via métodos).
- Construtor não atribuindo os valores (`this.titular = titular`).

## Padrão de feedback
O saldo final 1200 é o teste principal. Apontar problemas de encapsulamento como melhoria de estilo.

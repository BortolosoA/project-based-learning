# AGENTS.md — Correção do Exercício 14

## Critérios de correção
1. 4 arquivos (`Veiculo`, `Carro`, `Moto`, `Main`) compilam com `javac *.java`.
2. `Carro` e `Moto` usam `extends Veiculo` e `super(...)` no construtor.
3. `descricao()` é sobrescrito (idealmente com `@Override`) nas duas subclasses.
4. `Main` usa `ArrayList<Veiculo>` e a saída mostra as descrições específicas (polimorfismo funcionando).

## Como verificar
```bash
javac *.java && java Main
```
A saída deve mostrar portas para carros e cilindradas para motos — se mostrar só marca/ano, a sobrescrita falhou.

## Erros comuns a apontar
- Assinatura do método diferente na subclasse (vira sobrecarga, não sobrescrita).
- Não chamar `super(marca, ano)` no construtor da subclasse.
- Atributos private em Veiculo sem acesso — soluções: `protected` ou getters.

## Padrão de feedback
O ponto central é o polimorfismo no laço. Se funcionar, aprovar e explicar brevemente por que é poderoso.

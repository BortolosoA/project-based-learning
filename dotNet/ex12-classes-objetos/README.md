# Exercício 12 — Classes e Objetos

## Conceito
Classes, propriedades, construtores, modificadores de acesso e métodos de instância.

## Enunciado
Crie um projeto console com dois arquivos:

### `Conta.cs`
Uma classe `Conta` (conta bancária) com:
- Propriedades: `Titular` (string) e `Saldo` (double) — o setter de `Saldo` deve ser privado.
- Construtor que recebe o titular e o saldo inicial.
- Métodos:
  - `void Depositar(double valor)` — soma ao saldo.
  - `bool Sacar(double valor)` — subtrai do saldo **apenas** se houver saldo
    suficiente; se não houver, retorna `false` (ou imprime `Saldo insuficiente`).
  - `void ExibirSaldo()` — imprime titular e saldo.

### `Program.cs`
1. Cria uma conta para "Maria" com saldo inicial 1000.
2. Deposita 500.
3. Saca 2000 (deve ser recusado).
4. Saca 300.
5. Exibe o saldo final (deve ser 1200).

## Como executar
```bash
dotnet run
```

## Dica
Em C# não escrevemos getters/setters separados — usamos **propriedades**:
```csharp
public string Titular { get; set; }
public double Saldo { get; private set; }
```
O `private set` deixa o saldo legível de fora, mas só alterável pelos métodos da conta.

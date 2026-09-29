# Exercício 14 — Herança e Polimorfismo

## Conceito
Herança (`:`), `virtual`/`override`, `base` e polimorfismo com `List<ClasseBase>`.

## Enunciado
Sistema de funcionários:
- `Funcionario` (classe base): `Nome` e `SalarioBase` + método `virtual double CalcularSalario()` (retorna o salário base) e `virtual string Descricao()` (retorna `Funcionário`).
- `Gerente` : herda de `Funcionario` e sobrescreve `CalcularSalario()` com `override` — recebe base + 20% de bônus.
- `Desenvolvedor` : herda e sobrescreve — recebe base + 10% por projeto concluído (quantidade recebida no construtor).
- No `Program`: crie uma `List<Funcionario>` com um de cada tipo e percorra num
  `foreach` chamando `CalcularSalario()` — o polimorfismo decide qual versão executar.
  Imprima também o total da folha de pagamento.

## Exemplo
```
Maria (Gerente): R$ 6000
João (Desenvolvedor): R$ 5500
Ana (Funcionário): R$ 3000
Total da folha: R$ 14500
```

## Como executar
```bash
dotnet run
```

## Dica
Sem `virtual` na base e `override` na filha, o compilador chama sempre o método
da classe base quando a variável é do tipo `Funcionario`. Experimente remover o
`override` e veja o que muda — é o experimento mais didático deste exercício.

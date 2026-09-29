# AGENTS.md — Correção do Exercício 17 (Interfaces, Abstração e Generics)

## Critérios de correção
1. `IPagamento` existe como interface com os dois membros pedidos.
2. `PagamentoCartao` e `PagamentoPix` implementam a interface (o Pix gera código com `Guid`).
3. `PagamentoBase` é `abstract` e as concretas herdam dela e implementam `IPagamento`.
4. `RepositorioPagamentos<T>` é genérico e expõe Adicionar/ObterTodos/Total.
5. `ProcessadorPagamentos` itera sobre `List<IPagamento>` chamando `Pagar()` — não faz `if tipo == cartao`.

## Como verificar
```bash
dotnet run
```
Conferir: dois pagamentos processados com logs distintos (Pix com código, cartão sem),
repositório guardando 2 itens, e nenhuma checagem de tipo concreta no processador.

Verificação estática (arquivos do aluno):
```bash
grep -n "is PagamentoCartao\|as PagamentoCartao\|GetType()" *.cs   # não deve existir no Processador
grep -n "interface IPagamento" *.cs                               # interface presente
grep -n "class RepositorioPagamentos<T>" *.cs                     # generic presente
```

## Erros comuns a apontar
- Processador com `if (pagamento is PagamentoPix)` — isso quebra o propósito do polimorfismo.
- Classe abstrata com `new` (tentativa de instanciar dá erro, mas o aluno pode ter feito `PagamentoBase p = new PagamentoBase()` para "testar").
- Repositório genérico "falso": `class RepositorioPagamentos` com `List<object>`.
- Interface com implementação embutida (default interface methods) sem necessidade — apontar como curiosidade, não erro.

## Padrão de feedback
Aqui o objetivo é o "shape" do código profissional: contratos (interface), reuso
(abstract) e tipos parametrizáveis (generics). Se o processador está polimórfico,
celebrar — é exatamente como os frameworks trabalham por baixo dos panos.

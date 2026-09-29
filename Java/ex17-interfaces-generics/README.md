# Exercício 17 — Sistema de Pagamentos

## Conceito
Praticar **interfaces**, **classes abstratas com template method** e **generics**: os três pilares do polimorfismo em Java, em um cenário real de meios de pagamento.

## Enunciado
Uma loja virtual precisa processar pagamentos por Pix, cartão de crédito e boleto de forma uniforme. Você vai modelar isso com uma interface, uma classe abstrata e um repositório genérico.

Itens esperados:

- **Interface `Pagavel`** — método `boolean processar(double valor)`.
- **Interface `Identificavel`** — método `String getId()` (usada pelo repositório genérico).
- **Classe abstrata `Pagamento`** — implementa `Pagavel` e `Identificavel`. Atributos: `id` (String), `valor` (double), `status` (String). Implementa `processar` como **template method**:
  1. `validar()` (abstrato) — cada meio valida seus dados.
  2. `executarPagamento()` (abstrato) — lógica específica.
  3. `registrar()` (concreto) — atualiza o status para "CONFIRMADO" e imprime um comprovante.
  Se a validação falhar, status vira "RECUSADO" e retorna `false`.
- **`Pix`**, **`CartaoCredito`**, **`Boleto`** — estendem `Pagamento`, cada uma com seus atributos (ex.: chave Pix; número/limite do cartão; código de barras e vencimento do boleto) e validações próprias (ex.: cartão recusa se valor > limite; boleto recusa se vencido).
- **`Repositorio<T extends Identificavel>`** — genérico, com `void salvar(T item)`, `T buscarPorId(String id)` e `List<T> listarTodos()`, usando um `ArrayList` interno.
- **`Main`** — cria um `Repositorio<Pagamento>`, processa pagamentos variados (válidos e inválidos) via polimorfismo (lista de `Pagavel`) e ao final imprime um resumo listando todos os pagamentos com id, tipo, valor e status.

## Requisitos
1. `Pagamento.processar` deve seguir exatamente o fluxo do template method (validar → executar → registrar), sem ser sobrescrito pelas subclasses.
2. As subclasses só implementam os métodos abstratos — nada de duplicar lógica de registro.
3. `Repositorio` deve ser realmente genérico: o tipo `T` deve ter o bound `extends Identificavel` e `buscarPorId` deve usar `getId()` (sem cast/atributo específico).
4. O processamento no `Main` deve ser feito percorrendo uma coleção do tipo da **interface** (`Pagavel` ou `Pagamento`), demonstrando polimorfismo.
5. Pelo menos um pagamento de cada tipo deve falhar na validação no exemplo, mostrando status "RECUSADO".
6. `buscarPorId` retorna `null` (ou `Optional`) quando não encontra — documente a escolha.

## Exemplo de fluxo
```
Processando pagamentos...
[OK] Pix PAY-001: R$ 150.00 — CONFIRMADO
[X] Cartão PAY-002: R$ 900.00 — RECUSADO (limite excedido)
[OK] Boleto PAY-003: R$ 320.00 — CONFIRMADO

=== Resumo ===
PAY-001 | Pix            | R$ 150.00 | CONFIRMADO
PAY-002 | CartaoCredito  | R$ 900.00 | RECUSADO
PAY-003 | Boleto         | R$ 320.00 | CONFIRMADO
Busca por PAY-003: encontrado (Boleto, R$ 320.00)
Busca por PAY-999: não encontrado
```

## Como executar
```bash
javac *.java
java Main
```

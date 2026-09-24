# AGENTS.md — Correção do Exercício 17 (Interfaces e Generics)

## Critérios de correção
1. Compila com `javac *.java` sem erros.
2. Existe a interface `Pagavel` com `processar(double)` e a interface `Identificavel` com `getId()`.
3. `Pagamento` é `abstract` e implementa `processar` como template method (método concreto chamando métodos abstratos `validar`/`executarPagamento`).
4. Subclasses **não** sobrescrevem `processar` — só implementam os passos abstratos. (Ideal: `processar` declarado `final`.)
5. `Pix`, `CartaoCredito`, `Boleto` têm validações distintas e coerentes; pagamento inválido fica com status "RECUSADO".
6. `Repositorio<T extends Identificavel>` compila e `buscarPorId` usa `getId()` sem cast para tipo concreto.
7. `Main` processa uma coleção via tipo da interface (polimorfismo), sem `instanceof` para decidir comportamento.
8. Saída mostra os 3 meios de pagamento, com pelo menos um recusado, e demonstra `buscarPorId` (encontrado e não encontrado).
9. Sem raw types: nada de `Repositorio` sem parâmetro de tipo ou `ArrayList` sem generics.

## Como verificar
```bash
javac *.java
java Main
```
Verificações estáticas rápidas:
```bash
grep -n "extends Identificavel" Repositorio.java        # bound genérico presente
grep -n "instanceof" Main.java Pagamento.java           # não deve aparecer
grep -n "class Repositorio" Repositorio.java            # deve ser Repositorio<T ...>
grep -rn "void processar\|boolean processar" Pix.java CartaoCredito.java Boleto.java  # não devem sobrescrever
```
Na saída, conferir: três linhas de processamento, resumo com status corretos, busca por id existente e inexistente.

## Erros comuns a apontar
- Colocar lógica de pagamento direto nas subclasses duplicando registro/comprovante, ignorando o template method.
- Usar `instanceof` + cast no `Main` em vez de polimorfismo.
- `Repositorio` não genérico (guardando `Object` ou só `Pagamento`), perdendo o ponto do exercício.
- `buscarPorId` lançando exceção ou quebrando quando não encontra, sem documentar.
- Validação fora do template (ex.: validar no `Main` antes de chamar `processar`).
- Usar raw type `Repositorio r = new Repositorio();`.

## Padrão de feedback
Elogie primeiro o desenho: se o template method e o repositório genérico funcionam, diga explicitamente que esse é o padrão usado em frameworks reais (Spring, JPA). Aponte no máximo 2-3 problemas, descrevendo o efeito ("seu Main usa instanceof, então adicionar um novo meio de pagamento exigiria mexer no Main — o polimorfismo evita isso"). Feche com um desafio curto, como adicionar um método genérico `<T> void salvarTodos(List<T> itens)` ou um novo meio de pagamento (ex.: `DebitoConta`) sem alterar o `Main`.

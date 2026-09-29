# Exercício 20 — Conversor de Moedas com Testes

## Conceito
Primeiro contato com **Maven** (estrutura padrão de projeto, `pom.xml`, dependências automáticas) e **JUnit 5**: asserções, testes parametrizados e teste de exceções. Bônus: consumir uma API pública de câmbio com `java.net.http.HttpClient`.

## Enunciado
Você vai construir um conversor de moedas **testado de verdade** — nada de "rodei e funcionou". O Maven baixa o JUnit sozinho (como prometido, chega de baixar jar na mão).

Estrutura esperada:

```
ex20-maven-junit/
├── pom.xml
└── src/
    ├── main/java/
    │   ├── ConversorMoedas.java
    │   └── Main.java
    └── test/java/
        └── ConversorMoedasTest.java
```

Classes:

- **`ConversorMoedas`** —
  - `void definirTaxa(String de, String para, double taxa)` — registra taxa de câmbio (ex.: USD→BRL = 5.0); lança `IllegalArgumentException` se taxa ≤ 0.
  - `double converter(double valor, String de, String para)` — lança `IllegalArgumentException` se valor < 0; lança exceção customizada `TaxaNaoEncontradaException` (unchecked) se o par de moedas não existir.
  - `double getTaxa(String de, String para)`.
- **`ConversorMoedasTest`** — pelo menos:
  - 2 testes de conversão com conhecidos (`assertEquals` com delta para double, ex.: `assertEquals(500.0, c.converter(100, "USD", "BRL"), 0.001)`).
  - 1 **`@ParameterizedTest`** com `@CsvSource` testando vários pares/valores de uma vez.
  - 1 teste de exceção com **`assertThrows`** para taxa inexistente e 1 para valor negativo.
  - 1 teste verificando que taxa ≤ 0 é rejeitada.
- **`Main`** — demonstração simples no console: registra 2-3 taxas e converte alguns valores.
- **Bônus (opcional):** classe `ApiCambio` usando `java.net.http.HttpClient` para buscar a taxa real de um par em uma API pública sem chave, por exemplo:
  - `https://open.er-api.com/v6/latest/USD` (retorna JSON com campo `rates`)
  Faça o parse mínimo do JSON (pode ser com regex/`split` simples ou a dependência Gson declarada no `pom.xml`) e registre a taxa no conversor. Trate erros de rede sem quebrar o programa.

`pom.xml` mínimo: `groupId`, `artifactId`, `<properties>` com `maven.compiler.release` 17+ (ou `source`/`target` 11+) e `junit-jupiter` 5.x em escopo `test`.

## Requisitos
1. Projeto segue a estrutura padrão Maven (`src/main/java`, `src/test/java`) — sem `Main.java` solto na raiz.
2. `mvn test` passa com todos os testes verdes.
3. Pelo menos 6 testes no total (contando cada execução do teste parametrizado).
4. `assertEquals` em doubles sempre com delta.
5. Exceção customizada `TaxaNaoEncontradaException` testada com `assertThrows`.
6. Nada de `System.out.println` como "verificação" dentro dos testes — só asserções.
7. O `Main` roda com `mvn compile exec:java -Dexec.mainClass=Main` (ou `mvn package` + `java -cp`), demonstrando o conversor.

## Exemplo de fluxo
```
$ mvn test
[INFO] Tests run: 8, Failures: 0, Errors: 0, Skipped: 0
[INFO] BUILD SUCCESS

$ mvn -q compile exec:java -Dexec.mainClass=Main
100.00 USD = 500.00 BRL (taxa 5.00)
50.00 EUR = 270.00 BRL (taxa 5.40)
Erro esperado ao converter USD→JPY: taxa não cadastrada
```

## Como executar
```bash
mvn test                                    # roda todos os testes
mvn -q compile exec:java -Dexec.mainClass=Main   # roda a demonstração
```

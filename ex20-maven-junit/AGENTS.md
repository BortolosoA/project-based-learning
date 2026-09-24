# AGENTS.md — Correção do Exercício 20 (Maven e JUnit 5)

## Critérios de correção
1. Estrutura Maven padrão presente: `pom.xml` na raiz, código em `src/main/java`, testes em `src/test/java`. Nada de código-fonte solto na raiz.
2. `pom.xml` declara `junit-jupiter` (JUnit 5) em escopo `test` e configura versão do Java. `mvn -v` funciona e o build resolve dependências sozinho.
3. `mvn test` termina com `BUILD SUCCESS` e todos os testes verdes — verificar o número real de testes rodados.
4. Há pelo menos 6 execuções de teste no total, incluindo: conversões conhecidas, 1 `@ParameterizedTest` com `@CsvSource` (ou `MethodSource`), e `assertThrows` para taxa inexistente e valor negativo.
5. Doubles comparados com delta no `assertEquals`.
6. `TaxaNaoEncontradaException` existe, é unchecked, lançada por `converter` e **testada**.
7. Testes sem `System.out.println`, sem lógica condicional (`if`) para "decidir" se passou — só asserções.
8. Condições de borda cobertas: valor negativo, taxa ≤ 0, par inexistente.
9. Testes independentes entre si (ideal: novo `ConversorMoedas` em `@BeforeEach`), sem depender de ordem de execução.
10. Bônus (se feito): chamada HTTP à API pública funciona e erros de rede são tratados (ex.: `try/catch` de `IOException`/`InterruptedException`, com `Thread.currentThread().interrupt()` no segundo). Não penalizar se o bônus estiver ausente ou se a API estiver fora do ar — avaliar o código, não a rede.

## Como verificar
```bash
mvn -q test                                    # deve terminar com BUILD SUCCESS
mvn test | grep -E "Tests run|BUILD"           # conferir contagem de testes
mvn -q compile exec:java -Dexec.mainClass=Main # demonstração roda

# Verificações estáticas
ls src/main/java src/test/java pom.xml         # estrutura padrão
grep -n "ParameterizedTest\|CsvSource" src/test/java/*.java
grep -n "assertThrows" src/test/java/*.java
grep -n "System.out" src/test/java/*.java      # não deve aparecer
grep -n "scope>test" pom.xml                   # JUnit em escopo de teste
```
Teste de mutação rápido (testes realmente testam?): altere temporariamente a lógica do conversor (ex.: multiplicar por 2 a taxa) e rode `mvn test` — **deve falhar**. Se continuar verde, os testes são inúteis; aponte isso e depois reverta.

## Erros comuns a apontar
- JUnit 4 (`org.junit.Test`) misturado com JUnit 5 (`org.junit.jupiter.api.Test`) — os imports denunciam.
- `assertEquals(esperado, atual)` com doubles sem delta.
- Um único teste gigante testando tudo (um único ponto de falha, sem granularidade).
- Teste que sempre passa (ex.: `assertTrue(true)` ou capturar exceção com try/catch em vez de `assertThrows`).
- Estado compartilhado entre testes via campo estático, fazendo a ordem importar.
- Colocar os testes em `src/main/java` ou na pasta errada — `mvn test` roda 0 testes, o que passa despercebido.
- Esperar exceção em teste parametrizado misturando casos válidos e inválidos no mesmo `@CsvSource`.

## Padrão de feedback
Enquadre a conquista: rodar `mvn test` verde pela primeira vez marca a transição de "estudante" para quem trabalha como dev profissional — diga isso. Mostre a contagem de testes como evidência. Aponte no máximo 2-3 problemas focando no que mais importa em testes (independência, granularidade, delta em doubles). Se fez o teste de mutação e os testes não detectaram a quebra, explique com calma que "teste que passa com código errado é pior que não ter teste". Feche sugerindo o próximo passo natural: adicionar `maven-surefire-report` ou um `@DisplayName` descritivo em cada teste.

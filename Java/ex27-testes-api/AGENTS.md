# AGENTS.md — Correção do Exercício 27 (Testes Automatizados de API)

## Critérios de correção

1. **`mvn test` executa e passa 100%** sem necessidade de banco externo ou servidor rodando. Teste que quebra na máquina do corretor = falha grave.
2. **Existem pelo menos 3 classes de teste**, cobrindo as 3 camadas: service (unitário com Mockito), controller (`@WebMvcTest` + MockMvc ou `@SpringBootTest` + MockMvc) e repository (`@DataJpaTest`).
3. **Service testado isoladamente**: usa `@Mock`/`@ExtendWith(MockitoExtension.class)` no repository — não sobe contexto Spring nem toca banco.
4. **Mockito usado corretamente**: `when(...).thenReturn(...)`, `assertThrows` para exceções e ao menos um `verify(...)` confirmando interação com o mock.
5. **Teste de validação**: existe teste verificando que payload inválido (preço negativo, nome vazio) retorna **400** — prova de que `@Valid` + Bean Validation funcionam.
6. **Teste de 404**: busca por id inexistente resulta em 404 na camada web (o service deve lançar exceção e haver tratamento, ex.: `@ExceptionHandler`/`ProblemDetail`).
7. **`@DataJpaTest`** salva e recupera dados de verdade no banco de teste (H2).
8. **Nomes de testes descritivos** e em português; assertions claras (AssertJ/`jsonPath`/`status()`).
9. Sem testes desabilitados (`@Disabled`) nem asserts vazios/comentados para "fazer passar".

## Como verificar

```bash
# 1. Rodar tudo — esperado BUILD SUCCESS com Tests run: N, Failures: 0
mvn test

# 2. Listar as classes de teste
find src/test -name "*Test.java"

# 3. Conferir que o service test não sobe Spring
#    (abrir ProdutoServiceTest: não deve ter @SpringBootTest)

# 4. Quebrar propositalmente uma regra de negócio (ex.: remover @NotBlank)
#    e rodar mvn test — o teste de validação deve FALHAR. Desfazer depois.

# 5. Cobertura (se JaCoCo configurado)
mvn test jacoco:report && ls target/site/jacoco/index.html
```

Resultado esperado do passo 1: `Tests run: 10 (ou mais), Failures: 0, Errors: 0` e `BUILD SUCCESS`.

## Erros comuns a apontar

- Teste de service usando `@SpringBootTest` (vira teste de integração lento — perde o propósito do unitário).
- Mock sem stub (`when`) e teste que quebra com `NullPointerException` disfarçada.
- Testes "triviais" que só verificam `assertNotNull` no objeto criado na própria linha — não testam comportamento.
- `@DataJpaTest` falhando porque a entidade usa banco real configurado no `application.properties` sem profile de teste.
- Testes acoplados à ordem de execução ou dependendo de dados deixados por outros testes (falta de isolamento / `@Transactional` no `@DataJpaTest` já ajuda).
- Ausência de cenário de erro: só "caminho feliz" não é cobertura de verdade.
- `spring-boot-starter-test` excluído do pom ou JUnit 4 legado misturado com JUnit 5.

## Padrão de feedback

Comece pelo resultado de `mvn test` — se não roda verde, esse é o único ponto até resolver. Depois elogie a separação por camadas se existir e destaque **um** teste bem escrito como exemplo. Aponte o cenário de erro mais importante que faltou (geralmente validação 400 ou not-found 404) e, se houver tempo, incentive a olhar o relatório JaCoCo como curiosidade, não como meta de 100% de cobertura.

# Exercício 27 — Testes Automatizados de API

## Conceito

Código sem teste é código frágil. Em empresas, nenhuma alteração sobe para produção sem testes automatizados rodando no pipeline. Neste exercício você vai aprender a **pirâmide de testes** do ecossistema Spring:

- **Testes unitários** (JUnit 5 + Mockito): testam uma classe isolada, mockando suas dependências — rápidos e numerosos.
- **Testes de integração de camada web** (`@WebMvcTest` com MockMvc, ou `@SpringBootTest`): testam controllers, serialização JSON, validação e códigos HTTP.
- **Testes de persistência** (`@DataJpaTest`): testam repositories e queries contra um banco em memória.

Ao final, `mvn test` deve rodar toda a suíte e reportar sucesso — e você pode medir a cobertura com o **JaCoCo** (opcional).

## Enunciado

Parta de uma API simples de **produtos** (pode criar do zero): `Produto` com `id`, `nome` (obrigatório, 3–50 caracteres) e `preco` (obrigatório, maior que zero), com CRUD completo em `ProdutoController` → `ProdutoService` → `ProdutoRepository`. Seu trabalho é cobrir essa API com testes:

1. **Unitários do service** (`ProdutoServiceTest` — JUnit 5 + Mockito, com `@ExtendWith(MockitoExtension.class)` e mock do repository):
   - salvar produto válido retorna o produto salvo;
   - buscar por id inexistente lança exceção (ex.: `produto nao encontrado`);
   - listar retorna todos;
   - deletar chama `deleteById` do repository (verificar com `verify`).
2. **Testes da camada web** (`ProdutoControllerTest` — `@WebMvcTest` + `MockMvc` + `@MockBean` do service):
   - `GET /produtos` retorna 200 e JSON com a lista;
   - `POST /produtos` com dados válidos retorna 201;
   - `POST /produtos` com preço negativo ou nome vazio retorna **400** (validação funcionando);
   - `GET /produtos/{id}` com mock lançando "não encontrado" retorna 404.
3. **Testes de repository** (`ProdutoRepositoryTest` — `@DataJpaTest`):
   - salvar e recuperar produto;
   - busca customizada se existir (ex.: `findByNomeContaining`).
4. **Opcional**: configurar o plugin **JaCoCo** no `pom.xml` e gerar relatório com `mvn test jacoco:report` (abrir `target/site/jacoco/index.html`).

## Requisitos

- Java 17+, Spring Boot 3.x, Maven.
- Dependência `spring-boot-starter-test` (vem no start.spring.io; inclui JUnit 5, Mockito, AssertJ).
- Banco H2 para os testes.
- Pelo menos **3 classes de teste**, uma por camada (service, controller, repository).
- Nomes de testes descritivos em português (ex.: `deveRetornar400QuandoPrecoForNegativo()`).
- `mvn test` roda 100% verde **sem precisar subir servidor nem banco externo**.
- Assertions com AssertJ ou `MockMvcResultMatchers` (não apenas `assertTrue` genérico).

## Exemplo de uso

Rodando a suíte completa:

```bash
mvn test
```

Saída esperada (trecho):

```
[INFO] -------------------------------------------------------
[INFO]  T E S T S
[INFO] -------------------------------------------------------
[INFO] Running com.exemplo.produtos.ProdutoServiceTest
[INFO] Tests run: 4, Failures: 0, Errors: 0, Skipped: 0
[INFO] Running com.exemplo.produtos.ProdutoControllerTest
[INFO] Tests run: 4, Failures: 0, Errors: 0, Skipped: 0
[INFO] Running com.exemplo.produtos.ProdutoRepositoryTest
[INFO] Tests run: 2, Failures: 0, Errors: 0, Skipped: 0
[INFO] Results:
[INFO] Tests run: 10, Failures: 0, Errors: 0, Skipped: 0
[INFO] BUILD SUCCESS
```

Exemplo de um teste de controller com MockMvc:

```java
@Test
void deveRetornar400QuandoPrecoForNegativo() throws Exception {
    mockMvc.perform(post("/produtos")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"nome\":\"Mouse\",\"preco\":-10}"))
        .andExpect(status().isBadRequest());
}
```

Relatório de cobertura (opcional):

```bash
mvn test jacoco:report
# abrir target/site/jacoco/index.html no navegador
```

## Como executar

```bash
mvn test                     # toda a suíte
mvn -Dtest=ProdutoServiceTest test   # apenas uma classe
mvn spring-boot:run          # subir a API para testar na mão (opcional)
```

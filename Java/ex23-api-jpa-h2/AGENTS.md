# AGENTS.md — Correção do Exercício 23 (JPA com H2)

## Critérios de correção
1. `pom.xml` contém `spring-boot-starter-data-jpa` e `h2` (além de `spring-boot-starter-web`).
2. `Produto` está anotado com `@Entity`, tem `@Id` com `@GeneratedValue` (IDENTITY ou AUTO) e possui construtor sem argumentos (explícito ou gerado).
3. `ProdutoRepository` é uma **interface** que estende `JpaRepository<Produto, Long>` — sem implementação escrita à mão.
4. O `ProdutoService` usa o repository (`findAll`, `findById`, `save`, `deleteById`...); não resta nenhuma lista em memória.
5. `application.properties` configura o H2 e habilita o console (`spring.h2.console.enabled=true`).
6. O CRUD funciona com os status corretos (200/201/204/404) e o id é gerado pelo banco.
7. O console H2 abre em `/h2-console` e a tabela do produto aparece com os dados inseridos via API.
8. (Extra, valendo ponto bônus) Método derivado como `findByNomeContainingIgnoreCase` implementado só pela assinatura.

## Como verificar

```bash
./mvnw spring-boot:run

curl -i -X POST "http://localhost:8080/produtos" \
  -H "Content-Type: application/json" \
  -d '{"nome":"Açúcar 1kg","preco":5.20,"quantidade":80}'
# Esperado: HTTP 201 com id gerado

curl "http://localhost:8080/produtos"
# Esperado: lista com o produto

curl -i "http://localhost:8080/produtos/999"
# Esperado: HTTP 404

# Navegador: http://localhost:8080/h2-console
# JDBC URL: jdbc:h2:mem:loja | user: sa | senha vazia
# Rodar: SELECT * FROM PRODUTO;  → deve mostrar o registro criado via curl
```

## Erros comuns a apontar
- Esquecer o construtor padrão na entidade (o JPA quebra com erro de instanciação).
- Criar uma classe implementando o repository manualmente em vez de deixar o Spring Data gerar.
- Manter a lista em memória "por segurança" junto com o repository (fonte dupla de verdade).
- Não habilitar o console H2 ou errar a JDBC URL no login do console.
- Confundir `save()` com merge: usar PUT sem verificar existência antes, criando registros novos silenciosamente.
- Remover os `@GeneratedValue` e tentar controlar o id no código.

## Padrão de feedback
Excelente passo — você acabou de trocar uma lista volátil por persistência de verdade com pouquíssimo código, e é exatamente essa a mágica do Spring Data JPA. O foco didático foi entender entidade, repository e geração de id. Se algum critério não passou, corrija apenas ele e revalide com os curls. Guarde bem esse padrão: praticamente toda aplicação profissional usa essa estrutura como base.

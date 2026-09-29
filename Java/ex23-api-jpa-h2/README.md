# Exercício 23 — API de Produtos com Banco H2

## Conceito
Migrar o CRUD do exercício 22 para persistência de verdade: Spring Data JPA com uma entidade `@Entity`, um repository que estende `JpaRepository` e o banco H2 em memória, inspecionável pelo console H2.

## Enunciado
O dono da loja aprovou o protótipo do exercício anterior, mas reclamou: "toda vez que o servidor reinicia, perco tudo!". Chegou a hora de persistir os dados — sem instalar nada, usando o banco embarcado H2.

Pegue a API de produtos (id, nome, preco, quantidade) e migre-a para JPA:

1. Transforme `Produto` numa entidade: anote com `@Entity`, marque o `id` com `@Id` e `@GeneratedValue(strategy = GenerationType.IDENTITY)` para o banco gerar os ids automaticamente.
2. Crie a interface `ProdutoRepository extends JpaRepository<Produto, Long>` — sem escrever nenhuma implementação: o Spring Data gera os métodos (`findAll`, `findById`, `save`, `deleteById`...) sozinho.
3. Ajuste o `ProdutoService` para usar o repository no lugar da lista em memória.
4. Configure o H2 no `application.properties` e habilite o console H2 (`/h2-console`) para visualizar a tabela.
5. Mantenha os mesmos endpoints e status HTTP do exercício 22 (200, 201, 204, 404).

Extra (opcional): adicione um método de consulta derivado no repository, ex.: `List<Produto> findByNomeContainingIgnoreCase(String nome)`, exposto em `GET /produtos/buscar?nome=arr`.

## Requisitos
1. Dependências: `spring-boot-starter-web`, `spring-boot-starter-data-jpa` e `com.h2database:h2` (runtime).
2. `Produto` anotado com `@Entity`, com `@Id` e `@GeneratedValue`.
3. Repository estendendo `JpaRepository` — nenhuma implementação manual.
4. `application.properties` com datasource H2 e `spring.h2.console.enabled=true`.
5. Console H2 acessível em `http://localhost:8080/h2-console` e a tabela `PRODUTO` visível após inserts.
6. CRUD completo funcionando com os mesmos status do exercício anterior.
7. Construtor padrão (sem argumentos) na entidade — exigência do JPA.

## Exemplo de uso

```bash
# Criar produto (id agora vem do banco)
curl -i -X POST "http://localhost:8080/produtos" \
  -H "Content-Type: application/json" \
  -d '{"nome":"Café 500g","preco":18.90,"quantidade":30}'
# Resposta: 201 Created — {"id":1,"nome":"Café 500g","preco":18.90,"quantidade":30}

# Listar
curl "http://localhost:8080/produtos"
# Resposta: 200 OK — [{"id":1,"nome":"Café 500g",...}]

# Buscar inexistente
curl -i "http://localhost:8080/produtos/42"
# Resposta: 404 Not Found

# Busca por nome (extra)
curl "http://localhost:8080/produtos/buscar?nome=caf"
# Resposta: 200 OK — produtos cujo nome contém "caf"

# Console H2: abra http://localhost:8080/h2-console no navegador
# JDBC URL: jdbc:h2:mem:loja  |  usuário: sa  |  senha: (vazia)
# Execute: SELECT * FROM PRODUTO;
```

## Como executar

```bash
# Gerar o projeto (site): https://start.spring.io
#   Maven | Java | Spring Boot 3.x | Java 17
#   Dependências: Spring Web, Spring Data JPA, H2 Database

# Ou pela linha de comando:
curl https://start.spring.io/starter.tgz \
  -d dependencies=web,data-jpa,h2 \
  -d javaVersion=17 \
  -d name=ex23-api-jpa-h2 | tar -xzvf -

# application.properties (sugestão):
#   spring.datasource.url=jdbc:h2:mem:loja
#   spring.datasource.username=sa
#   spring.datasource.password=
#   spring.h2.console.enabled=true
#   spring.jpa.hibernate.ddl-auto=update

# Rodar
./mvnw spring-boot:run

# Testar com os curls acima e abrir o console H2 no navegador.
```

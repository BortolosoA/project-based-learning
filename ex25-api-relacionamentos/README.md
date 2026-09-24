# Exercício 25 — API de Clientes e Pedidos

## Conceito
Modelar relacionamentos JPA de verdade: `Cliente` 1—N `Pedido` com itens, cuidando da serialização (DTOs contra ciclos infinitos), entendendo LAZY vs EAGER e o problema N+1, e expondo listagens paginadas e ordenadas com `Pageable`.

## Enunciado
A loja virou e-commerce. Agora é preciso cadastrar **clientes** e seus **pedidos**, cada pedido com **itens** e um **status** que muda com o tempo.

Modele as entidades:

- **Cliente**: `id`, `nome` (`@NotBlank`), `email` (`@NotBlank` + `@Email`).
- **Pedido**: `id`, `dataHora` (preenchida automaticamente na criação), `status` (enum `StatusPedido`: `ABERTO`, `PAGO`, `ENVIADO`, `CANCELADO` — persistido com `@Enumerated(EnumType.STRING)`), e relacionamento `@ManyToOne` com Cliente.
- **ItemPedido**: `id`, `descricao`, `quantidade`, `precoUnitario`, e `@ManyToOne` com Pedido. Pedido tem `@OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)` para os itens.

Endpoints obrigatórios:

- `POST /clientes` e `GET /clientes` — cadastro e listagem de clientes (com validação e DTOs, padrão do exercício 24).
- `POST /clientes/{clienteId}/pedidos` — cria um pedido já com seus itens para o cliente; 404 se o cliente não existir.
- `GET /clientes/{clienteId}/pedidos` — lista paginada dos pedidos do cliente, usando `Pageable` (suportar `page`, `size` e `sort`, ex.: `?page=0&size=5&sort=dataHora,desc`).
- `GET /pedidos/{id}` — detalhe do pedido com seus itens e o nome do cliente (via DTO, sem ciclo de serialização); 404 se não existir.
- `PATCH /pedidos/{id}/status?novoStatus=PAGO` — altera o status do pedido; 404 se não existir.

Boas práticas exigidas:

- Use `FetchType.LAZY` (padrão em `@ManyToOne` já é EAGER em alguns casos — no `@OneToMany` o padrão é LAZY; declare explicitamente o fetch dos seus relacionamentos e justifique em comentário).
- **Nunca** serialize as entidades diretamente: crie DTOs (`ClienteRequest/Response`, `PedidoResponse` com `List<ItemPedidoResponse>`, etc.). Serializar `@OneToMany`/`@ManyToOne` bidirecional direto causa `StackOverflowError` ou JSON infinito.
- Fique atento ao problema **N+1** ao listar pedidos com itens (no mínimo reconheça em comentário; bônus se resolver com `JOIN FETCH` ou `@EntityGraph`).

## Requisitos
1. Projeto Spring Boot 3.x, Java 17+, dependências: web, data-jpa, h2, validation.
2. Entidades `Cliente`, `Pedido`, `ItemPedido` com os relacionamentos 1-N e N-1 mapeados corretamente (`mappedBy` + `@JoinColumn`).
3. Enum `StatusPedido` persistido como STRING.
4. DTOs em todas as respostas — nenhuma entidade exposta, nenhum ciclo de serialização.
5. Listagem de pedidos paginada com `Pageable` e ordenação (`sort=dataHora,desc` funcionando).
6. Status HTTP corretos (201, 200, 404) e validação dos campos de cliente.
7. Fetch type declarado explicitamente e comentado; menção ao N+1 (resolvido ou justificado) no código.

## Exemplo de uso

```bash
# Criar cliente
curl -X POST "http://localhost:8080/clientes" \
  -H "Content-Type: application/json" \
  -d '{"nome":"Ana Silva","email":"ana@email.com"}'
# Resposta: 201 — {"id":1,"nome":"Ana Silva","email":"ana@email.com"}

# Criar pedido com itens para a cliente 1
curl -X POST "http://localhost:8080/clientes/1/pedidos" \
  -H "Content-Type: application/json" \
  -d '{"itens":[{"descricao":"Café 500g","quantidade":2,"precoUnitario":18.90},
               {"descricao":"Filtro de papel","quantidade":1,"precoUnitario":7.50}]}'
# Resposta: 201 — {"id":1,"status":"ABERTO","dataHora":"2026-09-23T10:30:00",
#                  "clienteNome":"Ana Silva",
#                  "itens":[{"descricao":"Café 500g","quantidade":2,"precoUnitario":18.90}, ...]}

# Listar pedidos da cliente, paginado e ordenado
curl "http://localhost:8080/clientes/1/pedidos?page=0&size=5&sort=dataHora,desc"
# Resposta: 200 — página Spring (content, totalElements, totalPages, size, number...)

# Detalhe de um pedido
curl "http://localhost:8080/pedidos/1"
# Resposta: 200 — pedido completo com itens e nome do cliente, sem JSON circular

# Avançar o status
curl -X PATCH "http://localhost:8080/pedidos/1/status?novoStatus=PAGO"
# Resposta: 200 — {"id":1,"status":"PAGO",...}

# Status inválido ou cliente inexistente
curl -i -X POST "http://localhost:8080/clientes/99/pedidos" \
  -H "Content-Type: application/json" -d '{"itens":[]}'
# Resposta: 404
```

## Como executar

```bash
# Gerar o projeto (site): https://start.spring.io
#   Maven | Java | Spring Boot 3.x | Java 17
#   Dependências: Spring Web, Spring Data JPA, H2 Database, Validation

# Ou pela linha de comando:
curl https://start.spring.io/starter.tgz \
  -d dependencies=web,data-jpa,h2,validation \
  -d javaVersion=17 \
  -d name=ex25-api-relacionamentos | tar -xzvf -

# Rodar
./mvnw spring-boot:run

# Testar com os curls acima ou com o Postman;
# console H2 em http://localhost:8080/h2-console para inspecionar CLIENTE, PEDIDO e ITEM_PEDIDO.
```

# AGENTS.md — Correção do Exercício 25 (Relacionamentos JPA)

## Critérios de correção
1. Entidades `Cliente`, `Pedido` e `ItemPedido` mapeadas com `@OneToMany(mappedBy = ...)` no lado "um" e `@ManyToOne` + `@JoinColumn` no lado "muitos"; os relacionamentos funcionam de fato (FKs criadas no H2).
2. `status` do pedido é um enum `StatusPedido` com `@Enumerated(EnumType.STRING)` (não ORDINAL).
3. Itens são criados em cascata junto com o pedido (`cascade = CascadeType.ALL` ou persistência equivalente) e cada item referencia o pedido correto.
4. **Nenhuma entidade é serializada** nos endpoints: DTOs em todas as respostas; não ocorre erro de serialização circular ao consultar pedidos.
5. `GET /clientes/{id}/pedidos` usa `Pageable` e respeita `page`, `size` e `sort` (testar `sort=dataHora,desc`).
6. `PATCH /pedidos/{id}/status` altera o status corretamente e rejeita id inexistente com 404.
7. Fetch types declarados explicitamente com comentário justificando (ex.: LAZY nas coleções para não carregar tudo sempre).
8. Há tratamento do problema N+1: resolvido com `JOIN FETCH`/`@EntityGraph` **ou** comentário no código reconhecendo o problema e quando ele aparece.
9. Validação e erro padronizado (padrão do ex24) mantidos para cliente e para "não encontrado".

## Como verificar

```bash
./mvnw spring-boot:run

curl -X POST "http://localhost:8080/clientes" \
  -H "Content-Type: application/json" \
  -d '{"nome":"Ana Silva","email":"ana@email.com"}'
# Esperado: 201 com id

curl -i -X POST "http://localhost:8080/clientes/1/pedidos" \
  -H "Content-Type: application/json" \
  -d '{"itens":[{"descricao":"Café","quantidade":2,"precoUnitario":18.90}]}'
# Esperado: 201, pedido com status ABERTO, itens e clienteNome; SEM JSON infinito

curl "http://localhost:8080/clientes/1/pedidos?page=0&size=2&sort=dataHora,desc"
# Esperado: 200, objeto Page (content, totalElements, totalPages)

curl "http://localhost:8080/pedidos/1"
# Esperado: 200, pedido completo; se der StackOverflow/erro de serialização, critério 4 falhou

curl -X PATCH "http://localhost:8080/pedidos/1/status?novoStatus=PAGO"
# Esperado: 200 com status PAGO

curl -i "http://localhost:8080/pedidos/999"
# Esperado: 404 com corpo de erro padronizado

# Conferir FKs no console H2:
# SELECT * FROM PEDIDO; SELECT * FROM ITEM_PEDIDO;  (colunas CLIENTE_ID / PEDIDO_ID preenchidas)
```

## Erros comuns a apontar
- Serializar entidades com relacionamento bidirecional → `StackOverflowError`/JSON infinito (resolver com DTOs, não com `@JsonIgnore` espalhado).
- Esquecer o `mappedBy` e criar tabela de junção desnecessária.
- `@Enumerated` ausente → status salvo como ordinal (frágil a mudanças na ordem do enum).
- Esquecer de setar `item.setPedido(pedido)` → FK nula em `ITEM_PEDIDO`.
- `findAll()` sem `Pageable` na listagem que deveria ser paginada.
- EAGER em coleções + listagem grande → N+1 invertido (carrega o banco inteiro sem perceber).
- PATCH aceitando qualquer string de status sem validar contra o enum.

## Padrão de feedback
Parabéns — relacionamentos, paginação e DTOs são o uso mais próximo de um sistema real em produção, e você completou a trilha de APIs. O foco didático deste exercício foi mapear cardinalidades com clareza, proteger a API de ciclos de serialização com DTOs e reconhecer os custos de fetch (LAZY vs EAGER e N+1). Se algum critério não passou, corrija só o ponto indicado e revalide com os curls; daqui em diante você já tem base para construir APIs completas por conta própria.

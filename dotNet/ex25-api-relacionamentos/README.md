# Exercício 25 — API de Pedidos e Clientes (Relacionamentos + Paginação)

## Conceito
Relacionamentos no EF Core (um-para-muitos), carregamento de relacionados (`Include`),
transações e **paginação** (`Skip`/`Take`) — o que faz uma API escalar de 10 para 10 milhões de registros.

## Enunciado
Uma loja com clientes e pedidos:

**Domínio:**
- `Cliente` — `Id`, `Nome`, `Email`. Um cliente tem **muitos** pedidos (`List<Pedido>`).
- `Pedido` — `Id`, `Data` (DateTime), `Total`, `ClienteId` + referência ao `Cliente`.

**Endpoints:**
| Método | Rota | Comportamento |
|--------|------|---------------|
| GET | /clientes | lista paginada: `?pagina=1&tamanho=10` |
| POST | /clientes | cria cliente (valida e-mail com DataAnnotations) |
| GET | /clientes/{id} | detalhe com seus pedidos (`Include`) |
| POST | /clientes/{id}/pedidos | cria pedido para o cliente; `Data = DateTime.Now`; total vem no body |
| GET | /clientes/{id}/pedidos | pedidos do cliente, paginados |
| GET | /pedidos/{id} | detalhe do pedido com dados do cliente |

## Requisitos
1. Migration nova para as duas entidades com FK (`ClienteId`) — sem FK não conta como relacionamento.
2. Paginação de verdade no banco: `Skip((pagina-1)*tamanho).Take(tamanho)` — **não** buscar tudo e fatiar em memória.
3. Respostas de listagem trazem metadados: `pagina`, `tamanho`, `total` e `totalPaginas`.
4. `Include` usado onde precisa (evitar `N+1` e evitar `null` na navegação).
5. POST de pedido para cliente inexistente → 404.
6. E-mails únicos — tentar cadastrar duplicado → 409.
7. DTOs nas respostas: `ClienteResumo` (sem pedidos) na lista, `ClienteDetalhe` (com pedidos) no GET por id.

## Exemplo de uso
```bash
curl -X POST http://localhost:5000/clientes -H "Content-Type: application/json" -d '{"nome":"Ana","email":"ana@teste.com"}'
# {"id":1,"nome":"Ana","email":"ana@teste.com"}

curl -X POST http://localhost:5000/clientes/1/pedidos -H "Content-Type: application/json" -d '{"total":159.80}'
# 201 + pedido com data de agora

curl "http://localhost:5000/clientes/1/pedidos?pagina=1&tamanho=10"
# {"itens":[...],"pagina":1,"tamanho":10,"total":1,"totalPaginas":1}
```

## Como executar
```bash
dotnet ef migrations add ClientesEPedidos
dotnet ef database update
dotnet run --urls http://localhost:5000
```

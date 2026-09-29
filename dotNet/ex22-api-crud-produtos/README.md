# Exercício 22 — API de Produtos (CRUD em Memória)

## Conceito
REST de verdade: CRUD completo com camada de service, `ActionResult`, status codes
corretos (200/201/204/400/404) e organização em camadas — o padrão que todo projeto
profissional usa.

## Enunciado
Uma API de produtos em memória com `Id`, `Nome`, `Preco` e `Estoque`:

| Método | Rota | Comportamento |
|--------|------|---------------|
| GET | /produtos | lista todos |
| GET | /produtos/{id} | um produto ou 404 |
| POST | /produtos | cria e retorna 201 com header Location |
| PUT | /produtos/{id} | atualiza ou 404 |
| DELETE | /produtos/{id} | remove → 204 ou 404 |

Estrutura em camadas:
- `Produto` (record ou classe).
- `ProdutoService` — mantém a `List<Produto>` e toda a lógica: buscar por id,
  adicionar (gerando id), atualizar, remover, e validar as regras abaixo.
- `ProdutosController` — só HTTP: chama o service e mapeia o resultado para
  `ActionResult` (200/201/204/404/400).
- Registro no Program: `builder.Services.AddSingleton<ProdutoService>();` e o
  controller recebe o service pelo construtor (**injeção de dependência**).

## Requisitos
1. Status codes corretos: 201 + header `Location` no POST (use `CreatedAtAction`),
   204 no DELETE (`NoContent()`), 404 para id inexistente (`NotFound()`).
2. Regras de negócio no service: nome não pode ser vazio, preço deve ser > 0 —
   violação devolve 400 com mensagem clara.
3. O controller não tem regra de negócio (só HTTP) — a lógica é testável sem rede.
4. Ids gerados automaticamente, sem colisão.

## Exemplo de uso
```bash
dotnet run --urls http://localhost:5000

# Criar (esperado: 201 + Location + o produto com id 1)
curl -i -X POST http://localhost:5000/produtos \
  -H "Content-Type: application/json" \
  -d '{"nome":"Mouse","preco":79.90,"estoque":10}'

# Listar
curl http://localhost:5000/produtos

# Buscar por id
curl -i http://localhost:5000/produtos/1

# Atualizar (esperado: 200 + produto atualizado)
curl -i -X PUT http://localhost:5000/produtos/1 \
  -H "Content-Type: application/json" \
  -d '{"nome":"Mouse Gamer","preco":129.90,"estoque":5}'

# Remover (esperado: 204) e conferir que sumiu (esperado: 404)
curl -i -X DELETE http://localhost:5000/produtos/1
curl -i http://localhost:5000/produtos/1

# Payload inválido (esperado: 400)
curl -i -X POST http://localhost:5000/produtos \
  -H "Content-Type: application/json" \
  -d '{"nome":"","preco":-1}'
```

## Como executar
```bash
dotnet new webapi --use-controllers -n Ex22Api
cd Ex22Api
dotnet run --urls http://localhost:5000
```

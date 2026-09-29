# AGENTS.md — Correção do Exercício 25 (Relacionamentos + Paginação)

## Critérios de correção
1. Duas entidades com FK real (`ClienteId`) criada por migration — conferir no SQLite.
2. `Include` carregando os pedidos do cliente (e o cliente do pedido) sem N+1.
3. Paginação feita no banco com `Skip`/`Take`, com metadados (pagina, tamanho, total, totalPaginas) na resposta.
4. POST de pedido para cliente inexistente → 404; e-mail duplicado → 409.
5. DTOs distintos: lista de clientes sem pedidos, detalhe com pedidos.

## Como verificar
```bash
dotnet ef database update
dotnet run --urls http://localhost:5000
```
```bash
# Relacionamento no banco
sqlite3 produtos.db "SELECT name FROM sqlite_master WHERE type='table';"
sqlite3 produtos.db ".schema Pedidos"    # deve ter FOREIGN KEY (ClienteId)

# Criar cliente + pedido
curl -s -X POST http://localhost:5000/clientes -H "Content-Type: application/json" -d '{"nome":"Ana","email":"ana@teste.com"}' | jq .id   # 1
curl -s -X POST http://localhost:5000/clientes/1/pedidos -H "Content-Type: application/json" -d '{"total":159.8}' > /dev/null

# Include no detalhe
curl -s http://localhost:5000/clientes/1 | jq '.pedidos | length'   # 1

# 404 e 409
curl -s -o /dev/null -w "%{http_code}\n" -X POST http://localhost:5000/clientes/999/pedidos -H "Content-Type: application/json" -d '{"total":1}'   # 404
curl -s -o /dev/null -w "%{http_code}\n" -X POST http://localhost:5000/clientes -H "Content-Type: application/json" -d '{"nome":"Ana2","email":"ana@teste.com"}'   # 409

# Paginação real (criar 15 clientes e pedir tamanho=10)
curl -s "http://localhost:5000/clientes?pagina=2&tamanho=10" | jq '{pagina, totalPaginas, total}'
```

## Erros comuns a apontar
- Paginação falsa: `ToList()` de tudo e `.Skip().Take()` em memória — o SQL gerado deve ter OFFSET/FETCH.
- `Include` ausente → `pedidos` null na resposta (ou exception de navegação não carregada).
- `Include` demais: incluir pedidos na listagem simples de clientes (peso desnecessário).
- Circular reference na serialização (Cliente → Pedidos → Cliente → ...) — resolver com DTOs unidirecionais, não com `ReferenceHandler.IgnoreCycles` como "golden hammer" (aceitar, mas comentar).
- Esquecer o índice/FK — apontar que a FK também dá integrity no banco.
- Metadados de paginação ausentes (o cliente da API não sabe quantas páginas existem).

## Padrão de feedback
O relacionamento 1:N é a última peça de EF Core "core" da trilha. Testar a paginação
com 15+ registros para provar que a página 2 realmente existe. Se o aluno chegar
aqui com DTOs + Include + paginação no banco, ele já escreve APIs como o mercado pede.

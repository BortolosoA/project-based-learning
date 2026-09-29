# AGENTS.md — Correção do Exercício 22 (CRUD em Memória)

## Critérios de correção
1. Os 5 endpoints do CRUD funcionam com os status codes corretos (200/201/204/404/400).
2. POST retorna 201 com header `Location` apontando para o recurso criado.
3. `ProdutoService` contém a lógica; o controller só faz HTTP — regra de negócio no controller reprova o item.
4. Service registrado no DI (`AddSingleton`) e injetado no controller.
5. Validação: nome vazio ou preço <= 0 → 400 com mensagem.
6. Ids automáticos sem colisão.

## Como verificar
```bash
dotnet run --urls http://localhost:5000
```
```bash
curl -i -X POST http://localhost:5000/produtos -H "Content-Type: application/json" -d '{"nome":"Mouse","preco":79.9,"estoque":10}'
# 201 + Location: http://localhost:5000/produtos/1

curl -s http://localhost:5000/produtos          # lista com 1 item
curl -i -X PUT http://localhost:5000/produtos/1 -H "Content-Type: application/json" -d '{"nome":"Mouse Gamer","preco":129.9,"estoque":5}'   # 200
curl -i -X DELETE http://localhost:5000/produtos/1    # 204
curl -i http://localhost:5000/produtos/1             # 404
curl -i -X POST http://localhost:5000/produtos -H "Content-Type: application/json" -d '{"nome":"","preco":-1}'   # 400
curl -i http://localhost:5000/produtos/999           # 404
```

## Erros comuns a apontar
- `List<Produto>` como campo estático solto no controller em vez de service injetado.
- Esquecer o `AddSingleton` no Program → `InvalidOperationException` (service não registrado).
- Retornar 200 no DELETE em vez de 204, ou mensagem no corpo do 204 (204 não tem corpo).
- Id fixo igual a 1 em todos os POSTs (colisão).
- Validação "à mão" no controller em vez do service lançar exceção/regra.
- `AddScoped` vs `AddSingleton` — com Scoped, a lista pode zerar entre requisições dependendo do setup; aqui Singleton é o correto para manter estado em memória.

## Padrão de feedback
Este exercício cria o esqueleto que será usado até o fim da trilha (controller →
service → dados). Reforçar a separação de camadas: perguntar "se amanhã trocarmos
a lista por um banco, o que muda no controller?" — a resposta certa é "nada".

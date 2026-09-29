# AGENTS.md — Correção do Exercício 22 (CRUD de Produtos)

## Critérios de correção
1. A aplicação sobe sem erros e expõe os 5 endpoints do CRUD sob `/produtos`.
2. Existe separação real de camadas: `ProdutoController` (HTTP) e `ProdutoService` anotado com `@Service` detendo a lista em memória e as regras; o controller não acessa a lista diretamente.
3. `POST /produtos` gera o id automaticamente (sequencial) e retorna **201** com o JSON do produto criado.
4. `GET /produtos/{id}` retorna **200** com o produto quando existe e **404** quando não existe.
5. `PUT /produtos/{id}` atualiza mantendo o mesmo id; retorna **200** ou **404** conforme a existência.
6. `DELETE /produtos/{id}` retorna **204 No Content** ao remover e **404** quando o id não existe.
7. O controller usa `ResponseEntity` (ou abordagem equivalente) em vez de retornar apenas o objeto para todos os casos.
8. O service usa `Optional` ou equivalente para comunicar "não encontrado" ao controller, sem try/catch genérico desnecessário.

## Como verificar

```bash
./mvnw spring-boot:run

# Criar e verificar 201
curl -i -X POST "http://localhost:8080/produtos" \
  -H "Content-Type: application/json" \
  -d '{"nome":"Feijão 1kg","preco":8.50,"quantidade":100}'
# Esperado: HTTP 201, corpo com id gerado

# Lista
curl "http://localhost:8080/produtos"
# Esperado: lista com o produto criado

# 404 na busca
curl -i "http://localhost:8080/produtos/999"
# Esperado: HTTP 404

# PUT em id inexistente
curl -i -X PUT "http://localhost:8080/produtos/999" \
  -H "Content-Type: application/json" \
  -d '{"nome":"X","preco":1.0,"quantidade":1}'
# Esperado: HTTP 404

# DELETE e depois DELETE de novo
curl -i -X DELETE "http://localhost:8080/produtos/1"   # Esperado: 204
curl -i -X DELETE "http://localhost:8080/produtos/1"   # Esperado: 404
```

## Erros comuns a apontar
- Retornar 200 em tudo (inclusive em criação e exclusão), ignorando a semântica HTTP.
- Aceitar o `id` enviado pelo cliente no POST em vez de gerá-lo no servidor.
- Colocar a lógica de busca/remoção dentro do controller, sem camada de service.
- Retornar o produto removido no DELETE com corpo, em vez de 204 vazio.
- PUT criando um novo produto quando o id não existe (comportamento não pedido; o correto aqui é 404).
- Lista declarada como variável local ou recriada, perdendo dados entre requisições.

## Padrão de feedback
Muito bem — um CRUD completo é o coração de quase toda API real, e você já está usando os status HTTP como um profissional. O foco didático aqui foi a separação Controller/Service e a semântica dos status (201, 204, 404). Se algum ponto ficou faltando, ajuste só o critério indicado; no próximo exercício essa mesma estrutura ganhará um banco de dados de verdade, então vale deixar a base sólida.

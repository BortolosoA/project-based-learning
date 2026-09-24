# Exercício 22 — API REST de Produtos (em memória)

## Conceito
Construir um CRUD REST completo com arquitetura em camadas (Controller → Service) usando uma lista em memória como "banco de dados", retornando `ResponseEntity` com os status HTTP corretos para cada situação.

## Enunciado
Uma loja de bairro quer modernizar o controle de estoque. Antes de comprar um banco de dados, o dono pediu um protótipo: uma API REST que gerencie produtos enquanto o servidor estiver no ar.

Crie uma API para o recurso **Produto**, com os atributos: `id` (Long, gerado automaticamente), `nome` (String), `preco` (double/BigDecimal) e `quantidade` (int).

Endpoints obrigatórios:

- `GET /produtos` → lista todos os produtos (200).
- `GET /produtos/{id}` → busca por id; 200 se existir, 404 se não existir.
- `POST /produtos` → cria um produto; retorna 201 com o produto criado (com id gerado).
- `PUT /produtos/{id}` → atualiza todos os campos de um produto existente; 200 se atualizado, 404 se o id não existir.
- `DELETE /produtos/{id}` → remove; 204 (sem conteúdo) se removido, 404 se o id não existir.

Arquitetura: separe as responsabilidades em duas camadas — `ProdutoController` (lida com HTTP) e `ProdutoService` (anotado com `@Service`, guarda a lista em memória e contém as regras: gerar ids sequenciais, buscar, atualizar, remover). Nos endpoints que podem dar 404, o service pode retornar `Optional<Produto>` e o controller decide o status.

## Requisitos
1. Projeto Spring Boot 3.x, Java 17+, dependência `spring-boot-starter-web`.
2. Classe `Produto` com os 4 atributos (id, nome, preco, quantidade).
3. Camada `Service` (`@Service`) com a lista em memória e toda a lógica; o controller não manipula a lista diretamente.
4. Ids gerados automaticamente de forma sequencial (ex.: contador no service).
5. Uso de `ResponseEntity` com os status corretos: 200, 201, 204 e 404.
6. PUT substitui os dados do produto mantendo o mesmo id.

## Exemplo de uso

```bash
# Criar produto
curl -X POST "http://localhost:8080/produtos" \
  -H "Content-Type: application/json" \
  -d '{"nome":"Arroz 5kg","preco":29.90,"quantidade":50}'
# Resposta: 201 Created — {"id":1,"nome":"Arroz 5kg","preco":29.90,"quantidade":50}

# Listar todos
curl "http://localhost:8080/produtos"
# Resposta: 200 OK — [{"id":1,"nome":"Arroz 5kg","preco":29.90,"quantidade":50}]

# Buscar por id existente
curl -i "http://localhost:8080/produtos/1"
# Resposta: 200 OK — {"id":1,"nome":"Arroz 5kg",...}

# Buscar por id inexistente
curl -i "http://localhost:8080/produtos/99"
# Resposta: 404 Not Found

# Atualizar
curl -X PUT "http://localhost:8080/produtos/1" \
  -H "Content-Type: application/json" \
  -d '{"nome":"Arroz 5kg (promoção)","preco":24.90,"quantidade":45}'
# Resposta: 200 OK — {"id":1,"nome":"Arroz 5kg (promoção)","preco":24.90,"quantidade":45}

# Remover
curl -i -X DELETE "http://localhost:8080/produtos/1"
# Resposta: 204 No Content

curl -i -X DELETE "http://localhost:8080/produtos/1"
# Resposta: 404 Not Found
```

## Como executar

```bash
# Gerar o projeto (site): https://start.spring.io
#   Maven | Java | Spring Boot 3.x | Java 17 | Dependência: Spring Web

# Ou pela linha de comando:
curl https://start.spring.io/starter.tgz \
  -d dependencies=web \
  -d javaVersion=17 \
  -d name=ex22-api-crud-produtos | tar -xzvf -

# Rodar
./mvnw spring-boot:run

# Testar com os curls acima ou com o Postman.
```

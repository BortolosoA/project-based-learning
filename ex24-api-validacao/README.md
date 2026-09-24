# Exercício 24 — API Profissional: Validação e Erros

## Conceito
Elevar a API a nível profissional: validar a entrada com Bean Validation (Jakarta), receber DTOs separados da entidade e padronizar as respostas de erro com `@ControllerAdvice` e `@ExceptionHandler`.

## Enunciado
A loja cresceu e outros sistemas começaram a consumir a API de produtos. Problema: estão chegando cadastros com nome vazio, preço negativo e e-mail de fornecedor inválido, e quando algo dá errado a API responde aquela tela de erro genérica do Spring. O time pediu duas coisas: **barrar dados inválidos na entrada** e **responder erros num formato padronizado e amigável**.

Evolua a API de produtos (exercício 23) adicionando:

1. **DTOs**: crie `ProdutoRequest` (dados de entrada: nome, preco, quantidade, emailFornecedor) e `ProdutoResponse` (dados de saída: id, nome, preco, quantidade). O controller recebe `ProdutoRequest` e devolve `ProdutoResponse` — a entidade `Produto` nunca aparece exposta na API.
2. **Bean Validation** no `ProdutoRequest`:
   - `nome`: `@NotBlank` (mensagem: "Nome é obrigatório");
   - `preco`: `@NotNull` + `@Positive` (mensagem: "Preço deve ser maior que zero");
   - `quantidade`: `@NotNull` + `@PositiveOrZero`;
   - `emailFornecedor`: `@NotBlank` + `@Email` (mensagem: "E-mail do fornecedor inválido").
   Use `@Valid` no parâmetro `@RequestBody` dos endpoints POST e PUT.
3. **Erro padronizado**: crie um `@RestControllerAdvice` (ou `@ControllerAdvice`) com `@ExceptionHandler` para:
   - `MethodArgumentNotValidException` → 400 com lista de erros por campo;
   - produto não encontrado → 404 (crie uma exceção customizada, ex.: `ProdutoNaoEncontradoException`);
   - formato de erro (classe/record `ErroResponse`): `{ "timestamp": ..., "status": ..., "mensagem": ..., "erros": [ { "campo": ..., "mensagem": ... } ] }` (a lista `erros` pode ser vazia/ausente no caso 404).

## Requisitos
1. Dependências: `spring-boot-starter-web`, `spring-boot-starter-data-jpa`, `h2` e `spring-boot-starter-validation`.
2. DTOs `ProdutoRequest` e `ProdutoResponse` distintos da entidade; conversão DTO ↔ entidade feita no service (ou em mapper próprio).
3. Anotações de validação (`@NotBlank`, `@Positive`, `@PositiveOrZero`, `@Email`) com mensagens em português.
4. `@Valid` nos endpoints de criação e atualização.
5. `@ControllerAdvice` centralizando os erros com corpo padronizado contendo `timestamp`, `status`, `mensagem` e `erros` de campo.
6. Requisição inválida retorna 400 **sem** stacktrace e **sem** a tela de erro padrão do Spring.
7. O CRUD válido continua funcionando normalmente (201/200/204/404).

## Exemplo de uso

```bash
# Criação inválida: nome vazio, preço negativo e e-mail errado
curl -i -X POST "http://localhost:8080/produtos" \
  -H "Content-Type: application/json" \
  -d '{"nome":"","preco":-5,"quantidade":10,"emailFornecedor":"nao-e-email"}'
# Resposta: 400 Bad Request
# {
#   "timestamp": "2026-09-23T10:15:30",
#   "status": 400,
#   "mensagem": "Dados inválidos",
#   "erros": [
#     {"campo":"nome","mensagem":"Nome é obrigatório"},
#     {"campo":"preco","mensagem":"Preço deve ser maior que zero"},
#     {"campo":"emailFornecedor","mensagem":"E-mail do fornecedor inválido"}
#   ]
# }

# Criação válida
curl -i -X POST "http://localhost:8080/produtos" \
  -H "Content-Type: application/json" \
  -d '{"nome":"Café 500g","preco":18.90,"quantidade":30,"emailFornecedor":"fornecedor@cafe.com"}'
# Resposta: 201 Created — {"id":1,"nome":"Café 500g","preco":18.90,"quantidade":30}
# (note que a resposta NÃO inclui emailFornecedor, pois é ProdutoResponse)

# Id inexistente
curl -i "http://localhost:8080/produtos/999"
# Resposta: 404 com corpo padronizado
# {"timestamp":"...","status":404,"mensagem":"Produto 999 não encontrado","erros":[]}
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
  -d name=ex24-api-validacao | tar -xzvf -

# Rodar
./mvnw spring-boot:run

# Testar com os curls acima (repare no -i para conferir os status).
```

# Exercício 24 — API Profissional (Validação, DTOs e Erros Padronizados)

## Conceito
Deixar a API "empresa-grade": validação com **DataAnnotations** (`[Required]`,
`[Range]`, `[StringLength]`), **DTOs** de entrada e saída (records), `ProblemDetails`
e **middleware de tratamento global de exceções**.

## Enunciado
Evolua a API de produtos (ex23) para o padrão profissional:

1. **DTOs separados** — a entidade do EF Core nunca é exposta/aceita diretamente:
   - `CriarProdutoRequest` (Nome, Preco, Estoque) para o POST.
   - `AtualizarProdutoRequest` para o PUT.
   - `ProdutoResponse` (Id, Nome, Preco, Estoque) para todas as saídas.
2. **Validação** — DataAnnotations nos DTOs:
   - `Nome`: `[Required]` + `[StringLength(100, MinimumLength = 2)]`
   - `Preco`: `[Range(0.01, 999_999)]`
   - `Estoque`: `[Range(0, 100_000)]`
   - Payload inválido → **400** com `ProblemDetails` listando os campos com erro
     (o `[ApiController]` já faz isso automaticamente — confira o formato!).
3. **Erros padronizados** — middleware de exceção global (`app.UseExceptionHandler`
   ou middleware customizado) que captura qualquer exceção não tratada e responde
   `ProblemDetails` com status 500 — **nunca stacktrace vazando** na resposta.
4. **Regras tipadas** — `ProdutoService` lança exceções de negócio:
   - `ProdutoNaoEncontradoException` → middleware/controller mapeia para **404**.
   - `EstoqueInsuficienteException` → mapeia para **409** (Conflict).
5. **Documentação** — `ProducesResponseType` nos actions, deixando o Swagger
   mostrando as respostas 200/201/400/404/409.

## Requisitos
- Nenhuma entidade EF Core aparece no JSON de resposta (sempre DTOs).
- Toda resposta de erro segue o mesmo formato (`ProblemDetails`: type, title, status, detail/errors).
- Validador de e-mail? Ainda não temos usuários — foque nos produtos.

## Exemplo de uso
```bash
# Payload inválido → 400 com ProblemDetails apontando os campos
curl -i -X POST http://localhost:5000/produtos \
  -H "Content-Type: application/json" \
  -d '{"nome":"","preco":-1}'
# {
#   "type": "https://tools.ietf.org/html/rfc9110#section-15.5.1",
#   "title": "One or more validation errors occurred.",
#   "status": 400,
#   "errors": { "Nome": ["The Nome field is not valid."], "Preco": [...] }
# }

# Id inexistente → 404 padronizado
curl -i http://localhost:5000/produtos/999
```

## Como executar
```bash
dotnet run --urls http://localhost:5000
# Swagger em http://localhost:5000/swagger — confira os status codes documentados
```

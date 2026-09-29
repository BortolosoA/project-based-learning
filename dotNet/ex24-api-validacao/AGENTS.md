# AGENTS.md — Correção do Exercício 24 (Validação, DTOs e Erros)

## Critérios de correção
1. DTOs de entrada e saída separados — nenhuma entidade EF Core é serializada na resposta.
2. DataAnnotations funcionando: payload inválido → 400 com ProblemDetails listando os campos.
3. Middleware de exceção global responde 500 com ProblemDetails (sem stacktrace no corpo).
4. Exceções de negócio mapeadas: `ProdutoNaoEncontradoException` → 404; `EstoqueInsuficienteException` → 409.
5. `ProducesResponseType` documentando os status codes no Swagger.

## Como verificar
```bash
dotnet run --urls http://localhost:5000
```
```bash
# 400 com erros de validação
curl -s -i -X POST http://localhost:5000/produtos -H "Content-Type: application/json" -d '{"nome":"","preco":-1}' | head -1
# HTTP/1.1 400

# corpo é ProblemDetails (json com "title" e "errors")
curl -s -X POST http://localhost:5000/produtos -H "Content-Type: application/json" -d '{"nome":"","preco":-1}' | jq .title
# "One or more validation errors occurred."

# 404 padronizado
curl -s -i http://localhost:5000/produtos/999 | head -1    # HTTP/1.1 404
curl -s http://localhost:5000/produtos/999 | jq .status   # 404

# resposta normal usa DTO (não deve ter campos da entidade além do response)
curl -s -X POST http://localhost:5000/produtos -H "Content-Type: application/json" -d '{"nome":"Mouse","preco":79.9,"estoque":10}' | jq keys
# ["estoque", "id", "nome", "preco"]
```

## Erros comuns a apontar
- Usar a entidade EF direto no POST/GET (sem DTO) — reprova o item 1.
- Validação "na mão" com ifs no controller em vez de DataAnnotations + `[ApiController]`.
- Exceção de negócio chegando ao cliente como 500 (sem mapeamento) — falta o handler.
- Stacktrace no corpo do 500 (modo Developer Exception Page "vazando") — em produção isso é vulnerabilidade.
- Mapear tudo para 400 — 404 e 409 são semanticamente diferentes e o Swagger deve mostrar.

## Padrão de feedback
Nível de detalhe que separa "funciona" de "está pronto para um code review real".
Se os DTOs + ProblemDetails estiverem certos, elogiar: a maioria dos juniors não
faz isso. Apontar no máximo 2-3 melhorias priorizadas por impacto.

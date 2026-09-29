# Exercício 27 — Testes de API (Integração + Cobertura)

## Conceito

Testar a API **de verdade**: subir ela em memória com `WebApplicationFactory<Program>`
e disparar requisições HTTP reais contra os endpoints — sem porta, sem processo externo.
Além disso: **Moq** para isolar services em testes unitários e **cobertura de código**
com `coverlet` para saber o que está (e não está) sendo testado.

## Enunciado

Adicione testes à sua API de produtos (a do ex24 é a ideal, com validação e DTOs):

1. **Testes unitários** — `ProdutoService` testado isoladamente:
   - criar com payload válido retorna produto com id.
   - preço inválido lança a exceção de regra.
   - buscar id inexistente lança `ProdutoNaoEncontradoException`.
   - use **Moq** para simular o `AppDbContext` ou qualquer dependência externa
     (dica: se o service usa o contexto direto, crie uma interface `IProdutoRepositorio`
     para poder mockar — refatorar para testar é parte do exercício).

2. **Testes de integração** — com `WebApplicationFactory<Program>`:
   - `POST /produtos` com payload válido → **201** e o JSON contém o produto criado.
   - `POST /produtos` com payload inválido → **400** com ProblemDetails.
   - `GET /produtos` → **200** e lista contém o que foi criado.
   - `GET /produtos/999` → **404**.
   - `DELETE /produtos/{id}` existente → **204**.
   - Use um banco de teste: SQLite in-memory (`DataSource=:memory:`) ou
     `Microsoft.EntityFrameworkCore.InMemory` — nunca o banco de dev.

3. **Teste de autorização** (se veio do ex26): endpoint protegido sem token → **401**.

4. **Cobertura**: rode `dotnet test --collect:"XPlat Code Coverage"` e verifique o
   `coverage.cobertura.xml`. Meta: **60%+ nas classes de service**.

## Requisitos

- `dotnet test` 100% verde.
- Testes de integração não dependem de banco externo rodando nem de rede.
- Ao menos um teste para cada status code importante (201/400/204/404).
- Classe auxiliar `ApiFixture : WebApplicationFactory<Program>` com o ambiente de teste configurado.

## Como executar

```bash
dotnet add package Microsoft.AspNetCore.Mvc.Testing
dotnet add package Moq
dotnet test
dotnet test --collect:"XPlat Code Coverage"
# resultado em Tests/TestResults/*/coverage.cobertura.xml
# (extensão "Coverage Gutters" no VS Code mostra linha a linha)
```

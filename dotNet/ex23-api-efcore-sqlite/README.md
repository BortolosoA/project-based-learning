# Exercício 23 — API de Produtos com Banco de Dados (EF Core + SQLite)

## Conceito
**Entity Framework Core**: o ORM do .NET (equivalente do JPA/Hibernate no mundo Java).
`DbContext`, `DbSet`, migrations e SQL real rodando no SQLite — os dados agora
sobrevivem a restarts.

## Enunciado
Evolua a API de produtos do ex22 substituindo a lista em memória pelo EF Core + SQLite:

1. `AppDbContext : DbContext` com `DbSet<Produto>` — configure a connection string
   `Data Source=produtos.db` (via `appsettings.json`).
2. Entidade `Produto` mapeada: `Id` gerado pelo banco, `Nome`, `Preco`, `Estoque`.
3. Migrations:
   ```bash
   dotnet ef migrations add Inicial
   dotnet ef database update
   ```
4. Troque o repositório do service pelo `AppDbContext` (injetado) — os controllers
   continuam iguais por fora.
5. Seed: ao iniciar, se a tabela estiver vazia, insira 3 produtos (pode ser no
   próprio `Program.cs` com um escopo do contexto).
6. Remova o `AddSingleton` — o contexto é registrado com `AddDbContext`
   (lifetime Scoped, que é o padrão).

## Requisitos
- Pacotes: `Microsoft.EntityFrameworkCore.Sqlite` e `Microsoft.EntityFrameworkCore.Design`.
- Ferramenta instalada: `dotnet tool install --global dotnet-ef`.
- Prove a persistência: crie um produto, **reinicie** a API e confirme que ele continua lá.
- Abra o `produtos.db` (extensão SQLite no VS Code, ou `sqlite3 produtos.db "SELECT * FROM Produtos;"`)
  e veja a tabela criada pelas migrations.

## Exemplo de uso
```bash
dotnet ef migrations add Inicial
dotnet ef database update
dotnet run --urls http://localhost:5000

curl http://localhost:5000/produtos
# 3 produtos do seed

curl -X POST http://localhost:5000/produtos -H "Content-Type: application/json" \
  -d '{"nome":"Teclado","preco":199.90,"estoque":7}'
# reinicie a API e liste de novo — o Teclado continua lá!
```

## Como executar
```bash
dotnet add package Microsoft.EntityFrameworkCore.Sqlite
dotnet add package Microsoft.EntityFrameworkCore.Design
dotnet ef migrations add Inicial
dotnet ef database update
dotnet run --urls http://localhost:5000
```

# AGENTS.md — Correção do Exercício 23 (EF Core + SQLite)

## Critérios de correção
1. `AppDbContext` com `DbSet<Produto>` e connection string no `appsettings.json`.
2. Migration gerada e aplicada: o arquivo `produtos.db` existe com a tabela `Produtos`
   (e a tabela `__EFMigrationsHistory`).
3. Service usa o contexto injetado (não há mais lista em memória).
4. `AddDbContext` no Program (não `AddSingleton` do contexto).
5. Seed de 3 produtos funciona na primeira execução.
6. Persistência real: produto criado sobrevive a restart da API.

## Como verificar
```bash
dotnet ef migrations add Inicial
dotnet ef database update
dotnet run --urls http://localhost:5000
```
```bash
# Seed
curl -s http://localhost:5000/produtos | jq length    # esperado: 3

# Persistência
curl -s -X POST http://localhost:5000/produtos -H "Content-Type: application/json" -d '{"nome":"Teclado","preco":199.9,"estoque":7}' > /dev/null
# reiniciar a API (Ctrl+C e dotnet run de novo)
curl -s http://localhost:5000/produtos | jq length    # esperado: 4

# Banco de verdade
sqlite3 produtos.db "SELECT Nome, Preco FROM Produtos;"
sqlite3 produtos.db "SELECT * FROM __EFMigrationsHistory;"
```

## Erros comuns a apontar
- `dotnet ef` não instalado ("Could not execute because the specified command...") — `dotnet tool install --global dotnet-ef`.
- Contexto sem construtor recebendo `DbContextOptions` → migrations não encontram o provider.
- `EnsureCreated()` convivendo com migrations — as duas abordagens brigam; com migrations, use `Database.Migrate()`.
- Contexto registrado como Singleton → não thread-safe, erro clássico ("A second operation started on this context").
- Seed rodando a cada request em vez de uma vez no startup.
- Esquecer de salvar: chamar `Add`/`Update`/`Remove` sem `SaveChangesAsync`.

## Padrão de feedback
O momento "uhu" deste exercício é matar a API, subir de novo e os dados continuarem
lá. Garantir que o aluno viu isso. Preparar o terreno: "no ex28 trocaremos o SQLite
por PostgreSQL dentro de um container — a única linha que muda é a connection string".

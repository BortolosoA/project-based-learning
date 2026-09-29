# Exercício 28 — API com PostgreSQL + Docker

## Conceito

Docker para desenvolvedores: **Dockerfile** multi-stage (build → publish → runtime),
**docker-compose.yml** orquestrando serviços e **PostgreSQL** com EF Core
(`Npgsql`) — a API roda em qualquer máquina com um comando, sem "na minha máquina funciona".

## Enunciado

Sua API de produtos (ex24/25) agora roda em containers:

1. **Dockerfile multi-stage**:
   - Stage 1 (build): `mcr.microsoft.com/dotnet/sdk:8.0` → `dotnet restore` + `dotnet publish -c Release -o /app`.
   - Stage 2 (runtime): `mcr.microsoft.com/dotnet/aspnet:8.0` → copia o publicado e expõe a porta.
2. **docker-compose.yml** com dois serviços:
   - `db`: imagem `postgres:16` com healthcheck, volume persistente e variáveis (POSTGRES_USER/PASSWORD/DB).
   - `api`: build do Dockerfile, `depends_on` o db (com `condition: service_healthy`),
     connection string vinda de variável de ambiente.
3. **EF Core + Npgsql**:
   - `dotnet add package Npgsql.EntityFrameworkCore.PostgreSQL`
   - connection string: `Host=db;Port=5432;Database=produtos;Username=postgres;Password=postgres`
   - migrations aplicadas na subida do container (rode `dotnet ef migrations script`
     e aplique via entrypoint, ou `Database.Migrate()` no startup).
4. **Config por ambiente**: a connection string lê `ConnectionStrings__DefaultConnection`
   (variável de ambiente) — nada de senha hardcoded no appsettings.
5. **Um comando sobe tudo**: `docker compose up --build` → API em
   `http://localhost:5000` respondendo e dados persistindo no volume do Postgres.

## Requisitos

- `docker compose up --build` funciona em um clone limpo do projeto.
- Postgres healthcheck antes da API iniciar (não pode dar "connection refused" na subida).
- Dados sobrevivem a `docker compose down` (volume nomeado) — mas um `down -v` reseta tudo.
- Migrations aplicadas automaticamente (o banco já nasce com as tabelas).
- `.dockerignore` com `bin/`, `obj/` (imagem menor e build sem surpresas).

## Exemplo de uso

```bash
docker compose up --build
# aguardar a API subir, depois:
curl http://localhost:5000/produtos

# dados no postgres, direto do container:
docker compose exec db psql -U postgres -d produtos -c "\dt"
docker compose exec db psql -U postgres -d produtos -c "SELECT id, nome FROM \"Produtos\";"

# persistência:
docker compose down        # sem -v
docker compose up         # os dados continuam lá
```

## Como executar

```bash
docker compose up --build
# API: http://localhost:5000
```

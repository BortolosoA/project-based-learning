# Trilha de Aprendizado .NET/C# — Project-Based Learning

Esta trilha usa a metodologia **Project-Based Learning (PBL)**: cada exercício é um
mini-projeto com um problema real para resolver, e não apenas um trecho de código
para repetir. A dificuldade aumenta gradualmente.

## Regras da casa
- Cada pasta `exNN-nome` contém:
  - `README.md` → o enunciado do projeto (o problema a resolver).
  - `AGENTS.md` → critérios de correção (usado pelo agente/tutor para avaliar sua solução).
- Tente resolver **antes** de pedir dicas. Errar e tentar outra vez faz parte.
- Só avance quando o exercício atual estiver funcionando e corrigido.

---

## Fase 1 — Fundamentos de C# (ex01–ex15)
Variáveis, operadores, condicionais, laços, métodos, arrays, strings, POO e
um projeto final de console (Sistema de Biblioteca).

## Fase 2 — C# Intermediário (ex16–ex20)
C# "de verdade" como usado no mercado, ainda no console, agora com testes
automatizados e ferramentas profissionais.

| # | Projeto | Conceitos |
|---|---------|-----------|
| 16 | Caixa Eletrônico seguro | Exceções (try/catch, throw, exceções customizadas) |
| 17 | Sistema de Pagamentos | Interfaces, classes abstratas, generics, polimorfismo |
| 18 | Analisador de Vendas | Lambdas, LINQ, GroupBy, nullable types |
| 19 | Gerenciador de Tarefas com persistência | I/O de arquivos, JSON (System.Text.Json), datas (DateTime) |
| 20 | Conversor de Moedas com testes | xUnit, TDD, consumo de API HTTP |

## Fase 3 — ASP.NET Core do Zero (ex21–ex25)
O framework web mais usado do ecossistema .NET.

| # | Projeto | Conceitos |
|---|---------|-----------|
| 21 | Primeira API REST | ASP.NET Core, controllers, HTTP verbs, JSON |
| 22 | API de Produtos (CRUD em memória) | REST completo, services, ActionResult, status codes |
| 23 | API com banco de dados SQLite | Entity Framework Core, DbContext, migrations |
| 24 | API profissional | DataAnnotations, DTOs (records), tratamento global de erros |
| 25 | API de Pedidos e Clientes | Relacionamentos EF Core (1:N), Include, paginação |

## Fase 4 — Tecnologias de Mercado (ex26–ex30)
O que as vagas de emprego pedem.

| # | Projeto | Conceitos |
|---|---------|-----------|
| 26 | API com autenticação | JWT Bearer, roles, hash de senha |
| 27 | Testes de API | xUnit, Moq, WebApplicationFactory, cobertura |
| 28 | API com PostgreSQL + Docker | Docker, Docker Compose, migrations (EF Core) |
| 29 | Sistema com mensageria | RabbitMQ, produtor/consumidor, arquitetura orientada a eventos |
| 30 | **Projeto Final**: API de E-commerce | Tudo junto: ASP.NET Core + EF Core + JWT + Docker + testes + Swagger |

---

## Java → .NET: dicionário de bolso

Se você já viu a trilha Java, esta tabela ajuda a traduzir os conceitos:

| Mundo Java | Mundo .NET |
|------------|------------|
| `javac` / `java` | `dotnet build` / `dotnet run` |
| Maven / Gradle | NuGet + `dotnet` CLI |
| Spring Boot | ASP.NET Core |
| Spring Data JPA | Entity Framework Core |
| H2 | SQLite |
| Bean Validation | DataAnnotations |
| JUnit 5 | xUnit |
| Mockito | Moq |
| Jackson | System.Text.Json |
| Streams API | LINQ |
| Flyway | EF Core Migrations |
| Swagger (springdoc) | Swashbuckle (já vem no template) |
| Spring Security + JWT | JWT Bearer Authentication + Authorization |

## Como executar os exercícios

- **Fase 1 (ex01–ex15) e ex16–ex19:** `dotnet new console` + `dotnet run`.
- **A partir do ex20:** projetos com testes: `dotnet test`.
- **ASP.NET Core:** `dotnet new webapi --use-controllers` + `dotnet run`
  (instruções dentro de cada exercício).
- Para as APIs, use sempre `dotnet run --urls http://localhost:5000` — assim os
  exemplos de `curl` dos enunciados funcionam sem surpresas de porta.

## Pré-requisitos
- .NET SDK 8.0 ou superior (confira com `dotnet --version`).
- Docker e Docker Compose (ex28+).
- Um cliente HTTP: Postman, Insomnia, Thunder Client ou `curl`.

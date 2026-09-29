# Learn — Trilhas de Project-Based Learning

Repositório de trilhas de aprendizado baseadas em **Project-Based Learning (PBL)**:
em vez de ler teoria e copiar código de tutorial, você resolve **problemas reais**
em mini-projetos, um por vez, com dificuldade crescente.

## Trilhas disponíveis

| Trilha | Conteúdo | Roadmap |
|--------|----------|---------|
| ☕ **Java** | Fundamentos → Spring Boot → API de E-commerce | [Java/ROADMAP.md](Java/ROADMAP.md) |
| 🔷 **.NET/C#** | Fundamentos → ASP.NET Core → API de E-commerce | [dotNet/ROADMAP.md](dotNet/ROADMAP.md) |

As duas trilhas são paralelas: mesmos 30 projetos, mesmos conceitos, ecossistemas
diferentes. Faça uma ou as duas — a segunda sai muito mais rápido, porque os
conceitos se repetem (veja o dicionário Java ↔ .NET no roadmap).

## Como funciona

Cada pasta `exNN-nome` é um mini-projeto com dois arquivos:

- **`README.md`** → o enunciado: o problema a resolver e a saída esperada.
- **`AGENTS.md`** → os critérios de correção, usados por um agente de IA
  (como o opencode) para avaliar sua solução como um tutor.

O fluxo de estudo é:

1. Abra o `README.md` do exercício e leia o problema.
2. **Tente resolver sozinho antes de pedir dicas.** Errar e tentar de novo faz
   parte do processo — é assim que o conteúdo fixa.
3. Peça para o agente corrigir seu exercício (ele segue o `AGENTS.md` da pasta).
4. Só avance para o próximo exercício quando o atual estiver funcionando
   e corrigido.

## Estrutura das trilhas

As 30 fases se dividem em 4 fases (detalhes nos roadmaps):

- **Fase 1 (ex01–ex15) — Fundamentos:** variáveis, condicionais, laços, métodos,
  arrays, strings, POO e um projeto final de console (Sistema de Biblioteca).
- **Fase 2 (ex16–ex20) — Intermediário:** exceções, interfaces, generics,
  streams/LINQ, arquivos e JSON, testes automatizados e build profissional.
- **Fase 3 (ex21–ex25) — APIs REST do zero:** primeira API, CRUD, banco de
  dados, validação e relacionamentos (Spring Boot ou ASP.NET Core).
- **Fase 4 (ex26–ex30) — Tecnologias de mercado:** autenticação JWT, testes de
  API, Docker + PostgreSQL, mensageria com RabbitMQ e o **projeto final**:
  uma API de e-commerce completa.

## Pré-requisitos

- **Java:** JDK 17+ e Maven (a partir do ex20).
- **.NET:** .NET SDK 8.0+ (`dotnet --version`).
- Ambas: Docker e Docker Compose (ex28+) e um cliente HTTP (`curl`, Postman…).

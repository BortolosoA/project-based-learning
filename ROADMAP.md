# Trilha de Aprendizado Java — Project-Based Learning

Esta trilha usa a metodologia **Project-Based Learning (PBL)**: cada exercício é um
mini-projeto com um problema real para resolver, e não apenas um trecho de código
para repetir. A dificuldade aumenta gradualmente.

## Regras da casa
- Cada pasta `exNN-nome` contém:
  - `README.md` → o enunciado do projeto (o problema a resolver).
  - `AGENTS.md` → critérios de correção (usado pelo agente/tutor para avaliar sua solução).
- Tente resolver **antes** de pedir dicas. Errado e outra vez faz parte.
- Só avance quando o exercício atual estiver funcionando e corrigido.

---

## Fase 1 — Fundamentos de Java (ex01–ex15) ✅
Variáveis, operadores, condicionais, laços, métodos, arrays, strings, POO e
um projeto final de console (Sistema de Biblioteca).

## Fase 2 — Java Intermediário (ex16–ex20)
Java "de verdade" como usado no mercado, ainda no console, agora com build
profissional.

| # | Projeto | Conceitos |
|---|---------|-----------|
| 16 | Caixa Eletrônico seguro | Exceções (checked/unchecked, try/catch, exceções customizadas) |
| 17 | Sistema de Pagamentos | Interfaces, classes abstratas, generics, polimorfismo |
| 18 | Analisador de Vendas | Lambdas, Streams API, Map/Set, Optional |
| 19 | Gerenciador de Tarefas com persistência | I/O de arquivos, JSON (Jackson/Gson), datas (java.time) |
| 20 | Conversor de Moedas com testes | Maven, JUnit 5, TDD, consumo de API HTTP |

## Fase 3 — Spring Boot do Zero (ex21–ex25)
O framework mais usado no mercado Java.

| # | Projeto | Conceitos |
|---|---------|-----------|
| 21 | Primeira API REST | Spring Boot, `@RestController`, HTTP verbs, JSON |
| 22 | API de Produtos (CRUD em memória) | REST completo, `@Service`, ResponseEntity, status codes |
| 23 | API com banco de dados H2 | Spring Data JPA, entidades, repositories |
| 24 | API profissional | Bean Validation, tratamento global de exceções, DTOs |
| 25 | API de Pedidos e Clientes | Relacionamentos JPA (OneToMany/ManyToOne), paginação |

## Fase 4 — Tecnologias de Mercado (ex26–ex30)
O que as vagas de emprego pedem.

| # | Projeto | Conceitos |
|---|---------|-----------|
| 26 | API com autenticação | Spring Security, JWT, roles |
| 27 | Testes de API | JUnit, Mockito, testes de integração, cobertura |
| 28 | API com PostgreSQL + Docker | Docker, Docker Compose, profiles, migrations (Flyway) |
| 29 | Sistema com mensageria | RabbitMQ, produtor/consumidor, arquitetura orientada a eventos |
| 30 | **Projeto Final**: API de E-commerce | Tudo junto: Spring Boot + JPA + Security + Docker + testes + documentação (Swagger) |

---

## Como executar os exercícios

- **Fase 1 e 2 (ex16–ex19):** `javac *.java && java Main` (igual à fase 1).
- **A partir do ex20:** usa Maven (`mvn test`, `mvn spring-boot:run`).
- **Spring Boot:** gere o esqueleto em [start.spring.io](https://start.spring.io)
  ou com `spring init` (instruções dentro de cada exercício).

## Pré-requisitos para as fases 3 e 4
- Java 17+ e Maven instalados.
- Docker e Docker Compose (ex28+).
- Um cliente HTTP: Postman, Insomnia ou `curl`.

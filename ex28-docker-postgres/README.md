# Exercício 28 — API com PostgreSQL e Docker

## Conceito

Na sua máquina funciona com H2 — e na máquina do colega? E no servidor? Neste exercício você vai resolver o clássico "na minha máquina funciona" usando **Docker** e **Docker Compose**: sua API Spring Boot e um banco **PostgreSQL** sobem juntos com um único comando, em qualquer máquina. Você vai aprender:

- Como conectar o Spring Boot no PostgreSQL (driver, datasource).
- **Profiles do Spring** (`application-dev` para desenvolver com H2/local, `application-prod` para o container).
- **Flyway** para versionar o schema do banco com migrations SQL (nada de `spring.jpa.hibernate.ddl-auto=update` em produção).
- **Variáveis de ambiente** para configuração (senha do banco nunca fixada no código).
- `Dockerfile` da aplicação, `docker-compose.yml` orquestrando banco + API, e **healthcheck** para o banco só ser dado como pronto de fato.

## Enunciado

Você vai "profissionalizar" uma API simples de produtos (id, nome, preco — pode reaproveitar exercícios anteriores) para rodar em containers:

1. **Banco PostgreSQL real** em desenvolvimento e produção (o H2 passa a ser só para testes, se quiser).
2. **Flyway**: o schema nasce de migrations versionadas, ex.:
   - `src/main/resources/db/migration/V1__criar_tabela_produtos.sql` — `CREATE TABLE produto (...)`.
   - `V2__inserir_produtos_exemplo.sql` — inserts de exemplo (opcional).
   - `ddl-auto` deve ser `validate` ou `none` — o Hibernate **não** cria tabelas.
3. **Profiles**:
   - `application.properties` — configuração comum.
   - `application-dev.properties` — aponta para um Postgres local ou H2 (para você codar sem Docker).
   - `application-prod.properties` — lê host/usuário/senha do banco de **variáveis de ambiente** (`DB_URL`, `DB_USER`, `DB_PASSWORD`).
4. **Dockerfile** multi-stage da API (build com Maven + imagem final enxuta com JRE 17+, ex.: `eclipse-temurin:17-jre`). O `.jar` gerado por `mvn package` deve rodar dentro do container.
5. **docker-compose.yml** com dois serviços:
   - `db`: imagem `postgres:16` (ou 15), volume para persistência, variáveis `POSTGRES_DB/USER/PASSWORD` e **healthcheck** com `pg_isready`.
   - `api`: build do Dockerfile, porta `8080:8080`, `depends_on: db` com `condition: service_healthy`, e variáveis de ambiente conectando ao serviço `db`.
6. Subir tudo com **um comando**: `docker compose up --build` e a API funcionar em `http://localhost:8080/produtos`.

## Requisitos

- Java 17+, Maven, Docker e Docker Compose instalados.
- Dependência `org.postgresql:postgresql` e `org.flywaydb:flyway-core` (+ `flyway-database-postgresql` no Boot 3.2+) no `pom.xml`.
- Nenhuma senha hardcoded no código nem commitada em texto puro no properties de produção — usar `${DB_PASSWORD}` ou `env_file`.
- Migration V1 executa automaticamente na subida do container (ver tabela `flyway_schema_history` no banco).
- Dados sobrevivem a `docker compose down` (volume nomeado).
- Healthcheck: o container da API só inicia após o Postgres estar saudável.

## Exemplo de uso

Subindo o ambiente completo:

```bash
docker compose up --build
```

Saída esperada (trecho):

```
[+] Running 2/2
 ✔ Container ex28-db-1   Healthy
 ✔ Container ex28-api-1  Started
api-1  | ... Started ProdutosApplication in 4.2 seconds
api-1  | ... Successfully applied 2 migrations
```

Testando a API:

```bash
curl http://localhost:8080/produtos
```

```json
[{"id":1,"nome":"Notebook","preco":3500.00},{"id":2,"nome":"Mouse","preco":79.90}]
```

```bash
curl -X POST http://localhost:8080/produtos \
  -H "Content-Type: application/json" \
  -d '{"nome":"Teclado","preco":199.90}'
```

Conferindo as migrations e a persistência:

```bash
docker compose exec db psql -U postgres -d produtosdb -c "SELECT * FROM flyway_schema_history;"
docker compose down && docker compose up -d
curl http://localhost:8080/produtos   # dados ainda estão lá
```

## Como executar

```bash
# Ambiente completo (banco + API) — requer Docker
docker compose up --build

# Apenas desenvolvimento local, sem Docker (profile dev):
mvn spring-boot:run -Dspring-boot.run.profiles=dev

# Derrubar tudo e limpar volume (zera o banco):
docker compose down -v
```

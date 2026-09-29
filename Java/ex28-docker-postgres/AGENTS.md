# AGENTS.md — Correção do Exercício 28 (API com PostgreSQL e Docker)

## Critérios de correção

1. **`docker compose up --build` sobe tudo com um comando** e a API responde em `http://localhost:8080/produtos` sem intervenção manual.
2. **Flyway funciona**: existe pelo menos `V1__*.sql` criando as tabelas; a tabela `flyway_schema_history` aparece no Postgres; a aplicação **não** recria schema via Hibernate (`ddl-auto` é `validate` ou `none`).
3. **PostgreSQL é o banco de verdade** no container (não H2 disfarçado); dados persistem após `docker compose down` + `up` (volume nomeado configurado).
4. **Configuração por variáveis de ambiente**: host/usuário/senha do banco chegam à aplicação via `environment` no compose + placeholders `${...}` no properties — sem senha hardcoded no código.
5. **Profiles separados**: `application-dev` (local) e `application-prod` (container) existem e o correto é ativado por `SPRING_PROFILES_ACTIVE` no compose.
6. **Healthcheck real**: serviço `db` tem `healthcheck` com `pg_isready` e a API usa `depends_on` com `condition: service_healthy` (ou restart adequado) — a API não morre por "Connection refused" na primeira subida.
7. **Dockerfile funcional** (idealmente multi-stage): gera imagem que roda `java -jar` sem expor o Maven na imagem final.
8. Versões do Postgres pinadas (`postgres:16`), não `latest`.

## Como verificar

```bash
# 1. Subir do zero — esperado: db Healthy, api Started, sem stacktrace de conexão
docker compose down -v && docker compose up --build

# 2. API responde com dados — esperado JSON 200
curl -i http://localhost:8080/produtos

# 3. Migrations aplicadas — esperado linha(s) com V1, V2... e success = t
docker compose exec db psql -U postgres -d produtosdb -c "SELECT version, description, success FROM flyway_schema_history;"

# 4. Persistência: inserir, derrubar, subir, conferir
curl -X POST http://localhost:8080/produtos -H "Content-Type: application/json" -d '{"nome":"Monitor","preco":899.9}'
docker compose down && docker compose up -d && sleep 5
curl http://localhost:8080/produtos   # Monitor deve estar na lista

# 5. Sem senha em texto puro no código
grep -rn "password" src/main/resources/ Dockerfile docker-compose.yml
```

Se o Docker não estiver disponível na máquina do corretor, aceitar validação estática do `docker-compose.yml` + `Dockerfile` + migrations + execução local com `mvn spring-boot:run` contra um Postgres local.

## Erros comuns a apontar

- `ddl-auto=update` ou `create-drop` convivendo com Flyway (o Hibernate cria a tabela antes e o Flyway falha — ou a migration nunca é testada de verdade).
- `depends_on` simples, sem healthcheck: a API sobe antes do Postgres e morre com `Connection refused`.
- Conectar em `localhost:5432` de dentro do container da API — dentro do compose o host do banco é o nome do serviço (`db`).
- Senha do banco commitada em texto puro sem usar variáveis de ambiente / `.env` no `.gitignore`.
- Migration editada depois de aplicada (checksum do Flyway quebra!) — migration imutável; criou errado, faz V2 corrigindo.
- Dockerfile que copia o jar com nome fixo que não bate com o artifactId/versão, ou que não limpa o build (imagem gigante com Maven).
- Nome de migration fora do padrão `V<n>__descricao.sql` (dois underscores), o que faz o Flyway ignorar.
- Expor a porta do Postgres (`5432:5432`) sem necessidade em produção — ok para debug, mas comentar o porquê.

## Padrão de feedback

A validação principal é binária: `docker compose up --build` de diretório limpo funcionou ou não. Se funcionou, elogie bastante — esse é o fluxo real de deploy local em times profissionais. Aponte no máximo 2 riscos (geralmente senha exposta e ausência de healthcheck) e explique o "porquê" de cada um com o cenário de falha concreto (ex.: "sem healthcheck, a primeira subida após `down -v` vai falhar intermitentemente").

# AGENTS.md — Correção do Exercício 28 (Docker + PostgreSQL)

## Critérios de correção

1. `Dockerfile` multi-stage (sdk → aspnet) com o app publicado — imagem final sem SDK.
2. `docker-compose.yml` com `db` (postgres:16 + healthcheck + volume) e `api`
   (`depends_on` com `condition: service_healthy`).
3. Connection string via variável de ambiente (`ConnectionStrings__DefaultConnection`) — sem senha hardcoded no código.
4. Migrations aplicadas automaticamente na subida (tabelas presentes no Postgres).
5. Clone limpo + `docker compose up --build` sobe tudo e a API responde em `http://localhost:5000`.
6. Dados persistem entre `down`/`up` (volume nomeado).

## Como verificar

```bash
# clone limpo sobe tudo
docker compose up --build -d
sleep 15   # aguardar subida
curl -s http://localhost:5000/produtos | jq length

# migrations no banco
docker compose exec db psql -U postgres -d produtos -c "\dt"
# deve listar __EFMigrationsHistory e as tabelas do domínio

# dados sobrevivem ao restart
curl -s -X POST http://localhost:5000/produtos -H "Content-Type: application/json" -d '{"nome":"Mouse","preco":79.9,"estoque":10}' > /dev/null
docker compose down
docker compose up -d && sleep 15
curl -s http://localhost:5000/produtos | jq length    # 1 (o Mouse sobreviveu)

# imagem enxuta
docker images | grep ex28   # runtime deve ser aspnet:8.0, não sdk:8.0
```

## Erros comuns a apontar

- API subindo antes do Postgres e quebrando ("connection refused") — falta healthcheck/condition.
- `localhost` na connection string dentro do container — entre containers o host é o
  nome do serviço (`Host=db`).
- Senha/postgres hardcoded no `appsettings.json` em vez de variável de ambiente.
- Stage único com SDK inteiro (imagem de 1GB+ para uma API simples).
- Migrations "no gato": rodar `dotnet ef database update` manualmente na máquina host
  não conta — o container tem que se autoconfigurar.
- Esquecer o `EXPOSE`/`ASPNETCORE_URLS=http://+:5000` — API responde dentro mas não fora.
- `.dockerignore` ausente: `COPY . .` levando `bin/`, `obj/` e o próprio `produtos.db` do dev.

## Padrão de feedback

"Reproduzível com um comando" é o critério profissional. Se um avaliador com Docker
instalado consegue clonar e rodar sem ler README, o exercício está perfeito. Apontar
no máximo 2-3 melhorias por impacto (tamanho de imagem, healthcheck, secrets).
Preparar o terreno: "no ex30, este compose ganha mais um serviço (RabbitMQ)".

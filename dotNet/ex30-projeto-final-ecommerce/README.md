# Exercício 30 — Projeto Final: API de E-commerce

## Conceito

Este é o projeto de **portfólio** que consolida toda a trilha. Você vai construir uma
API de e-commerce completa, do jeito que um time profissional faria: ASP.NET Core 8 +
EF Core + PostgreSQL + Docker + JWT + validação/DTOs + paginação + testes
automatizados + Swagger. O diferencial aqui não é só a tecnologia — é a
**apresentação**: o README do seu projeto será a primeira coisa que um recrutador vai
ler no seu GitHub. Trate este repositório como vitrine.

## Enunciado

Uma loja virtual precisa da API que sustentará seu site/app. Você é o desenvolvedor
back-end responsável por entregar a versão 1.0. Escopo:

**Domínio**: usuários (roles `Admin` e `Cliente`), produtos com estoque, carrinho de
compras e pedidos.

**Fluxo principal**: o cliente se cadastra, faz login, navega pelo catálogo, adiciona
produtos ao carrinho e finaliza o pedido — que dá baixa no estoque. Administradores
gerenciam o catálogo e visualizam todos os pedidos.

**Endpoints esperados** (mínimo):

| Método | Rota | Acesso | Descrição |
|--------|------|--------|-----------|
| POST | /auth/register | Público | Cadastro de usuário (vira Cliente) |
| POST | /auth/login | Público | Retorna JWT |
| GET | /produtos?pagina=1&tamanho=10&ordem=nome | Público | Catálogo paginado |
| GET | /produtos/{id} | Público | Detalhe do produto |
| POST / PUT / DELETE | /produtos/** | Admin | Gerenciar catálogo |
| GET | /carrinho | Cliente | Ver meu carrinho |
| POST | /carrinho/itens | Cliente | Adicionar item `{produtoId, quantidade}` |
| DELETE | /carrinho/itens/{id} | Cliente | Remover item |
| POST | /pedidos | Cliente | Finaliza o carrinho → vira pedido e dá baixa no estoque |
| GET | /pedidos | Cliente | Meus pedidos (paginado) |
| GET | /pedidos/{id} | Cliente (dono) ou Admin | Detalhe |
| PATCH | /pedidos/{id}/status | Admin | Atualizar status (Pendente → Pago → Enviado → Entregue, ou Cancelado) |

**Regras de negócio obrigatórias**:

- Não permitir adicionar ao carrinho nem finalizar pedido com quantidade maior que o
  estoque → 400/409 com mensagem clara.
- Finalizar pedido **decrementa o estoque** e esvazia o carrinho, tudo na mesma
  **transação** (uma `SaveChanges` no fim ou `TransactionScope`).
- Um pedido tem: id, usuário, itens (produto, quantidade, **preço do momento da
  compra**), total, status, data.
- Cancelar um pedido (se ainda Pendente) **devolve** os itens ao estoque.
- Senhas com hash; cliente só acessa o próprio carrinho/pedidos; Admin acessa tudo.

## Requisitos

**Funcionais**: endpoints da tabela completos, regras de estoque e de acesso por role funcionando.

**Não-funcionais** (todos obrigatórios):

1. **Persistência**: PostgreSQL via Docker (`docker-compose.yml` com banco + API,
   como no ex28), EF Core migrations versionadas aplicadas na subida.
2. **Segurança**: JWT Bearer (como no ex26), senhas com hash, `[Authorize(Roles = ...)]` stateless.
3. **Validação**: DataAnnotations em todos os DTOs de entrada; respostas de erro
   padronizadas (ProblemDetails).
4. **DTOs e mappers**: entidades EF Core nunca são expostas/aceitas diretamente
   (nem a senha do usuário vaza no JSON).
5. **Paginação**: `Skip`/`Take` no banco nas listagens de produtos e pedidos, com
   metadados.
6. **Testes**: `dotnet test` verde, com ao menos testes de service (Moq) e de
   controller/integração (WebApplicationFactory) cobrindo as regras de estoque e autorização.
7. **Documentação**: Swagger (`/swagger`) listando todos os endpoints, com esquema
   de segurança Bearer configurado no Swashbuckle.
8. **Execução com um comando**: `docker compose up --build` sobe banco + API documentada.

**README do seu projeto** — escreva como se fosse para um recrutador que nunca falou com você:

- Título e descrição do problema que a API resolve (2–3 frases, sem gírias).
- Tecnologias utilizadas (lista com badges opcionais).
- Prints do Swagger UI e/ou diagrama simples de entidades.
- Instruções de execução em 3 passos (`git clone`, `docker compose up --build`, abrir swagger).
- Tabela de endpoints + credenciais de teste (`admin@loja.com / admin123` e
  `cliente@loja.com / cliente123` criados por seed/migration).
- Seção "Decisões técnicas": 3–5 bullets explicando escolhas (por que JWT? por que
  migrations automáticas? como tratei a baixa de estoque?).
- O que você faria em uma v2 (deploy em nuvem, mensageria, cache...) — mostra visão de crescimento.

## Exemplo de uso

```bash
git clone <seu-repo> && cd <seu-repo>
docker compose up --build
```

Login e fluxo de compra:

```bash
TOKEN=$(curl -s -X POST http://localhost:5000/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"cliente@loja.com","senha":"cliente123"}' | jq -r .token)

curl -X POST http://localhost:5000/carrinho/itens \
  -H "Authorization: Bearer $TOKEN" -H "Content-Type: application/json" \
  -d '{"produtoId":1,"quantidade":2}'

curl -X POST http://localhost:5000/pedidos -H "Authorization: Bearer $TOKEN"
```

Resposta esperada do pedido:

```json
{
  "id": 1,
  "status": "Pendente",
  "total": 159.80,
  "itens": [{"produto":"Mouse","quantidade":2,"precoUnitario":79.90}],
  "criadoEm": "2026-09-28T10:15:00"
}
```

Tentativa de comprar mais do que há em estoque:

```bash
curl -X POST http://localhost:5000/carrinho/itens \
  -H "Authorization: Bearer $TOKEN" -H "Content-Type: application/json" \
  -d '{"produtoId":1,"quantidade":9999}'
```

```json
{ "status": 400, "title": "Estoque insuficiente para o produto 'Mouse' (disponível: 10)" }
```

## Como executar

```bash
docker compose up --build          # sobe Postgres + API
# Swagger UI: http://localhost:5000/swagger

dotnet test                        # roda a suíte de testes localmente
dotnet run --urls http://localhost:5000   # desenvolvimento local
```

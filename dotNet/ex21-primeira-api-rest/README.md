# Exercício 21 — API de Saudações e Tarefas Simples

## Conceito
Seu primeiro contato com **ASP.NET Core**: subir uma aplicação web com
`dotnet new webapi`, criar um controller (`[ApiController]`, `[Route]`, `[HttpGet]`,
`[HttpPost]`) e expor endpoints REST que recebem parâmetros de várias formas
(`[FromQuery]`, `[FromRoute]`, `[FromBody]`) e retornam JSON automaticamente.

## Enunciado
Você foi contratado para criar o "cartão de visitas" de uma empresa: uma mini-API de
saudações e de anotação de tarefas rápidas. Ela será usada pelos colegas para testar
se o ambiente ASP.NET Core está funcionando — então deve ser pequena, simples e animadora.

Construa uma API ASP.NET Core com um controller contendo:

- `GET /saudacao?nome=Ana` → retorna JSON com uma saudação personalizada, ex.:
  `{ "mensagem": "Olá, Ana!" }`. Se `nome` não for informado, responder `"Olá, visitante!"`.
- `GET /saudacao/{nome}` → mesma saudação, mas com o nome vindo no caminho da URL.
- `GET /tarefas` → retorna a lista de tarefas em memória (pode começar vazia).
- `POST /tarefas` → recebe um JSON `{ "descricao": "Estudar ASP.NET" }` no corpo,
  guarda a descrição numa lista em memória e retorna a tarefa criada com status 201.

Modelos: use `record` (ex.: `record Tarefa(int Id, string Descricao)`). Não é
necessário banco de dados — uma `List<Tarefa>` no controller (ou num service
simples) já basta.

## Requisitos
1. Projeto gerado com .NET 8 e template `webapi --use-controllers`.
2. Controller com `[ApiController]` e rotas corretas.
3. Uso correto de `[FromQuery]` (com valor padrão), `[FromRoute]` e `[FromBody]`.
4. Respostas em JSON (o ASP.NET Core serializa objetos/records automaticamente).
5. A lista de tarefas persiste **enquanto a aplicação estiver no ar**: tarefas enviadas
   via POST devem aparecer no GET.
6. Swagger (`/swagger`) abre e lista os endpoints.

## Exemplo de uso

```bash
# Saudação com query param
curl "http://localhost:5000/saudacao?nome=Ana"
# Resposta esperada: {"mensagem":"Olá, Ana!"}

# Saudação sem parâmetro
curl "http://localhost:5000/saudacao"
# Resposta esperada: {"mensagem":"Olá, visitante!"}

# Saudação com path variable
curl "http://localhost:5000/saudacao/Carlos"
# Resposta esperada: {"mensagem":"Olá, Carlos!"}

# Criar uma tarefa
curl -i -X POST "http://localhost:5000/tarefas" \
  -H "Content-Type: application/json" \
  -d '{"descricao":"Estudar ASP.NET Core"}'
# Resposta esperada: 201 + {"id":1,"descricao":"Estudar ASP.NET Core"}

# Listar tarefas
curl "http://localhost:5000/tarefas"
# Resposta esperada: [{"id":1,"descricao":"Estudar ASP.NET Core"}]
```

## Como executar

```bash
dotnet new webapi --use-controllers -n Ex21Api
cd Ex21Api
dotnet run --urls http://localhost:5000

# A API sobe em http://localhost:5000 — Swagger em http://localhost:5000/swagger
# Teste com os comandos curl acima ou com o Postman.
```

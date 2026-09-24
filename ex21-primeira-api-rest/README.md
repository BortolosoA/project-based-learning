# Exercício 21 — API de Saudações e Tarefas Simples

## Conceito
Seu primeiro contato com Spring Boot: subir uma aplicação web com `@SpringBootApplication`, criar um `@RestController` e expor endpoints REST que recebem parâmetros de várias formas (`@RequestParam`, `@PathVariable`, `@RequestBody`) e retornam JSON automaticamente.

## Enunciado
Você foi contratado para criar o "cartão de visitas" de uma empresa: uma mini-API de saudações e de anotação de tarefas rápidas. Ela será usada pelos colegas para testar se o ambiente Spring Boot está funcionando — então deve ser pequena, simples e animadora.

Construa uma aplicação Spring Boot com um controller REST contendo os seguintes endpoints:

- `GET /saudacao?nome=Ana` → retorna JSON com uma saudação personalizada, ex.: `{ "mensagem": "Olá, Ana!" }`. Se `nome` não for informado, responder `"Olá, visitante!"`.
- `GET /saudacao/{nome}` → mesma saudação, mas com o nome vindo no caminho da URL.
- `GET /tarefas` → retorna a lista de tarefas em memória (pode começar vazia).
- `POST /tarefas` → recebe um JSON `{ "descricao": "Estudar Spring" }` no corpo da requisição, guarda a descrição numa lista em memória e retorna a tarefa criada.

Modelos: pode usar `record` ou classes simples (ex.: `Saudacao`, `Tarefa`). Não é necessário banco de dados neste exercício — apenas uma `List` no controller (ou num componente simples) já basta.

## Requisitos
1. Projeto gerado com Java 17+ e Spring Boot 3.x com a dependência `spring-boot-starter-web`.
2. Classe principal anotada com `@SpringBootApplication`.
3. Um `@RestController` com os 4 endpoints descritos acima.
4. Uso correto de `@RequestParam` (com valor padrão), `@PathVariable` e `@RequestBody`.
5. Respostas em JSON (o Spring serializa objetos/records automaticamente com Jackson).
6. A lista de tarefas persiste em memória enquanto a aplicação estiver no ar: tarefas enviadas via POST devem aparecer no GET.

## Exemplo de uso

```bash
# Saudação com query param
curl "http://localhost:8080/saudacao?nome=Ana"
# Resposta esperada: {"mensagem":"Olá, Ana!"}

# Saudação sem parâmetro
curl "http://localhost:8080/saudacao"
# Resposta esperada: {"mensagem":"Olá, visitante!"}

# Saudação com path variable
curl "http://localhost:8080/saudacao/Carlos"
# Resposta esperada: {"mensagem":"Olá, Carlos!"}

# Criar uma tarefa
curl -X POST "http://localhost:8080/tarefas" \
  -H "Content-Type: application/json" \
  -d '{"descricao":"Estudar Spring Boot"}'
# Resposta esperada (exemplo): {"descricao":"Estudar Spring Boot"}

# Listar tarefas
curl "http://localhost:8080/tarefas"
# Resposta esperada (exemplo): [{"descricao":"Estudar Spring Boot"}]
```

## Como executar

```bash
# Gerar o projeto (opção 1: site)
# Acesse https://start.spring.io e selecione:
#   Project: Maven | Language: Java | Spring Boot: 3.x
#   Java: 17 | Dependências: Spring Web
# Baixe, extraia e abra a pasta no seu IDE.

# Gerar o projeto (opção 2: linha de comando)
curl https://start.spring.io/starter.tgz \
  -d dependencies=web \
  -d javaVersion=17 \
  -d name=ex21-primeira-api-rest | tar -xzvf -

# Rodar a aplicação
./mvnw spring-boot:run

# A aplicação sobe em http://localhost:8080
# Teste com os comandos curl acima ou com o Postman.
```

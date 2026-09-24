# Exercício 26 — API Segura com Autenticação

## Conceito

Até aqui, qualquer pessoa podia chamar qualquer endpoint da sua API. No mercado real, isso é inaceitável. Neste exercício você vai aprender **Spring Security** com **JWT (JSON Web Token)**: o fluxo clássico de autenticação stateless. O cliente faz login, recebe um token assinado, e envia esse token em cada requisição no header `Authorization: Bearer <token>`. O servidor valida o token e identifica quem é o usuário — sem guardar sessão em memória. Você também vai usar **BCrypt** para nunca armazenar senhas em texto puro e **roles** (`USER`/`ADMIN`) para autorização por perfil.

## Enunciado

Uma loja precisa proteger sua API de produtos. Qualquer pessoa pode se cadastrar e listar produtos, mas apenas usuários autenticados podem ver detalhes de pedidos, e apenas **administradores** podem cadastrar, alterar e remover produtos.

Você vai evoluir uma API simples de produtos (pode criar do zero com um recurso `Produto` com `id`, `nome`, `preco`) adicionando:

1. **Cadastro de usuário** (`POST /auth/register`): recebe `username` e `password`, salva a senha com hash BCrypt e atribui a role `USER`.
2. **Login** (`POST /auth/login`): recebe credenciais, valida e retorna um **JWT** válido por pelo menos 1 hora.
3. **Endpoints públicos**: `/auth/**` e `GET /produtos`.
4. **Endpoint exclusivo de ADMIN**: `POST /produtos`, `PUT /produtos/{id}` e `DELETE /produtos/{id}` exigem a role `ADMIN`.
5. **Filtro JWT**: um filtro (ex.: `OncePerRequestFilter`) que intercepta requisições, extrai e valida o token do header.
6. Configuração explícita de segurança com um bean `SecurityFilterChain`.

Para gerar um usuário ADMIN, pode ser um usuário criado via seed/data.sql ou promovido manualmente no banco (ex.: `admin/admin123`).

## Requisitos

- Java 17+, Spring Boot 3.x, Maven.
- Dependências: `spring-boot-starter-web`, `spring-boot-starter-security`, `spring-boot-starter-data-jpa`, `spring-boot-starter-validation`, `io.jsonwebtoken:jjwt-*` (0.11.x ou 0.12.x), H2 ou outro banco.
- Senhas armazenadas **somente** com hash BCrypt (bean `PasswordEncoder`).
- JWT assinado com chave secreta (em `application.properties` ou classe de config).
- Endpoints protegidos retornam **401** sem token e **403** sem permissão suficiente.
- `SecurityFilterChain` configurado com `SessionCreationPolicy.STATELESS`.
- Validação básica nos DTOs de registro/login (`@NotBlank`, tamanho mínimo de senha).

## Exemplo de uso

Cadastro:

```bash
curl -X POST http://localhost:8080/auth/register \
  -H "Content-Type: application/json" \
  -d '{"username":"maria","password":"senha123"}'
```

Saída esperada: `201 Created` (ou 200).

Login:

```bash
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"maria","password":"senha123"}'
```

```json
{ "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtYXJpYSIs..." }
```

Acessando endpoint protegido sem permissão (USER tentando cadastrar produto):

```bash
TOKEN="cole-o-token-aqui"
curl -i -X POST http://localhost:8080/produtos \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"nome":"Mouse","preco":79.90}'
```

Saída esperada: `HTTP/1.1 403`.

Com ADMIN deve retornar `201` e o produto criado. Sem token nenhum: `401`.

## Como executar

```bash
mvn spring-boot:run
# ou
mvn package && java -jar target/*.jar
```

Endpoints de exemplo:

| Método | Rota | Acesso |
|--------|------|--------|
| POST | /auth/register | Público |
| POST | /auth/login | Público |
| GET | /produtos | Público |
| GET | /produtos/{id} | Autenticado |
| POST/PUT/DELETE | /produtos/** | ADMIN |

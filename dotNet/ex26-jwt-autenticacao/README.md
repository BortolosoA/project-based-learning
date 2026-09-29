# Exercício 26 — API Segura com Autenticação (JWT)

## Conceito

Até aqui, qualquer pessoa podia chamar qualquer endpoint da sua API. No mercado real,
isso é inaceitável. Neste exercício você vai aprender autenticação **stateless** com
**JWT (JSON Web Token)**: o cliente faz login, recebe um token assinado, e envia esse
token em cada requisição no header `Authorization: Bearer <token>`. O servidor valida
o token e identifica quem é o usuário — sem guardar sessão. Você também vai guardar
senhas apenas como **hash** e usar **roles** (`User`/`Admin`) para autorização por perfil.

No ASP.NET Core isso é feito com o pacote `Microsoft.AspNetCore.Authentication.JwtBearer`:
um `TokenService` gera o token, o `Program.cs` configura a autenticação/autorização e
os actions usam `[Authorize]` / `[Authorize(Roles = "Admin")]`.

## Enunciado

Uma loja precisa proteger sua API de produtos. Qualquer pessoa pode se cadastrar e
listar produtos, mas apenas usuários autenticados podem ver detalhes de pedidos, e
apenas **administradores** podem cadastrar, alterar e remover produtos.

Você vai evoluir uma API de produtos (crie do zero com `Produto` = `Id`, `Nome`, `Preco`) adicionando:

1. **Cadastro de usuário** (`POST /auth/register`): recebe `username` e `password`,
   salva a senha com hash e atribui a role `User`.
2. **Login** (`POST /auth/login`): valida credenciais e retorna um **JWT** válido
   por pelo menos 1 hora, com claims de nome e role.
3. **Endpoints públicos**: `/auth/**` e `GET /produtos`.
4. **Exclusivo de Admin**: `POST /produtos`, `PUT /produtos/{id}` e
   `DELETE /produtos/{id}` exigem `[Authorize(Roles = "Admin")]`.
5. **Autenticação configurada**: `AddAuthentication().AddJwtBearer()` com validação
   de issuer/audience/chave, e `app.UseAuthentication()` **antes** de `app.UseAuthorization()`.
6. **TokenService**: classe dedicada que gera o JWT (`JwtSecurityToken` +
   `SymmetricSecurityKey`), não espalhada pelo código.

Para gerar um usuário Admin: seed no banco ou promotion manual (`admin/admin123`).

## Requisitos

- .NET 8, pacotes `Microsoft.AspNetCore.Authentication.JwtBearer` e `BCrypt.Net-Next`
  (hash de senha) — ou `Microsoft.AspNetCore.Identity`'s `PasswordHasher<string>`.
- Senhas armazenadas **somente** com hash (nunca texto puro).
- JWT assinado com chave secreta (HS256, mínimo 256 bits) vinda de configuração
  (`appsettings.json` ou variável de ambiente).
- Endpoints protegidos retornam **401** sem token e **403** sem permissão suficiente.
- Validação básica nos DTOs de registro/login (`[Required]`, tamanho mínimo de senha).

## Exemplo de uso

Cadastro:
```bash
curl -X POST http://localhost:5000/auth/register \
  -H "Content-Type: application/json" \
  -d '{"username":"maria","password":"senha123"}'
```
Saída esperada: `201 Created`.

Login:
```bash
curl -X POST http://localhost:5000/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"maria","password":"senha123"}'
```
```json
{ "token": "eyJhbGciOiJIUzI1NiJ9.eyJodHRwOi8vc2NoZW1hcy54..." }
```

USER tentando cadastrar produto (deve dar **403**):
```bash
TOKEN="cole-o-token-aqui"
curl -i -X POST http://localhost:5000/produtos \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"nome":"Mouse","preco":79.90}'
```

Com ADMIN: `201`. Sem token nenhum: `401`.

## Como executar

```bash
dotnet add package Microsoft.AspNetCore.Authentication.JwtBearer
dotnet add package BCrypt.Net-Next
dotnet run --urls http://localhost:5000
```

Endpoints:

| Método | Rota | Acesso |
|--------|------|--------|
| POST | /auth/register | Público |
| POST | /auth/login | Público |
| GET | /produtos | Público |
| GET | /produtos/{id} | Autenticado |
| POST/PUT/DELETE | /produtos/** | Admin |

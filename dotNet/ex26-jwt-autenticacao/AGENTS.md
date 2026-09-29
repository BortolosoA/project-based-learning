# AGENTS.md — Correção do Exercício 26 (JWT + Autenticação)

## Critérios de correção

1. **Registro funciona**: `POST /auth/register` persiste o usuário com senha hasheada
   (verificar no banco que a senha NÃO está em texto puro — hash BCrypt começa com `$2`).
2. **Login retorna JWT**: credenciais corretas → token; credenciais erradas → 401
   com resposta controlada (sem stacktrace).
3. **Endpoints públicos acessíveis sem token**: `/auth/**` e `GET /produtos`.
4. **Autorização por role**: `POST /produtos` com token de User → **403**; com Admin → **201**.
5. **Sem token** em endpoint protegido → **401**.
6. `AddAuthentication().AddJwtBearer()` configurado com validação de chave/issuer/audience;
   `app.UseAuthentication()` antes de `app.UseAuthorization()` (ordem errada quebra tudo).
7. `TokenService` separado; a chave secreta NÃO está hardcoded no meio do código.
8. Token carrega claims de nome e role (`ClaimTypes.Name`, `ClaimTypes.Role`).

## Como verificar

```bash
dotnet run --urls http://localhost:5000
```

```bash
# 1. Registrar e logar
curl -s -X POST http://localhost:5000/auth/register -H "Content-Type: application/json" -d '{"username":"ana","password":"senha123"}'
TOKEN=$(curl -s -X POST http://localhost:5000/auth/login -H "Content-Type: application/json" -d '{"username":"ana","password":"senha123"}' | jq -r .token)

# 2. Público sem token — esperado 200
curl -s -o /dev/null -w "%{http_code}\n" http://localhost:5000/produtos

# 3. USER criando produto — esperado 403
curl -s -o /dev/null -w "%{http_code}\n" -X POST http://localhost:5000/produtos -H "Authorization: Bearer $TOKEN" -H "Content-Type: application/json" -d '{"nome":"Teclado","preco":199.9}'

# 4. Sem token — esperado 401
curl -s -o /dev/null -w "%{http_code}\n" -X POST http://localhost:5000/produtos -H "Content-Type: application/json" -d '{"nome":"X","preco":1}'

# 5. Login com senha errada — esperado 401
curl -s -o /dev/null -w "%{http_code}\n" -X POST http://localhost:5000/auth/login -H "Content-Type: application/json" -d '{"username":"ana","password":"errada"}'

# 6. Hash no banco
sqlite3 produtos.db "SELECT Username, Password FROM Users;"   # senha deve começar com $2
```

## Erros comuns a apontar

- Senha salva em texto puro — erro crítico, sempre o primeiro a apontar.
- `app.UseAuthorization()` antes de `app.UseAuthentication()` → 401 para tudo, mesmo com token válido.
- Chave secreta curta demais (HS256 exige ≥ 256 bits) ou hardcoded sem nota — sugestão: `builder.Configuration["Jwt:Key"]`.
- Token sem a claim de role → `[Authorize(Roles = "Admin")]` nunca passa (403 sempre).
- Login inválido respondendo 500 com stacktrace em vez de 401 controlado.
- `[Authorize]` no controller inteiro bloqueando o `GET /produtos` que deveria ser público — usar `[AllowAnonymous]`.
- Validar token com `ValidateIssuer = false` e `ValidateAudience = false` sem entender — perguntar o porquê.

## Padrão de feedback

Comece confirmando o fluxo ponta a ponta (registrar → logar → acessar com token →
ser bloqueado sem permissão). Aponte no máximo os 3 problemas de segurança mais
graves (senha em texto puro é sempre o primeiro). Elogie explicitamente se o aluno
separou `TokenService`, controllers de auth e configuração — esse é o formato de
projeto profissional.

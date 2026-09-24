# AGENTS.md — Correção do Exercício 26 (Spring Security + JWT)

## Critérios de correção

1. **Registro funciona**: `POST /auth/register` persiste usuário com senha hasheada com BCrypt (verificar no banco/H2 console que a senha NÃO está em texto puro).
2. **Login retorna JWT**: `POST /auth/login` com credenciais corretas retorna um token JWT válido; com credenciais erradas retorna 401/403 (não lança stacktrace).
3. **Endpoints públicos acessíveis sem token**: `/auth/**` e `GET /produtos` respondem sem header Authorization.
4. **Autorização por role**: `POST /produtos` com token de USER retorna **403**; com token de ADMIN retorna **201/200**.
5. **Ausência de token**: endpoint protegido sem header retorna **401** (ou 403 padrão do Spring — aceitar, mas apontar o ideal de distinguir).
6. **SecurityFilterChain** presente, com `SessionCreationPolicy.STATELESS` e regras `requestMatchers` claras.
7. **Filtro JWT** (`OncePerRequestFilter` ou equivalente) extrai o token de `Authorization: Bearer`, valida assinatura/expiração e popula o `SecurityContext`.
8. **Bean `PasswordEncoder`** configurado (não é instância de `BCryptPasswordEncoder` solta espalhada pelo código).
9. Token carrega a informação de usuário/role (subject + claim de role ou lookup no banco pelo subject).

## Como verificar

```bash
mvn spring-boot:run
```

Em outro terminal:

```bash
# 1. Registrar e logar
curl -X POST http://localhost:8080/auth/register -H "Content-Type: application/json" -d '{"username":"ana","password":"senha123"}'
TOKEN=$(curl -s -X POST http://localhost:8080/auth/login -H "Content-Type: application/json" -d '{"username":"ana","password":"senha123"}' | jq -r .token)

# 2. Público sem token — esperado 200
curl -i http://localhost:8080/produtos

# 3. USER tentando criar produto — esperado 403
curl -i -X POST http://localhost:8080/produtos -H "Authorization: Bearer $TOKEN" -H "Content-Type: application/json" -d '{"nome":"Teclado","preco":199.9}'

# 4. Sem token — esperado 401/403
curl -i -X POST http://localhost:8080/produtos -H "Content-Type: application/json" -d '{"nome":"X","preco":1}'

# 5. Login com senha errada — esperado 401/403
curl -i -X POST http://localhost:8080/auth/login -H "Content-Type: application/json" -d '{"username":"ana","password":"errada"}'

# 6. Verificar hash no H2 console / banco
# SELECT username, password FROM users;  -> deve começar com $2a$/$2b$
```

## Erros comuns a apontar

- Senha salva em texto puro ou com encoder fraco (MD5/SHA-1, `NoOpPasswordEncoder`).
- Chave secreta do JWT curta demais (HS256 exige ≥ 256 bits) ou hardcoded de forma insegura sem nota.
- `.csrf().disable()` sem justificativa (ok para API stateless, mas o aluno deve saber explicar por quê).
- Filtro JWT que não limpa o `SecurityContext` / que ignora token expirado ou mal assinado.
- Autorização checada "na mão" dentro do controller em vez de usar `hasRole("ADMIN")`/`@PreAuthorize`.
- Usar versões incompatíveis do jjwt (API 0.12.x mudou: `parserBuilder` → `parser()`, etc.).
- Retornar 500 com stacktrace em login inválido em vez de resposta controlada.

## Padrão de feedback

Comece confirmando se o fluxo completo (registrar → logar → acessar com token → ser bloqueado sem permissão) funciona de ponta a ponta. Aponte no máximo os 3 problemas de segurança mais graves (senha em texto puro é sempre o primeiro). Elogie explicitamente se o aluno separou o filtro, o serviço de token e a configuração de segurança em classes distintas — esse é o formato esperado em projetos profissionais.

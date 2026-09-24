# AGENTS.md — Correção do Exercício 30 (Projeto Final: API de E-commerce)

## Critérios de correção

1. **Sobe com um comando**: `docker compose up --build` em clone limpo levanta Postgres + API sem erros, e o Swagger UI abre em `http://localhost:8080/swagger-ui.html`.
2. **Endpoints mínimos da tabela do enunciado existem** e respeitam as roles: `/auth/**` público, catálogo público para GET, escrita em `/produtos/**` só ADMIN, carrinho/pedidos só autenticado e **apenas do próprio usuário** (CLIENTE não vê pedido de outro).
3. **Regra de estoque correta**: não permite quantidade acima do estoque (400 com mensagem clara); finalizar pedido decrementa estoque e esvazia carrinho **em uma mesma transação**; cancelar pedido PENDENTE devolve estoque.
4. **Segurança**: JWT funcional login→token→Bearer; senhas com BCrypt; `SecurityFilterChain` stateless; 401/403 coerentes.
5. **Qualidade de API**: DTOs em entrada e saída (nenhuma entidade JPA vazando), Bean Validation funcionando (400 em payload inválido), erro padronizado, paginação (`Page`) em `/produtos` e `/pedidos`.
6. **Persistência séria**: PostgreSQL, Flyway com migrations versionadas, `ddl-auto` sem criar schema, dados de seed (admin/cliente) via migration ou CommandLineRunner.
7. **Testes**: `mvn test` verde; ao menos service testado com Mockito e controller com MockMvc, cobrindo regra de estoque e autorização (não só caminho feliz).
8. **Swagger**: springdoc-openapi listando todos os endpoints, com esquema Bearer configurado e descrições legíveis (não só nomes de métodos crus).
9. **README do projeto em nível de portfólio**: problema, tecnologias, instruções em ≤3 passos, endpoints, credenciais de teste, decisões técnicas e próximos passos. README bagunçado/ausente reprova o item de apresentação.

## Como verificar

```bash
# 1. Clone limpo sobe tudo
git clone <repo> && cd <repo> && docker compose up --build

# 2. Swagger aberto e completo
curl -s http://localhost:8080/v3/api-docs | jq '.paths | keys'

# 3. Fluxo completo de compra com cliente seed
TOKEN=$(curl -s -X POST http://localhost:8080/auth/login -H "Content-Type: application/json" \
  -d '{"email":"cliente@loja.com","senha":"cliente123"}' | jq -r .token)
curl -X POST http://localhost:8080/carrinho/itens -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" -d '{"produtoId":1,"quantidade":2}'
curl -X POST http://localhost:8080/pedidos -H "Authorization: Bearer $TOKEN"

# 4. Estoque: antes x depois do pedido (deve ter decrementado)
curl -s http://localhost:8080/produtos/1 | jq .   # conferir campo de estoque

# 5. Excesso de estoque -> 400
curl -i -X POST http://localhost:8080/carrinho/itens -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" -d '{"produtoId":1,"quantidade":999999}'

# 6. Segurança: sem token -> 401; CLIENTE em POST /produtos -> 403;
#    CLIENTE acessando pedido de outro usuário -> 403/404

# 7. Testes
mvn test    # esperado: BUILD SUCCESS, Failures: 0

# 8. Migrations
docker compose exec db psql -U postgres -d <banco> -c "SELECT version, description FROM flyway_schema_history;"
```

## Erros comuns a apontar

- Confirmar pedido sem `@Transactional`: se a baixa de estoque falhar no meio, pedido fica inconsistente (carrinho esvaziado sem pedido, estoque sem baixa).
- Cliente acessando recurso de outro cliente: checar que o serviço valida `pedido.usuario.id == usuarioLogado.id`, não só "está autenticado".
- Expor entidades JPA nas respostas (loop infinito de serialização, senha do usuário vazando no JSON do pedido!).
- Senha retornada no DTO de usuário; campo `senha` no JSON de resposta.
- Swagger sem esquema de segurança — cada endpoint retorna 403 no "Try it out" e o avaliador acha que está quebrado.
- README que continua sendo o enunciado do exercício (copiar enunciado ≠ documentação de portfólio) ou sem credenciais de teste.
- Paginação fake: buscar tudo e fatiar na memória em vez de usar `Pageable`.
- Preço do item do pedido referenciando o produto em vez de congelar o preço na hora da compra (se o produto mudar de preço, o histórico do pedido muda — errado).
- Testes que só existem para "ter teste": nenhum cobre estoque insuficiente ou acesso indevido.
- `ddl-auto=update` convivendo com Flyway; projetos sem `.env`/senha externalizada.

## Padrão de feedback

Este é o fechamento da trilha — o feedback tem **duas camadas**. Primeiro, avaliação global: comente a maturidade do projeto como se você fosse um tech lead revisando o trabalho de um júnior que acaba de terminar um programa de formação — destaque o que já está em nível profissional (ex.: pipeline Flyway + Docker reproduzível, testes de regra de negócio, autorização por dono do recurso) e aponte no máximo 3 melhorias priorizadas por impacto. Segundo, celebre de verdade: a pessoa construiu, do zero, uma API completa com autenticação, banco real, containers, testes e documentação — isso é repertório de entrevista e de primeiro emprego. Feche incentivando a publicação no GitHub/LinkedIn e sugerindo o próximo passo natural (deploy em nuvem ou adicionar mensageria), com tom de conquista e parabéns pela conclusão da trilha.

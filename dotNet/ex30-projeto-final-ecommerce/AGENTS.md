# AGENTS.md — Correção do Exercício 30 (Projeto Final: API de E-commerce)

## Critérios de correção

1. **Sobe com um comando**: `docker compose up --build` em clone limpo levanta
   Postgres + API sem erros, e o Swagger UI abre em `http://localhost:5000/swagger`.
2. **Endpoints mínimos da tabela existem** e respeitam as roles: `/auth/**` público,
   catálogo público para GET, escrita em `/produtos/**` só Admin, carrinho/pedidos
   só autenticado e **apenas do próprio usuário** (Cliente não vê pedido de outro).
3. **Regra de estoque correta**: quantidade acima do estoque → 400/409 com mensagem
   clara; finalizar pedido decrementa estoque e esvazia carrinho **na mesma
   transação**; cancelar pedido Pendente devolve estoque.
4. **Segurança**: JWT funcional (login → token → Bearer); senhas com hash; 401/403
   coerentes; nenhuma senha no JSON de resposta.
5. **Qualidade de API**: DTOs em entrada e saída (nenhuma entidade EF vazando),
   DataAnnotations funcionando (400 em payload inválido), ProblemDetails padronizado,
   paginação real no banco em `/produtos` e `/pedidos`.
6. **Persistência séria**: PostgreSQL no Docker, migrations EF versionadas
   (tabela `__EFMigrationsHistory`), seed de admin/cliente e produtos.
7. **Testes**: `dotnet test` verde; ao menos service testado com Moq e integração
   com WebApplicationFactory, cobrindo regra de estoque e autorização (não só caminho feliz).
8. **Swagger**: todos os endpoints listados, esquema de segurança Bearer configurado
   (o "Authorize" do Swagger UI funciona) e descrições legíveis.
9. **README em nível de portfólio**: problema, tecnologias, instruções em ≤3 passos,
   endpoints, credenciais de teste, decisões técnicas e próximos passos. README
   bagunçado/ausente ou igual ao enunciado reprova o item de apresentação.

## Como verificar

```bash
# 1. Clone limpo sobe tudo
git clone <repo> && cd <repo> && docker compose up --build

# 2. Swagger aberto e completo
curl -s http://localhost:5000/swagger/v1/swagger.json | jq '.paths | keys'

# 3. Fluxo completo de compra com cliente seed
TOKEN=$(curl -s -X POST http://localhost:5000/auth/login -H "Content-Type: application/json" \
  -d '{"email":"cliente@loja.com","senha":"cliente123"}' | jq -r .token)
curl -s -X POST http://localhost:5000/carrinho/itens -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" -d '{"produtoId":1,"quantidade":2}'
curl -s -X POST http://localhost:5000/pedidos -H "Authorization: Bearer $TOKEN" | jq '{id, status, total}'

# 4. Estoque: antes x depois do pedido (deve ter decrementado)
curl -s http://localhost:5000/produtos/1 | jq .estoque

# 5. Excesso de estoque -> 400/409
curl -s -o /dev/null -w "%{http_code}\n" -X POST http://localhost:5000/carrinho/itens \
  -H "Authorization: Bearer $TOKEN" -H "Content-Type: application/json" \
  -d '{"produtoId":1,"quantidade":999999}'

# 6. Segurança: sem token -> 401; Cliente em POST /produtos -> 403;
#    Cliente acessando pedido de outro usuário -> 403/404

# 7. Cancelamento devolve estoque
curl -s -X PATCH http://localhost:5000/pedidos/1/status -H "Authorization: Bearer $ADMIN_TOKEN" \
  -H "Content-Type: application/json" -d '{"status":"Cancelado"}'
curl -s http://localhost:5000/produtos/1 | jq .estoque   # voltou ao valor original

# 8. Testes
docker compose exec api dotnet test   # ou na máquina host: dotnet test

# 9. Migrations
docker compose exec db psql -U postgres -d <banco> -c 'SELECT * FROM "__EFMigrationsHistory";'
```

## Erros comuns a apontar

- Confirmar pedido sem transação única: se a baixa falhar no meio, o pedido fica
  inconsistente (carrinho esvaziado sem pedido, ou estoque sem baixa).
- Cliente acessando recurso de outro cliente: o service precisa validar
  `pedido.UsuarioId == usuarioLogadoId`, não só "está autenticado".
- Expor entidades EF nas respostas (loop infinito de serialização; senha do usuário
  vazando no JSON do pedido!).
- Campo `senha` aparecendo no DTO de resposta de usuário.
- Swagger sem esquema de segurança — todo endpoint protegido dá 401 no "Try it out"
  e o avaliador acha que a API está quebrada.
- README que continua sendo o enunciado do exercício (copiar enunciado ≠ documentação
  de portfólio) ou sem credenciais de teste.
- Paginação fake: buscar tudo e fatiar na memória em vez de `Skip/Take` no banco.
- Preço do item referenciando o produto em vez de **congelar** o preço na hora da
  compra (se o produto muda de preço, o histórico do pedido muda — errado).
- Testes que só cobrem o caminho feliz: nenhum testa estoque insuficiente ou acesso indevido.
- `EnsureCreated` convivendo com migrations; senhas/secrets hardcoded no compose sem variável.

## Padrão de feedback

Este é o fechamento da trilha — o feedback tem **duas camadas**. Primeiro, avaliação
global: comente a maturidade do projeto como se você fosse um tech lead revisando o
trabalho de um júnior que acaba de terminar um programa de formação — destaque o que
já está em nível profissional (pipeline de migrations + Docker reproduzível, testes
de regra de negócio, autorização por dono do recurso) e aponte no máximo 3 melhorias
priorizadas por impacto. Segundo, celebre de verdade: a pessoa construiu, do zero,
uma API completa com autenticação, banco real, containers, testes e documentação —
isso é repertório de entrevista e de primeiro emprego. Feche incentivando a
publicação no GitHub/LinkedIn e sugerindo o próximo passo natural (deploy em nuvem
ou adicionar mensageria), com tom de conquista e parabéns pela conclusão da trilha.

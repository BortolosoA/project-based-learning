# AGENTS.md — Correção do Exercício 29 (Mensageria RabbitMQ)

## Critérios de correção

1. `POST /pedidos` responde 201 rápido e publica o evento `PedidoCriado` — não faz
   os efeitos colaterais no mesmo request.
2. Dois consumers independentes escutam o evento e cada um loga seu efeito.
3. Exchange + filas criadas no RabbitMQ (visíveis na Management UI :15672).
4. Idempotência: mesma mensagem entregue 2x → estoque baixado 1x (verificar no banco).
5. Durabilidade: consumer fora → mensagem fica na fila → consumer sobe → processa o backlog.
6. Compose sobe postgres + rabbitmq + api + 2 consumers com healthcheck/depends_on.

## Como verificar

```bash
docker compose up --build -d
docker compose logs -f pedidos-api consumidor-notificacoes consumidor-estoque

# 1. Fluxo básico
curl -s -X POST http://localhost:5000/pedidos -H "Content-Type: application/json" -d '{"cliente":"Ana","total":150.0}'
# nos logs: notificação + baixa de estoque para o pedido criado

# 2. Backlog: derrubar um consumer, criar pedido, subir consumer
docker compose stop consumidor-estoque
curl -s -X POST http://localhost:5000/pedutos -H "Content-Type: application/json" -d '{"cliente":"Bia","total":80.0}' > /dev/null
sleep 3
docker compose start consumidor-estoque
sleep 5
docker compose logs consumidor-estoque | tail -3   # deve ter processado a Bia

# 3. Idempotência: republicar a mesma mensagem e conferir estoque (SELECT antes/depois)
docker compose exec db psql -U postgres -d produtos -c 'SELECT * FROM "Produtos";'

# 4. Topologia na Management UI
curl -s -u guest:guest http://localhost:15672/api/exchanges | jq '.[] | select(.name | contains("pedidos"))'
curl -s -u guest:guest http://localhost:15672/api/queues | jq '.[].name'
```

## Erros comuns a apontar

- API chamando os consumers direto (HTTP/RPC) em vez de publicar evento — perdeu o exercício.
- POST /pedidos demorado porque espera processar tudo no request.
- Consumer crashando por mensagem malformada e a fila travando (sem try/catch e dead-letter).
- Exchange/fila declaradas como não duráveis + broker reinicia → tudo some.
- `PedidoId` no JSON como string e int no consumer (desserialização silenciosa quebrando idempotência).
- Sem idempotência: baixa de estoque duplicada em reentrega — RabbitMQ entrega *at least once*.
- Healthcheck ausente: consumers conectando antes do broker subir.

## Padrão de feedback

O conceito a celebrar: a API virou "editora" e não conhece seus assinantes. Testar o
cenário de falha (consumer fora) é o que separa entendimento de copiação. Se a
idempotência e o backlog funcionarem, o aluno entendeu o modelo de mensageria de
verdade — perguntar "o que acontece se DOIS pedidos chegarem juntos?" para introduzir
concorrência, o gancho natural para o projeto final.

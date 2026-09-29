# Exercício 29 — Sistema com Mensageria (RabbitMQ)

## Conceito

Desacoplar sistemas com mensagens: **RabbitMQ** (exchanges, queues, produtor/consumidor)
e **arquitetura orientada a eventos**. Em vez de a API fazer tudo no mesmo request
(pedido + e-mail + baixa de estoque), ela publica um evento `PedidoCriado` — e quem
quiser escuta. Cada consumidor evolui, falha e escala **sozinho**.

Para integrar, use `RabbitMQ.Client` (client oficial) ou **MassTransit** (abstração
mais idiomática, recomendada — transport `RabbitMQ` com `AddMassTransit`).

## Enunciado

Uma loja quer que "pedido criado" dispare outros processos **sem acoplar tudo na API**:

- `PedidosController` → `POST /pedidos` cria o pedido (salva no banco) e publica o
  evento `PedidoCriado` no RabbitMQ. Deve responder **201 imediatamente**, sem
  esperar os efeitos colaterais.
- `ConsumidorNotificacoes` — worker (projeto `Worker` ou console) que escuta
  `PedidoCriado` e "envia e-mail" (log no console).
- `ConsumidorEstoque` — outro worker que escuta o mesmo evento e dá baixa no estoque.
- Exchange `pedidos-events` (fanout ou topic `pedido.criado`), com fila para cada consumidor.
- Tudo no `docker-compose.yml`: postgres + **rabbitmq** (com management UI) + api + 2 consumers.

## Requisitos

1. A API **não conhece** os consumidores — só publica o evento.
2. Mensagem: registro `PedidoCriado(int PedidoId, string Cliente, decimal Total, DateTime CriadoEm)` — serializada em JSON.
3. Consumers são **idempotentes**: receber a mesma mensagem 2x não duplica efeito
   (baixa estoque uma vez só — dica: controle por `PedidoId`).
4. Mensagens ficam na fila se um consumer estiver fora; ao subir, ele processa o backlog.
5. Management UI (http://localhost:15672, guest/guest) mostra exchanges, filas e mensagens.
6. Healthcheck do RabbitMQ no compose antes de api e consumers subirem.

## Exemplo de uso

```bash
docker compose up --build

curl -X POST http://localhost:5000/pedidos \
  -H "Content-Type: application/json" \
  -d '{"cliente":"Ana","total":150.0}'
# 201 imediato

# nos logs dos consumers (cada um no seu terminal/container):
# [Notificacoes] E-mail enviado para o cliente do pedido #1 (R$ 150,00)
# [Estoque] Baixa de estoque processada para o pedido #1
```

## Como executar

```bash
docker compose up --build
# API: http://localhost:5000
# RabbitMQ Management: http://localhost:15672 (guest/guest)
docker compose logs -f consumidor-notificacoes consumidor-estoque
```

# Exercício 29 — Sistema de Pedidos Assíncrono

## Conceito

Quando um cliente faz um pedido, a API não deveria ficar parada esperando o envio de e-mail, a geração de nota fiscal e a baixa no estoque. Essas tarefas são **assíncronas**: a API responde rápido ("pedido recebido!") e publica uma **mensagem** num broker. Outros processos consomem essas mensagens no seu ritmo. É assim que sistemas reais escalam e se tornam resilientes — se o serviço de e-mail estiver fora do ar, a mensagem espera na fila e o pedido não se perde.

Neste exercício você vai usar **RabbitMQ** (rodando em Docker) e **Spring AMQP** para construir um fluxo produtor → fila → consumidor, entendendo os conceitos de **exchange**, **queue** e **binding**.

## Enunciado

Uma loja quer desacoplar o recebimento de pedidos do "envio de e-mail de confirmação". Você vai construir **duas aplicações Spring Boot** (ou dois módulos Maven, rode um de cada vez):

1. **Produtor (api-pedidos, porta 8080)**:
   - `POST /pedidos` recebe `{"cliente":"Ana","email":"ana@email.com","itens":["Mouse","Teclado"],"total":279.80}`.
   - Salva o pedido (pode ser H2 em memória ou só log) e publica uma mensagem JSON na exchange `pedidos.exchange` com routing key `pedidos.criado`, que chega à fila `pedidos.criados`.
   - Responde imediatamente `202 Accepted` com o id do pedido — **sem** esperar o e-mail.
2. **Consumidor (servico-email, porta 8081)**:
   - Com `@RabbitListener(queues = "pedidos.criados")`, consome a mensagem e simula o envio de e-mail com um log bem visível, ex.: `>>> E-MAIL ENVIADO para ana@email.com: pedido #42 confirmado`.
   - Se a mensagem estiver com e-mail inválido (sem `@`), deve lançar exceção e a mensagem voltar/rejeitar (veremos o comportamento de erro).
3. **RabbitMQ em Docker**: suba o broker com o console de administração habilitado.
4. **Topologia declarada no código**: beans `TopicExchange` (ou `DirectExchange`), `Queue` e `Binding` via `RabbitAdmin`/configuração — não criar fila na mão pelo console.
5. **(Opcional/bônus)** Dead Letter Queue (DLQ): declarar `pedidos.dlq` e configurar a fila principal com argumentos `x-dead-letter-exchange`/`x-dead-letter-routing-key`, de modo que mensagens rejeitadas parem na DLQ em vez de ficarem em loop infinito.

## Requisitos

- Java 17+, Spring Boot 3.x, Maven, Docker.
- Dependência `spring-boot-starter-amqp` nos dois módulos.
- Mensagens serializadas em **JSON** (configurar `Jackson2JsonMessageConverter` — nada de serialização Java binária).
- `docker run` ou `docker-compose.yml` para o RabbitMQ (imagem `rabbitmq:3-management`).
- Console de administração acessível em `http://localhost:15672` (guest/guest).
- Fluxo ponta a ponta verificável: POST no produtor → log aparece no consumidor em segundos.
- Tratamento de erro demonstrável: mensagem com e-mail inválido não derruba o consumidor.

## Exemplo de uso

Subindo o RabbitMQ:

```bash
docker run -d --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3-management
```

Subindo os dois módulos (terminais separados):

```bash
# terminal 1
cd api-pedidos && mvn spring-boot:run
# terminal 2
cd servico-email && mvn spring-boot:run -Dspring-boot.run.arguments=--server.port=8081
```

Enviando um pedido:

```bash
curl -i -X POST http://localhost:8080/pedidos \
  -H "Content-Type: application/json" \
  -d '{"cliente":"Ana","email":"ana@email.com","itens":["Mouse","Teclado"],"total":279.80}'
```

Resposta esperada:

```
HTTP/1.1 202
{"id":1,"status":"RECEBIDO"}
```

Log esperado no consumidor (alguns segundos depois):

```
>>> E-MAIL ENVIADO para ana@email.com: pedido #1 confirmado
```

Conferindo no console do RabbitMQ: abrir `http://localhost:15672` → aba *Queues* → `pedidos.criados` (contadores de publish/deliver).

Teste de erro:

```bash
curl -X POST http://localhost:8080/pedidos -H "Content-Type: application/json" \
  -d '{"cliente":"Bob","email":"invalido","itens":["X"],"total":10}'
```

Esperado: consumidor loga o erro; com DLQ configurada, a mensagem aparece em `pedidos.dlq` sem loop infinito.

## Como executar

```bash
# 1. Broker
docker run -d --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3-management

# 2. Produtor
cd api-pedidos && mvn spring-boot:run

# 3. Consumidor
cd servico-email && mvn spring-boot:run

# 4. Testar
curl -X POST http://localhost:8080/pedidos -H "Content-Type: application/json" \
  -d '{"cliente":"Ana","email":"ana@email.com","itens":["Mouse"],"total":79.9}'
```

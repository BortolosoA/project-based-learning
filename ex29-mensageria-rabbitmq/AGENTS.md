# AGENTS.md — Correção do Exercício 29 (Sistema de Pedidos Assíncrono)

## Critérios de correção

1. **Fluxo ponta a ponta funciona**: `POST /pedidos` no produtor → mensagem na fila → log de "e-mail enviado" no consumidor em poucos segundos, sem o consumidor chamar o produtor por HTTP.
2. **Produtor é de fato assíncrono**: responde (202/200/201) imediatamente, sem esperar processamento do consumidor — derrubando o consumidor, o POST continua funcionando e a mensagem fica na fila.
3. **Topologia declarada em código**: beans `Exchange`, `Queue` e `Binding` (via `@Configuration`) no produtor e/ou consumidor — abrir o console do RabbitMQ (15672) e confirmar que `pedidos.criados` existe com o binding correto.
4. **Mensagens em JSON**: `Jackson2JsonMessageConverter` configurado nas duas pontas (ou `content_type: application/json` visível no console); não usar serialização Java padrão.
5. **Consumidor com `@RabbitListener`** apontando para a fila correta, recebendo um DTO/record tipado (não `String` crua parseada na mão).
6. **Tratamento de erro**: mensagem inválida não derruba o consumidor nem entra em loop infinito de requeue (ideal: DLQ implementada; aceitável: `defaultRequeueRejected(false)` ou throw controlado com explicação no README).
7. **RabbitMQ via Docker** com instruções claras no README (comando `docker run` ou compose).
8. Estrutura de dois módulos/aplicações clara, com instrução de como subir cada um.

## Como verificar

```bash
# 1. Broker
docker run -d --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3-management

# 2. Subir consumidor e produtor (terminais separados) e conferir no console:
#    http://localhost:15672 (guest/guest) -> Queues -> pedidos.criados existe

# 3. Enviar pedido válido — esperado 202/200 imediato
curl -i -X POST http://localhost:8080/pedidos -H "Content-Type: application/json" \
  -d '{"cliente":"Ana","email":"ana@email.com","itens":["Mouse"],"total":79.9}'
#    -> log "E-MAIL ENVIADO" no consumidor em segundos

# 4. Prova de desacoplamento: parar o consumidor, enviar 2 pedidos,
#    conferir na fila (mensagens "Ready" = 2), religar consumidor e ver os 2 logs.

# 5. Mensagem inválida (email sem @) — esperado erro tratado, sem loop
curl -X POST http://localhost:8080/pedidos -H "Content-Type: application/json" \
  -d '{"cliente":"Bob","email":"invalido","itens":["X"],"total":10}'
#    -> com DLQ: mensagem aparece em pedidos.dlq no console
```

## Erros comuns a apontar

- "Mensageria falsa": consumidor chamando endpoint REST do produtor, ou ambos lendo o mesmo banco — nenhuma fila envolvida.
- Fila criada manualmente no console em vez de declarada por beans/Bindings (não reproduzível).
- Serialização Java padrão (`SimpleMessageConverter`) — frágil e insegura; exigir JSON.
- Loop infinito de requeue: listener lança exceção, mensagem volta, lança de novo... (falta `defaultRequeueRejected(false)` ou DLQ).
- Timeout implícito: não lembrar que o guest/guest do RabbitMQ só aceita conexão localhost (ok aqui, mas vale a nota se usarem docker-compose com serviços separados).
- Produtor e consumidor com classes DTO de nomes/pacotes idênticos amarrados por dependência de módulo — duplicar o DTO em cada módulo é aceitável e comum; conversar sobre o trade-off.
- Responder 200 só depois do processamento completo (volta a ser síncrono — perde o objetivo).
- `@RabbitListener` em fila errada ou routing key/binding incompatível (mensagem some silenciosamente — verificar "unroutable"/mandatory).

## Padrão de feedback

Valide primeiro o desacoplamento (teste 4 acima): é a prova de que o aluno entendeu mensageria e não apenas copiou configuração. Elogie explicitamente quem implementou a DLQ com argumentos `x-dead-letter-*` — é requisito bônus e mostra maturidade. Se houver loop de requeue ou fila criada na mão, explique o incidente real que isso causaria em produção (consumidor travado com CPU em 100% / deploy em máquina nova sem a fila).

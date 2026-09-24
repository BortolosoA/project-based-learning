# AGENTS.md — Correção do Exercício 24 (Validação e Erros)

## Critérios de correção
1. `pom.xml` inclui `spring-boot-starter-validation` (sem ela, as anotações Jakarta são ignoradas silenciosamente).
2. Existem DTOs separados da entidade: `ProdutoRequest` na entrada e `ProdutoResponse` na saída; o controller não expõe a entidade `Produto`.
3. `ProdutoRequest` usa `@NotBlank`, `@Positive`, `@PositiveOrZero` e `@Email` com mensagens em português.
4. Os endpoints POST e PUT usam `@Valid` junto ao `@RequestBody`.
5. Existe um `@ControllerAdvice`/`@RestControllerAdvice` com `@ExceptionHandler` para `MethodArgumentNotValidException` (400) e para a exceção de "não encontrado" (404).
6. O corpo de erro padronizado contém `timestamp`, `status`, `mensagem` e a lista `erros` por campo (400).
7. Requisição inválida retorna 400 com JSON padronizado — nada de stacktrace nem Whitelabel Error Page.
8. O CRUD com dados válidos continua íntegro (201/200/204/404).

## Como verificar

```bash
./mvnw spring-boot:run

# Deve dar 400 com a lista de campos inválidos
curl -i -X POST "http://localhost:8080/produtos" \
  -H "Content-Type: application/json" \
  -d '{"nome":"","preco":-1,"quantidade":-2,"emailFornecedor":"xyz"}'
# Esperado: HTTP 400; corpo contém timestamp, status, mensagem e 4 itens em "erros"

# Deve funcionar (201)
curl -i -X POST "http://localhost:8080/produtos" \
  -H "Content-Type: application/json" \
  -d '{"nome":"Chá","preco":9.90,"quantidade":5,"emailFornecedor":"contato@cha.com"}'
# Esperado: HTTP 201, corpo é ProdutoResponse (sem emailFornecedor)

# 404 padronizado
curl -i "http://localhost:8080/produtos/999"
# Esperado: HTTP 404, corpo padronizado com mensagem "não encontrado"

# PUT inválido também deve dar 400
curl -i -X PUT "http://localhost:8080/produtos/1" \
  -H "Content-Type: application/json" \
  -d '{"nome":"","preco":0,"quantidade":1,"emailFornecedor":"x@y.com"}'
# Esperado: HTTP 400
```

## Erros comuns a apontar
- Esquecer a dependência `spring-boot-starter-validation` — as anotações existem mas nada é validado.
- Colocar as anotações de validação na **entidade** em vez do DTO de entrada.
- Esquecer o `@Valid` no controller (anotações presentes, mas nunca disparadas).
- Capturar `Exception` genérica no advice e devolver 500 para tudo.
- Devolver campo `erros` nulo em vez de lista (ou formato diferente entre os endpoints).
- Deixar a entidade vazar na resposta (expõe campos internos como o e-mail do fornecedor).

## Padrão de feedback
Ótimo trabalho — validação na entrada e erros padronizados são o que separa uma API de estudo de uma API profissional, e você chegou lá. O foco didático foi: Bean Validation + `@Valid` barram dados ruins antes da lógica, e o `@ControllerAdvice` concentra o tratamento de erro num lugar só. Se algum critério falhou, ajuste somente ele e reexecute os curls de verificação. Esse padrão de DTO + validação + advice será reaproveitado no próximo exercício.

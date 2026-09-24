# AGENTS.md — Correção do Exercício 21 (Primeira API REST)

## Critérios de correção
1. O projeto é uma aplicação Spring Boot 3.x com Java 17+ e a dependência `spring-boot-starter-web` no `pom.xml`.
2. Existe uma classe principal anotada com `@SpringBootApplication` e a aplicação sobe sem erros na porta 8080.
3. O controller está anotado com `@RestController` (e não `@Controller` puro).
4. `GET /saudacao` usa `@RequestParam` com valor padrão ("visitante" ou similar) quando o parâmetro `nome` não é enviado.
5. `GET /saudacao/{nome}` usa `@PathVariable` corretamente.
6. `POST /tarefas` usa `@RequestBody` para receber o JSON e adicionar o item numa lista em memória.
7. `GET /tarefas` retorna todas as tarefas criadas anteriormente (estado mantido em memória entre requisições).
8. Todas as respostas são JSON válido (objetos ou records serializados, não strings concatenadas manualmente).

## Como verificar

```bash
# Subir a aplicação
./mvnw spring-boot:run

# Em outro terminal:
curl "http://localhost:8080/saudacao?nome=Ana"
# Esperado: {"mensagem":"Olá, Ana!"}

curl "http://localhost:8080/saudacao"
# Esperado: {"mensagem":"Olá, visitante!"} (ou equivalente com o nome padrão)

curl "http://localhost:8080/saudacao/Carlos"
# Esperado: {"mensagem":"Olá, Carlos!"}

curl -X POST "http://localhost:8080/tarefas" \
  -H "Content-Type: application/json" \
  -d '{"descricao":"Testar API"}'
curl "http://localhost:8080/tarefas"
# Esperado: lista contendo a tarefa criada
```

## Erros comuns a apontar
- Usar `@Controller` em vez de `@RestController` e esquecer `@ResponseBody`.
- Construir JSON "na mão" com concatenação de strings em vez de retornar objetos.
- Esquecer `defaultValue` (ou `required = false`) no `@RequestParam`, gerando erro 400 quando `nome` não é enviado.
- Recriar a lista de tarefas dentro do método POST, perdendo os dados a cada requisição.
- Conflito de mapeamento: dois métodos com o mesmo path/verbo.

## Padrão de feedback
Parabéns por subir sua primeira API com Spring Boot — esse é um marco importante! O objetivo deste exercício era entender como o Spring despacha requisições HTTP para métodos Java e converte objetos em JSON automaticamente. Se algo faltou, revise apenas o ponto indicado acima: cada anotação (`@RequestParam`, `@PathVariable`, `@RequestBody`) resolve um tipo diferente de entrada, e dominá-las agora facilita todos os próximos exercícios.

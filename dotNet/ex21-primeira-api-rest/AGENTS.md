# AGENTS.md — Correção do Exercício 21 (Primeira API REST)

## Critérios de correção
1. O projeto é uma ASP.NET Core Web API (.NET 8) e sobe sem erros.
2. O controller está anotado com `[ApiController]` e usa `[Route]` com os paths pedidos.
3. `GET /saudacao` usa `[FromQuery]` com valor padrão ("visitante") quando o parâmetro `nome` não é enviado.
4. `GET /saudacao/{nome}` usa `[FromRoute]` corretamente.
5. `POST /tarefas` usa `[FromBody]` para receber o JSON e adiciona o item numa lista em memória.
6. `GET /tarefas` retorna todas as tarefas criadas anteriormente (estado mantido entre requisições).
7. Todas as respostas são JSON válido (objetos serializados, não strings concatenadas na mão).
8. POST retorna 201 (CreatedAtAction ou `StatusCodes.Status201Created`).

## Como verificar

```bash
dotnet run --urls http://localhost:5000
```

Em outro terminal:

```bash
curl -s "http://localhost:5000/saudacao?nome=Ana"
# Esperado: {"mensagem":"Olá, Ana!"}

curl -s "http://localhost:5000/saudacao"
# Esperado: {"mensagem":"Olá, visitante!"} (ou equivalente com o nome padrão)

curl -s "http://localhost:5000/saudacao/Carlos"
# Esperado: {"mensagem":"Olá, Carlos!"}

curl -s -X POST "http://localhost:5000/tarefas" -H "Content-Type: application/json" -d '{"descricao":"Testar API"}'
curl -s "http://localhost:5000/tarefas"
# Esperado: lista contendo a tarefa criada
```

## Erros comuns a apontar
- Construir JSON "na mão" com concatenação de strings em vez de retornar objetos/records.
- Recriar a lista de tarefas dentro do método POST, perdendo os dados a cada requisição
  (a lista deve ser um campo do controller/service).
- Parâmetro opcional mal resolvido: sem valor padrão, `?nome` ausente gera erro ou null.
- Conflito de rota: dois métodos com o mesmo path/verbo.
- Rodar sem `--urls` e testar curl na porta errada (ver "Now listening on" no console).
- Retornar 200 no POST em vez de 201 (aceitar, mas apontar o padrão REST).

## Padrão de feedback
Parabéns por subir sua primeira API com ASP.NET Core — esse é um marco importante!
O objetivo era entender como o framework despacha requisições HTTP para métodos C#
e converte objetos em JSON automaticamente. Se algo faltou, revise apenas o ponto
indicado: cada tipo de binding (`[FromQuery]`, `[FromRoute]`, `[FromBody]`) resolve
um tipo diferente de entrada, e dominá-los agora facilita todos os próximos exercícios.

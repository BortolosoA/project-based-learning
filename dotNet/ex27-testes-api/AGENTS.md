# AGENTS.md — Correção do Exercício 27 (Testes de API)

## Critérios de correção

1. `WebApplicationFactory<Program>` sobe a API nos testes sem banco externo nem porta ocupada.
2. Testes de integração cobrem 201 (POST), 400 (validação), 200 (GET lista), 404 (id inexistente) e 204 (DELETE).
3. Testes unitários do service funcionam com Moq (service não depende de banco real).
4. Banco de teste isolado (SQLite in-memory ou EF InMemory) — os testes NÃO tocam `produtos.db` de dev.
5. `dotnet test` todo verde.
6. Cobertura coletada com `--collect:"XPlat Code Coverage"`; service com 60%+.

## Como verificar

```bash
dotnet test            # esperado: Passed, 0 Failed, 0 Skipped
dotnet test --collect:"XPlat Code Coverage"
grep -o 'line-rate="[0-9.]*"' Tests/TestResults/*/coverage.cobertura.xml
```
Verificar também que os testes não dependem da API estar rodando:
```bash
# com nada rodando na porta 5000, os testes de integração ainda devem passar
```

Verificações estáticas:
```bash
grep -rn "WebApplicationFactory" Tests/         # integração presente
grep -rn "new Mock<" Tests/                      # Moq em uso
grep -rn "localhost:5000" Tests/                 # NÃO deve existir — testes não usam rede
```

## Erros comuns a apontar

- Teste "de integração" que faz `HttpClient` para `http://localhost:5000` exigindo a
  API rodando manualmente — reprova; o objetivo é o factory.
- Esquecer o `<Project Sdk="Microsoft.NET.Sdk.Web">` ou o `public partial class Program {}`
  — o `WebApplicationFactory<Program>` precisa do tipo `Program` acessível.
- Banco de dev sendo apagado/alterado pelos testes — sempre ambiente isolado.
- `Assert.Equal` de double sem `precision`.
- Testes que dependem da ordem de execução (criam em um teste e usam no outro) — cada teste deve ser independente.
- Mock verificando interação demais (`Verify` de tudo) — mockar o necessário, não o cenário inteiro.

## Padrão de feedback

Se tudo verde: agora o aluno tem o ciclo completo (código → API → teste). Mostrar
como rodar cobertura e interpretar o line-rate. Desafio: "deixe a suíte verde e
apague um `[Required]` do DTO — um teste deve quebrar (vermelho). Se quebrou, seus
testes estão realmente protegendo o comportamento; se não, há um buraco na cobertura".

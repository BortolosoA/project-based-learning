# AGENTS.md — Correção do Exercício 16 (Exceções)

## Critérios de correção
1. Duas exceções customizadas herdando de `Exception`, com mensagens claras.
2. As regras de negócio (`Conta`) lançam exceções — não imprimem e retornam.
3. O `Program` usa `try/catch` em volta de cada operação e imprime `ERRO: <mensagem>`.
4. O programa sobrevive a: entrada inválida (texto), valor negativo e saldo insuficiente — sempre volta ao menu.

## Como verificar
```bash
printf "1\n50\n2\n100\n2\n-10\n3\nabc\n0\n" | dotnet run
```
Fluxo: deposita 50, tenta sacar 100 (ERRO saldo), tenta sacar -10 (ERRO valor),
vê saldo, digita texto no menu (não pode quebrar), sai.

## Erros comuns a apontar
- `catch` vazio ou `catch (Exception)` engolindo tudo — capturar o mais específico possível.
- Regra de negócio com `Console.WriteLine` no lugar de `throw` — o exercício exige exceção.
- Mensagem genérica ("Erro") sem usar `ex.Message`.
- Lançar `Exception` genérica em vez das customizadas.

## Padrão de feedback
Verificar o essencial: erros viram exceções na classe de domínio e o Main é a
camada que captura. Esse padrão (lugar certo para cada coisa) será cobrado em
toda a carreira do aluno — vale destacar.

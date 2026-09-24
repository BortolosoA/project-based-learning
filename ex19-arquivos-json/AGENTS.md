# AGENTS.md — Correção do Exercício 19 (Arquivos e JSON)

## Critérios de correção
1. Compila com `javac -cp .:<jar> *.java` e roda com `java -cp .:<jar> Main` (Gson ou Jackson; qualquer um dos dois é aceitável, mas o JSON deve ser de biblioteca, não montado com concatenação de strings).
2. `Tarefa` tem os 5 atributos pedidos, incluindo enum `Prioridade` e `LocalDate`.
3. `LocalDate` serializa/desserializa corretamente (formato ISO `AAAA-MM-DD`) — com Gson, via `TypeAdapter`/`JsonSerializer` registrado; sem isso, Gson quebra com `LocalDate`. **Este é o ponto mais provável de falha: teste.**
4. Primeira execução sem `tarefas.json` funciona e começa vazia, sem exceção.
5. Arquivo JSON gerado é legível e válido (abrir e conferir).
6. CRUD completo funciona: adicionar, listar com filtro, concluir, remover; cada alteração persiste (salva na hora).
7. **Teste de ida e volta**: adicionar tarefa, sair, rodar de novo — a tarefa reaparece.
8. Entradas inválidas (data errada, prioridade inexistente, texto em campo numérico) tratadas com mensagem amigável, sem stack trace.
9. `IOException` tratada no carregar/salvar (não só `throws` na `main`).
10. Remover/arquivar por índice ou id sem `ConcurrentModification` (ex.: não remover dentro de for-each da mesma lista).

## Como verificar
```bash
jar=$(ls gson-*.jar jackson-*.jar 2>/dev/null | head -1); echo "Usando: $jar"
javac -cp .:$jar *.java

# Fluxo: adicionar 1 tarefa, listar pendentes, sair
printf '1\nEstudar Streams\nTerminar ex18\nALTA\n2026-09-30\n2\n2\n0\n' | java -cp .:$jar Main

# Ida e volta: rodar de novo, a tarefa deve aparecer
printf '2\n1\n0\n' | java -cp .:$jar Main

# Arquivo JSON é válido?
cat tarefas.json

# Data inválida não pode quebrar
printf '1\nTeste\nDesc\nMEDIA\n30/09/2026\n0\n' | java -cp .:$jar Main 2>&1 | grep -i "at .*\.java" && echo "FALHOU: stack trace" || echo "OK"
```
Também: apagar `tarefas.json` e rodar — não pode lançar erro; corromper o arquivo (escrever texto inválido) e rodar — deve avisar e não travar.

## Erros comuns a apontar
- Esquecer o adaptador de `LocalDate` no Gson (erro `Unable to invoke no-args constructor`/`InaccessibleObjectException`).
- Montar JSON na mão com `+` de strings (foge do objetivo e quebra com aspas no título).
- Só salvar ao sair: se o programa fecha inesperadamente, perde tudo — salvar a cada alteração é o requisito.
- Resetar a lista ao carregar (`new ArrayList` ignorando o arquivo).
- Não tratar arquivo inexistente na primeira execução.
- Class-path errado: compilar sem `-cp` ou esquecer o `.` no classpath.
- Índice de remoção sem validar intervalo (IndexOutOfBounds).

## Padrão de feedback
Valorize o "teste de ida e volta": se os dados sobrevivem ao reinício, o núcleo está certo — diga isso claramente, pois é o coração do exercício. Aponte no máximo 2-3 problemas, descrevendo o cenário que reproduz ("apaguei o tarefas.json e na primeira execução deu FileNotFoundException"). Finalize conectando com o futuro: "no próximo exercício o Maven baixa essa dependência pra você — nunca mais baixar jar na mão", criando gancho natural para o exercício 20.

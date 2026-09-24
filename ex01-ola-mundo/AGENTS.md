# AGENTS.md — Correção do Exercício 1

## Critérios de correção
1. Existe um arquivo `OlaMundo.java` com uma classe pública `OlaMundo`.
2. O método `main` tem a assinatura correta: `public static void main(String[] args)`.
3. O programa imprime `Olá, Mundo!` e o nome do aluno.

## Como verificar
```bash
javac OlaMundo.java && java OlaMundo
```
A saída deve ter **duas linhas**: a mensagem e o nome.

## Erros comuns a apontar
- Nome da classe diferente do nome do arquivo.
- Falta de ponto e vírgula.
- `String[] args` escrito de forma incorreta.

## Padrão de feedback
- Se compilar e imprimir corretamente: aprovar e parabenizar.
- Caso contrário: explicar o erro de forma didática e apontar a linha do problema. Não reescreva o código inteiro pelo aluno.

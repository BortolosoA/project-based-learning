# Exercício 4 — Entrada de Dados com Scanner

## Conceito
Leitura de dados do usuário com a classe `java.util.Scanner`.

## Introdução ao tema

Programas ficam interessantes quando **interagem com o usuário**. A classe
`java.util.Scanner` lê dados digitados no console, e cada método lê um tipo:
`nextLine()` (texto), `nextInt()` (inteiro), `nextDouble()` (decimal). Não
esqueça do **import** no topo do arquivo — e de que cada leitura espera o
usuário apertar ENTER.

## Exemplo simples

```java
import java.util.Scanner;

Scanner sc = new Scanner(System.in);       // "abre o ouvido" pro console
System.out.print("Digite seu nome: ");     // print não pula linha
String nome = sc.nextLine();               // lê a linha inteira digitada
System.out.println("Olá, " + nome + "!");
```

## Enunciado
Crie um arquivo `Perfil.java` que:
1. Peça o nome do usuário (`nextLine`).
2. Peça a idade (`nextInt`).
3. Peça a altura em metros (`nextDouble`).
4. Imprima: `Olá, <nome>! Você tem <idade> anos e <altura>m de altura.`

## Exemplo
```
Digite seu nome: Ana
Digite sua idade: 25
Digite sua altura: 1.65
Olá, Ana! Você tem 25 anos e 1.65m de altura.
```

## Como executar
```bash
javac Perfil.java
java Perfil
```

## Dica
Não esqueça de importar: `import java.util.Scanner;`

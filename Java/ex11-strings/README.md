# Exercício 11 — Manipulação de Strings

## Conceito
Métodos da classe `String`: `length`, `toUpperCase`, `toLowerCase`, `charAt`, `contains`, `replace`.

## Introdução ao tema

`String` é a classe que representa texto — e uma das mais usadas no dia a dia.
Ela traz dezenas de métodos prontos: `length()` (tamanho), `toUpperCase()`/
`toLowerCase()` (caixa), `charAt(i)` (caractere na posição), `contains(...)`
(contém?), `replace(a, b)` (substitui). E o detalhe que pega todo iniciante:
Strings se comparam com **`equals()`**, nunca com `==`.

## Exemplo simples

```java
String frase = "Aprender Java é bom";

System.out.println(frase.length());       // 19
System.out.println(frase.toUpperCase());  // APRENDER JAVA É BOM
System.out.println(frase.charAt(0));      // A

String outra = "aprender java é bom";
System.out.println(frase.equals(outra));              // false (case sensitive)
System.out.println(frase.equalsIgnoreCase(outra));   // true
```

## Enunciado
Crie um arquivo `AnalisaTexto.java` que leia uma frase do usuário e imprima:
1. Quantidade de caracteres.
2. A frase em MAIÚSCULAS e em minúsculas.
3. A primeira e a última letra.
4. Se a frase contém a palavra "java" (ignorando maiúsculas/minúsculas).
5. A frase com todas as vogais `a` substituídas por `*`.

## Exemplo
```
Digite uma frase: Eu amo Java
Caracteres: 11
MAIÚSCULAS: EU AMO JAVA
minúsculas: eu amo java
Primeira letra: E
Última letra: a
Contém "java"? true
Com vogais trocadas: Eu *mo J*v*
```

## Como executar
```bash
javac AnalisaTexto.java
java AnalisaTexto
```

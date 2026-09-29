# Exercício 6 — Switch Case

## Conceito
Estrutura de seleção múltipla `switch`.

## Introdução ao tema

O `switch` substitui uma **escadinha de if/else** quando você compara *uma
mesma variável* contra vários valores exatos (dias, opções de menu, códigos).
Cada `case` é um valor possível e o `default` cobre "qualquer outro". Cuidado
com o clássico: sem `break`, a execução "cai" para o case seguinte
(*fall-through*) e você imprime tudo de uma vez.

## Exemplo simples

```java
int opcao = 2;

switch (opcao) {
    case 1:
        System.out.println("Adicionar");
        break;                          // sem o break, cairia no próximo case!
    case 2:
        System.out.println("Listar");
        break;
    default:
        System.out.println("Opção inválida");
}
```

## Enunciado
Crie um arquivo `DiaSemana.java` que:
1. Leia um número de 1 a 7.
2. Use `switch` para imprimir o dia da semana correspondente:
   - 1 = Domingo, 2 = Segunda, 3 = Terça, 4 = Quarta, 5 = Quinta, 6 = Sexta, 7 = Sábado.
3. Para qualquer outro número, imprima: `Dia inválido`.

## Exemplo
```
Digite um número (1-7): 3
Terça
```

## Como executar
```bash
javac DiaSemana.java
java DiaSemana
```

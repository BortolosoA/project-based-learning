# Exercício 10 — Arrays

## Conceito
Declaração, preenchimento e percorrimento de arrays.

## Introdução ao tema

Um **array** guarda vários valores **do mesmo tipo** numa única variável,
acessados por **índice a partir de 0**. O tamanho é **fixo** na criação.
`array.length` (sem parênteses!) devolve o tamanho — logo, o último índice
válido é `length - 1`. Ultrapassar esse limite gera o clássico
`ArrayIndexOutOfBoundsException`.

## Exemplo simples

```java
int[] numeros = {10, 20, 30, 40};

System.out.println(numeros[0]);      // 10 (primeiro elemento)
System.out.println(numeros.length);  // 4  (tamanho)

for (int i = 0; i < numeros.length; i++) {
    System.out.println(numeros[i]);  // 10, 20, 30, 40
}
```

## Enunciado
Crie um arquivo `Main.java` que:
1. Peça ao usuário quantas notas deseja informar.
2. Crie um array de `double` com esse tamanho e leia todas as notas.
3. Calcule e imprima:
   - A média das notas;
   - A maior nota;
   - A menor nota.

## Exemplo
```
Quantas notas? 3
Nota 1: 7.5
Nota 2: 4.0
Nota 3: 9.0
Média: 6.833333333333333
Maior: 9.0
Menor: 4.0
```

## Como executar
```bash
javac Main.java
java Main
```

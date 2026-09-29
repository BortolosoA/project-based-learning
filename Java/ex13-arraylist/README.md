# Exercício 13 — ArrayList

## Conceito
Coleções: `ArrayList`, métodos `add`, `remove`, `get`, `size` e laço for-each.

## Introdução ao tema

Arrays têm tamanho fixo — mas programas reais precisam de listas que **crescem
e encolhem**. O `ArrayList` é a coleção mais usada do Java: `add` adiciona,
`remove` remove, `get(i)` acessa, `size()` conta. Entre `<>` (generics)
declaramos o tipo dos elementos: `ArrayList<String>` só aceita strings — o
compilador barra tipo errado na hora.

## Exemplo simples

```java
import java.util.ArrayList;

ArrayList<String> frutas = new ArrayList<>();

frutas.add("Maçã");
frutas.add("Banana");
System.out.println(frutas.size());   // 2
System.out.println(frutas.get(0));    // Maçã

frutas.remove("Maçã");
for (String f : frutas) {             // for-each: percorre sem índice
    System.out.println(f);
}
```

## Enunciado
Crie um arquivo `ListaCompras.java` — um programa interativo com o menu:
```
1 - Adicionar item
2 - Remover item
3 - Listar itens
0 - Sair
```
Regras:
1. Armazene os itens em um `ArrayList<String>`.
2. Ao remover, peça o nome do item; se não existir, imprima `Item não encontrado`.
3. Ao listar, mostre os itens numerados (1., 2., 3., ...) ou `Lista vazia` se não houver itens.
4. O programa só termina quando o usuário escolher 0.

## Como executar
```bash
javac ListaCompras.java
java ListaCompras
```

## Dica
Importe: `import java.util.ArrayList;`

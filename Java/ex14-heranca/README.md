# Exercício 14 — Herança

## Conceito
Herança com `extends`, `super` e sobrescrita de métodos (`@Override`).

## Introdução ao tema

**Herança** evita repetição: a subclasse **herda** atributos e métodos da
superclasse com `extends`, e o `super(...)` chama o construtor do "pai". A
filha pode **sobrescrever** métodos (`@Override`) com comportamento próprio —
e o **polimorfismo** garante que a versão certa execute, mesmo quando tratamos
todos os objetos como o tipo genérico.

## Exemplo simples

```java
class Animal {
    void falar() { System.out.println("..."); }
}

class Gato extends Animal {          // Gato É UM Animal
    @Override
    void falar() {                   // sobrescreve o comportamento
        System.out.println("Miau!");
    }
}

Animal a = new Gato();   // variável genérica, objeto específico
a.falar();               // Miau! — o OBJETO decide, não a variável
```

## Enunciado
Modele uma hierarquia de veículos em arquivos separados:

### `Veiculo.java`
- Atributos: `marca` (String), `ano` (int).
- Construtor e método `descricao()` que imprime: `<marca>, ano <ano>`.

### `Carro.java` (herda de Veiculo)
- Atributo extra: `portas` (int).
- Sobrescreva `descricao()` para incluir as portas: `<marca>, ano <ano>, <portas> portas`.

### `Moto.java` (herda de Veiculo)
- Atributo extra: `cilindradas` (int).
- Sobrescreva `descricao()` para incluir as cilindradas: `<marca>, ano <ano>, <cilindradas>cc`.

### `Main.java`
Crie um `ArrayList<Veiculo>` com pelo menos 1 carro e 1 moto, percorra a lista e chame `descricao()` de cada um.

## Saída esperada (exemplo)
```
Toyota, ano 2020, 4 portas
Honda, ano 2022, 160cc
```

## Como executar
```bash
javac *.java
java Main
```

## Conceito bônus a observar
O laço chama o método certo de cada objeto mesmo tratando todos como `Veiculo` — isso é polimorfismo!

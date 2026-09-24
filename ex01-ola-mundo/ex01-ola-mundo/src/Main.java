import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        var scanner = new Scanner(System.in);

        System.out.print("Quantas notas? ");
        var quantNotas = scanner.nextInt();
        double[] notas = new double[quantNotas];
        int count = 0;
        while (count < quantNotas) {
            System.out.printf("Qual a nota %s? ", (count + 1));
            var nota = scanner.nextDouble();
            notas[count] = nota;
            count++;
        }
        System.out.printf("Notas realizadas: %f\n", notas[1]);

    }
}
import java.util.Random;

public class DosNumerosAleatorios {
    public static void main(String[] args) {
        Random random = new Random();
        int numA = random.nextInt();
        int numB = random.nextInt();

        System.out.println("Numero A: " + numA + "\nNumero B: " + numB);

        System.out.print("\nEl numero mayor es: ");

        if (numA > numB) {
            System.out.println(numA);
        }else {
            System.out.println(numB);
        }
    }
}

import java.util.Arrays;

public class OrdenarListaNumeros {

    public static void ordenarLista(String[] lista) {
        // Convertir el arreglo de String a un arreglo de int
        int[] numeros = new int[lista.length];
        for (int i = 0; i < lista.length; i++) {
            try {
                numeros[i] = (int) Integer.parseInt(lista[i]);
            } catch (NumberFormatException e) {
                System.out.println("El argumento \"" + lista[i] + "\" no es un número válido.");
                return;
            }
        }
        
        // Ordenar el arreglo de números
        Arrays.sort(numeros);
        
        // Imprimir el arreglo ordenado
        System.out.print("Lista ordenada: [");
        for (int i = 0; i < numeros.length; i++) {
            System.out.print(numeros[i]);
            if (i < numeros.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("]");
    }
    
    public static void main(String[] args) {
        ordenarLista(args);
    }
}

import java.util.Arrays;

public class OrdenarListaNumeros {

    public static void ordenarLista(int[] lista) {
        Arrays.sort(lista);
        System.out.print("Lista ordenada: [");
        for (int i = 0; i < lista.length; i++) {
            System.out.print(lista[i]);
            if (i < lista.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("]");
    }
    
    public static void main(String[] args) {
        int[] lista = {5, 3, 2, 1, 4};
        ordenarLista(lista);
    }   
}

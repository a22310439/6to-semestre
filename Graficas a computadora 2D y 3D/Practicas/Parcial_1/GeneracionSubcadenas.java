public class GeneracionSubcadenas {

    public static void generarSubcadena(String cadena) {
        for (int i = cadena.length(); i > 0; i--) {
            System.out.println(cadena.substring(0, i));
        }

        for (int i = cadena.length() - 1; i >= 0; i--) {
            System.out.println(cadena.substring(i, cadena.length()));
        }
    }

    public static void main(String[] args) {
        generarSubcadena(args[0]);
    }
}

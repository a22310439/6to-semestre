public class Hex2IP {

    public void HexToIP(String hex) {
        if (hex.length() != 8) {
            System.out.println("El hexadecimal debe tener 8 caracteres");
            return;
        }
        String ip = "";
        for (int i = 0; i < hex.length(); i += 2) {
            String sub = hex.substring(i, i + 2);
            try{
                int decimal = Integer.parseInt(sub, 16);
                if (i == hex.length() - 2) {
                    ip += decimal;
                    break;
                }else {
                    ip += decimal + ".";
                }
            }catch (NumberFormatException e) {
                System.out.println("El hexadecimal debe ser un número válido");
                return;
            }
        }
        System.out.println(ip);
    }

    public void IPToHex(String ip) {
        String[] octets = ip.split("\\.");
        if (octets.length != 4) {
            System.out.println("La dirección IP debe tener 4 partes");
            return;
        }
        if (octets[0].equals("0")) {
            System.out.println("La dirección IP no puede empezar con 0");
            return;
        }
        String hex = "";
        for (int i = 0; i < octets.length; i++) {
            int decimal = Integer.parseInt(octets[i]);
            if (decimal >= 0 || decimal < 255) {
                hex += Integer.toHexString(decimal);
            }else {
                System.out.println("Cada octeto debe ser un número entre 0 y 255, " + octets[i] + " no lo es");
                return;
            }
        }
        System.out.println(hex);
    }

    public static void main (String[] args) {
        if (args[0].equals("-hex")) {
            Hex2IP hex2ip = new Hex2IP();
            hex2ip.HexToIP(args[1]);
        } else if (args[0].equals("-ip")) {
            Hex2IP ip2hex = new Hex2IP();
            ip2hex.IPToHex(args[1]);
        } else {
            System.out.println("El primer argumento debe ser -hex o -ip");
        }
    }
}
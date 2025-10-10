package ucn.taller2;

import java.util.Scanner;

public class UserInterface {
	Sistema s = new Sistema();
    private final Scanner sc = new Scanner(System.in);

    public void start() {
        while (true) {
            String usuario = login();
            if (usuario ==  null) break;
            
            String rol = s.getRolUsuario(usuario);
            if ("ADMIN".equals(rol)) menuAdmin();
            else if ("USER".equals(rol)) menuUsuario();
            else break;
        }
        System.out.println("Adiós.");
    }

    // LOGIN 
    private String login() {
        System.out.println("\n=== LOGIN ===");
        System.out.print("Usuario: "); String u = sc.nextLine();
        System.out.print("Password: "); String p = sc.nextLine();
        boolean autorizado = s.login(u, p);
        if (!autorizado) { System.out.println("usuario o contraseña incorrectos");
        return null;
        }
        System.out.println("Autenticacion Exitosa");
        return u;
    }
 

    // MENÚ ADMIN 
    private void menuAdmin() {
        int op;
        do {
            System.out.println("\n=== Menú ADMIN ===");
            System.out.println("1) Ver lista completa de PCs (IP, SO y puertos)");
            System.out.println("2) Agregar o eliminar un PC (con sus puertos)");
            System.out.println("3) Clasificar PCs por nivel de riesgo (mostrar vulnerabilidades)");
            System.out.println("0) Cerrar sesión");
            op = readInt("Opción: ");
            switch (op) {
                case 1 -> s.verListaCompletaPCs();     //  usar Sistema.getPCs()
                case 2 -> s.agregarOEliminarPC();      //  Sistema.agregarPC()/eliminarPC()
                case 3 -> s.clasificarRiesgoPCs();     //  contar vulns abiertas y rotular
                case 0 -> System.out.println("Sesión cerrada.");
                default -> System.out.println("Opción inválida.");
            }
        } while (op != 0);
    }

    // MENÚ USUARIO
    private void menuUsuario() {
        int op;
        do {
            System.out.println("\n=== Menú USUARIO ===");
            System.out.println("1) Ver lista de PCs");
            System.out.println("2) Escanear un PC y guardar en reportes.txt");
            System.out.println("3) Ver TODOS los puertos abiertos de la red con su vulnerabilidad");
            System.out.println("4) Ordenar PCs según clase de IP (A/B/C)");
            System.out.println("0) Cerrar sesión");
            op = readInt("Opción: ");
            switch (op) {
                case 1 -> s.verListaPCs();             //TODO: Sistema.getPCs()
                case 2 -> s.escanearPCyGuardar();      //  compilar datos + fecha + riesgo → reportes.txt
                case 3 -> s.verPuertosAbiertosRed();   //  recorrer todos y filtrar "Abierto"
                case 4 -> s.ordenarPCsPorClaseIP();    //  A/B/C según rangos
                case 0 -> System.out.println("Sesión cerrada.");
                default -> System.out.println("Opción inválida.");
            }
        } while (op != 0);
    }

 

    //Util
    private int readInt(String msg) {
        while (true) {
            try { System.out.print(msg); return Integer.parseInt(sc.nextLine().trim()); }
            catch (NumberFormatException e) { System.out.println("Número inválido."); }
        }
    }
}

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
                case 1 -> verListaCompletaPCs();     //  usar Sistema.getPCs()
                case 2 -> agregarOEliminarPC();      //  Sistema.agregarPC()/eliminarPC()
                case 3 -> clasificarRiesgoPCs();     //  contar vulns abiertas y rotular
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
                case 1 -> verListaPCs();             //TODO: Sistema.getPCs()
                case 2 -> escanearPCyGuardar();      //  compilar datos + fecha + riesgo → reportes.txt
                case 3 -> verPuertosAbiertosRed();   //  recorrer todos y filtrar "Abierto"
                case 4 -> ordenarPCsPorClaseIP();    //  A/B/C según rangos
                case 0 -> System.out.println("Sesión cerrada.");
                default -> System.out.println("Opción inválida.");
            }
        } while (op != 0);
    }

    // Placeholders de cada opción
    private void verListaCompletaPCs()      { System.out.println("Mostrar PCs con IP, SO y puertos."); }
    private void agregarOEliminarPC()       { System.out.println("Crear/Eliminar PC + puertos asociados."); }
    private void clasificarRiesgoPCs()      { System.out.println("Riesgo: Bajo(0–1), Medio(1–2), Alto(≥3)."); }
    private void verListaPCs()              { System.out.println("Listar PCs (info básica)."); }
    private void escanearPCyGuardar()       { System.out.println("Guardar: pc, info, puertos, usuario, riesgo, fecha → reportes.txt"); }
    private void verPuertosAbiertosRed()    { System.out.println("Listar TODOS los puertos 'Abierto' + vulnerabilidad."); }
    private void ordenarPCsPorClaseIP()     { System.out.println(" Ordenar por clase A/B/C según rangos."); }

    //Util
    private int readInt(String msg) {
        while (true) {
            try { System.out.print(msg); return Integer.parseInt(sc.nextLine().trim()); }
            catch (NumberFormatException e) { System.out.println("Número inválido."); }
        }
    }
}

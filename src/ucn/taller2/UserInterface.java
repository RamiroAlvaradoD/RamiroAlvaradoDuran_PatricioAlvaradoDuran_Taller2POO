package ucn.taller2;

import java.util.Scanner;

public class UserInterface {

    private final Scanner sc = new Scanner(System.in);

    public void start() {
        boolean continuar = true;
        while (continuar) {
            System.out.println("\nLOGIN  ");
            System.out.println("1) Entrar como ADMIN");
            System.out.println("2) Entrar como USER");
            System.out.println("0) Salir");
            System.out.print("Opción: ");
            int opcion = leerInt();

            switch (opcion) {
                case 1 -> mostrarMenuAdmin();
                case 2 -> mostrarMenuUsuario();
                case 0 -> {
                    System.out.println("Saliendo del sistema...");
                    continuar = false;
                }
                default -> System.out.println("Opción inválida.");
            }
        }
    }

    private void mostrarMenuAdmin() {
        int opcion;
        do {
            System.out.println("\nMENÚ ADMIN");
            System.out.println("1) Ver lista completa de PCs (IP, SO y puertos)");
            System.out.println("2) Agregar o eliminar un PC");
            System.out.println("3) Clasificar PCs por nivel de riesgo");
            System.out.println("0) Cerrar sesión");
            System.out.print("Opción: ");
            opcion = leerInt();

            switch (opcion) {
                case 1 -> System.out.println(" Ver lista completa de PCs");
                case 2 -> System.out.println(" Agregar o eliminar PC");
                case 3 -> System.out.println(" Clasificar PCs por riesgo");
                case 0 -> System.out.println("Sesión cerrada (ADMIN).");
                default -> System.out.println("Opción inválida.");
            }
        } while (opcion != 0);
    }

    private void mostrarMenuUsuario() {
        int opcion;
        do {
            System.out.println("\nMENÚ USUARIO");
            System.out.println("1) Ver lista de PCs");
            System.out.println("2) Escanear un PC (genera reportes.txt)");
            System.out.println("3) Ver puertos abiertos y vulnerabilidades");
            System.out.println("4) Ordenar PCs por clase de IP (A/B/C)");
            System.out.println("0) Cerrar sesión");
            System.out.print("Opción: ");
            opcion = leerInt();

            switch (opcion) {
                case 1 -> System.out.println("Ver lista de PCs");
                case 2 -> System.out.println("Escanear PC y generar reporte");
                case 3 -> System.out.println("Ver puertos abiertos y vulnerabilidades");
                case 4 -> System.out.println("Ordenar PCs por clase de IP");
                case 0 -> System.out.println("Sesión cerrada (USER).");
                default -> System.out.println("Opción inválida.");
            }
        } while (opcion != 0);
    }

    private int leerInt() {
        try {
            return Integer.parseInt(sc.nextLine().trim());
        } catch (Exception e) {
            System.out.println("Entrada inválida, usando 0 por defecto.");
            return 0;
        }
    }
}
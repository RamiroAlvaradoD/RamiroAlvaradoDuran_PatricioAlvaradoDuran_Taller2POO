package ucn.taller2;

import java.util.Scanner;

public class UserInterface {

	private final Scanner sc = new Scanner(System.in);
	Sistema s = new Sistema();

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
		int op;
		do {
			System.out.println("\nMENÚ ADMIN");
			System.out.println("1) Ver lista completa de PCs (IP, SO y puertos)");
			System.out.println("2) Agregar o eliminar un PC");
			System.out.println("3) Clasificar PCs por nivel de riesgo");
			System.out.println("0) Cerrar sesión");
			op = readInt("Opción: ");
			switch (op) {
			case 1 -> s.verListaCompletaPCs(); // usar Sistema.getPCs()
			case 2 -> s.agregarOEliminarPC(); // Sistema.agregarPC()/eliminarPC()
			case 3 -> s.clasificarRiesgoPCs(); // contar vulns abiertas y rotular
			case 0 -> System.out.println("Sesión cerrada.");
			}
			System.out.print("Opción: ");
			op = leerInt();

			switch (op) {
			case 1 -> System.out.println(" Ver lista completa de PCs");
			case 2 -> System.out.println(" Agregar o eliminar PC");
			case 3 -> System.out.println(" Clasificar PCs por riesgo");
			case 0 -> System.out.println("Sesión cerrada (ADMIN).");
			default -> System.out.println("Opción inválida.");
			}
		} while (op != 0);
	}

	private void mostrarMenuUsuario() {
		int op;
		do {
			System.out.println("\nMENÚ USUARIO");
			System.out.println("1) Ver lista de PCs");
			System.out.println("2) Escanear un PC (genera reportes.txt)");
			System.out.println("3) Ver puertos abiertos y vulnerabilidades");
			System.out.println("4) Ordenar PCs por clase de IP (A/B/C)");
			System.out.println("0) Cerrar sesión");
			op = readInt("Opción: ");
			switch (op) {
			case 1 -> s.verListaPCs(); // TODO: Sistema.getPCs()
			case 2 -> s.escanearPCyGuardar(); // compilar datos + fecha + riesgo → reportes.txt
			case 3 -> s.verPuertosAbiertosRed(); // recorrer todos y filtrar "Abierto"
			case 4 -> s.ordenarPCsPorClaseIP(); // A/B/C según rangos
			case 0 -> System.out.println("Sesión cerrada.");
			}
			System.out.print("Opción: ");
			op = leerInt();

			switch (op) {
			case 1 -> System.out.println("Ver lista de PCs");
			case 2 -> System.out.println("Escanear PC y generar reporte");
			case 3 -> System.out.println("Ver puertos abiertos y vulnerabilidades");
			case 4 -> System.out.println("Ordenar PCs por clase de IP");
			case 0 -> System.out.println("Sesión cerrada (USER).");
			default -> System.out.println("Opción inválida.");
			}
		} while (op != 0);
	}

	// Util
	private int readInt(String msg) {
		while (true) {
			try {
				System.out.print(msg);
				return Integer.parseInt(sc.nextLine().trim());
			} catch (NumberFormatException e) {
				System.out.println("Número inválido.");
			}
		}
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
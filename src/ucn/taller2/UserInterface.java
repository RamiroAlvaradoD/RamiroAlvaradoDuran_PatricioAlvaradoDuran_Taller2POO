package ucn.taller2;

import java.util.Scanner;

public class UserInterface {
	private Sistema sistema;
	public void setSistema(Sistema sistema) {
	    this.sistema = sistema;
	}

	

	private final Scanner sc = new Scanner(System.in);
	Sistema s = new Sistema();

	public void start() {
		boolean continuar = true;
		s.loadFiles();
		while (continuar) {
			System.out.println("\nLOGIN  ");
			System.out.print("Usuario: ");
			String user = sc.nextLine();
			System.out.print("Contraseña: ");
			String pass = sc.nextLine();

			if (s.login(user, pass)) {
				String rol = s.getRolUsuario(user);
				System.out.println("Bienvenid@, has entrado como " + rol);
				if ("ADMIN".equalsIgnoreCase(rol)) {
					mostrarMenuAdmin();
				}else if ("USER".equalsIgnoreCase(rol)) {
					mostrarMenuUsuario();
				}
			} else {
				System.out.println("Usuario o contraseña incorrectos. Intenta de nuevo");

			}
			System.out.print("\nDeseas intentar de nuevo?(S/N): ");
			String op = sc.nextLine();
			if (!op.equalsIgnoreCase("S")) {
				System.out.println("Saliendo del sistema...");
				continuar =false;
			}
		}

	}

	private void mostrarMenuAdmin() {
	    int op;
	    do {
	        System.out.println("\n=== MENÚ ADMIN ===");
	        System.out.println("1) Ver lista completa de PCs (IP, SO y puertos)");
	        System.out.println("2) Agregar o eliminar un PC");
	        System.out.println("3) Clasificar PCs por nivel de riesgo");
	        System.out.println("0) Cerrar sesión");

	        op = readInt("Opción: ");

	        switch (op) {
	            case 1 -> sistema.verListaCompletaPCs();
	            case 2 -> sistema.agregarOEliminarPC();
	            case 3 -> sistema.clasificarRiesgoPCs();
	            case 0 -> System.out.println("Sesión cerrada (ADMIN).");
	            default -> System.out.println("Opción inválida.");
	        }

	    } while (op != 0);
	}


	private void mostrarMenuUsuario() {
	    int op;
	    do {
	        System.out.println("\n=== MENÚ USUARIO ===");
	        System.out.println("1) Ver lista de PCs");
	        System.out.println("2) Escanear un PC (genera reportes.txt)");
	        System.out.println("3) Ver puertos abiertos y vulnerabilidades");
	        System.out.println("4) Ordenar PCs por clase de IP (A/B/C)");
	        System.out.println("0) Cerrar sesión");

	        op = readInt("Opción: ");

	        switch (op) {
	            case 1 -> sistema.verListaPCs();
	            case 2 -> sistema.escanearPCyGuardar();
	            case 3 -> sistema.verPuertosAbiertosRed();
	            case 4 -> sistema.ordenarPCsPorClaseIP();
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

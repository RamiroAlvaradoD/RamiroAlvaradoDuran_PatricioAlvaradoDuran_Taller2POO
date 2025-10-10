package ucn.taller2;

import java.util.Base64;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.security.MessageDigest;
public class Sistema {
	private ArrayList<PC> listaPCs = new ArrayList<>();
	private ArrayList<Puerto> listaPuertos = new ArrayList<>();
	private ArrayList<Vulnerabilidad> listaVulnerabilidades = new ArrayList<>();

	public boolean login(String usuario, String contraseña) {
		try {
			Scanner sc = new Scanner(new File("data/usuarios.txt"));
			while (sc.hasNextLine()) {
				String line = sc.nextLine();
				String[] data = line.split(";");
				String userStored = data[0];
				String hashPass = data[1];
				String userType = data[2];
				if (userStored.equals(usuario)) {
					String hashInput = hashSHA256Base64(contraseña);
					sc.close();
					return hashInput.equals(hashPass);
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("Error leyendo usuarios.txt");
		}
		return false;
	}

	private String hashSHA256Base64(String pass) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte[] hash = md.digest(pass.getBytes());
			return Base64.getEncoder().encodeToString(hash);
		} catch (Exception e) {
			return null;
		}
	}

	public String getRolUsuario(String usuario) {
		try {
			File file = new File("data/usuarios.txt");
			Scanner sc = new Scanner(file);
			while (sc.hasNextLine()) {
				String linea = sc.nextLine();
				String[] datos = linea.split(";");
				String userStored = datos[0];
				if (userStored.equals(usuario) && datos.length >= 3) {
					sc.close();
					return datos[2]; // retorna el rol, ej. "ADMIN" o "USER"
				}
			}
			sc.close();
		} catch (Exception e) {
			System.out.println("Error leyendo usuarios.txt");
		}
		return null; // si no encuentra al usuario
	}

	public void agregarOEliminarPC() {
		// TODO Auto-generated method stub

	}

	public void verListaCompletaPCs() {
		for (PC pc : listaPCs) {
			System.out.println(pc);
			System.out.println("Puertos:");
			for (Puerto puerto : pc.getPuertos()) {
				String vuln = (puerto.getVulnerabilidad() != null) ? puerto.getVulnerabilidad().getNombre()
						: "Sin vulnerabilidad";
				System.out.println("  - " + puerto.getNumero() + " (" + puerto.getEstado() + ", " + vuln + ")");
			}
		}
	}

	public void clasificarRiesgoPCs() {
		for (PC pc : listaPCs) {
			int cantVuln = 0;
			for (Puerto p : pc.getPuertos()) {
				if (p.getVulnerabilidad() != null) {
					cantVuln++;
					System.out.println(pc);
				}
			}
			String risk = (cantVuln == 0 || cantVuln == 1) ? "Bajo": (cantVuln <=2) ? "Medio" : "Alto";
			System.out.println("PC "+pc.getId() + " - Nivel de riesgo = "+risk+" ("+cantVuln + " vulnerabilidades");
		}

	}

	public void verListaPCs() {
		for (PC pc : listaPCs) {
			System.out.println(pc);
		}

	}

	public void escanearPCyGuardar() {
		// TODO Auto-generated method stub

	}

	public void verPuertosAbiertosRed() {
		for (Puerto p : listaPuertos) {
			if (p.getEstado().equals("Abierto")) {
				System.out.println(p);
			}
		}

	}

	public void ordenarPCsPorClaseIP() {
		// TODO Auto-generated method stub

	}

	public void loadFiles() {
		loadPC("data/pcs.txt");
		loadPorts("data/puertos.txt");
		loadVuln("data/vulnerabilidades.txt");

	}

	public void loadVuln(String file) {
		try {
			Scanner sc = new Scanner(new File(file));
			while (sc.hasNextLine()) {
				String linea = sc.nextLine();
				String[] data = linea.split("\\|");
				int port = Integer.parseInt(data[0]);
				String name = data[1];
				String desc = data[2];
				Vulnerabilidad vuln = new Vulnerabilidad(port, name, desc);
				listaVulnerabilidades.add(vuln);

				for (Puerto puerto : listaPuertos) {
					if (puerto.getNumero() == port) {
						puerto.setVulnerabilidad(vuln);
					}
				}

			}
		} catch (FileNotFoundException e) {
			System.out.println("No se puede abrir " + file);
		}

	}

	public void loadPorts(String file) {
		try {
			Scanner sc = new Scanner(new File(file));
			while (sc.hasNextLine()) {
				String linea = sc.nextLine();
				String[] data = linea.split("\\|");
				String idPC = data[0];
				int numPort = Integer.parseInt(data[1]);
				String estado = data[2];
				Puerto puerto = new Puerto(idPC, numPort, estado);
				listaPuertos.add(puerto);

				for (PC pc : listaPCs) {
					if (pc.getId().equals(idPC)) {
						pc.getPuertos().add(puerto);
					}
				}

			}
		} catch (FileNotFoundException e) {
			System.out.println("No se puede abrir " + file);
		}

	}

	public void loadPC(String file) {
		try {
			Scanner sc = new Scanner(new File(file));
			while (sc.hasNextLine()) {
				String linea = sc.nextLine();
				String[] data = linea.split("\\|");
				String id = data[0];
				String ip = data[1];
				String os = data[2];
				PC pc = new PC(id, ip, os);
				listaPCs.add(pc);
			}

		} catch (FileNotFoundException e) {
			System.out.println("No se puede abrir " + file);
		}

	}
}

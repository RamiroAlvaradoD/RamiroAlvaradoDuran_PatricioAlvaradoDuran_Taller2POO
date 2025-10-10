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
	private ArrayList<Usuario> listaUsuarios = new ArrayList<>();
	private String usuarioActual;
	
	public boolean login(String usuario, String contraseña) {
		for (Usuario u : listaUsuarios) {
			if (u.getUsername().equals(usuario)){
				String hashInput = hashSHA256Base64(contraseña);

				usuarioActual = usuario;
				return hashInput.equals(u.getHash());
			}
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
		Scanner sc = new Scanner(System.in);
		int op;
		
			
		
		System.out.print("Deseas agregar (1) o eliminar (2) un PC?");
		op = sc.nextInt();
		switch (op) {
		case 1:
			System.out.print("ID: ");String id = sc.nextLine();
			System.out.print("IP(xxx.xxx.xxx): ");String ip = sc.nextLine();
			System.out.print("Sistema Operativo: ");String OS = sc.nextLine();			
			PC pc = new PC(id, ip, OS);
			listaPCs.add(pc);
			System.out.print("Cantidad de puertos a agregar?: "); int cant = sc.nextInt();
			sc.nextLine();
			for(int i =0; i<cant;i++) {
				System.out.print("Numero de puerto: "); int port = sc.nextInt();
				sc.nextLine();
				System.out.print("Estado(Abierto o Cerrado): "); String estado = sc.nextLine();
				Puerto p = new Puerto(id,port,estado);
				pc.getPuertos().add(p);
				listaPuertos.add(p);
			}
			System.out.println("PC agregado correctamente");
			break;
		case 2:
			System.out.print("ID del PC a Eliminar: "); String idPC = sc.nextLine();
			PC encontrado = null;
			for (PC pcs : listaPCs) {
				if (pcs.getId().equals(idPC)) {
					encontrado = pcs;
					break;
				}
				
			}
			if (encontrado !=null) {
				listaPCs.remove(encontrado);
				ArrayList<Puerto> porRemover = new ArrayList<>(encontrado.getPuertos());
				listaPuertos.removeAll(porRemover);
				System.out.println("PC eliminada correctamente.");
			}else {
				System.out.println("No se encontro esa PC.");
			}
			break;
		default:
			System.out.println("Ingrese una opcion valida");
			break;
		}

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
			System.out.println("PC "+pc.getId() + " - Nivel de riesgo = "+risk+" ("+cantVuln + " vulnerabilidades)");
		}

	}

	public void verListaPCs() {
		for (PC pc : listaPCs) {
			System.out.println(pc);
		}

	}

	public void escanearPCyGuardar() {
		
		Scanner sc = new Scanner(System.in);
		System.out.print("ID del PC a escanear: ");
		String id = sc.nextLine();
		for (PC pc : listaPCs) {
			if (pc.getId().equals(id)) {
				int cantVuln = 0;
				for (Puerto puerto : pc.getPuertos()) {
					if (puerto.getVulnerabilidad() != null) cantVuln++;
				}
				String nivel = (cantVuln == 0 || cantVuln == 1) ? "Bajo": (cantVuln <=2) ? "Medio" : "Alto";
				
				
				//Mostrar informacion
				System.out.println("Escaneo de: "+id);
				System.out.println("IP: "+pc.getIp() + ", SO: "+pc.getSistemaOperativo());
				for (Puerto puerto : pc.getPuertos()) {
					System.out.println(puerto);
				}
				System.out.println("Nivel de riesgo: "+ nivel);
				
				try {
					FileWriter fw = new FileWriter("data/reportes.txt", true);
					fw.write("Usuario: "+ usuarioActual+", PC: "+id+ ", IP: "+pc.getIp()+", SO: "+pc.getSistemaOperativo()+", Puertos: "
					+pc.getPuertos().toString()+", Nivel de riesgo: "+nivel+", Fecha: "+java.time.LocalDateTime.now()+"\n");
					fw.close();
				}catch(IOException e) {
					System.out.println("Error al guardar el reporte");					
				}
				return;
			}	
		}
		System.out.println("No se encontro ese PC");

	}

	public void verPuertosAbiertosRed() {
		for (Puerto p : listaPuertos) {
			if (p.getEstado().equals("Abierto")) {
				System.out.println(p);
			}
		}

	}

	public void ordenarPCsPorClaseIP() {
		ArrayList<PC> claseA = new ArrayList<>();
		ArrayList<PC> claseB = new ArrayList<>();
		ArrayList<PC> claseC = new ArrayList<>();
		
		for (PC pc : listaPCs) {
			String[] ipParts = pc.getIp().split("\\.");
			int primerOct = Integer.parseInt(ipParts[0]);
			if (primerOct >= 0 && primerOct <= 127) claseA.add(pc);
			else if (primerOct >= 128 && primerOct <= 191) claseB.add(pc);
			else if (primerOct >= 192 && primerOct <= 223) claseC.add(pc);
		}
		System.out.println("Clase A:");
		claseA.forEach(pc -> System.out.println(pc));
		
		System.out.println("Clase B:");
		claseB.forEach(pc -> System.out.println(pc));
		
		System.out.println("Clase C:");
		claseC.forEach(pc -> System.out.println(pc));

	}

	public void loadFiles() {
		loadPC("data/pcs.txt");
		loadPorts("data/puertos.txt");
		loadVuln("data/vulnerabilidades.txt");
		loadUsers("data/usuarios.txt");

	}

	private void loadUsers(String file) {
		try {
			Scanner sc = new Scanner(new File(file));
			while (sc.hasNextLine()) {
				String linea = sc.nextLine();
				String[] data = linea.split(";");
				String name = data[0];
				String hash = data[1];
				String type = data[2];
				Usuario usuario = new Usuario(name, hash, type);
				listaUsuarios.add(usuario);
			}
		}catch(FileNotFoundException e) {
			
		}
		
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

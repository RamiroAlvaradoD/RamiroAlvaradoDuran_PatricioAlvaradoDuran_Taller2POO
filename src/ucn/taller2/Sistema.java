package ucn.taller2;

import java.util.Base64;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;
import java.util.ArrayList;

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

	public void agregarOEliminarPC(Scanner sc) {
	    System.out.print("¿Deseas agregar (1) o eliminar (2) un PC? ");
	    int op;
	    try {
	        op = Integer.parseInt(sc.nextLine().trim());
	    } catch (NumberFormatException e) {
	        System.out.println("Opción inválida.");
	        return;
	    }

	    switch (op) {
	        case 1: { // AGREGAR
	            System.out.print("ID: ");
	            String id = sc.nextLine().trim();

	            System.out.print("IP (xxx.xxx.xxx.xxx): ");
	            String ip = sc.nextLine().trim();

	            System.out.print("Sistema Operativo: ");
	            String OS = sc.nextLine().trim();

	            if (getPCById(id) != null) {
	                System.out.println("Ya existe un PC con ese ID.");
	                return;
	            }

	            PC pc = new PC(id, ip, OS);
	            listaPCs.add(pc);

	            System.out.print("Cantidad de puertos a agregar?: ");
	            int cant = 0;
	            try { cant = Integer.parseInt(sc.nextLine().trim()); } catch (NumberFormatException ignored) {}

	            for (int i = 0; i < cant; i++) {
	                System.out.print("Número de puerto: ");
	                int port;
	                try { port = Integer.parseInt(sc.nextLine().trim()); }
	                catch (NumberFormatException e) { System.out.println("Puerto inválido, se omite."); continue; }

	                System.out.print("Estado (Abierto o Cerrado): ");
	                String estado = sc.nextLine().trim();

	                Puerto p = new Puerto(id, port, estado);
	                // enlaza vulnerabilidad si existe
	                Vulnerabilidad v = getVulnByPort(port);
	                if (v != null) p.setVulnerabilidad(v);

	                pc.getPuertos().add(p);
	                listaPuertos.add(p);
	            }

	            System.out.println("PC agregada correctamente.");
	            break;
	        }

	        case 2: { // ELIMINAR
	            System.out.print("ID del PC a eliminar: ");
	            String idPC = sc.nextLine().trim();

	            PC encontrado = null;
	            for (PC pcs : listaPCs) {
	                if (pcs.getId().equals(idPC)) { encontrado = pcs; break; }
	            }

	            if (encontrado != null) {
	                // quitar puertos asociados del global
	                ArrayList<Puerto> porRemover = new ArrayList<>(encontrado.getPuertos());
	                for (Puerto p : porRemover) {
	                    listaPuertos.remove(p);
	                }
	                listaPCs.remove(encontrado);
	                System.out.println("PC eliminada correctamente.");
	            } else {
	                System.out.println("No se encontró esa PC.");
	            }
	            break;
	        }

	        default:
	            System.out.println("Ingrese una opción válida");
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

	public void escanearPCyGuardar(Scanner sc) {
	    try  {
	        System.out.print("ID del PC a escanear: ");
	        String id = sc.nextLine();
	        for (PC pc : listaPCs) {
	            if (pc.getId().equals(id)) {
	                int cantVuln = 0;
	                for (Puerto puerto : pc.getPuertos()) {
	                    if (puerto.getVulnerabilidad() != null) cantVuln++;
	                }
	                String nivel = (cantVuln == 0 || cantVuln == 1) ? "Bajo" : (cantVuln <= 2) ? "Medio" : "Alto";

	                // Mostrar informacion
	                System.out.println("Escaneo de: " + id);
	                System.out.println("IP: " + pc.getIp() + ", SO: " + pc.getSistemaOperativo());
	                for (Puerto puerto : pc.getPuertos()) {
	                    System.out.println(puerto);
	                }
	                System.out.println("Nivel de riesgo: " + nivel);

	                try {
	                    FileWriter fw = new FileWriter("data/reportes.txt", true);
	                    fw.write("Usuario: " + usuarioActual + ", PC: " + id + ", IP: " + pc.getIp() + ", SO: " + pc.getSistemaOperativo() + ", Puertos: "
	                            + pc.getPuertos().toString() + ", Nivel de riesgo: " + nivel + ", Fecha: " + java.time.LocalDateTime.now() + "\n");
	                    fw.close();
	                } catch (IOException e) {
	                    System.out.println("Error al guardar el reporte");
	                }
	                return;
	            }
	        }
	    } catch (Exception e) {
	        
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
		try (Scanner sc = new Scanner(new File(file))) {
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
		try (Scanner sc = new Scanner(new File(file))) {
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
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	

	public void loadPorts(String file) {
		try (Scanner sc = new Scanner(new File(file))) {
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
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void loadPC(String file) {
		try (Scanner sc = new Scanner(new File(file))) {
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
	
	private PC getPCById(String id) {
	    for (PC pc : listaPCs) {
	        if (pc.getId().equalsIgnoreCase(id)) {
	            return pc;
	        }
	    }
	    return null;
	}
	
	private Vulnerabilidad getVulnByPort(int numPuerto) {
	    for (Vulnerabilidad v : listaVulnerabilidades) {
	        if (v.getPuertoAfectado() == numPuerto) {
	            return v;
	        }
	    }
	    return null;
	}
	
}

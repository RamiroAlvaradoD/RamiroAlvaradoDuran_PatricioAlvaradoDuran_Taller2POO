package ucn.taller2;

import java.util.Base64;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.security.MessageDigest;
public class Sistema {

	
	public boolean login(String usuario, String contraseña) {
		try {
			Scanner sc = new Scanner(new File("data/usuarios.txt"));
			while(sc.hasNextLine()) {
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
		}catch(FileNotFoundException e) {
			System.out.println("Error leyendo usuarios.txt");
		}
		return false;
	}
	private String hashSHA256Base64(String pass) {
		try {
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		byte[] hash = md.digest(pass.getBytes());
		return Base64.getEncoder().encodeToString(hash);
		}
		catch(Exception e) {
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
	public Object agregarOEliminarPC() {
		// TODO Auto-generated method stub
		return null;
	}
	public Object verListaCompletaPCs() {
		// TODO Auto-generated method stub
		return null;
	}
	public Object clasificarRiesgoPCs() {
		// TODO Auto-generated method stub
		return null;
	}
	public Object verListaPCs() {
		// TODO Auto-generated method stub
		return null;
	}
	public Object escanearPCyGuardar() {
		// TODO Auto-generated method stub
		return null;
	}
	public Object verPuertosAbiertosRed() {
		// TODO Auto-generated method stub
		return null;
	}
	public Object ordenarPCsPorClaseIP() {
		// TODO Auto-generated method stub
		return null;
	}
}

package ucn.taller2;

public class Usuario {
	private String username;
	private String passHash;
	private String rol;
	
	
	public Usuario (String username, String passHash, String rol) {
		this.username = username;
		this.passHash = passHash;
		this.rol = rol;
	}

}

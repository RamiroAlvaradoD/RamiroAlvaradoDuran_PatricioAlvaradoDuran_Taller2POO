package ucn.taller2;

/**
 * Representa a un usuario del sistema.
 * Puede ser ADMIN o USER según su rol.
 */
public class Usuario {

    private String username;
    private String passwordHash;
    private String rol;

    /**
     * Constructor de Usuario.
     *
     * @param username nombre del usuario
     * @param passwordHash contraseña hasheada (SHA-256 + Base64)
     * @param rol rol del usuario (ADMIN o USER)
     */
    public Usuario(String username, String passwordHash, String rol) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.rol = rol;
    }

    // Getters
    public String getUsername() {
        return username;
    }

    public String getHash() {
        return passwordHash;
    }

    public String getRol() {
        return rol;
    }

    // Método para verificar si el usuario es administrador
    public boolean esAdmin() {
        return rol.equalsIgnoreCase("ADMIN");
    }

    // Método para verificar si el usuario es usuario normal
    public boolean esUser() {
        return rol.equalsIgnoreCase("USER");
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "username='" + username + '\'' +
                ", rol='" + rol + '\'' +
                '}';
    }
}

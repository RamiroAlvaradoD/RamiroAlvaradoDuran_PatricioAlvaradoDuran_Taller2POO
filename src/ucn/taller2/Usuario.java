package ucn.taller2;

public class Usuario {
    private final String username;
    private final String hash; // contraseña en SHA-256 codificada en Base64
    private final String rol;  // ADMIN o USER

    public Usuario(String username, String hash, String rol) {
        this.username = username;
        this.hash = hash;
        this.rol = rol;
    }

    public String getUsername() {
        return username;
    }

    public String getHash() {
        return hash;
    }

    public String getRol() {
        return rol;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "username='" + username + '\'' +
                ", rol='" + rol + '\'' +
                '}';
    }
}
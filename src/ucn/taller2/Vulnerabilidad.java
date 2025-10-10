package ucn.taller2;

public class Vulnerabilidad {
    private int puerto;
    private String nombre;
    private String descripcion;

    public Vulnerabilidad(int puerto, String nombre, String descripcion) {
        this.puerto = puerto;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public int getPuerto() {
        return puerto;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    @Override
    public String toString() {
        return "Vulnerabilidad{" +
                "puerto=" + puerto +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}
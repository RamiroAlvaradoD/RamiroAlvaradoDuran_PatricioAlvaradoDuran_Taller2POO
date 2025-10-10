package ucn.taller2;

/**
 * Representa una vulnerabilidad asociada a un puerto.
 */
public class Vulnerabilidad {

    private int puertoAfectado;
    private String nombre;
    private String descripcion;

    /**
     * Constructor de Vulnerabilidad.
     *
     * @param puertoAfectado número del puerto afectado
     * @param nombre nombre de la vulnerabilidad
     * @param descripcion descripción del problema
     */
    public Vulnerabilidad(int puertoAfectado, String nombre, String descripcion) {
        this.puertoAfectado = puertoAfectado;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    // Getters
    public int getPuertoAfectado() {
        return puertoAfectado;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    @Override
    public String toString() {
        return nombre + " (Puerto " + puertoAfectado + "): " + descripcion;
    }
}

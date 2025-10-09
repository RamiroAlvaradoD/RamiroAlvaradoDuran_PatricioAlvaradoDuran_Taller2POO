package ucn.taller2;

public class Puerto {
    private final int numero;
    private final String estado; // "abierto" o "cerrado"
    private final Vulnerabilidad vulnerabilidad; // puede ser null

    public Puerto(int numero, String estado, Vulnerabilidad vulnerabilidad) {
        this.numero = numero;
        this.estado = estado;
        this.vulnerabilidad = vulnerabilidad;
    }

    public int getNumero() {
        return numero;
    }

    public String getEstado() {
        return estado;
    }

    public Vulnerabilidad getVulnerabilidad() {
        return vulnerabilidad;
    }

    @Override
    public String toString() {
        String v = (vulnerabilidad == null) ? "sin vuln" : vulnerabilidad.getNombre();
        return "Puerto " + numero + " (" + estado + ", " + v + ")";
    }
}


package ucn.taller2;

public class Puerto {
	private String id;
    private int numero;
    private String estado; // "abierto" o "cerrado"
    private Vulnerabilidad vulnerabilidad; // puede ser null

    public Puerto(String id, int numero, String estado) {
    	this.id = id;
        this.numero = numero;
        this.estado = estado;
    }

    public String getId() {
    	return id;
    }
    public int getNumero() {
        return numero;
    }

	public Vulnerabilidad getVulnerabilidad() {
		return vulnerabilidad;
	}

    public void setVulnerabilidad(Vulnerabilidad vuln) {
    	vulnerabilidad = vuln;
    }


    @Override
    public String toString() {
        String v = (vulnerabilidad == null) ? "sin vuln" : vulnerabilidad.getNombre();
        return "Puerto " + numero + " (" + estado + ", " + v + ")";
    }

	public String getEstado() {
		return estado;
	}
}

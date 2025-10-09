package ucn.taller2;
import java.util.ArrayList;

public class PC {
	private String id;
	private String ip;
	private String sistemaOperativo;
	private ArrayList<Puerto> puertos;
	
	public PC(String id, String ip, String sistemaOperativo) {
		this.id = id;
		this.ip = ip;
		this.sistemaOperativo = sistemaOperativo;
		this.puertos = new ArrayList<>();
	}

}

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

	public String getId() {
		return id;
	}

	public String getIp() {
		return ip;
	}

	public String getSistemaOperativo() {
		return sistemaOperativo;
	}

	public ArrayList<Puerto> getPuertos() {
		return puertos;
	}

	@Override
	public String toString() {
		return "PC[" + "id='" + id + '\'' + ", ip='" + ip + '\'' + ", sistemaOperativo='" + sistemaOperativo + '\''
				+ ']';
	}
}

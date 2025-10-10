package ucn.taller2;
import java.util.ArrayList;

import java.util.ArrayList;


public class PC {
private final String id;
private final String ip;
private final String sistemaOperativo;
private final ArrayList<Puerto> puertos;


public PC(String id, String ip, String sistemaOperativo) {
this.id = id;
this.ip = ip;
this.sistemaOperativo = sistemaOperativo;
this.puertos = new ArrayList<Puerto>();
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
return "PC{" +
"id='" + id + '\'' +
", ip='" + ip + '\'' +
", sistemaOperativo='" + sistemaOperativo + '\'' +
'}';
}
}

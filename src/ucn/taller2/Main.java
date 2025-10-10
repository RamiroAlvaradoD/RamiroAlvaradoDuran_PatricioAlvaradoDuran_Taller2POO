package ucn.taller2;

/*
// Nombres: Ramiro Alvarado Durán - Patricio Alvarado Durán
// RUT: 19.428.146-3 - 20.955.249-3
// Carrera: ITI - ITI
// Taller 2 - Programación Orientada a Objetos
*/

public class Main {

    public static void main(String[] args) {

        // 1) Cargar todo desde /data
        Sistema sistema = new Sistema();
        sistema.loadFiles();

        // 2) Lanzar la interfaz (versión sin constructor con parámetros)
        UserInterface ui = new UserInterface();
        ui.setSistema(sistema); // ← agregamos un setter en UserInterface
        ui.start();
    }
}

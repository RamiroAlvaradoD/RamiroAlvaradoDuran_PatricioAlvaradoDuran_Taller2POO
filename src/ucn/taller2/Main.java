package ucn.taller2;

/*
// Nombres: Ramiro Alvarado Durán - Patricio Alvarado Durán
// RUT: 19.428.146-3 - 20.955.249-3
// Carrera: ITI - ITI
// Taller 2 - Programación Orientada a Objetos
//
*/

/*Clase Main, esta inicia el sistema principal que se encarga de cargar los datos de los archivos
*y lanza la interfaz de usuario para interactuar mediante menus
*/

public class Main {
/*
 * El método main crea un objeto Sistema, este se encarga de toda la lógica
 * del programa, y llama al método loadFiles para leer los txt.
 * Luego, se crea la interfaz de usuario y se conecta el sistema a esta interfaz
 * finalizando con el método start, que da paso al login y posterior navegación 
 * entre menús.
 */
    public static void main(String[] args) {

        
        Sistema sistema = new Sistema();
        sistema.loadFiles();

        
        UserInterface ui = new UserInterface();
        ui.setSistema(sistema); 
        ui.start();
    }
}

# Taller N°2 - Programación Orientada a Objetos  
**II Semestre – 2025**  
**Carrera:** Ingeniería en Computación e Informática (ITI)  
**Universidad Católica del Norte**

---

## Integrantes  
- **Ramiro Alvarado Durán** — RUT: 19.428.146-3  
- **Patricio Alvarado Durán** — RUT: 20.955.249-3  

---

## Contexto  
En el año **2032**, la empresa **SecureNet Ltda.** detectó intentos de acceso no autorizado a su red interna.  
Como medida preventiva, contrató a **EclipSec**, encargada de desarrollar una herramienta de análisis y monitoreo de red.

El sistema deberá:
- Cargar información de PCs, puertos, vulnerabilidades y usuarios desde archivos `.txt`.
- Permitir login según **rol (ADMIN o USER)**.
- Implementar **menús diferenciados**:
  - Menú **Administrador:** gestión y clasificación de PCs.
  - Menú **Usuario:** escaneos, reportes y visualización de puertos abiertos.
- Generar un archivo de salida `reportes.txt` simulando un registro de auditoría.

---

## Archivos de entrada
| Archivo | Descripción |
|----------|--------------|
| `pcs.txt` | Lista de equipos (ID, IP, Sistema Operativo) |
| `puertos.txt` | Estado de puertos (ID PC, número, estado) |
| `usuarios.txt` | Usuarios registrados (nombre, hash SHA-256+Base64, rol) |
| `vulnerabilidades.txt` | Vulnerabilidades por puerto (número, nombre, descripción) |

---

## Requerimientos principales
### Menú Administrador
- Ver lista completa de PCs y puertos asociados.  
- Agregar o eliminar PCs.  
- Clasificar PCs por nivel de riesgo:
  - Bajo: 0–1 vulnerabilidades  
  - Medio: 2 vulnerabilidades  
  - Alto: ≥3 vulnerabilidades  

### Menú Usuario
- Ver lista de PCs.  
- Escanear un PC y guardar resultado en `reportes.txt`.  
- Mostrar todos los puertos abiertos con su vulnerabilidad asociada.  
- Ordenar PCs según clase de IP:
  - Clase A → 0.0.0.0 – 127.255.255.255  
  - Clase B → 128.0.0.0 – 191.255.255.255  
  - Clase C → 192.0.0.0 – 223.255.255.255  

---

## Herramientas permitidas
- `ArrayList`, `Scanner`, `File`, `FileWriter`, `MessageDigest`, `Base64`.
- **No** se permite ninguna otra librería externa.

---

## Modelo de Clases (por implementar)
Clases principales:
- **PC**
  - id, ip, sistemaOperativo, listaPuertos
- **Puerto**
  - numero, estado, vulnerabilidad
- **Vulnerabilidad**
  - puerto, nombre, descripcion
- **Usuario**
  - username, passwordHash, rol
- **Reporte**
  - pc, usuario, fecha, riesgo, listaPuertos
- **Sistema**
  - métodos de carga, login, menús y generación de reportes

---

## Fechas del Taller
- **Inicio:** Lunes 22 de septiembre  
- **Entrega:** Sábado 11 de octubre  

---


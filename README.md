# MotorSport App

## 1. Nombre del proyecto
**MotorSport App** – Aplicación Android para la gestión y visualización de vehículos, garajes, órdenes y favoritos, con integración a APIs externas y microservicios propios.

## 2. Integrantes
- Benjamin Zepeda
- Cristobal Collao


## 3. Funcionalidades
La aplicación permite a los usuarios:

- **Autenticación:** Registro e inicio de sesión.
- **Explorar vehículos:** Visualización de vehículos con detalles como fabricante, modelo, precio, asientos y velocidad máxima.
- **Gestión de garaje:** Añadir y consultar vehículos en el garaje del usuario.
- **Órdenes:** Consultar órdenes realizadas y su estado.
- **Favoritos:** Marcar y consultar vehículos favoritos.
- **Búsqueda y filtros:** Buscar vehículos por marca o modelo.
- **Interfaz moderna:** Implementada con Jetpack Compose para una experiencia fluida en móviles.


Tecnologías utilizadas

- Kotlin
- Jetpack Compose
- MVVM
- Retrofit
- Coroutines
  
## 4. Endpoints utilizados

### Microservicio propio
- `POST /auth/login` – Inicia sesión del usuario.
- `POST /auth/register` – Registro de nuevo usuario.
- `GET /garage` – Obtiene el garaje del usuario actual.
- `GET /orders/{userId}` – Obtiene órdenes de un usuario.
- `POST /favorites` – Añadir vehículo a favoritos.
- `GET /favorites/{userId}` – Consultar vehículos favoritos de un usuario.
- `GET /vehicles` – Obtiene lista de vehículos disponibles.
- `GET /vehicles/{id}` – Obtiene detalles de un vehículo específico.

> Nota: la mayoria de los endpoints de microservicio requieren autenticación con token.

## 5. Pasos para ejecutar
1. **Clonar el repositorio**
bash
git clone [https://github.com/tu_usuario/MotorSportApp.git](https://github.com/BenjaminAlejandroZepeda/Proyecto-de-m-vil.git)
cd MotorSportApp 
Abrir en Android Studio

Selecciona Open an existing project.
Navega hasta la carpeta clonada.
Configurar dependencias
Asegúrate de tener Kotlin, Jetpack Compose, Retrofit y DataStore correctamente configurados.
Ejecuta Gradle sync para instalar dependencias.

Configurar API
Verifica los endpoints en RetrofitInstance.kt.
Si se requiere, añade tu token o URL base de la API.

Ejecutar la aplicación
Conecta un emulador o dispositivo Android.

Ejecuta el proyecto con Run > Run 'app'.

Ejecutar pruebas unitarias

Captura del APK firmado y .jks

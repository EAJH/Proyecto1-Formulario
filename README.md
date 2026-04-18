# 📱 Proyecto 1 - Formulario de Perfil (Cómputo Móvil)

Alumno: Juárez Herrera Erick Adrián

![Kotlin](https://img.shields.io/badge/Kotlin-100%25-blue?logo=kotlin)
![Jetpack Compose](https://img.shields.io/badge/Jetpack_Compose-Material_3-4CAF50?logo=android)
![Status](https://img.shields.io/badge/Status-Completado-success)

Este repositorio contiene el **Proyecto 1** de la materia de Cómputo Móvil. Se trata de una aplicación Android moderna construida enteramente con **Jetpack Compose** que implementa un formulario de creación de perfil de usuario altamente interactivo, con validaciones robustas, soporte multilingüe y navegación segura por tipos.

## ✨ Características Principales

* **Interfaz Declarativa (Material 3):** Diseño moderno, responsivo y sin hardcoding, utilizando los componentes más recientes de Material Design 3.
* **Navegación Type-Safe:** Implementación de `Compose Navigation 2.8+` con `kotlinx.serialization` para el paso de datos estructurados entre pantallas sin depender de cadenas de texto (URLs).
* **Gestión de Estado Avanzada (State Hoisting):** Arquitectura limpia donde los estados fluyen hacia abajo y los eventos hacia arriba, separando completamente la lógica de negocio de la interfaz gráfica.
* **Soporte Multilingüe (i18n):** Adaptación total del texto, placeholders y formatos de fecha (ej. `dd/MM/yyyy` vs `MM/dd/yyyy`) dependiendo del idioma del dispositivo configurado por el usuario.
* **Validaciones Dinámicas y en Tiempo Real:**
    * **Cálculo de Edad:** Validación matemática con `LocalDate` que exige que el usuario tenga un mínimo de 13 años cumplidos para habilitar el registro.
    * **Teléfonos Internacionales:** Integración de `KomposeCountryCodePicker` para auto-formatear y validar la longitud del número telefónico según la lada del país.
    * **Correo Electrónico:** Validación de estructura con *feedback* visual (colores de error) si el formato es incorrecto.
* **Componentes Nativos Interactivos:** Uso de `DatePickerDialog` para seleccionar la fecha de nacimiento de forma gráfica y un menú tipo *Dropdown* para la selección de género.

## 🛠️ Tecnologías y Librerías

* **Lenguaje:** [Kotlin](https://kotlinlang.org/)
* **UI Toolkit:** [Jetpack Compose](https://developer.android.com/jetpack/compose)
* **Diseño:** Material Design 3
* **Navegación:** `androidx.navigation:navigation-compose`
* **Serialización:** `org.jetbrains.kotlinx:kotlinx-serialization-json`
* **Librerías de Terceros:** [KomposeCountryCodePicker](https://github.com/JoelKanyi/KomposeCountryCodePicker)

## 🚀 Arquitectura del Proyecto

El proyecto sigue el estándar **Single Activity Architecture**. La lógica está dividida de la siguiente manera:

1.  **MainActivity:** Punto de entrada minimalista que inicializa el tema global y delega el control al sistema de navegación.
2.  **AppNavigation (NavHost):** Componente central que define las rutas (`MainScreenDestination`, `DataScreenDestination`) y orquesta el paso seguro de datos.
3.  **MainScreen:** Pantalla que aloja el formulario. Se encarga de recolectar los estados, disparar las validaciones y bloquear o habilitar la acción principal.
4.  **DataScreen:** Pantalla de resumen de sólo lectura que recupera y formatea visualmente la información procesada desde el NavHost.

## ⚙️ Instrucciones de Instalación

1. Clona este repositorio:
   ```bash
   git clone [https://github.com/EAJH/Proyecto1-Formulario.git](https://github.com/EAJH/Proyecto1-Formulario.git)

# VORA Coach

App Android (Kotlin + Jetpack Compose) de coaching de entrenamiento y nutrición, construida a partir del diseño de Figma para el parcial domiciliario de **Taller de Programación 3 / Desarrollo de Aplicaciones Móviles** (ORT Argentina, Prof. Martín Rivas, curso BE-TP3A y B).

## Integrantes

| Integrante | Área principal |
|---|---|
| María Fernanda Caballero | Navegación, inventario, README e integración |
| Alén Carbajal | Tema y componentes reutilizables |
| Joaquín Liendro | Entrenamiento (Retrofit + Room) |
| Enrique Wainstein | Nutrición (Room) |
| Lisandro Dinamarca | Asistente VORA, Progreso, Perfil y verificación visual |

## Enlaces

- **Figma:** https://www.figma.com/design/1nvbbfYNYmKAevuaG28J85/Parcial-Domiciliario-7-Oct-26
- **Servidor de la API:** https://tp3.martinrivas.ar/ (emulador con servidor local: `http://10.0.2.2:8000/`)
- **Video de demostración:** [PENDIENTE]
- **Tag de entrega:** `entrega-tp3` [PENDIENTE]

## Estado del proyecto

Última actualización: 18/10/2026. Estados: ✅ hecho · 🔶 en curso · ⬜ pendiente.

| Requisito                                                                                                              | Estado | Detalle                                                                       |
| ---------------------------------------------------------------------------------------------------------------------- | ------ | ----------------------------------------------------------------------------- |
| Proyecto compila, Hilt configurado                                                                                     | ✅      | `@HiltAndroidApp`, `@AndroidEntryPoint`                                       |
| Navegación entre áreas principales                                                                                     | 🔶     | NavHost base con pantallas temporales                                         |
| Tema y componentes (fidelidad Figma)                                                                                   | ⬜      | Tokens documentados en `specs/06-design-tokens.md`                            |
| Retrofit: `GET /v1/workouts` y `/v1/workouts/{id}` (RF01)                                                              | ⬜      |                                                                               |
| Room: caché de entrenamientos (RF02)                                                                                   | ⬜      |                                                                               |
| Room: registro manual de comidas con validación, listado por fecha, borrado con confirmación, total diario (RF03/RF04) | ✅      | Formulario, lista, detalle y dashboard implementados; pendiente prueba manual |
| Estados de carga / vacío / error con reintento (RF05)                                                                  | ⬜      |                                                                               |
| Separación DTO / Model / Entity + Repository (RF06)                                                                    | ⬜      |                                                                               |
| ViewModel con StateFlow y corrutinas (RF07)                                                                            | ⬜      |                                                                               |
| Hilt para repositorios y ViewModels (RF08)                                                                             | 🔶     | Aplicación y actividad listas; módulos pendientes                             |

## Inventario de pantallas

37 pantallas en 7 flujos, más una pantalla adicional de registro manual de comidas que no está en Figma pero es necesaria para RF03. El detalle completo (nombre en Figma, ruta, destinos y estado funcional o demo) está en [`specs/02-screens.md`](specs/02-screens.md).

| Flujo | Pantallas |
|---|---|
| Acceso | 2 |
| Inicio | 2 |
| Asistente VORA | 6 |
| Entrenamiento | 7 |
| Nutrición | 8 (+1 adicional) |
| Progreso | 6 |
| Perfil | 6 |

Pantallas **funcionales** (datos reales): lista y detalle de entrenamientos (Retrofit + Room) y comidas de Nutrición (Room). El resto son pantallas de **demostración** con datos de ejemplo, como indica la consigna.

## Arquitectura

```
UI (Compose) → ViewModel (StateFlow + corrutinas) → Repository → Retrofit (DTO)
                                                              → Room (Entity)
DTO → Model ← Entity   (mappers; la UI solo usa Model)
```

- La UI no accede a Retrofit ni a los DAO: solo a través del ViewModel y el Repository.
- Hilt provee Retrofit, la base de datos Room, los DAO, los repositorios y los ViewModels.
- Estados de pantalla: `Loading`, `Success`, `Empty`, `Error` (con reintento). Un error de red no borra la caché.

Versiones: Kotlin 2.2.10, Compose BOM 2026.02.01, AGP 9.3.3, Hilt 2.60.1, Room 2.8.5, Retrofit 3.0.0, Navigation Compose 2.9.0, minSdk 24.

## Cómo ejecutar

1. Clonar el repositorio y abrirlo en Android Studio.
2. Esperar la sincronización de Gradle.
3. Ejecutar la configuración `app` en un emulador.
4. La app usa por defecto `https://tp3.martinrivas.ar/`. Para usar un servidor local, cambiar la URL base a `http://10.0.2.2:8000/` (solo en desarrollo).

## Metodología y organización

- **Spec-Driven Development:** la carpeta [`specs/`](specs/) contiene requisitos, inventario de pantallas, contrato de API, arquitectura, tokens de diseño y la lista de tareas (T01…T62).
- **Flujo Git:** `main` (estable, entregas) ← `develop` (integración) ← ramas `feature/<área>-<tarea>`. Todo cambio entra por Pull Request a `develop`. Los mensajes de commit incluyen el identificador de tarea.

## Comparaciones visuales con Figma

[PENDIENTE: 5 capturas lado a lado, Figma vs app]

## Uso de tokens (IA)

Registro del consumo de tokens del asistente de IA, actualizado por el grupo. Las cifras aproximadas provienen del contador de cada sesión; el dato exacto se verifica en el panel de uso de la cuenta (Settings → Usage).

| Fecha | Tarea | Tokens aprox. | Fuente |
|---|---|---|---|
| 17/10/2026 | Lectura de la consigna y armado de `specs/` | ~143.000 | Contador de la sesión |
| 18/10/2026 | Configuración (Hilt, Room, Retrofit), NavHost, inventario de pantallas, README | [completar con Settings → Usage] | Panel de uso de la cuenta |

Estrategia para ahorrar tokens: un chat por tarea, contexto breve a partir de `specs/`, pegar solo el error o el archivo puntual, y pedir cambios acotados en lugar de reescribir archivos.

## Declaración de uso de IA

[PENDIENTE: completar con el grupo antes de la entrega]

Borrador: el grupo utilizó Claude (Anthropic) como asistente docente para planificar la arquitectura, generar las especificaciones de la carpeta `specs/`, explicar conceptos y revisar errores de configuración. El código fue escrito, probado y comprendido por los integrantes, y cada uno puede explicar sus commits.
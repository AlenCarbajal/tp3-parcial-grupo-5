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
- **Servidor de la API:** https://tp3.martinrivas.ar/ (también se puede usar el servidor local del profe, ver [Probar con el servidor local](#probar-con-el-servidor-local))
- **Video de demostración:** [PENDIENTE]
- **Tag de entrega:** `entrega-tp3` [PENDIENTE]

## Estado del proyecto

Última actualización: 01/11/2026. Estados: ✅ hecho y probado · 🔶 en curso · ⬜ pendiente.

| Requisito | Estado | Detalle |
| --- | --- | --- |
| Proyecto compila, Hilt configurado | ✅ | `@HiltAndroidApp`, `@AndroidEntryPoint` |
| Navegación entre áreas (NAV02–NAV04) | ✅ | Todas las pantallas del inventario están conectadas; ya no quedan pantallas temporales |
| Tema y componentes (UI01/UI02) | 🔶 | Tema en `ui/theme` y componentes en `ui/components`; faltan las 5 comparaciones con Figma |
| Retrofit: `GET /v1/workouts` y `/v1/workouts/{id}` (RF01) | ✅ | `ApiService` + `WorkoutRepository`; cada tarjeta abre su propio detalle |
| Room: caché de entrenamientos (RF02) | ✅ | Tablas `workouts` y `exercises`. Un detalle ya visto se ve con el servidor apagado, y actualizar la lista no borra los ejercicios cacheados. Detalle nunca descargado: "No disponible sin conexión." |
| Room: comidas con validación, listado por fecha, borrado con confirmación, total diario (RF03/RF04) | ✅ | Registro manual validado, lista por día, detalle, borrado con confirmación y total diario. En el flujo demo, "Add to Lunch" también guarda en Room |
| Estados de carga / vacío / error con reintento (RF05) | ✅ | Probados con los escenarios del mock (ver más abajo). Un fallo de red no borra la caché |
| Separación DTO / Model / Entity + Repository (RF06) | ✅ | `data/remote/dto`, `domain/model`, `data/local/entity` con mappers; la UI solo ve `Model` |
| ViewModel con StateFlow y corrutinas (RF07) | ✅ | `WorkoutViewModel` y `NutritionViewModel`; la rotación conserva el estado y lo escrito en el formulario |
| Hilt para repositorios y ViewModels (RF08) | ✅ | `@HiltViewModel`, repositorios por constructor, `NetworkModule` y `DatabaseModule` |
| Tests unitarios | ✅ | 24 tests en `app/src/test` (validación de comidas, total diario, fechas, tipos) |
| Video, tag de entrega, comparaciones con Figma, declaración de IA | ⬜ | Ver los apartados correspondientes más abajo |

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

Del flujo de Nutrición, Add Food, Voice Logging, Analyzing, Analyse Meal y Review Meal son demo (sin micrófono, cámara ni IA reales). "Search Database" muestra el aviso de modo demostración porque no tiene pantalla en Figma.

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
4. La app usa por defecto `https://tp3.martinrivas.ar/`. Para usar el servidor local, ver la sección siguiente.
5. Si el emulador muestra la pantalla negra al abrir el teclado o un diálogo, cambiar el gráfico del dispositivo a **Software** (Device Manager → Edit → Additional settings → Emulated Performance → Graphics).

## Probar con el servidor local

El servidor del profe (`mock/server.py`, solo biblioteca estándar de Python) permite probar la app sin depender del servidor publicado.

1. Levantarlo: `python server.py --host 0.0.0.0` (desde la carpeta `mock`).
2. Con el emulador abierto: `adb reverse tcp:8000 tcp:8000`.
3. En `ApiConfig.kt` poner `USE_LOCAL_SERVER = true` (no commitear; para entregar tiene que quedar en `false`). El HTTP sin cifrar solo está permitido en el build de debug (`app/src/debug/AndroidManifest.xml`).

### Escenarios del mock (T60)

`MockConfig` (en `MockHeadersInterceptor.kt`) envía los headers `X-Mock-Scenario` y `X-Mock-Delay-Ms`. Con los valores por defecto (`"normal"` y `0`) no agrega ningún header.

| Escenario | Resultado esperado en la app |
| --- | --- |
| `empty` | Pantalla de lista vacía |
| `error` (503) | Mensaje de error del servidor con botón **Reintentar**; la lista cacheada sigue visible |
| `malformed` | "El servidor devolvió datos que la app no pudo leer", sin cerrarse |
| `delayMs = 3000` | Indicador de carga mientras espera |
| Servidor apagado | Detalle ya visto: se ve desde Room. Detalle nunca descargado: "No disponible sin conexión." |

El servidor publicado devolvió error 522 (Cloudflare) el 01/11/2026; la caché de Room permite seguir viendo los entrenamientos ya abiertos.

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
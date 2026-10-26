# Tareas (orden sugerido). Cada una: rama feature/..., PR revisado, ID de spec en el commit.

## Base (hacer primero, una sola persona) -> Hito 1
- T01 Proyecto base + dependencias (Hilt, Retrofit, Room, Navigation Compose, kapt/ksp) compila.  [constitution]
- T02 @HiltAndroidApp + @AndroidEntryPoint en MainActivity.  [RF08]
- T03 Estructura de paquetes de 04-architecture.  [RF06]
- T04 NavHost con las 7 áreas como rutas vacías.  [NAV02]
- T05 Completar 02-screens.md con nombres reales de Figma.  [NAV01]

## Tema, componentes, Acceso y Home (Alén)  [UI01, UI02, UI03, NAV02, NAV03]
- T10 Colores, tipografías, formas desde Figma.
- T11 Componentes: botón, card, campo de texto, bottom bar, top bar.
- T12 Acceso (2, demo): Sign In y Sign Up maquetados según Figma; acceso de demostración sin Firebase que entra a home con cualquier dato; enlaces cruzados login ↔ signup; los botones de Apple y Google muestran aviso "modo demostración".
- T13 Home (2, demo): dashboard y notificaciones con datos de ejemplo; accesos principales según 02-screens (campana, coach, entrenamiento, comida, resumen del día) y bottom bar; desde home el atrás del sistema no vuelve al login.

## Workout (Joaquín)  [RF01, RF02, RF05, RF06, RF07]
- T20 ApiService + DTOs + NetworkModule (primera llamada a /v1/workouts).
- T21 Entities + DAO + DatabaseModule; mappers DTO->Model->Entity.
- T22 WorkoutRepository con caché; WorkoutViewModel con UiState.
- T23 Pantallas catálogo y detalle (ID correcto en la ruta).  [NAV04]
- T24 Pantallas demo del flujo (plan, preparación, activo, guía, completado).

## Nutrition (Enrique)  [RF03, RF04, RF06, RF07]
- T30 MealEntity + DAO (primer insert y lectura).
- T31 MealRepository + MealViewModel.
- T32 Formulario con validación; listado por fecha; borrar con confirmación; total diario.
- T33 Pantallas demo (voz, análisis, foto, revisión).

## Coach / Progress / Profile (Lisandro)  [NAV05, UI01]
- T40 Coach con estados simulados. T41 Progress (6). T42 Profile (6).

## Navegación e integración (María Fernanda)  [NAV01-NAV03]
- T50 Conectar todas las rutas y destinos; atrás coherente. T51 README completo. T52 Demo ensayada.

## Cierre
- T60 Probar escenarios: empty, error, malformed, delay. T61 Comparaciones visuales (5). T62 Tag entrega-tp3.

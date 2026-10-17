# Arquitectura

UI (Compose) -> ViewModel (StateFlow + coroutines) -> Repository -> Retrofit (DTO) / Room (Entity)
DTO --mapper--> Model <--mapper--> Entity

## Estructura de paquetes (ar.edu.ort.vora)
- ui/theme          Color, Type, Shape, Theme
- ui/components     botones, cards, campos, bottom bar
- ui/navigation     rutas (sealed class) + NavHost
- ui/<area>         screens + ViewModel (auth, home, coach, workout, nutrition, progress, profile)
- domain/model      Workout, Exercise, MealLog, MealType...
- data/remote       ApiService, DTOs
- data/local        AppDatabase, DAOs, Entities
- data/repository   WorkoutRepository, MealRepository (+ mappers)
- di                NetworkModule, DatabaseModule, RepositoryModule(si hace falta)

## Estado de pantalla (patrón común)
sealed interface UiState { Loading, Success(data), Empty, Error(message) } + acción retry().

## Estrategia de caché (Workout)
1. Pedir a la red. 2. Si OK: mapear DTO->Entity, guardar en Room, emitir desde Room. 3. Si falla: emitir lo que haya en Room; si no hay => Error "No disponible sin conexión". Nunca borrar la caché por un error.

## Comidas (Nutrition)
MealEntity(id, name, date, type, kcal, protein, carbs, fat). DAO: insert, delete, getByDate(date): Flow. Total diario = suma sobre el Flow (cambia solo al insertar/borrar).

## Hilt – qué decir en la demo
Hilt genera (vía Dagger, en compilación) el grafo de dependencias. @HiltAndroidApp crea el contenedor; @AndroidEntryPoint habilita inyección en Activity; @HiltViewModel crea ViewModels con sus repos; @Module/@Provides entregan Retrofit, Room y DAOs.

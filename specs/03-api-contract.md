# Contrato de API (solo lo obligatorio)
Base URL publicada: https://tp3.martinrivas.ar/   (sin autenticación)
Emulador con server local: http://10.0.2.2:8000/  (HTTP solo en desarrollo; requiere usesCleartextTraffic)
Éxito: { "data": ... }    Error: { "error": { "code": string, "message": string } }

## GET /v1/workouts?level=beginner|intermediate|advanced   (level opcional)
200 -> { "data": [ WorkoutSummary ] }   (3 entrenamientos: w01, w02, w03)
WorkoutSummary: { id: string, name: string, level: string, durationMinutes: int, exerciseCount: int }

## GET /v1/workouts/{workoutId}
200 -> { "data": Workout }   404 -> error WORKOUT_NOT_FOUND
Workout = WorkoutSummary + { description: string, exercises: [Exercise] }
Exercise: { id, name, muscleGroup, sets:int, reps:int, restSeconds:int, instructions, tip }

## Códigos de error posibles
400 INVALID_REQUEST · 404 · 405 · 503 SERVICE_UNAVAILABLE ("Servicio simulado no disponible.")

## Headers de prueba (para RF05)
X-Mock-Scenario: normal | empty | error (503) | malformed (JSON inválido)
X-Mock-Delay-Ms: 0..10000   (3000 = 3 s de demora)

## Notas para el cliente
- Parsear tolerando campos extra. `malformed` debe terminar en estado Error, no en crash.
- Ningún POST guarda datos. Opcionales (no obligatorios): /v1/foods, /v1/coach/*, /v1/nutrition/analyses, /v1/plans/weekly, /v1/progress/weekly-review, /v1/activity/daily, /health.

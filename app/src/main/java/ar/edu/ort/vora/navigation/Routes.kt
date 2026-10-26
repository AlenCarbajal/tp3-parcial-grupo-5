
package ar.edu.ort.vora.navigation

object Routes {
    const val LOGIN = "login"
    const val SIGNUP = "signup"
    const val HOME = "home"
    const val NOTIFICATIONS = "notifications"

    // Asistente VORA
    const val COACH = "coach"
    const val COACH_LISTENING = "coach/listening"
    const val COACH_PROCESSING = "coach/processing"
    const val COACH_SPEAKING = "coach/speaking"
    const val COACH_CHAT = "coach/chat"
    const val COACH_ERROR = "coach/error"

    // Entrenamiento
    const val WORKOUT = "workout"
    const val WORKOUT_PLAN = "workout/plan"
    const val WORKOUT_DETAIL = "workout/{workoutId}"
    const val WORKOUT_PREP = "workout/{workoutId}/prep"
    const val WORKOUT_ACTIVE = "workout/{workoutId}/active"
    const val WORKOUT_GUIDE = "workout/{workoutId}/guide/{exerciseId}"
    const val WORKOUT_DONE = "workout/{workoutId}/done"

    fun workoutDetail(workoutId: String) = "workout/$workoutId"
    fun workoutPrep(workoutId: String) = "workout/$workoutId/prep"
    fun workoutActive(workoutId: String) = "workout/$workoutId/active"
    fun workoutGuide(workoutId: String, exerciseId: String) =
        "workout/$workoutId/guide/$exerciseId"
    fun workoutDone(workoutId: String) = "workout/$workoutId/done"

    // Nutrición
    const val NUTRITION = "nutrition"
    const val NUTRITION_MEALS = "nutrition/meals"
    const val NUTRITION_MEAL = "nutrition/meal/{id}"
    const val NUTRITION_ADD = "nutrition/add"
    const val NUTRITION_VOICE = "nutrition/voice"
    const val NUTRITION_ANALYZING = "nutrition/analyzing"
    const val NUTRITION_PHOTO = "nutrition/photo"
    const val NUTRITION_REVIEW = "nutrition/review"
    const val NUTRITION_MANUAL = "nutrition/manual"

    fun nutritionMeal(id: String) = "nutrition/meal/$id"

    // Progreso
    const val PROGRESS = "progress"
    const val PROGRESS_TODAY = "progress/today"
    const val PROGRESS_WORKOUT = "progress/workout"
    const val PROGRESS_NUTRITION = "progress/nutrition"
    const val PROGRESS_HEALTH = "progress/health"
    const val PROGRESS_WEEKLY = "progress/weekly"

    // Perfil
    const val PROFILE = "profile"
    const val PROFILE_EDIT = "profile/edit"
    const val PROFILE_GOALS = "profile/goals"
    const val PROFILE_NUTRITION = "profile/nutrition"
    const val PROFILE_VOICE = "profile/voice"
    const val PROFILE_SETTINGS = "profile/settings"
}


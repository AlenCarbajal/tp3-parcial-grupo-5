package ar.edu.ort.vora.navigation

object Routes {
    // Login
    const val LOGIN = "login"
    const val SIGNUP = "signup"
    // Home
    const val HOME = "home"
    const val NOTIFICATIONS = "notifications"
    // Coach (asistente VORA)
    const val COACH = "coach"
    const val COACH_LISTENING = "coach/listening"
    const val COACH_PROCESSING = "coach/processing"
    const val COACH_SPEAKING = "coach/speaking"
    const val COACH_CHAT = "coach/chat"
    const val COACH_ERROR = "coach/error"
    // Workout
    const val WORKOUT = "workout"
    const val WORKOUT_PLAN = "workout/plan"
    const val WORKOUT_DETAIL = "workout/{id}"
    const val WORKOUT_PREP = "workout/{id}/prep"
    const val WORKOUT_ACTIVE = "workout/{id}/active"
    const val WORKOUT_GUIDE = "workout/{id}/guide/{exerciseId}"
    const val WORKOUT_DONE = "workout/{id}/done"
    // Nutrition
    const val NUTRITION = "nutrition"
    const val NUTRITION_MEALS = "nutrition/meals"
    const val NUTRITION_ADD = "nutrition/add"
    const val NUTRITION_VOICE = "nutrition/voice"
    const val NUTRITION_ANALYZING = "nutrition/analyzing"
    const val NUTRITION_PHOTO = "nutrition/photo"
    const val NUTRITION_SEARCH = "nutrition/search"
    const val NUTRITION_REVIEW = "nutrition/review"
    const val NUTRITION_MEAL = "nutrition/meal/{id}"
    const val NUTRITION_MANUAL = "nutrition/manual"
    // Progress
    const val PROGRESS = "progress"
    const val PROGRESS_TODAY = "progress/today"
    const val PROGRESS_WORKOUT = "progress/workout"
    const val PROGRESS_NUTRITION = "progress/nutrition"
    const val PROGRESS_HEALTH = "progress/health"
    const val PROGRESS_WEEKLY = "progress/weekly"
    // Profile
    const val PROFILE = "profile"
    const val PROFILE_EDIT = "profile/edit"
    const val PROFILE_GOALS = "profile/goals"
    const val PROFILE_NUTRITION = "profile/nutrition"
    const val PROFILE_VOICE = "profile/voice"
    const val PROFILE_SETTINGS = "profile/settings"

    // Helpers para rutas con argumentos
    fun workoutDetail(id: String) = "workout/$id"
    fun workoutPrep(id: String) = "workout/$id/prep"
    fun workoutActive(id: String) = "workout/$id/active"
    fun workoutGuide(id: String, exerciseId: String) = "workout/$id/guide/$exerciseId"
    fun workoutDone(id: String) = "workout/$id/done"
    fun nutritionMeal(id: String) = "nutrition/meal/$id"
}
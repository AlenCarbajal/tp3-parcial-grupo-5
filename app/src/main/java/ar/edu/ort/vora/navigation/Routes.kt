package ar.edu.ort.vora.navigation

object Routes {
    const val LOGIN = "login"
    const val SIGNUP = "signup"
    const val HOME = "home"
    const val NOTIFICATIONS = "notifications"
    const val COACH = "coach"
    const val WORKOUT = "workout"
    const val WORKOUT_DETAIL = "workout/{workoutId}"
    const val NUTRITION = "nutrition"
    const val PROGRESS = "progress"
    const val PROFILE = "profile"

    fun workoutDetail(workoutId: String): String = "workout/$workoutId"
}


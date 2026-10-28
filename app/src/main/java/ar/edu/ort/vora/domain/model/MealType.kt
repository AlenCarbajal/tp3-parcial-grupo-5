package ar.edu.ort.vora.domain.model

enum class MealType {
    BREAKFAST,
    LUNCH,
    DINNER,
    SNACK;

// recomendacion de ia
    companion object {
        fun fromName(name: String): MealType {
            return when (name.uppercase()) {
                "BREAKFAST", "DESAYUNO" -> BREAKFAST
                "LUNCH", "ALMUERZO" -> LUNCH
                "DINNER", "CENA" -> DINNER
                "SNACK", "MERIENDA" -> SNACK
                else -> SNACK // Valor por defecto si llega algo raro
            }
        }
    }

}
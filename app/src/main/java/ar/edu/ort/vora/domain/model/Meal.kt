package ar.edu.ort.vora.domain.model

data class Meal(

    val id: Int = 0,
    val name: String,
    val date: String,
    val type: MealType,
    val kcal: Int,
    val carbs: Double,
    val fat: Double,
    val protein: Double

)

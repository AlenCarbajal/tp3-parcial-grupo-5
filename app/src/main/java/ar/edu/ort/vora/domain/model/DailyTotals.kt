package ar.edu.ort.vora.domain.model

data class DailyTotals(
    val kcal: Int = 0,
    val carbs: Double = 0.0,
    val fat: Double = 0.0,
    val protein: Double = 0.0
)
fun List<Meal>.toDailyTotals(): DailyTotals =
    DailyTotals(
        kcal = this.sumOf { it.kcal },
        carbs = this.sumOf { it.carbs },
        fat = this.sumOf { it.fat },
        protein = this.sumOf { it.protein }
    )

package ar.edu.ort.vora.ui.nutrition

import ar.edu.ort.vora.domain.model.Meal

import ar.edu.ort.vora.domain.model.DailyTotals
sealed interface NutritionUIState  {
    data object Loading : NutritionUIState
    data object Empty : NutritionUIState
    data class Success(
        val meals: List<Meal>,
        val totals: DailyTotals
    ) : NutritionUIState

    data class Error(
        val message: String
    ) : NutritionUIState
}

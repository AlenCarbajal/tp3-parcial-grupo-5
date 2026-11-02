package ar.edu.ort.vora.ui.nutrition

import androidx.annotation.StringRes
import ar.edu.ort.vora.R
import ar.edu.ort.vora.domain.model.Meal
import ar.edu.ort.vora.domain.model.MealType

data class MealFormState(
    val name: String = "",
    val type: MealType = MealType.BREAKFAST,
    val kcal: String= "",
    val carbs: String = "",
    val fat: String = "",
    val protein: String = "",
    val date: String = ""
)

// Cada error es un @StringRes: la pantalla lo resuelve con stringResource.
data class MealFormErrors(
    @StringRes val name: Int? = null,
    @StringRes val kcal: Int? = null,
    @StringRes val carbs: Int? = null,
    @StringRes val fat: Int? = null,
    @StringRes val protein: Int? = null,
    @StringRes val date: Int? = null
){
    val hasErrors: Boolean get() = listOf(name, kcal, carbs, fat, protein, date).any { it != null}
}

private fun parseDecimal(text: String): Double? =
    text.trim().replace(',', '.').toDoubleOrNull()?.takeIf { it.isFinite() }

private fun numberError(text: String, integer: Boolean = false): Int? {
    if (text.isBlank()) return R.string.nutrition_form_error_required
    val value = if (integer) text.trim().toIntOrNull()?.toDouble() else parseDecimal(text)
    return when {
        value == null -> if (integer) R.string.nutrition_form_error_integer else R.string.nutrition_form_error_number
        value < 0 -> R.string.nutrition_form_error_negative
        else -> null
    }
}

fun MealFormState.errors(): MealFormErrors = MealFormErrors(
    name = if (name.isBlank()) R.string.nutrition_form_error_name else null,
    date = if (!isValidIsoDate(date.trim())) R.string.nutrition_form_error_date else null,
    kcal = numberError(kcal, integer = true),
    protein = numberError(protein),
    carbs = numberError(carbs),
    fat = numberError(fat),
)

// Devuelve null si algo es inválido; el mismo criterio que errors()
fun MealFormState.toMealOrNull(): Meal? {
    if (errors().hasErrors) return null
    return Meal(
        name = name.trim(),
        date = date.trim(),
        type = type,
        kcal = kcal.trim().toInt(),
        protein = parseDecimal(protein)!!,
        carbs = parseDecimal(carbs)!!,
        fat = parseDecimal(fat)!!,
    )
}
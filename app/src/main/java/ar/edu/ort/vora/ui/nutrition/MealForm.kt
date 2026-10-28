package ar.edu.ort.vora.ui.nutrition

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

data class MealFormErrors(
    val name: String? = null,
    val kcal: String? = null,
    val carbs: String? = null,
    val fat: String? = null,
    val protein: String? = null,
    val date: String? = null
){
    val hasErrors: Boolean get() = listOf(name, kcal, carbs, fat, protein, date).any { it != null}
}

private fun parseDecimal(text: String): Double? =
    text.trim().replace(',', '.').toDoubleOrNull()?.takeIf { it.isFinite() }

private fun numberError(text: String, integer: Boolean = false): String? {
    if (text.isBlank()) return "Obligatorio."
    val value = if (integer) text.trim().toIntOrNull()?.toDouble() else parseDecimal(text)
    return when {
        value == null -> if (integer) "Ingresá un entero." else "Número inválido."
        value < 0 -> "No puede ser negativo."
        else -> null
    }
}

fun MealFormState.errors(): MealFormErrors = MealFormErrors(
    name = if (name.isBlank()) "Ingresá un nombre." else null,
    date = if (!isValid(date.trim())) "Usá el formato AAAA-MM-DD." else null,
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
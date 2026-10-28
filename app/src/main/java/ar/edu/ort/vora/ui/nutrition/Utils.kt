package ar.edu.ort.vora.ui.nutrition

import ar.edu.ort.vora.domain.model.MealType
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


private fun isoFmt() =
    SimpleDateFormat("yyyy-MM-dd", Locale.US)
    .apply { isLenient = false}

fun todayIso(): String = isoFmt().format(Date())

fun shiftIso(date: String, dys: Int): String {
    val cal = Calendar.getInstance()
    runCatching { isoFmt().parse(date)}.getOrNull()?.let { cal.time = it }
    cal.add(Calendar.DAY_OF_YEAR, dys)
    return isoFmt().format(cal.time)
}

fun isValid(text: String): Boolean =
    Regex("""\d{4}-\d{2}-\d{2}""").matches(text) &&
            runCatching { isoFmt().parse(text) != null }.getOrDefault(false)

fun dayLabel(date: String): String = when (date) {
    todayIso() -> "Hoy"
    shiftIso(todayIso(), -1) -> "Ayer"
    shiftIso(todayIso(), 1) -> "Mañana"
    else -> date
}

fun MealType.toLabel(): String = when (this) {
    MealType.BREAKFAST -> "Desayuno"
    MealType.LUNCH -> "Almuerzo"
    MealType.DINNER -> "Cena"
    MealType.SNACK -> "Merienda"
}



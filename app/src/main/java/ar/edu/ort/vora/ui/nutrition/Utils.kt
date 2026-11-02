package ar.edu.ort.vora.ui.nutrition

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import ar.edu.ort.vora.R
import ar.edu.ort.vora.domain.model.MealType
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

private fun isoFmt() =
    SimpleDateFormat("yyyy-MM-dd", Locale.US)
        .apply { isLenient = false }

fun todayIso(): String = isoFmt().format(Date())

fun shiftIso(date: String, days: Int): String {
    val cal = Calendar.getInstance()
    runCatching { isoFmt().parse(date) }.getOrNull()?.let { cal.time = it }
    cal.add(Calendar.DAY_OF_YEAR, days)
    return isoFmt().format(cal.time)
}

fun isValidIsoDate(text: String): Boolean =
    Regex("""\d{4}-\d{2}-\d{2}""").matches(text) &&
            runCatching { isoFmt().parse(text) != null }.getOrDefault(false)

@Composable
fun dayLabel(date: String): String = when (date) {
    todayIso() -> stringResource(R.string.common_today)
    shiftIso(todayIso(), -1) -> stringResource(R.string.common_yesterday)
    shiftIso(todayIso(), 1) -> stringResource(R.string.nutrition_day_tomorrow)
    else -> date
}

val mealTypesInOrder = listOf(MealType.BREAKFAST, MealType.LUNCH, MealType.SNACK, MealType.DINNER)

@get:StringRes
val MealType.labelRes: Int
    get() = when (this) {
        MealType.BREAKFAST -> R.string.nutrition_meal_breakfast
        MealType.LUNCH -> R.string.nutrition_meal_lunch
        MealType.DINNER -> R.string.nutrition_meal_dinner
        MealType.SNACK -> R.string.nutrition_meal_snack
    }

@Composable
fun MealType.label(): String = stringResource(labelRes)
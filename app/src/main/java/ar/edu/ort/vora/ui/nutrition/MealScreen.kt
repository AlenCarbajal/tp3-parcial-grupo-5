package ar.edu.ort.vora.ui.nutrition

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ar.edu.ort.vora.R
import ar.edu.ort.vora.domain.model.Meal
import ar.edu.ort.vora.ui.components.ButtonSize
import ar.edu.ort.vora.ui.components.IconCircleButton
import ar.edu.ort.vora.ui.components.InfoCard
import ar.edu.ort.vora.ui.components.PrimaryButton
import ar.edu.ort.vora.ui.components.SecondaryButton
import ar.edu.ort.vora.ui.components.asString
import ar.edu.ort.vora.ui.home.TabScreen
import ar.edu.ort.vora.ui.theme.PillShape
import ar.edu.ort.vora.ui.theme.Spacing
import ar.edu.ort.vora.ui.theme.VoraBorder
import ar.edu.ort.vora.ui.theme.VoraError
import ar.edu.ort.vora.ui.theme.VoraGreen
import ar.edu.ort.vora.ui.theme.VoraTextMuted
import ar.edu.ort.vora.ui.theme.VoraTextPrimary
import ar.edu.ort.vora.ui.theme.VoraTextSecondary

// 19 · Today's Meals: comidas del día elegido agrupadas por tipo. Se borran desde el detalle (con confirmación).
@Composable
fun MealsScreen(
    onAdd: () -> Unit,
    onMeal: (Int) -> Unit,
    viewModel: NutritionViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val date by viewModel.selectedDate.collectAsStateWithLifecycle()
    val success = state as? NutritionUIState.Success

    TabScreen {
        Header(success?.totals?.kcal ?: 0)
        DaySelector(dayLabel(date), viewModel::previousDay, viewModel::nextDay)

        when (val s = state) {
            NutritionUIState.Loading -> Box(
                Modifier.fillMaxWidth().padding(Spacing.s24),
                contentAlignment = Alignment.Center,
            ) { CircularProgressIndicator(color = VoraGreen) }

            is NutritionUIState.Error -> InfoCard {
                Text(s.message.asString(), style = MaterialTheme.typography.bodyMedium, color = VoraError)
                SecondaryButton(stringResource(R.string.common_retry), viewModel::retry, Modifier.fillMaxWidth())
            }

            // Vacío y con datos se ven igual: cada tipo sin registrar muestra su botón para agregarlo.
            else -> {
                val meals = success?.meals.orEmpty()
                mealTypesInOrder.forEach { type ->
                    val ofType = meals.filter { it.type == type }.sortedBy { it.id }
                    if (ofType.isEmpty()) NotLoggedCard(type.label(), onAdd)
                    else MealGroup(type.label(), ofType, onMeal)
                }
            }
        }
    }
}

@Composable
private fun Header(totalKcal: Int) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Column(Modifier.weight(1f)) {
            Text(
                stringResource(R.string.nutrition_timeline),
                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Medium),
                color = VoraTextSecondary,
            )
            Text(stringResource(R.string.nutrition_meals_title), style = MaterialTheme.typography.headlineMedium, color = VoraTextPrimary)
        }
        Text(
            stringResource(R.string.common_value_kcal, totalKcal),
            modifier = Modifier
                .background(VoraBorder, PillShape)
                .padding(horizontal = Spacing.s12, vertical = Spacing.s6),
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold,
            color = VoraTextPrimary,
        )
    }
}

@Composable
private fun DaySelector(label: String, onPrevious: () -> Unit, onNext: () -> Unit) {
    Row(
        Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        IconCircleButton(R.drawable.ic_chevron_left, stringResource(R.string.nutrition_previous_day), onPrevious)
        Text(label, style = MaterialTheme.typography.titleMedium, color = VoraTextPrimary)
        IconCircleButton(
            icon = R.drawable.ic_chevron_left,
            contentDescription = stringResource(R.string.nutrition_next_day),
            onClick = onNext,
            modifier = Modifier.rotate(180f),
        )
    }
}

// Tarjeta de un tipo: título y kcal sumadas, divisor y una fila por comida que abre su detalle.
@Composable
private fun MealGroup(title: String, meals: List<Meal>, onMeal: (Int) -> Unit) {
    InfoCard(padding = Spacing.s12) {
        Row(Modifier.padding(horizontal = Spacing.s4), verticalAlignment = Alignment.CenterVertically) {
            Text(title, Modifier.weight(1f), style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = VoraTextPrimary)
            Text(
                stringResource(R.string.common_value_kcal, meals.sumOf { it.kcal }),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = VoraTextPrimary,
            )
        }
        HorizontalDivider(Modifier.padding(horizontal = Spacing.s4, vertical = Spacing.s8), color = VoraBorder)
        meals.forEach { meal ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .clip(MaterialTheme.shapes.small)
                    .clickable { onMeal(meal.id) }
                    .padding(Spacing.s4),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column(Modifier.weight(1f).padding(end = Spacing.s12)) {
                    Text(meal.name, style = MaterialTheme.typography.bodyLarge, color = VoraTextPrimary, maxLines = 1, overflow = TextOverflow.Ellipsis)
                    Text(
                        stringResource(R.string.nutrition_meal_macros, meal.protein, meal.carbs, meal.fat),
                        style = MaterialTheme.typography.bodySmall,
                        color = VoraTextMuted,
                    )
                }
                Text(stringResource(R.string.common_value_kcal, meal.kcal), style = MaterialTheme.typography.bodyMedium, color = VoraTextSecondary)
            }
        }
    }
}

// Tipo sin registrar: recuadro con borde punteado, como "Dinner is not logged yet" en Figma.
@Composable
private fun NotLoggedCard(title: String, onAdd: () -> Unit) {
    val radius = 16.dp
    Column(
        Modifier
            .fillMaxWidth()
            .drawBehind {
                drawRoundRect(
                    color = VoraBorder,
                    style = Stroke(width = 1.dp.toPx(), pathEffect = PathEffect.dashPathEffect(floatArrayOf(6.dp.toPx(), 4.dp.toPx()))),
                    cornerRadius = CornerRadius(radius.toPx()),
                )
            }
            .padding(Spacing.s16),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(Spacing.s12),
    ) {
        Text(stringResource(R.string.nutrition_type_not_logged, title), style = MaterialTheme.typography.bodyLarge, color = VoraTextSecondary)
        PrimaryButton(stringResource(R.string.nutrition_add_type, title), onAdd, size = ButtonSize.Small)
    }
}

package ar.edu.ort.vora.ui.nutrition

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.style.TextOverflow
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ar.edu.ort.vora.R
import ar.edu.ort.vora.domain.model.DailyTotals
import ar.edu.ort.vora.domain.model.Meal
import ar.edu.ort.vora.ui.components.ButtonSize
import ar.edu.ort.vora.ui.components.ConfirmDialog
import ar.edu.ort.vora.ui.components.DarkPanel
import ar.edu.ort.vora.ui.components.IconCircleButton
import ar.edu.ort.vora.ui.components.InfoCard
import ar.edu.ort.vora.ui.components.PrimaryButton
import ar.edu.ort.vora.ui.components.SecondaryButton
import ar.edu.ort.vora.ui.components.TopBarBack
import ar.edu.ort.vora.ui.components.VoraTopBar
import ar.edu.ort.vora.ui.theme.PanelTitleStyle
import ar.edu.ort.vora.ui.theme.Spacing
import ar.edu.ort.vora.ui.theme.VoraBackground
import ar.edu.ort.vora.ui.theme.VoraError
import ar.edu.ort.vora.ui.theme.VoraGreen
import ar.edu.ort.vora.ui.theme.VoraMint
import ar.edu.ort.vora.ui.theme.VoraTextPrimary
import ar.edu.ort.vora.ui.theme.VoraTextSecondary
import java.util.Locale

@Composable
fun MealsScreen(
    onBack: () -> Unit,
    onAdd: () -> Unit,
    onMeal: (Int) -> Unit,
    viewModel: NutritionViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val date by viewModel.selectedDate.collectAsStateWithLifecycle()
    // Se guarda el id (no el Meal) para que el diálogo sobreviva a la rotación
    var pendingDeleteId by rememberSaveable { mutableStateOf<Int?>(null) }

    val success = state as? NutritionUIState.Success
    success?.meals?.firstOrNull { it.id == pendingDeleteId }?.let { meal ->
        ConfirmDialog(
            title = "Borrar comida",
            message = "¿Borrar “${meal.name}”? No se puede deshacer.",
            confirmText = "Borrar",
            onConfirm = {
                viewModel.deleteMeal(meal)
                pendingDeleteId = null
            },
            onDismiss = { pendingDeleteId = null },
        )
    }

    Column(Modifier.fillMaxSize().background(VoraBackground)) {
        VoraTopBar(title = "Today's Meals", back = TopBarBack.Plain, onBack = onBack)
        Box(
            Modifier.weight(1f).fillMaxWidth().verticalScroll(rememberScrollState()),
            contentAlignment = Alignment.TopCenter,
        ) {
            Column(
                Modifier
                    .widthIn(max = Spacing.maxContentWidth)
                    .fillMaxWidth()
                    .padding(horizontal = Spacing.screen, vertical = Spacing.s16),
                verticalArrangement = Arrangement.spacedBy(Spacing.groupGap),
            ) {
                DaySelector(dayLabel(date), viewModel::previousDay, viewModel::nextDay)
                TotalsPanel(success?.totals ?: DailyTotals())

                when (val s = state) {
                    NutritionUIState.Loading -> Box(
                        Modifier.fillMaxWidth().padding(Spacing.s24),
                        contentAlignment = Alignment.Center,
                    ) { CircularProgressIndicator(color = VoraGreen) }

                    NutritionUIState.Empty -> InfoCard {
                        Text(
                            "No hay comidas registradas este día.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = VoraTextSecondary,
                        )
                    }

                    is NutritionUIState.Success -> s.meals.forEach { meal ->
                        MealRow(
                            meal = meal,
                            onClick = { onMeal(meal.id) },
                            onDelete = { pendingDeleteId = meal.id },
                        )
                    }

                    is NutritionUIState.Error -> InfoCard {
                        Text(s.message, style = MaterialTheme.typography.bodyMedium, color = VoraError)
                        SecondaryButton("Reintentar", viewModel::retry, Modifier.fillMaxWidth())
                    }
                }

                PrimaryButton(
                    text = "Agregar comida",
                    onClick = onAdd,
                    modifier = Modifier.fillMaxWidth(),
                    size = ButtonSize.Large,
                    icon = R.drawable.ic_plus,
                )
            }
        }
    }
}

@Composable
private fun DaySelector(label: String, onPrevious: () -> Unit, onNext: () -> Unit) {
    Row(
        Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        IconCircleButton(R.drawable.ic_chevron_left, "Día anterior", onPrevious)
        Text(label, style = MaterialTheme.typography.titleMedium, color = VoraTextPrimary)
        IconCircleButton(
            icon = R.drawable.ic_chevron_left,
            contentDescription = "Día siguiente",
            onClick = onNext,
            modifier = Modifier.rotate(180f),
        )
    }
}

@Composable
private fun TotalsPanel(totals: DailyTotals) {
    DarkPanel(padding = Spacing.s16, verticalArrangement = Arrangement.spacedBy(Spacing.s12)) {
        Text("TOTAL DEL DÍA", style = MaterialTheme.typography.labelSmall, color = VoraMint)
        Text("${totals.kcal} kcal", style = PanelTitleStyle)
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            MacroText("Proteínas", totals.protein)
            MacroText("Carbos", totals.carbs)
            MacroText("Grasas", totals.fat)
        }
    }
}

@Composable
private fun MacroText(label: String, grams: Double) {
    Column {
        Text(label, style = MaterialTheme.typography.bodySmall, color = VoraMint)
        Text(
            String.format(Locale.getDefault(), "%.1f g", grams),
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}

@Composable
private fun MealRow(meal: Meal, onClick: () -> Unit, onDelete: () -> Unit) {
    InfoCard(
        modifier = Modifier.clickable(onClick = onClick),
        shape = MaterialTheme.shapes.small,
        padding = Spacing.s12,
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(Modifier.weight(1f)) {
                Text(
                    meal.name,
                    style = MaterialTheme.typography.titleMedium,
                    color = VoraTextPrimary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    "${meal.type.label()} · ${meal.kcal} kcal",
                    style = MaterialTheme.typography.bodyMedium,
                    color = VoraTextSecondary,
                )
            }
            IconCircleButton(R.drawable.ic_close, "Borrar ${meal.name}", onDelete)
        }
    }
}
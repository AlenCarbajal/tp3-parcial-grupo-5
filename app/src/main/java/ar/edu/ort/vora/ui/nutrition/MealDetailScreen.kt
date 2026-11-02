package ar.edu.ort.vora.ui.nutrition

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ar.edu.ort.vora.R
import ar.edu.ort.vora.domain.model.Meal
import ar.edu.ort.vora.ui.components.ConfirmDialog
import ar.edu.ort.vora.ui.components.DarkPanel
import ar.edu.ort.vora.ui.components.InfoCard
import ar.edu.ort.vora.ui.components.SecondaryButton
import ar.edu.ort.vora.ui.components.TopBarBack
import ar.edu.ort.vora.ui.components.VoraTextButton
import ar.edu.ort.vora.ui.components.VoraTopBar
import ar.edu.ort.vora.ui.components.rememberDemoNotice
import ar.edu.ort.vora.ui.theme.PanelTitleStyle
import ar.edu.ort.vora.ui.theme.Spacing
import ar.edu.ort.vora.ui.theme.VoraBackground
import ar.edu.ort.vora.ui.theme.VoraError
import ar.edu.ort.vora.ui.theme.VoraMint
import ar.edu.ort.vora.ui.theme.VoraTextPrimary
import ar.edu.ort.vora.ui.theme.VoraTextSecondary

@Composable
fun MealDetailScreen(
    onBack: () -> Unit,
    detailViewModel: MealDetailViewModel = hiltViewModel(),
    nutritionViewModel: NutritionViewModel = hiltViewModel(),
) {
    val state by detailViewModel.uiState.collectAsStateWithLifecycle()
    var confirmDelete by rememberSaveable { mutableStateOf(false) }
    val demo = rememberDemoNotice()
    val meal = (state as? MealDetailUiState.Found)?.meal

    if (confirmDelete && meal != null) {
        ConfirmDialog(
            title = stringResource(R.string.nutrition_delete_title),
            message = stringResource(R.string.nutrition_delete_message, meal.name),
            confirmText = stringResource(R.string.common_delete),
            onConfirm = {
                confirmDelete = false
                nutritionViewModel.deleteMeal(meal, onBack)
            },
            onDismiss = { confirmDelete = false },
        )
    }

    Column(Modifier.fillMaxSize().background(VoraBackground)) {
        VoraTopBar(title = meal?.type?.label() ?: stringResource(R.string.nutrition_meal), back = TopBarBack.Plain, onBack = onBack)
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
                when (val s = state) {
                    MealDetailUiState.Loading -> Unit
                    MealDetailUiState.NotFound -> Text(
                        stringResource(R.string.nutrition_meal_not_found),
                        style = MaterialTheme.typography.bodyMedium,
                        color = VoraTextSecondary,
                    )
                    is MealDetailUiState.Found -> {
                        MealSummary(s.meal)
                        SecondaryButton(stringResource(R.string.nutrition_edit_meal), demo, Modifier.fillMaxWidth())
                        SecondaryButton(stringResource(R.string.nutrition_duplicate_meal), demo, Modifier.fillMaxWidth())
                        VoraTextButton(stringResource(R.string.nutrition_delete_title), { confirmDelete = true }, Modifier.fillMaxWidth(), color = VoraError)
                    }
                }
            }
        }
    }
}

@Composable
private fun MealSummary(meal: Meal) {
    InfoCard {
        Text(meal.name, style = MaterialTheme.typography.titleLarge, color = VoraTextPrimary)
        Text(
            "${meal.type.label()} · ${meal.date}",
            style = MaterialTheme.typography.bodyMedium,
            color = VoraTextSecondary,
        )
    }
    DarkPanel(padding = Spacing.s16, verticalArrangement = Arrangement.spacedBy(Spacing.s12)) {
        Text(stringResource(R.string.nutrition_kcal, meal.kcal), style = PanelTitleStyle)
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Column {
                Text(stringResource(R.string.common_protein), style = MaterialTheme.typography.bodySmall, color = VoraMint)
                Text(stringResource(R.string.nutrition_grams, meal.protein))
            }
            Column {
                Text(stringResource(R.string.common_carbs), style = MaterialTheme.typography.bodySmall, color = VoraMint)
                Text(stringResource(R.string.nutrition_grams, meal.carbs))
            }
            Column {
                Text(stringResource(R.string.common_fat), style = MaterialTheme.typography.bodySmall, color = VoraMint)
                Text(stringResource(R.string.nutrition_grams, meal.fat))
            }
        }
    }
}
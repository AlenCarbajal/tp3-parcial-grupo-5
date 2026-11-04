package ar.edu.ort.vora.ui.nutrition

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import ar.edu.ort.vora.domain.model.Meal
import ar.edu.ort.vora.domain.model.MealType
import ar.edu.ort.vora.ui.components.ButtonSize
import ar.edu.ort.vora.ui.components.ChipStyle
import ar.edu.ort.vora.ui.components.DarkPanel
import ar.edu.ort.vora.ui.components.InfoCard
import ar.edu.ort.vora.ui.components.PrimaryButton
import ar.edu.ort.vora.ui.components.SecondaryButton
import ar.edu.ort.vora.ui.components.TopBarBack
import ar.edu.ort.vora.ui.components.VoraChip
import ar.edu.ort.vora.ui.components.VoraTopBar
import ar.edu.ort.vora.ui.theme.PanelTitleStyle
import ar.edu.ort.vora.ui.theme.Spacing
import ar.edu.ort.vora.ui.theme.VoraBackground
import ar.edu.ort.vora.ui.theme.VoraMint
import ar.edu.ort.vora.ui.theme.VoraTextPrimary
import ar.edu.ort.vora.ui.theme.VoraTextSecondary
import java.util.Locale

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ReviewMealScreen(
    onBack: () -> Unit,
    onSaved: () -> Unit,
    viewModel: NutritionViewModel = hiltViewModel(),
) {
    var saving by rememberSaveable { mutableStateOf(false) }

    val sampleMeal = Meal(
        name = "Omelette de espinacas y tostada",
        date = todayIso(),
        type = MealType.LUNCH,
        kcal = 340,
        protein = 22.0,
        carbs = 28.0,
        fat = 14.0
    )

    val ingredients = listOf("Huevos x2", "Espinaca fresca", "Tostada masa madre", "Palta")

    Column(
        Modifier
            .fillMaxSize()
            .background(VoraBackground)
    ) {
        VoraTopBar(title = "Review Meal", back = TopBarBack.Plain, onBack = onBack)
        Box(
            Modifier
                .weight(1f)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            contentAlignment = Alignment.TopCenter,
        ) {
            Column(
                Modifier
                    .widthIn(max = Spacing.maxContentWidth)
                    .fillMaxWidth()
                    .padding(horizontal = Spacing.screen, vertical = Spacing.s24),
                verticalArrangement = Arrangement.spacedBy(Spacing.s20),
            ) {
                Text(
                    text = "RESULTADO DE VORA AI",
                    style = MaterialTheme.typography.labelSmall,
                    color = VoraMint,
                )

                InfoCard {
                    Text(
                        text = sampleMeal.name,
                        style = MaterialTheme.typography.titleLarge,
                        color = VoraTextPrimary,
                    )
                    Text(
                        text = "${sampleMeal.type.label()} · ${sampleMeal.date}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = VoraTextSecondary,
                    )
                }

                DarkPanel(
                    padding = Spacing.s16,
                    verticalArrangement = Arrangement.spacedBy(Spacing.s12),
                ) {
                    Text(
                        text = "ESTIMACIÓN NUTRICIONAL",
                        style = MaterialTheme.typography.labelSmall,
                        color = VoraMint,
                    )
                    Text(
                        text = "${sampleMeal.kcal} kcal",
                        style = PanelTitleStyle,
                    )
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Column {
                            Text("Proteínas", style = MaterialTheme.typography.bodySmall, color = VoraMint)
                            Text(String.format(Locale.getDefault(), "%.1f g", sampleMeal.protein))
                        }
                        Column {
                            Text("Carbos", style = MaterialTheme.typography.bodySmall, color = VoraMint)
                            Text(String.format(Locale.getDefault(), "%.1f g", sampleMeal.carbs))
                        }
                        Column {
                            Text("Grasas", style = MaterialTheme.typography.bodySmall, color = VoraMint)
                            Text(String.format(Locale.getDefault(), "%.1f g", sampleMeal.fat))
                        }
                    }
                }

                Text(
                    text = "INGREDIENTES DETECTADOS",
                    style = MaterialTheme.typography.labelSmall,
                    color = VoraMint,
                )

                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(Spacing.s8),
                    verticalArrangement = Arrangement.spacedBy(Spacing.s8),
                ) {
                    ingredients.forEach { item ->
                        VoraChip(
                            text = item,
                            onClick = {},
                            style = ChipStyle.Mint,
                        )
                    }
                }

                Spacer(Modifier.height(Spacing.s12))

                PrimaryButton(
                    text = "Add to Lunch (Guardar comida)",
                    onClick = {
                        saving = true
                        viewModel.addMeal(sampleMeal, onSaved)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    size = ButtonSize.Large,
                    enabled = !saving,
                )

                SecondaryButton(
                    text = "Descartar",
                    onClick = onBack,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }
    }
}

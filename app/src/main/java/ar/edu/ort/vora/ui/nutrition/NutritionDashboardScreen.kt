package ar.edu.ort.vora.ui.nutrition

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ar.edu.ort.vora.R
import ar.edu.ort.vora.domain.model.DailyTotals
import ar.edu.ort.vora.domain.model.Meal
import ar.edu.ort.vora.ui.components.ButtonSize
import ar.edu.ort.vora.ui.components.CoachNote
import ar.edu.ort.vora.ui.components.DarkPanel
import ar.edu.ort.vora.ui.components.IconCircleButton
import ar.edu.ort.vora.ui.components.InfoCard
import ar.edu.ort.vora.ui.components.LimeButton
import ar.edu.ort.vora.ui.components.PanelButton
import ar.edu.ort.vora.ui.components.PrimaryButton
import ar.edu.ort.vora.ui.components.ProgressBar
import ar.edu.ort.vora.ui.components.ProgressRing
import ar.edu.ort.vora.ui.components.VoraOrb
import ar.edu.ort.vora.ui.components.VoraTextButton
import ar.edu.ort.vora.ui.components.rememberDemoNotice
import ar.edu.ort.vora.ui.home.TabScreen
import ar.edu.ort.vora.ui.theme.Spacing
import ar.edu.ort.vora.ui.theme.VoraBorder
import ar.edu.ort.vora.ui.theme.VoraGreen
import ar.edu.ort.vora.ui.theme.VoraLime
import ar.edu.ort.vora.ui.theme.VoraMint
import ar.edu.ort.vora.ui.theme.VoraSurface
import ar.edu.ort.vora.ui.theme.VoraTextMuted
import ar.edu.ort.vora.ui.theme.VoraTextPrimary
import ar.edu.ort.vora.ui.theme.VoraTextSecondary
import java.text.SimpleDateFormat
import java.util.Date

// Objetivos diarios de ejemplo de Figma: la app no tiene perfil real del que sacarlos.
private const val KCAL_GOAL = 2100
private const val PROTEIN_GOAL = 140
private const val CARBS_GOAL = 220
private const val FAT_GOAL = 65

// 18 · Nutrition: calorías, macros y comidas de hoy salen de Room (mismo ViewModel que Today's Meals).
@Composable
fun NutritionDashboardScreen(
    onHistory: () -> Unit,
    onAdd: () -> Unit,
    onVoice: () -> Unit,
    onPhoto: () -> Unit,
    viewModel: NutritionViewModel = hiltViewModel(),
) {
    // Cada ruta tiene su propia instancia del ViewModel: acá la fecha queda siempre en hoy.
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val meals = (state as? NutritionUIState.Success)?.meals.orEmpty()
    val totals = (state as? NutritionUIState.Success)?.totals ?: DailyTotals()

    TabScreen {
        Header(onHistory)
        CaloriesCard(totals)
        CoachNote(stringResource(R.string.nutrition_dashboard_note))
        Row(Modifier.padding(top = Spacing.s4), verticalAlignment = Alignment.CenterVertically) {
            Text(stringResource(R.string.nutrition_meals_title), Modifier.weight(1f), style = MaterialTheme.typography.titleMedium, color = VoraTextPrimary)
            VoraTextButton(stringResource(R.string.nutrition_view_history), onHistory, color = VoraGreen)
        }
        Column(verticalArrangement = Arrangement.spacedBy(Spacing.s8)) {
            mealTypesInOrder.forEach { type ->
                MealTypeRow(type.label(), meals.filter { it.type == type }, onOpen = onHistory, onAdd = onAdd)
            }
        }
        TellVoraPanel(onVoice, onPhoto)
    }
}

@Composable
private fun Header(onCalendar: () -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Column(Modifier.weight(1f)) {
            Text(
                SimpleDateFormat("EEEE, d MMMM", LocalConfiguration.current.locales[0]).format(Date()).uppercase(),
                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Medium),
                color = VoraTextSecondary,
            )
            Text(stringResource(R.string.common_nav_nutrition), style = MaterialTheme.typography.headlineSmall, color = VoraTextPrimary)
        }
        IconCircleButton(R.drawable.ic_calendar, stringResource(R.string.nutrition_view_history), onCalendar)
    }
}

@Composable
private fun CaloriesCard(totals: DailyTotals) {
    InfoCard {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(Spacing.s4)) {
                Text(stringResource(R.string.nutrition_calories_consumed), style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Medium), color = VoraTextSecondary)
                Text(stringResource(R.string.common_number, totals.kcal), style = MaterialTheme.typography.displaySmall, color = VoraTextPrimary)
                Text(stringResource(R.string.nutrition_calorie_goal, KCAL_GOAL), style = MaterialTheme.typography.bodySmall, color = VoraTextMuted)
            }
            ProgressRing(totals.kcal / KCAL_GOAL.toFloat(), size = 88.dp) {
                Text(
                    stringResource(R.string.home_percent, totals.kcal * 100 / KCAL_GOAL),
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold,
                    color = VoraTextPrimary,
                )
            }
        }
        HorizontalDivider(Modifier.padding(vertical = Spacing.s16), color = VoraBorder)
        Row(horizontalArrangement = Arrangement.spacedBy(Spacing.s16)) {
            Macro(stringResource(R.string.common_protein), totals.protein, PROTEIN_GOAL, VoraGreen)
            Macro(stringResource(R.string.common_carbs), totals.carbs, CARBS_GOAL, VoraMint)
            Macro(stringResource(R.string.common_fat), totals.fat, FAT_GOAL, VoraLime)
        }
    }
}

@Composable
private fun RowScope.Macro(label: String, grams: Double, goal: Int, color: Color) {
    Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(Spacing.s4)) {
        Text(label, style = MaterialTheme.typography.bodyMedium, color = VoraTextSecondary)
        Text(
            buildAnnotatedString {
                withStyle(SpanStyle(fontWeight = FontWeight.Bold, color = VoraTextPrimary)) {
                    append(stringResource(R.string.nutrition_macro_value, grams.toInt()))
                }
                withStyle(SpanStyle(fontSize = MaterialTheme.typography.labelSmall.fontSize, color = VoraTextMuted)) {
                    append(stringResource(R.string.nutrition_macro_goal, goal))
                }
            },
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 1,
        )
        ProgressBar((grams / goal).toFloat(), color = color, height = 6.dp)
    }
}

// Una fila por tipo de comida: lo registrado ese día (nombres y kcal sumadas) o el botón para agregarla.
@Composable
private fun MealTypeRow(title: String, meals: List<Meal>, onOpen: () -> Unit, onAdd: () -> Unit) {
    val logged = meals.isNotEmpty()
    InfoCard(
        modifier = Modifier.clip(MaterialTheme.shapes.small).clickable(enabled = logged, onClick = onOpen),
        shape = MaterialTheme.shapes.small,
        padding = Spacing.s12,
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(Modifier.weight(1f).padding(end = Spacing.s12)) {
                Text(title, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Medium, color = VoraTextPrimary)
                Text(
                    if (logged) meals.sortedBy { it.id }.joinToString(" · ") { it.name } else stringResource(R.string.nutrition_not_logged),
                    style = MaterialTheme.typography.bodySmall,
                    color = if (logged) VoraTextSecondary else VoraTextMuted,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
            if (logged) {
                Text(
                    stringResource(R.string.common_value_kcal, meals.sumOf { it.kcal }),
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold,
                    color = VoraTextPrimary,
                )
            } else {
                PrimaryButton(stringResource(R.string.nutrition_add_short), onAdd, size = ButtonSize.Small)
            }
        }
    }
}

@Composable
private fun TellVoraPanel(onVoice: () -> Unit, onPhoto: () -> Unit) {
    val demoNotice = rememberDemoNotice()
    DarkPanel(padding = Spacing.s16, verticalArrangement = Arrangement.spacedBy(Spacing.s12)) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(Spacing.s8)) {
            VoraOrb(size = 28.dp, glowAlpha = 0.3f)
            Text(stringResource(R.string.nutrition_tell_vora), style = MaterialTheme.typography.labelLarge, color = VoraMint)
        }
        Text(
            stringResource(R.string.nutrition_tell_vora_hint),
            style = MaterialTheme.typography.bodyMedium,
            color = VoraSurface.copy(alpha = 0.6f),
        )
        Row(horizontalArrangement = Arrangement.spacedBy(Spacing.s8)) {
            LimeButton(stringResource(R.string.nutrition_add_food_voice), onVoice, Modifier.weight(1f), ButtonSize.Split, icon = R.drawable.ic_mic)
            PanelButton(stringResource(R.string.nutrition_add_food_photo), onPhoto, Modifier.weight(1f), ButtonSize.Split, icon = R.drawable.ic_camera)
            // Search no tiene pantalla en Figma: avisa que es demo, igual que en Add Food.
            PanelButton(stringResource(R.string.nutrition_search), demoNotice, Modifier.weight(1f), ButtonSize.Split, icon = R.drawable.ic_search)
        }
    }
}

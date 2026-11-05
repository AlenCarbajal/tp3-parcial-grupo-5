package ar.edu.ort.vora.ui.nutrition

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ar.edu.ort.vora.R
import ar.edu.ort.vora.domain.model.Meal
import ar.edu.ort.vora.domain.model.MealType
import ar.edu.ort.vora.ui.components.ChipStyle
import ar.edu.ort.vora.ui.components.CoachNote
import ar.edu.ort.vora.ui.components.InfoCard
import ar.edu.ort.vora.ui.components.MacroProgress
import ar.edu.ort.vora.ui.components.MicButton
import ar.edu.ort.vora.ui.components.PrimaryButton
import ar.edu.ort.vora.ui.components.SecondaryButton
import ar.edu.ort.vora.ui.components.TopBarBack
import ar.edu.ort.vora.ui.components.VoiceOrb
import ar.edu.ort.vora.ui.components.VoraChip
import ar.edu.ort.vora.ui.components.VoraOrb
import ar.edu.ort.vora.ui.components.VoraTextButton
import ar.edu.ort.vora.ui.components.VoraTopBar
import ar.edu.ort.vora.ui.components.asString
import ar.edu.ort.vora.ui.components.rememberDemoNotice
import ar.edu.ort.vora.ui.theme.Spacing
import ar.edu.ort.vora.ui.theme.VoraBackground
import ar.edu.ort.vora.ui.theme.VoraError
import ar.edu.ort.vora.ui.theme.VoraGreen
import ar.edu.ort.vora.ui.theme.VoraLime
import ar.edu.ort.vora.ui.theme.VoraMint
import ar.edu.ort.vora.ui.theme.VoraSurface
import ar.edu.ort.vora.ui.theme.VoraTextPrimary
import ar.edu.ort.vora.ui.theme.VoraTextSecondary
import kotlinx.coroutines.delay

// Pantallas de demostración del flujo Nutrition (T33): Add Food, Voice Logging,
// Analyzing, Analyse Meal (foto) y Review Meal. Usan datos de ejemplo fijos, sin
// micrófono, cámara ni servicio de IA, como indica la consigna para las pantallas demo.
// La carga real de comidas (con Room) está en ManualMealScreen.

// Estructura común: barra superior con atrás y contenido centrado con scroll.
@Composable
private fun DemoScreenLayout(
    title: String,
    onBack: () -> Unit,
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(Modifier.fillMaxSize().background(VoraBackground)) {
        VoraTopBar(title = title, back = TopBarBack.Plain, onBack = onBack)
        Box(
            Modifier.weight(1f).fillMaxWidth().verticalScroll(rememberScrollState()),
            contentAlignment = Alignment.TopCenter,
        ) {
            Column(
                Modifier
                    .widthIn(max = Spacing.maxContentWidth)
                    .fillMaxWidth()
                    .padding(horizontal = Spacing.screen, vertical = Spacing.s16),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(Spacing.groupGap),
                content = content,
            )
        }
    }
}

// 20 · Add Food: elegir cómo registrar la comida.
@Composable
fun AddFoodScreen(
    onBack: () -> Unit,
    onVoice: () -> Unit,
    onPhoto: () -> Unit,
    onManual: () -> Unit,
) {
    val demoNotice = rememberDemoNotice()
    DemoScreenLayout(title = stringResource(R.string.nutrition_add_food_title), onBack = onBack) {
        Text(
            stringResource(R.string.nutrition_add_food_question),
            style = MaterialTheme.typography.bodyLarge,
            color = VoraTextSecondary,
            modifier = Modifier.fillMaxWidth(),
        )
        PrimaryButton(stringResource(R.string.nutrition_add_food_voice), onVoice, Modifier.fillMaxWidth(), icon = R.drawable.ic_mic)
        PrimaryButton(stringResource(R.string.nutrition_add_food_photo), onPhoto, Modifier.fillMaxWidth(), icon = R.drawable.ic_camera)
        // "Search Database" no tiene pantalla en Figma: avisa que es demo (ver specs/02-screens.md).
        SecondaryButton(stringResource(R.string.nutrition_add_food_search), demoNotice, Modifier.fillMaxWidth(), icon = R.drawable.ic_search)
        SecondaryButton(stringResource(R.string.nutrition_manual_title), onManual, Modifier.fillMaxWidth(), icon = R.drawable.ic_plus)
    }
}

// 21 · Voice Logging: transcripción de ejemplo, sin micrófono real.
@Composable
fun VoiceLoggingScreen(
    onBack: () -> Unit,
    onDone: () -> Unit,
    onTypeInstead: () -> Unit,
) {
    DemoScreenLayout(title = stringResource(R.string.nutrition_voice_title), onBack = onBack) {
        Spacer(Modifier.height(Spacing.s8))
        VoiceOrb(listening = true)
        Spacer(Modifier.height(Spacing.s8))
        InfoCard {
            Text(stringResource(R.string.nutrition_voice_transcript_label), style = MaterialTheme.typography.labelSmall, color = VoraTextSecondary)
            Spacer(Modifier.height(Spacing.s6))
            Text(
                stringResource(R.string.nutrition_voice_transcript),
                style = MaterialTheme.typography.bodyLarge,
                color = VoraTextPrimary,
            )
        }
        MicButton(onClick = onDone, listening = true)
        PrimaryButton(stringResource(R.string.nutrition_voice_done), onDone, Modifier.fillMaxWidth())
        VoraTextButton(stringResource(R.string.common_type_instead), onTypeInstead, underline = true)
    }
}

// 22 · VORA AI (Analyzing Meal): simula el análisis y pasa sola a Review.
@Composable
fun AnalyzingMealScreen(
    onCancel: () -> Unit,
    onFinished: () -> Unit,
) {
    val currentOnFinished by rememberUpdatedState(onFinished)
    LaunchedEffect(Unit) {
        delay(2500)
        currentOnFinished()
    }

    DemoScreenLayout(title = stringResource(R.string.nutrition_ai_title), onBack = onCancel) {
        Spacer(Modifier.height(Spacing.s40))
        VoraOrb(size = 120.dp, ripple = true) {
            Icon(painterResource(R.drawable.ic_sparkles), contentDescription = null, tint = VoraSurface)
        }
        Spacer(Modifier.height(Spacing.s16))
        Text(
            stringResource(R.string.nutrition_analyzing_title),
            style = MaterialTheme.typography.titleLarge,
            color = VoraTextPrimary,
            textAlign = TextAlign.Center,
        )
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(Spacing.s8)) {
            CircularProgressIndicator(Modifier.size(16.dp), color = VoraGreen, strokeWidth = 2.dp)
            Text(
                stringResource(R.string.nutrition_analyzing_subtitle),
                style = MaterialTheme.typography.bodyMedium,
                color = VoraTextSecondary,
            )
        }
        Spacer(Modifier.height(Spacing.s24))
        VoraTextButton(stringResource(R.string.common_cancel), onCancel)
    }
}

// 23 · Analyse Meal (foto): vista previa simulada con los ingredientes "detectados".
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AnalyseMealScreen(
    onBack: () -> Unit,
    onReview: () -> Unit,
) {
    val demoNotice = rememberDemoNotice()
    DemoScreenLayout(title = stringResource(R.string.nutrition_analyse_title), onBack = onBack) {
        Box(
            Modifier
                .fillMaxWidth()
                .height(220.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(VoraGreen),
            contentAlignment = Alignment.Center,
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(Spacing.s8)) {
                Icon(painterResource(R.drawable.ic_camera), contentDescription = null, tint = VoraMint, modifier = Modifier.size(32.dp))
                Text(stringResource(R.string.nutrition_analyse_photo_preview), style = MaterialTheme.typography.bodyMedium, color = VoraSurface)
            }
        }
        Text(
            stringResource(R.string.nutrition_analyse_detected),
            style = MaterialTheme.typography.labelSmall,
            color = VoraTextSecondary,
            modifier = Modifier.fillMaxWidth(),
        )
        FlowRow(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(Spacing.s8),
            verticalArrangement = Arrangement.spacedBy(Spacing.s8),
        ) {
            listOf(
                R.string.nutrition_ingredient_chicken,
                R.string.nutrition_ingredient_avocado,
                R.string.nutrition_ingredient_greens,
                R.string.nutrition_ingredient_tomatoes,
            ).forEach {
                VoraChip(text = stringResource(it), onClick = {}, style = ChipStyle.Checked)
            }
        }
        CoachNote(stringResource(R.string.nutrition_analyse_coach_note))
        PrimaryButton(stringResource(R.string.nutrition_analyse_review), onReview, Modifier.fillMaxWidth())
        SecondaryButton(stringResource(R.string.nutrition_analyse_retake), demoNotice, Modifier.fillMaxWidth())
    }
}

// 24 · Review Meal: resumen de ejemplo (los valores del análisis son fijos, es demo).
// "Add to Lunch" SÍ guarda la comida en Room con el mismo NutritionViewModel que el registro
// manual, así que después aparece en el listado y suma al total del día. "Save without
// logging" vuelve sin guardar nada.
@Composable
fun ReviewMealScreen(
    onBack: () -> Unit,
    onAdd: () -> Unit,
    onSaveWithoutLogging: () -> Unit,
    viewModel: NutritionViewModel = hiltViewModel(),
) {
    // saving evita guardar dos veces si se toca el botón rápido
    var saving by rememberSaveable { mutableStateOf(false) }
    val actionError by viewModel.actionError.collectAsStateWithLifecycle()
    LaunchedEffect(actionError) { if (actionError != null) saving = false }
    DisposableEffect(Unit) { onDispose { viewModel.clearActionError() } }
    // Se resuelve acá porque también se usa como nombre de la comida que se guarda
    val demoMealName = stringResource(R.string.nutrition_review_meal_name)

    DemoScreenLayout(title = stringResource(R.string.nutrition_review_title), onBack = onBack) {
        InfoCard {
            Text(stringResource(R.string.nutrition_review_lunch), style = MaterialTheme.typography.labelSmall, color = VoraTextSecondary)
            Spacer(Modifier.height(Spacing.s4))
            Text(
                demoMealName,
                style = MaterialTheme.typography.titleLarge,
                color = VoraTextPrimary,
            )
            Spacer(Modifier.height(Spacing.s4))
            Text(
                stringResource(R.string.nutrition_kcal, 420),
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = VoraGreen,
            )
            Spacer(Modifier.height(Spacing.s16))
            Column(verticalArrangement = Arrangement.spacedBy(Spacing.s12)) {
                MacroProgress(stringResource(R.string.common_protein), stringResource(R.string.nutrition_grams_int, 38), 0.6f, VoraGreen)
                MacroProgress(stringResource(R.string.common_carbs), stringResource(R.string.nutrition_grams_int, 24), 0.3f, VoraMint)
                MacroProgress(stringResource(R.string.common_fat), stringResource(R.string.nutrition_grams_int, 18), 0.4f, VoraLime)
            }
        }
        CoachNote(stringResource(R.string.nutrition_review_coach_note))
        actionError?.let {
            Text(it.asString(), style = MaterialTheme.typography.bodySmall, color = VoraError, modifier = Modifier.fillMaxWidth())
        }
        PrimaryButton(
            text = stringResource(R.string.nutrition_review_add_to_lunch),
            onClick = {
                saving = true
                viewModel.addMeal(
                    Meal(
                        name = demoMealName,
                        date = todayIso(),
                        type = MealType.LUNCH,
                        kcal = 420,
                        carbs = 24.0,
                        fat = 18.0,
                        protein = 38.0,
                    ),
                    onSaved = onAdd,
                )
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !saving,
        )
        SecondaryButton(stringResource(R.string.nutrition_review_save_without_logging), onSaveWithoutLogging, Modifier.fillMaxWidth())
    }
}

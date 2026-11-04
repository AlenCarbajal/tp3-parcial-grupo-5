package ar.edu.ort.vora.ui.nutrition

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ar.edu.ort.vora.R
import ar.edu.ort.vora.domain.model.MealType
import ar.edu.ort.vora.ui.components.ButtonSize
import ar.edu.ort.vora.ui.components.ChipStyle
import ar.edu.ort.vora.ui.components.PrimaryButton
import ar.edu.ort.vora.ui.components.TopBarBack
import ar.edu.ort.vora.ui.components.VoraChip
import ar.edu.ort.vora.ui.components.VoraTextField
import ar.edu.ort.vora.ui.components.VoraTopBar
import ar.edu.ort.vora.ui.components.asString
import ar.edu.ort.vora.ui.theme.Spacing
import ar.edu.ort.vora.ui.theme.VoraBackground
import ar.edu.ort.vora.ui.theme.VoraError
import ar.edu.ort.vora.ui.theme.VoraTextSecondary


@Composable
fun ManualMealScreen(
    onBack: () -> Unit,
    onSaved: () -> Unit,
    viewModel: NutritionViewModel = hiltViewModel(),
) {
    // rememberSaveable: rotar no pierde lo que escribió (RF07)
    var name by rememberSaveable { mutableStateOf("") }
    var date by rememberSaveable { mutableStateOf(todayIso()) }
    var typeName by rememberSaveable { mutableStateOf(MealType.LUNCH.name) }
    var kcal by rememberSaveable { mutableStateOf("") }
    var protein by rememberSaveable { mutableStateOf("") }
    var carbs by rememberSaveable { mutableStateOf("") }
    var fat by rememberSaveable { mutableStateOf("") }
    var submitted by rememberSaveable { mutableStateOf(false) }
    var saving by rememberSaveable { mutableStateOf(false) }

    val actionError by viewModel.actionError.collectAsStateWithLifecycle()
    LaunchedEffect(actionError) { if (actionError != null) saving = false }
    DisposableEffect(Unit) { onDispose { viewModel.clearActionError() } }

    val form = MealFormState(name, MealType.valueOf(typeName), kcal , carbs, fat, protein, date )
    // Los errores aparecen recién después del primer intento de guardar, y se actualizan en vivo
    val errors = if (submitted) form.errors() else MealFormErrors()

    Column(Modifier.fillMaxSize().background(VoraBackground).imePadding()) {
        VoraTopBar(title = stringResource(R.string.nutrition_manual_title), back = TopBarBack.Plain, onBack = onBack)
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
                VoraTextField(name, { name = it }, stringResource(R.string.nutrition_field_name), placeholder = stringResource(R.string.nutrition_field_name_placeholder), error = errors.name?.let { stringResource(it) })

                VoraTextField(date, { date = it }, stringResource(R.string.nutrition_field_date), placeholder = stringResource(R.string.nutrition_field_date_placeholder), error = errors.date?.let { stringResource(it) })
                Row(horizontalArrangement = Arrangement.spacedBy(Spacing.s8)) {
                    VoraChip(stringResource(R.string.common_today), { date = todayIso() })
                    VoraChip(stringResource(R.string.common_yesterday), { date = shiftIso(todayIso(), -1) })
                }

                Text(stringResource(R.string.nutrition_field_type), style = MaterialTheme.typography.labelSmall, color = VoraTextSecondary)
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(Spacing.s8),
                    verticalArrangement = Arrangement.spacedBy(Spacing.s8),
                ) {
                    mealTypesInOrder.forEach { type ->
                        VoraChip(
                            text = type.label(),
                            onClick = { typeName = type.name },
                            style = if (type.name == typeName) ChipStyle.Mint else ChipStyle.Default,
                        )
                    }
                }

                VoraTextField(
                    kcal, { kcal = it }, stringResource(R.string.nutrition_field_kcal),
                    placeholder = "0", error = errors.kcal?.let { stringResource(it) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                )
                Row(horizontalArrangement = Arrangement.spacedBy(Spacing.s12), verticalAlignment = Alignment.Top) {
                    val decimal = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                    VoraTextField(protein, { protein = it }, stringResource(R.string.nutrition_field_protein), Modifier.weight(1f), "0", errors.protein?.let { stringResource(it) }, decimal)
                    VoraTextField(carbs, { carbs = it }, stringResource(R.string.nutrition_field_carbs), Modifier.weight(1f), "0", errors.carbs?.let { stringResource(it) }, decimal)
                    VoraTextField(fat, { fat = it }, stringResource(R.string.nutrition_field_fat), Modifier.weight(1f), "0", errors.fat?.let { stringResource(it) }, decimal)
                }

                actionError?.let { Text(it.asString(), style = MaterialTheme.typography.bodySmall, color = VoraError) }

                PrimaryButton(
                    text = stringResource(R.string.nutrition_save_meal),
                    onClick = {
                        submitted = true
                        form.toMealOrNull()?.let { meal ->
                            saving = true
                            viewModel.addMeal(meal, onSaved)
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    size = ButtonSize.Large,
                    enabled = !saving,
                )
            }
        }
    }
}
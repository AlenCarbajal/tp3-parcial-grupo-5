@file:OptIn(ExperimentalLayoutApi::class)

package ar.edu.ort.vora.ui.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import ar.edu.ort.vora.R
import ar.edu.ort.vora.ui.components.ButtonSize
import ar.edu.ort.vora.ui.components.ChipStyle
import ar.edu.ort.vora.ui.components.CoachNote
import ar.edu.ort.vora.ui.components.DarkPanel
import ar.edu.ort.vora.ui.components.InfoCard
import ar.edu.ort.vora.ui.components.LimeButton
import ar.edu.ort.vora.ui.components.PrimaryButton
import ar.edu.ort.vora.ui.components.SecondaryButton
import ar.edu.ort.vora.ui.components.TopBarBack
import ar.edu.ort.vora.ui.components.VoraChip
import ar.edu.ort.vora.ui.components.VoraTextButton
import ar.edu.ort.vora.ui.components.VoraTextField
import ar.edu.ort.vora.ui.components.VoraTopBar
import ar.edu.ort.vora.ui.components.rememberDemoNotice
import ar.edu.ort.vora.ui.theme.Spacing
import ar.edu.ort.vora.ui.theme.VoraBackground
import ar.edu.ort.vora.ui.theme.VoraCoachTheme
import ar.edu.ort.vora.ui.theme.VoraGreen
import ar.edu.ort.vora.ui.theme.VoraTextMuted
import ar.edu.ort.vora.ui.theme.VoraTextPrimary
import ar.edu.ort.vora.ui.theme.VoraTextSecondary

// Encabezado arriba y contenido con scroll. Mismo patrón que CoachLayout.
@Composable
private fun ProfileLayout(
    title: String,
    back: TopBarBack = TopBarBack.Circle,
    onBack: () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(Modifier.fillMaxSize().background(VoraBackground).statusBarsPadding()) {
        VoraTopBar(title, back = back, onBack = onBack, actions = actions)
        Column(
            Modifier.weight(1f).fillMaxWidth().verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Column(
                Modifier.widthIn(max = Spacing.maxContentWidth).fillMaxWidth()
                    .padding(horizontal = Spacing.screen, vertical = Spacing.s16),
                verticalArrangement = Arrangement.spacedBy(Spacing.s16),
                content = content,
            )
        }
    }
}

// ---------- Piezas reutilizables ----------

@Composable
private fun SectionLabel(text: String) {
    Text(text.uppercase(), style = MaterialTheme.typography.labelSmall, color = VoraTextSecondary)
}

@Composable
private fun Subtitle(text: String) {
    Text(text, style = MaterialTheme.typography.bodySmall, color = VoraTextSecondary)
}

// Fila de dato: etiqueta a la izquierda y valor a la derecha.
@Composable
private fun DataRow(label: String, value: String) {
    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Text(label, Modifier.weight(1f), style = MaterialTheme.typography.bodySmall, color = VoraTextMuted)
        Text(value, style = MaterialTheme.typography.bodySmall, color = VoraTextPrimary)
    }
}

// Tarjeta tocable: título, detalle y flecha.
@Composable
private fun ProfileRow(title: String, detail: String, onClick: () -> Unit) {
    InfoCard(Modifier.clip(MaterialTheme.shapes.medium).clickable(onClick = onClick)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(Modifier.weight(1f)) {
                Text(title, style = MaterialTheme.typography.titleSmall, color = VoraTextPrimary)
                Text(detail, style = MaterialTheme.typography.bodySmall, color = VoraTextSecondary)
            }
            Text("›", style = MaterialTheme.typography.titleLarge, color = VoraTextMuted)
        }
    }
}

// Tarjeta de opción única con tilde cuando está elegida.
@Composable
private fun OptionCard(title: String, detail: String, selected: Boolean, onClick: () -> Unit) {
    InfoCard(Modifier.clip(MaterialTheme.shapes.medium).clickable(onClick = onClick)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(Modifier.weight(1f)) {
                Text(title, style = MaterialTheme.typography.titleSmall, color = VoraTextPrimary)
                Text(detail, style = MaterialTheme.typography.bodySmall, color = VoraTextSecondary)
            }
            if (selected) Text("✓", style = MaterialTheme.typography.titleMedium, color = VoraGreen)
        }
    }
}

// Fila con interruptor: título, detalle opcional y Switch de material3.
@Composable
private fun SwitchRow(title: String, detail: String?, checked: Boolean, onChange: (Boolean) -> Unit) {
    InfoCard {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(Modifier.weight(1f)) {
                Text(title, style = MaterialTheme.typography.titleSmall, color = VoraTextPrimary)
                if (detail != null) {
                    Text(detail, style = MaterialTheme.typography.bodySmall, color = VoraTextSecondary)
                }
            }
            Switch(checked = checked, onCheckedChange = onChange)
        }
    }
}

// Opción única: el elegido se ve con selectedStyle (verde por defecto). Las opciones son ids de string.
@Composable
private fun SingleChoice(
    title: String,
    options: List<Int>,
    selected: Int,
    selectedStyle: ChipStyle = ChipStyle.Green,
    onSelect: (Int) -> Unit,
) {
    Column(verticalArrangement = Arrangement.spacedBy(Spacing.s8)) {
        SectionLabel(title)
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(Spacing.s8),
            verticalArrangement = Arrangement.spacedBy(Spacing.s8),
        ) {
            options.forEach {
                VoraChip(stringResource(it), { onSelect(it) }, style = if (it == selected) selectedStyle else ChipStyle.Default)
            }
        }
    }
}

// Selección múltiple: los elegidos se ven con borde menta y check. Las opciones son ids de string.
@Composable
private fun MultiChoice(title: String, options: List<Int>, selected: List<Int>, onToggle: (Int) -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(Spacing.s8)) {
        SectionLabel(title)
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(Spacing.s8),
            verticalArrangement = Arrangement.spacedBy(Spacing.s8),
        ) {
            options.forEach {
                VoraChip(stringResource(it), { onToggle(it) }, style = if (it in selected) ChipStyle.Checked else ChipStyle.Default)
            }
        }
    }
}

// ---------- 01 Profile (dashboard) ----------

@Composable
fun ProfileScreen(
    onEdit: () -> Unit,
    onGoals: () -> Unit,
    onNutrition: () -> Unit,
    onSettings: () -> Unit,
) {
    ProfileLayout(
        stringResource(R.string.common_nav_profile),
        back = TopBarBack.None,
        actions = { VoraTextButton(stringResource(R.string.profile_settings), onSettings) },
    ) {
        InfoCard {
            Text(stringResource(R.string.common_demo_user_name), style = MaterialTheme.typography.titleLarge, color = VoraTextPrimary)
            Text(stringResource(R.string.profile_tagline), style = MaterialTheme.typography.bodySmall, color = VoraTextSecondary)
            SecondaryButton(stringResource(R.string.profile_edit), onEdit, Modifier.padding(top = Spacing.s12), size = ButtonSize.Small)
        }
        InfoCard {
            SectionLabel(stringResource(R.string.profile_snapshot))
            DataRow(stringResource(R.string.profile_goal), stringResource(R.string.common_goal_strength_fitness))
            DataRow(stringResource(R.string.profile_experience), stringResource(R.string.common_level_intermediate))
            DataRow(stringResource(R.string.profile_training), pluralStringResource(R.plurals.profile_days_per_week, 4, 4))
            DataRow(stringResource(R.string.profile_preferred_workout), stringResource(R.string.profile_preferred_workout_value))
        }
        DarkPanel {
            Text(stringResource(R.string.profile_personalisation_label), style = MaterialTheme.typography.labelSmall)
            Text(stringResource(R.string.profile_personalisation_title), style = MaterialTheme.typography.titleMedium)
            Text(
                stringResource(R.string.profile_personalisation_body),
                style = MaterialTheme.typography.bodySmall,
            )
            LimeButton(stringResource(R.string.profile_review_preferences), onGoals, size = ButtonSize.Small)
        }
        ProfileRow(stringResource(R.string.profile_row_goals), stringResource(R.string.common_goal_strength_fitness), onGoals)
        ProfileRow(stringResource(R.string.profile_row_preferences), stringResource(R.string.profile_row_preferences_detail), onNutrition)
        ProfileRow(stringResource(R.string.profile_connected_data), stringResource(R.string.profile_connected_data_detail), onSettings)
        ProfileRow(stringResource(R.string.profile_account), stringResource(R.string.profile_account_detail), onSettings)
    }
}

// ---------- 02 Edit profile ----------

@Composable
fun ProfileEditScreen(onBack: () -> Unit, onSave: () -> Unit) {
    val demoFirst = stringResource(R.string.profile_demo_first_name)
    val demoLast = stringResource(R.string.profile_demo_last_name)
    val demoBirth = stringResource(R.string.profile_demo_birth)
    val numberError = stringResource(R.string.profile_error_number)
    var first by rememberSaveable { mutableStateOf(demoFirst) }
    var last by rememberSaveable { mutableStateOf(demoLast) }
    var birth by rememberSaveable { mutableStateOf(demoBirth) }
    var height by rememberSaveable { mutableStateOf("178") }
    var weight by rememberSaveable { mutableStateOf("76") }
    var gender by rememberSaveable { mutableStateOf(R.string.profile_gender_prefer_not) }

    ProfileLayout(stringResource(R.string.profile_edit), onBack = onBack) {
        VoraTextField(first, { first = it }, stringResource(R.string.profile_first_name))
        VoraTextField(last, { last = it }, stringResource(R.string.profile_last_name))
        VoraTextField(birth, { birth = it }, stringResource(R.string.profile_birth))
        VoraTextField(
            height, { height = it }, stringResource(R.string.profile_height),
            error = if (height.isNotEmpty() && height.toIntOrNull() == null) numberError else null,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        )
        VoraTextField(
            weight, { weight = it }, stringResource(R.string.profile_weight),
            error = if (weight.isNotEmpty() && weight.toIntOrNull() == null) numberError else null,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        )
        SingleChoice(
            stringResource(R.string.profile_gender),
            listOf(
                R.string.profile_gender_female,
                R.string.profile_gender_male,
                R.string.profile_gender_non_binary,
                R.string.profile_gender_prefer_not,
            ),
            gender,
        ) { gender = it }
        CoachNote(stringResource(R.string.profile_edit_note))
        PrimaryButton(stringResource(R.string.profile_save_changes), onSave, Modifier.fillMaxWidth(), size = ButtonSize.Large)
        Text(
            stringResource(R.string.profile_edit_footer),
            Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.bodySmall,
            color = VoraTextMuted,
            textAlign = TextAlign.Center,
        )
    }
}

// ---------- 03 Your goals ----------

@Composable
fun ProfileGoalsScreen(onBack: () -> Unit, onSave: () -> Unit) {
    var goal by rememberSaveable { mutableStateOf(R.string.common_goal_strength) }
    var days by rememberSaveable { mutableStateOf(R.string.profile_freq_4) }
    var workout by rememberSaveable { mutableStateOf(R.string.profile_workout_strength) }

    // Objetivo (título) -> detalle
    val goals = listOf(
        R.string.common_goal_strength to R.string.profile_goal_strength_detail,
        R.string.profile_goal_fitness to R.string.profile_goal_fitness_detail,
        R.string.profile_goal_fat to R.string.profile_goal_fat_detail,
        R.string.profile_goal_habits to R.string.profile_goal_habits_detail,
    )

    ProfileLayout(stringResource(R.string.profile_goals_title), onBack = onBack) {
        Subtitle(stringResource(R.string.profile_goals_subtitle))
        SectionLabel(stringResource(R.string.profile_primary_goal))
        goals.forEach { (title, detail) ->
            OptionCard(stringResource(title), stringResource(detail), goal == title) { goal = title }
        }
        SingleChoice(
            stringResource(R.string.profile_training_frequency),
            listOf(R.string.profile_freq_3, R.string.profile_freq_4, R.string.profile_freq_5, R.string.profile_freq_6_plus),
            days,
        ) { days = it }
        SingleChoice(
            stringResource(R.string.profile_workout_preference),
            listOf(
                R.string.profile_workout_strength,
                R.string.profile_workout_cardio,
                R.string.profile_workout_hiit,
                R.string.profile_workout_mobility,
                R.string.profile_workout_mixed,
            ),
            workout,
            selectedStyle = ChipStyle.Mint,
        ) { workout = it }
        SectionLabel(stringResource(R.string.profile_coaching_style_question))
        OptionCard(stringResource(R.string.profile_balanced), stringResource(R.string.profile_balanced_coaching_detail), true) {}
        PrimaryButton(stringResource(R.string.profile_save_preferences), onSave, Modifier.fillMaxWidth(), size = ButtonSize.Large)
    }
}

// ---------- 04 Nutrition preferences ----------

@Composable
fun ProfileNutritionScreen(onBack: () -> Unit, onSave: () -> Unit) {
    val foods = listOf(
        R.string.profile_food_chicken,
        R.string.profile_food_eggs,
        R.string.profile_food_fish,
        R.string.profile_food_rice,
        R.string.profile_food_pasta,
        R.string.profile_food_greek_yoghurt,
        R.string.profile_food_vegetables,
        R.string.profile_food_fruit,
    )
    val mattersOptions = listOf(R.string.profile_matters_protein, R.string.profile_matters_consistent)
    // Los evitados son texto libre (el usuario agrega los suyos), así que arrancan con los textos ya resueltos.
    val defaultAvoided = listOf(
        stringResource(R.string.profile_food_peanuts),
        stringResource(R.string.profile_food_shellfish),
        stringResource(R.string.profile_food_mushrooms),
    )
    var diet by rememberSaveable { mutableStateOf(R.string.profile_diet_everything) }
    var liked by rememberSaveable { mutableStateOf(foods) }
    var avoided by rememberSaveable { mutableStateOf(defaultAvoided) }
    var newItem by rememberSaveable { mutableStateOf("") }
    var matters by rememberSaveable { mutableStateOf(mattersOptions) }

    ProfileLayout(stringResource(R.string.profile_nutrition_title), onBack = onBack) {
        Subtitle(stringResource(R.string.profile_nutrition_subtitle))
        SingleChoice(
            stringResource(R.string.profile_dietary_preference),
            listOf(
                R.string.profile_diet_everything,
                R.string.profile_diet_vegetarian,
                R.string.profile_diet_vegan,
                R.string.profile_diet_pescatarian,
                R.string.profile_diet_other,
            ),
            diet,
            selectedStyle = ChipStyle.Mint,
        ) { diet = it }
        MultiChoice(stringResource(R.string.profile_foods_enjoy), foods, liked) {
            liked = if (it in liked) liked - it else liked + it
        }
        Column(verticalArrangement = Arrangement.spacedBy(Spacing.s8)) {
            SectionLabel(stringResource(R.string.profile_foods_avoid))
            VoraTextField(
                newItem,
                { newItem = it },
                stringResource(R.string.profile_add_ingredient),
                placeholder = stringResource(R.string.profile_add_ingredient_hint),
            )
            SecondaryButton(
                stringResource(R.string.common_add),
                {
                    val item = newItem.trim()
                    if (item.isNotEmpty() && item !in avoided) avoided = avoided + item
                    newItem = ""
                },
                size = ButtonSize.Small,
            )
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(Spacing.s8),
                verticalArrangement = Arrangement.spacedBy(Spacing.s8),
            ) {
                avoided.forEach { VoraChip(it, { avoided = avoided - it }, style = ChipStyle.Removable) }
            }
        }
        InfoCard {
            SectionLabel(stringResource(R.string.profile_meal_preferences))
            DataRow(stringResource(R.string.profile_meals_per_day), stringResource(R.string.profile_meals_per_day_value))
            DataRow(stringResource(R.string.profile_preferred_breakfast), stringResource(R.string.profile_preferred_breakfast_value))
            DataRow(stringResource(R.string.profile_preferred_dinner), stringResource(R.string.profile_preferred_dinner_value))
        }
        MultiChoice(stringResource(R.string.profile_matters_title), mattersOptions, matters) {
            matters = if (it in matters) matters - it else matters + it
        }
        PrimaryButton(stringResource(R.string.profile_save_preferences), onSave, Modifier.fillMaxWidth(), size = ButtonSize.Large)
    }
}

// ---------- 05 VORA Coach (Voice & AI settings) ----------

@Composable
fun ProfileVoiceScreen(onBack: () -> Unit) {
    val demo = rememberDemoNotice()
    var style by rememberSaveable { mutableStateOf(R.string.profile_balanced) }
    var aloud by rememberSaveable { mutableStateOf(true) }
    var autoPlay by rememberSaveable { mutableStateOf(true) }
    var interrupt by rememberSaveable { mutableStateOf(true) }
    var response by rememberSaveable { mutableStateOf(R.string.profile_balanced) }
    var voiceFeedback by rememberSaveable { mutableStateOf(true) }
    var haptic by rememberSaveable { mutableStateOf(true) }

    // Estilo de respuesta (título) -> detalle
    val responses = listOf(
        R.string.profile_response_concise to R.string.profile_response_concise_detail,
        R.string.profile_balanced to R.string.profile_response_balanced_detail,
        R.string.profile_response_detailed to R.string.profile_response_detailed_detail,
    )

    ProfileLayout(stringResource(R.string.coach_title), onBack = onBack) {
        Subtitle(stringResource(R.string.profile_voice_subtitle))
        SingleChoice(
            stringResource(R.string.profile_coaching_style),
            listOf(R.string.profile_style_supportive, R.string.profile_balanced, R.string.profile_style_direct),
            style,
        ) { style = it }
        SectionLabel(stringResource(R.string.profile_active_voice))
        InfoCard {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    stringResource(R.string.profile_voice_name),
                    Modifier.weight(1f),
                    style = MaterialTheme.typography.titleSmall,
                    color = VoraTextPrimary,
                )
                VoraTextButton(stringResource(R.string.profile_voice_preview), demo)
            }
        }
        SectionLabel(stringResource(R.string.profile_speaking_behaviour))
        SwitchRow(stringResource(R.string.profile_speak_aloud), null, aloud) { aloud = it }
        SwitchRow(stringResource(R.string.profile_auto_play), null, autoPlay) { autoPlay = it }
        SwitchRow(
            stringResource(R.string.profile_interruptions),
            stringResource(R.string.profile_interruptions_detail),
            interrupt,
        ) { interrupt = it }
        SectionLabel(stringResource(R.string.profile_response_style))
        responses.forEach { (title, detail) ->
            OptionCard(stringResource(title), stringResource(detail), response == title) { response = title }
        }
        SwitchRow(stringResource(R.string.profile_voice_feedback), null, voiceFeedback) { voiceFeedback = it }
        SwitchRow(stringResource(R.string.profile_haptic_feedback), null, haptic) { haptic = it }
        InfoCard {
            Text(stringResource(R.string.profile_control_title), style = MaterialTheme.typography.titleSmall, color = VoraTextPrimary)
            Text(
                stringResource(R.string.profile_control_body),
                style = MaterialTheme.typography.bodySmall,
                color = VoraTextSecondary,
            )
        }
    }
}

// ---------- 06 Settings ----------

@Composable
fun ProfileSettingsScreen(onBack: () -> Unit, onVoice: () -> Unit, onLogout: () -> Unit) {
    val demo = rememberDemoNotice()

    ProfileLayout(stringResource(R.string.profile_settings), onBack = onBack) {
        SectionLabel(stringResource(R.string.profile_account))
        ProfileRow(stringResource(R.string.common_demo_user_name), stringResource(R.string.profile_demo_email), demo)
        ProfileRow(stringResource(R.string.profile_premium), stringResource(R.string.profile_premium_renews), demo)
        ProfileRow(stringResource(R.string.profile_manage_account), stringResource(R.string.profile_manage_account_detail), demo)
        SectionLabel(stringResource(R.string.profile_app_preferences))
        ProfileRow(stringResource(R.string.coach_title), stringResource(R.string.profile_vora_coach_detail), onVoice)
        ProfileRow(stringResource(R.string.common_notifications), stringResource(R.string.profile_notifications_detail), demo)
        ProfileRow(stringResource(R.string.profile_units), stringResource(R.string.profile_units_detail), demo)
        ProfileRow(stringResource(R.string.profile_appearance), stringResource(R.string.profile_appearance_detail), demo)
        SectionLabel(stringResource(R.string.profile_connected_data))
        ProfileRow(stringResource(R.string.profile_health), stringResource(R.string.profile_health_detail), demo)
        SectionLabel(stringResource(R.string.profile_privacy_security))
        ProfileRow(stringResource(R.string.profile_privacy_policy), stringResource(R.string.profile_privacy_policy_detail), demo)
        ProfileRow(stringResource(R.string.profile_permissions), stringResource(R.string.profile_permissions_detail), demo)
        SectionLabel(stringResource(R.string.profile_support))
        ProfileRow(stringResource(R.string.profile_help_centre), stringResource(R.string.profile_help_centre_detail), demo)
        ProfileRow(stringResource(R.string.profile_contact_support), stringResource(R.string.profile_contact_support_detail), demo)
        SecondaryButton(stringResource(R.string.profile_log_out), onLogout, Modifier.fillMaxWidth(), size = ButtonSize.Large)
        Text(
            stringResource(R.string.profile_version),
            Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.bodySmall,
            color = VoraTextMuted,
            textAlign = TextAlign.Center,
        )
    }
}

// ---------- Previews ----------

@Preview(widthDp = 390, heightDp = 844)
@Composable
private fun ProfilePreview() {
    VoraCoachTheme { ProfileScreen({}, {}, {}, {}) }
}

@Preview(widthDp = 390, heightDp = 844)
@Composable
private fun ProfileEditPreview() {
    VoraCoachTheme { ProfileEditScreen({}, {}) }
}

@Preview(widthDp = 390, heightDp = 844)
@Composable
private fun ProfileGoalsPreview() {
    VoraCoachTheme { ProfileGoalsScreen({}, {}) }
}

@Preview(widthDp = 390, heightDp = 844)
@Composable
private fun ProfileNutritionPreview() {
    VoraCoachTheme { ProfileNutritionScreen({}, {}) }
}

@Preview(widthDp = 390, heightDp = 844)
@Composable
private fun ProfileVoicePreview() {
    VoraCoachTheme { ProfileVoiceScreen({}) }
}

@Preview(widthDp = 390, heightDp = 844)
@Composable
private fun ProfileSettingsPreview() {
    VoraCoachTheme { ProfileSettingsScreen({}, {}, {}) }
}
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import ar.edu.ort.vora.ui.components.ButtonSize
import ar.edu.ort.vora.ui.components.ChipStyle
import ar.edu.ort.vora.ui.components.InfoCard
import ar.edu.ort.vora.ui.components.PrimaryButton
import ar.edu.ort.vora.ui.components.TopBarBack
import ar.edu.ort.vora.ui.components.VoraChip
import ar.edu.ort.vora.ui.components.VoraTextButton
import ar.edu.ort.vora.ui.components.VoraTextField
import ar.edu.ort.vora.ui.components.VoraTopBar
import ar.edu.ort.vora.ui.theme.Spacing
import ar.edu.ort.vora.ui.theme.VoraBackground
import ar.edu.ort.vora.ui.theme.VoraCoachTheme
import ar.edu.ort.vora.ui.theme.VoraTextMuted
import ar.edu.ort.vora.ui.theme.VoraTextPrimary
import ar.edu.ort.vora.ui.theme.VoraTextSecondary
import androidx.compose.material3.Switch
import ar.edu.ort.vora.ui.components.SecondaryButton

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

// ---------- Profile ----------

// Tarjeta tocable de la lista de Profile: título, detalle y flecha.
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

@Composable
fun ProfileScreen(

    onEdit: () -> Unit,
    onGoals: () -> Unit,
    onNutrition: () -> Unit,
    onSettings: () -> Unit,
) {
    ProfileLayout(
        "Profile",
        back = TopBarBack.None,
        actions = { VoraTextButton("Settings", onSettings) },
    ) {
        InfoCard {
            Text("Alex Morgan", style = MaterialTheme.typography.titleLarge, color = VoraTextPrimary)
            Text("alex.morgan@example.com", style = MaterialTheme.typography.bodyMedium, color = VoraTextSecondary)
        }
        ProfileRow("Edit profile", "Name, email and age", onEdit)
        ProfileRow("Your goals", "Review your preferences", onGoals)
        ProfileRow("Nutrition preferences", "Diet and food restrictions", onNutrition)
        ProfileRow("Connected apps", "Manage in settings", onSettings)
    }
}

// ---------- Edit profile ----------

@Composable
fun ProfileEditScreen(onBack: () -> Unit, onSave: () -> Unit) {
    var name by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var age by rememberSaveable { mutableStateOf("") }

    ProfileLayout("Edit profile", onBack = onBack) {
        VoraTextField(name, { name = it }, "Name", placeholder = "Your name")
        VoraTextField(
            email, { email = it }, "Email", placeholder = "you@example.com",
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        )
        VoraTextField(
            age, { age = it }, "Age", placeholder = "30",
            error = if (age.isNotEmpty() && age.toIntOrNull() == null) "Enter a valid number" else null,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        )
        PrimaryButton("Save changes", onSave, Modifier.fillMaxWidth(), size = ButtonSize.Large)
    }
}

// ---------- Chips reutilizables ----------

// Opción única: el elegido se ve con selectedStyle (verde por defecto).
@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun SingleChoice(
    title: String,
    options: List<String>,
    selected: String,
    selectedStyle: ChipStyle = ChipStyle.Green,
    onSelect: (String) -> Unit,
) {
    Column(verticalArrangement = Arrangement.spacedBy(Spacing.s8)) {
        Text(title.uppercase(), style = MaterialTheme.typography.labelSmall, color = VoraTextSecondary)
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(Spacing.s8),
            verticalArrangement = Arrangement.spacedBy(Spacing.s8),
        ) {
            options.forEach {
                VoraChip(it, { onSelect(it) }, style = if (it == selected) selectedStyle else ChipStyle.Default)
            }
        }
    }
}

// Selección múltiple: los elegidos se ven con borde menta y check.
@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun MultiChoice(title: String, options: List<String>, selected: List<String>, onToggle: (String) -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(Spacing.s8)) {
        Text(title.uppercase(), style = MaterialTheme.typography.labelSmall, color = VoraTextSecondary)
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(Spacing.s8),
            verticalArrangement = Arrangement.spacedBy(Spacing.s8),
        ) {
            options.forEach {
                VoraChip(it, { onToggle(it) }, style = if (it in selected) ChipStyle.Checked else ChipStyle.Default)
            }
        }
    }
}

// ---------- Your goals ----------

@Composable
fun ProfileGoalsScreen(onBack: () -> Unit, onSave: () -> Unit) {
    var goal by rememberSaveable { mutableStateOf("Build muscle") }
    var days by rememberSaveable { mutableStateOf("3") }
    var level by rememberSaveable { mutableStateOf("Intermediate") }

    ProfileLayout("Your goals", onBack = onBack) {
        SingleChoice("Main goal", listOf("Lose weight", "Build muscle", "Stay active", "Improve health"), goal) { goal = it }
        SingleChoice("Workouts per week", listOf("2", "3", "4", "5", "6"), days) { days = it }
        SingleChoice("Experience level", listOf("Beginner", "Intermediate", "Advanced"), level) { level = it }
        PrimaryButton("Save preferences", onSave, Modifier.fillMaxWidth(), size = ButtonSize.Large)
    }
}

// ---------- Nutrition preferences ----------

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ProfileNutritionScreen(onBack: () -> Unit, onSave: () -> Unit) {
    var diet by rememberSaveable { mutableStateOf("Everything") }
    var liked by rememberSaveable { mutableStateOf(listOf("Chicken", "Rice")) }
    var avoided by rememberSaveable { mutableStateOf(listOf("Peanuts", "Shellfish")) }

    ProfileLayout("Nutrition preferences", onBack = onBack) {
        SingleChoice(
            "Dietary preference",
            listOf("Everything", "Vegetarian", "Vegan", "Pescatarian"),
            diet,
            selectedStyle = ChipStyle.Mint,
        ) { diet = it }
        MultiChoice(
            "Foods I like",
            listOf("Chicken", "Fish", "Rice", "Eggs", "Tofu", "Vegetables"),
            liked,
        ) { liked = if (it in liked) liked - it else liked + it }
        Column(verticalArrangement = Arrangement.spacedBy(Spacing.s8)) {
            Text("ALLERGIES AND DISLIKES", style = MaterialTheme.typography.labelSmall, color = VoraTextSecondary)
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(Spacing.s8),
                verticalArrangement = Arrangement.spacedBy(Spacing.s8),
            ) {
                avoided.forEach { VoraChip(it, { avoided = avoided - it }, style = ChipStyle.Removable) }
            }
        }
        PrimaryButton("Save preferences", onSave, Modifier.fillMaxWidth(), size = ButtonSize.Large)
    }
}
// ---------- Voice y Settings ----------

// Fila con interruptor: título, detalle y Switch de material3.
@Composable
private fun SwitchRow(title: String, detail: String, checked: Boolean, onChange: (Boolean) -> Unit) {
    InfoCard {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(Modifier.weight(1f)) {
                Text(title, style = MaterialTheme.typography.titleSmall, color = VoraTextPrimary)
                Text(detail, style = MaterialTheme.typography.bodySmall, color = VoraTextSecondary)
            }
            Switch(checked = checked, onCheckedChange = onChange)
        }
    }
}

@Composable
fun ProfileVoiceScreen(onBack: () -> Unit) {
    var responses by rememberSaveable { mutableStateOf(true) }
    var handsFree by rememberSaveable { mutableStateOf(false) }
    var style by rememberSaveable { mutableStateOf("Motivating") }

    ProfileLayout("VORA Coach", onBack = onBack) {
        SwitchRow("Voice responses", "VORA answers out loud", responses) { responses = it }
        SwitchRow("Hands-free mode", "Talk without tapping", handsFree) { handsFree = it }
        SingleChoice("Coaching style", listOf("Motivating", "Calm", "Direct"), style, selectedStyle = ChipStyle.Mint) { style = it }
    }
}

@Composable
fun ProfileSettingsScreen(onBack: () -> Unit, onVoice: () -> Unit, onLogout: () -> Unit) {
    var notifications by rememberSaveable { mutableStateOf(true) }

    ProfileLayout("Settings", onBack = onBack) {
        ProfileRow("VORA Coach", "Voice and AI", onVoice)
        SwitchRow("Notifications", "Reminders and weekly review", notifications) { notifications = it }
        SecondaryButton("Log out", onLogout, Modifier.fillMaxWidth(), size = ButtonSize.Large)
    }
}
// ---------- Previews ----------

@Preview(widthDp = 390, heightDp = 844)
@Composable
private fun ProfilePreview() {
    VoraCoachTheme { ProfileScreen({}, {}, {}, {}, ) }
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
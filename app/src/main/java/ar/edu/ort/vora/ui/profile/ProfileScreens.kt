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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
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

// Opción única: el elegido se ve con selectedStyle (verde por defecto).
@Composable
private fun SingleChoice(
    title: String,
    options: List<String>,
    selected: String,
    selectedStyle: ChipStyle = ChipStyle.Green,
    onSelect: (String) -> Unit,
) {
    Column(verticalArrangement = Arrangement.spacedBy(Spacing.s8)) {
        SectionLabel(title)
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
@Composable
private fun MultiChoice(title: String, options: List<String>, selected: List<String>, onToggle: (String) -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(Spacing.s8)) {
        SectionLabel(title)
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

// ---------- 01 Profile (dashboard) ----------

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
            Text("Building strength & improving fitness", style = MaterialTheme.typography.bodySmall, color = VoraTextSecondary)
            SecondaryButton("Edit profile", onEdit, Modifier.padding(top = Spacing.s12), size = ButtonSize.Small)
        }
        InfoCard {
            SectionLabel("My snapshot")
            DataRow("Goal", "Build strength & improve fitness")
            DataRow("Experience", "Intermediate")
            DataRow("Training", "4 days/week")
            DataRow("Preferred workout", "Strength training")
        }
        DarkPanel {
            Text("VORA Personalisation", style = MaterialTheme.typography.labelSmall)
            Text("VORA knows your goals", style = MaterialTheme.typography.titleMedium)
            Text(
                "Your coaching adapts to your training, nutrition and activity preferences.",
                style = MaterialTheme.typography.bodySmall,
            )
            LimeButton("Review preferences", onGoals, size = ButtonSize.Small)
        }
        ProfileRow("Your Goals", "Build strength & improve fitness", onGoals)
        ProfileRow("Your Preferences", "Training, nutrition and coaching", onNutrition)
        ProfileRow("Connected Data", "Health & activity data", onSettings)
        ProfileRow("Account", "Subscription, notifications & privacy", onSettings)
    }
}

// ---------- 02 Edit profile ----------

@Composable
fun ProfileEditScreen(onBack: () -> Unit, onSave: () -> Unit) {
    var first by rememberSaveable { mutableStateOf("Alex") }
    var last by rememberSaveable { mutableStateOf("Morgan") }
    var birth by rememberSaveable { mutableStateOf("14 March 1995") }
    var height by rememberSaveable { mutableStateOf("178") }
    var weight by rememberSaveable { mutableStateOf("76") }
    var gender by rememberSaveable { mutableStateOf("Prefer not to say") }

    ProfileLayout("Edit profile", onBack = onBack) {
        VoraTextField(first, { first = it }, "First name")
        VoraTextField(last, { last = it }, "Last name")
        VoraTextField(birth, { birth = it }, "Date of birth")
        VoraTextField(
            height, { height = it }, "Height (cm)",
            error = if (height.isNotEmpty() && height.toIntOrNull() == null) "Enter a valid number" else null,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        )
        VoraTextField(
            weight, { weight = it }, "Weight (kg)",
            error = if (weight.isNotEmpty() && weight.toIntOrNull() == null) "Enter a valid number" else null,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        )
        SingleChoice("Gender", listOf("Female", "Male", "Non-binary", "Prefer not to say"), gender) { gender = it }
        CoachNote("Used to personalise your recommendations.")
        PrimaryButton("Save changes", onSave, Modifier.fillMaxWidth(), size = ButtonSize.Large)
        Text(
            "Your information is used to personalise your VORA experience.",
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
    var goal by rememberSaveable { mutableStateOf("Build strength") }
    var days by rememberSaveable { mutableStateOf("4 days") }
    var workout by rememberSaveable { mutableStateOf("Strength") }

    ProfileLayout("Your goals", onBack = onBack) {
        Subtitle("Tell VORA what you're working towards. Your coaching adapts around this.")
        SectionLabel("Primary goal — what's your main goal?")
        OptionCard("Build strength", "Focus on weight training & mechanical load", goal == "Build strength") { goal = "Build strength" }
        OptionCard("Improve fitness", "Condition cardiovascular & energy systems", goal == "Improve fitness") { goal = "Improve fitness" }
        OptionCard("Lose body fat", "Reduce body fat with training and nutrition", goal == "Lose body fat") { goal = "Lose body fat" }
        OptionCard("Build healthy habits", "Stay consistent with a sustainable routine", goal == "Build healthy habits") { goal = "Build healthy habits" }
        SingleChoice("Training frequency", listOf("3 days", "4 days", "5 days", "6+ days"), days) { days = it }
        SingleChoice(
            "Workout preference",
            listOf("Strength", "Cardio", "HIIT", "Mobility", "Mixed"),
            workout,
            selectedStyle = ChipStyle.Mint,
        ) { workout = it }
        SectionLabel("Coaching style — how should VORA coach you?")
        OptionCard("Balanced", "Practical guidance with gentle motivation", true) {}
        PrimaryButton("Save preferences", onSave, Modifier.fillMaxWidth(), size = ButtonSize.Large)
    }
}

// ---------- 04 Nutrition preferences ----------

@Composable
fun ProfileNutritionScreen(onBack: () -> Unit, onSave: () -> Unit) {
    var diet by rememberSaveable { mutableStateOf("Everything") }
    var liked by rememberSaveable {
        mutableStateOf(listOf("Chicken", "Eggs", "Fish", "Rice", "Pasta", "Greek yoghurt", "Vegetables", "Fruit"))
    }
    var avoided by rememberSaveable { mutableStateOf(listOf("Peanuts", "Shellfish", "Mushrooms")) }
    var newItem by rememberSaveable { mutableStateOf("") }
    var matters by rememberSaveable { mutableStateOf(listOf("Hit protein targets", "Eat more consistently")) }

    ProfileLayout("Nutrition preferences", onBack = onBack) {
        Subtitle("Help VORA make food recommendations that actually fit your lifestyle.")
        SingleChoice(
            "Dietary preference",
            listOf("Everything", "Vegetarian", "Vegan", "Pescatarian", "Other"),
            diet,
            selectedStyle = ChipStyle.Mint,
        ) { diet = it }
        MultiChoice(
            "Foods you enjoy",
            listOf("Chicken", "Eggs", "Fish", "Rice", "Pasta", "Greek yoghurt", "Vegetables", "Fruit"),
            liked,
        ) { liked = if (it in liked) liked - it else liked + it }
        Column(verticalArrangement = Arrangement.spacedBy(Spacing.s8)) {
            SectionLabel("Foods to avoid")
            VoraTextField(newItem, { newItem = it }, "Add an ingredient", placeholder = "Add an ingredient...")
            SecondaryButton(
                "Add",
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
            SectionLabel("Meal preferences")
            DataRow("Meals per day", "3 meals + 1 snack")
            DataRow("Preferred breakfast", "Quick & high protein")
            DataRow("Preferred dinner", "Balanced")
        }
        MultiChoice(
            "What matters most to you?",
            listOf("Hit protein targets", "Eat more consistently"),
            matters,
        ) { matters = if (it in matters) matters - it else matters + it }
        PrimaryButton("Save preferences", onSave, Modifier.fillMaxWidth(), size = ButtonSize.Large)
    }
}

// ---------- 05 VORA Coach (Voice & AI settings) ----------

@Composable
fun ProfileVoiceScreen(onBack: () -> Unit) {
    val demo = rememberDemoNotice()
    var style by rememberSaveable { mutableStateOf("Balanced") }
    var aloud by rememberSaveable { mutableStateOf(true) }
    var autoPlay by rememberSaveable { mutableStateOf(true) }
    var interrupt by rememberSaveable { mutableStateOf(true) }
    var response by rememberSaveable { mutableStateOf("Balanced") }
    var voiceFeedback by rememberSaveable { mutableStateOf(true) }
    var haptic by rememberSaveable { mutableStateOf(true) }

    ProfileLayout("VORA Coach", onBack = onBack) {
        Subtitle("Choose how VORA communicates and coaches you.")
        SingleChoice("Coaching style", listOf("Supportive", "Balanced", "Direct"), style) { style = it }
        SectionLabel("Active voice")
        InfoCard {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("VORA Voice 01", Modifier.weight(1f), style = MaterialTheme.typography.titleSmall, color = VoraTextPrimary)
                VoraTextButton("Preview", demo)
            }
        }
        SectionLabel("Speaking behaviour")
        SwitchRow("Speak responses aloud", null, aloud) { aloud = it }
        SwitchRow("Auto-play coaching", null, autoPlay) { autoPlay = it }
        SwitchRow("Allow interruptions", "Interrupt VORA anytime while it's speaking.", interrupt) { interrupt = it }
        SectionLabel("Response style")
        OptionCard("Concise", "Quick answers", response == "Concise") { response = "Concise" }
        OptionCard("Balanced", "Useful detail without overwhelming", response == "Balanced") { response = "Balanced" }
        OptionCard("Detailed", "More explanation and context", response == "Detailed") { response = "Detailed" }
        SwitchRow("Voice feedback", null, voiceFeedback) { voiceFeedback = it }
        SwitchRow("Haptic feedback", null, haptic) { haptic = it }
        InfoCard {
            Text("Your control matters", style = MaterialTheme.typography.titleSmall, color = VoraTextPrimary)
            Text(
                "Manage microphone permissions and connected data from Settings.",
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

    ProfileLayout("Settings", onBack = onBack) {
        SectionLabel("Account")
        ProfileRow("Alex Morgan", "alex@morgan.com", demo)
        ProfileRow("VORA Premium", "Renews 14 June 2026", demo)
        ProfileRow("Manage account", "Subscription and details", demo)
        SectionLabel("App preferences")
        ProfileRow("VORA Coach", "Voice and AI settings", onVoice)
        ProfileRow("Notifications", "Workout and meal reminders", demo)
        ProfileRow("Units", "Metric (kg, cm, km)", demo)
        ProfileRow("Appearance", "System default", demo)
        SectionLabel("Connected data")
        ProfileRow("Health & Activity", "Apple Watch & Health connected", demo)
        SectionLabel("Privacy & security")
        ProfileRow("Privacy Policy", "How your data is used", demo)
        ProfileRow("Permissions & Data Controls", "Microphone and connected data", demo)
        SectionLabel("Support")
        ProfileRow("Help Centre", "Guides and answers", demo)
        ProfileRow("Contact Support", "Talk to the team", demo)
        SecondaryButton("Log out", onLogout, Modifier.fillMaxWidth(), size = ButtonSize.Large)
        Text(
            "VORA v1.0.0",
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
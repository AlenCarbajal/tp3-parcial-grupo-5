package ar.edu.ort.vora.ui.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
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
import ar.edu.ort.vora.ui.components.InfoCard
import ar.edu.ort.vora.ui.components.PrimaryButton
import ar.edu.ort.vora.ui.components.TopBarBack
import ar.edu.ort.vora.ui.components.VoraTextButton
import ar.edu.ort.vora.ui.components.VoraTextField
import ar.edu.ort.vora.ui.components.VoraTopBar
import ar.edu.ort.vora.ui.theme.Spacing
import ar.edu.ort.vora.ui.theme.VoraBackground
import ar.edu.ort.vora.ui.theme.VoraCoachTheme
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
    onBack: () -> Unit,
    onEdit: () -> Unit,
    onGoals: () -> Unit,
    onNutrition: () -> Unit,
    onSettings: () -> Unit,
) {
    ProfileLayout(
        "Profile",
        onBack = onBack,
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

@Preview(widthDp = 390, heightDp = 844)
@Composable
private fun ProfilePreview() {
    VoraCoachTheme { ProfileScreen({}, {}, {}, {}, {}) }
}
@Preview(widthDp = 390, heightDp = 844)
@Composable
private fun ProfileEditPreview() {
    VoraCoachTheme { ProfileEditScreen({}, {}) }
}
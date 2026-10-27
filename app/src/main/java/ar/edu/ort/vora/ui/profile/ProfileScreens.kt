package ar.edu.ort.vora.ui.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import ar.edu.ort.vora.ui.components.ButtonSize
import ar.edu.ort.vora.ui.components.PrimaryButton
import ar.edu.ort.vora.ui.components.TopBarBack
import ar.edu.ort.vora.ui.components.VoraTextField
import ar.edu.ort.vora.ui.components.VoraTopBar
import ar.edu.ort.vora.ui.theme.Spacing
import ar.edu.ort.vora.ui.theme.VoraBackground
import ar.edu.ort.vora.ui.theme.VoraCoachTheme

// Encabezado arriba y contenido con scroll. Mismo patrón que CoachLayout.
@Composable
private fun ProfileLayout(
    title: String,
    back: TopBarBack = TopBarBack.Circle,
    onBack: () -> Unit = {},
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(Modifier.fillMaxSize().background(VoraBackground).statusBarsPadding()) {
        VoraTopBar(title, back = back, onBack = onBack)
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

@Preview(widthDp = 390, heightDp = 844)
@Composable
private fun ProfileEditPreview() {
    VoraCoachTheme { ProfileEditScreen({}, {}) }
}
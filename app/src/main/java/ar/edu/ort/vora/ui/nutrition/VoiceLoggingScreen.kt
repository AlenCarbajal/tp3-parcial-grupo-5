package ar.edu.ort.vora.ui.nutrition

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import ar.edu.ort.vora.ui.components.ButtonSize
import ar.edu.ort.vora.ui.components.DarkTranscriptCard
import ar.edu.ort.vora.ui.components.PrimaryButton
import ar.edu.ort.vora.ui.components.TopBarBack
import ar.edu.ort.vora.ui.components.VoiceOrb
import ar.edu.ort.vora.ui.components.VoraTextButton
import ar.edu.ort.vora.ui.components.VoraTopBar
import ar.edu.ort.vora.ui.theme.Spacing
import ar.edu.ort.vora.ui.theme.VoraBackground
import ar.edu.ort.vora.ui.theme.VoraMint
import ar.edu.ort.vora.ui.theme.VoraTextSecondary

@Composable
fun VoiceLoggingScreen(
    onBack: () -> Unit,
    onDoneSpeaking: () -> Unit,
    onTypeInstead: () -> Unit,
) {
    Column(
        Modifier
            .fillMaxSize()
            .background(VoraBackground)
    ) {
        VoraTopBar(title = "Voice Logging", back = TopBarBack.Plain, onBack = onBack)
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
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(Spacing.s24),
            ) {
                Text(
                    text = "HABLANDO CON VORA AI",
                    style = MaterialTheme.typography.labelSmall,
                    color = VoraMint,
                )

                VoiceOrb(listening = true)

                DarkTranscriptCard(
                    text = "“Tuve un omelette de 2 huevos con espinaca y una tostada de masa madre con palta para el almuerzo”",
                    label = "Transcripción en vivo",
                )

                Spacer(Modifier.height(Spacing.s12))

                PrimaryButton(
                    text = "Done Speaking",
                    onClick = onDoneSpeaking,
                    modifier = Modifier.fillMaxWidth(),
                    size = ButtonSize.Large,
                )

                VoraTextButton(
                    text = "Escribir manualmente (Type instead)",
                    onClick = onTypeInstead,
                    modifier = Modifier.fillMaxWidth(),
                    color = VoraTextSecondary,
                )
            }
        }
    }
}

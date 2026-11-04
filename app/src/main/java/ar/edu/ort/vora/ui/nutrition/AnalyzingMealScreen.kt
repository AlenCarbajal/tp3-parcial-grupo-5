package ar.edu.ort.vora.ui.nutrition

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ar.edu.ort.vora.R
import ar.edu.ort.vora.ui.components.ButtonSize
import ar.edu.ort.vora.ui.components.DarkPanel
import ar.edu.ort.vora.ui.components.PrimaryButton
import ar.edu.ort.vora.ui.components.TopBarBack
import ar.edu.ort.vora.ui.components.VoraOrb
import ar.edu.ort.vora.ui.components.VoraTextButton
import ar.edu.ort.vora.ui.components.VoraTopBar
import ar.edu.ort.vora.ui.theme.Spacing
import ar.edu.ort.vora.ui.theme.VoraBackground
import ar.edu.ort.vora.ui.theme.VoraError
import ar.edu.ort.vora.ui.theme.VoraGreen
import ar.edu.ort.vora.ui.theme.VoraMint
import ar.edu.ort.vora.ui.theme.VoraSurface
import kotlinx.coroutines.delay

@Composable
fun AnalyzingMealScreen(
    onBack: () -> Unit,
    onAnalyzed: () -> Unit,
) {
    // Simula una breve demora de procesamiento (2.5s) para avanzar automáticamente
    LaunchedEffect(Unit) {
        delay(2500)
        onAnalyzed()
    }

    Column(
        Modifier
            .fillMaxSize()
            .background(VoraBackground)
    ) {
        VoraTopBar(title = "VORA AI", back = TopBarBack.Plain, onBack = onBack)
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
                    text = "ANALIZANDO REGISTRO",
                    style = MaterialTheme.typography.labelSmall,
                    color = VoraMint,
                )

                VoraOrb(size = 170.dp, glowAlpha = 0.5f, ripple = true) {
                    Icon(
                        painter = painterResource(R.drawable.ic_sparkles),
                        contentDescription = null,
                        tint = VoraSurface,
                        modifier = Modifier.size(48.dp),
                    )
                }

                DarkPanel(
                    padding = Spacing.s16,
                    verticalArrangement = Arrangement.spacedBy(Spacing.s12),
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(Spacing.s12),
                    ) {
                        CircularProgressIndicator(
                            color = VoraGreen,
                            modifier = Modifier.size(24.dp),
                            strokeWidth = 2.dp,
                        )
                        Text(
                            text = "Identificando ingredientes y calculando valor nutricional...",
                            style = MaterialTheme.typography.bodyMedium,
                            color = VoraSurface,
                        )
                    }
                }

                Spacer(Modifier.height(Spacing.s12))

                PrimaryButton(
                    text = "Ver resultado (Continuar)",
                    onClick = onAnalyzed,
                    modifier = Modifier.fillMaxWidth(),
                    size = ButtonSize.Large,
                )

                VoraTextButton(
                    text = "Cancelar",
                    onClick = onBack,
                    modifier = Modifier.fillMaxWidth(),
                    color = VoraError,
                )
            }
        }
    }
}

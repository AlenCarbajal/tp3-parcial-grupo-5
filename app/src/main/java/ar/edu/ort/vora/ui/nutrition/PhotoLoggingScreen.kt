package ar.edu.ort.vora.ui.nutrition

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ar.edu.ort.vora.R
import ar.edu.ort.vora.ui.components.ButtonSize
import ar.edu.ort.vora.ui.components.DarkPanel
import ar.edu.ort.vora.ui.components.PrimaryButton
import ar.edu.ort.vora.ui.components.SecondaryButton
import ar.edu.ort.vora.ui.components.TopBarBack
import ar.edu.ort.vora.ui.components.VoraTopBar
import ar.edu.ort.vora.ui.theme.Spacing
import ar.edu.ort.vora.ui.theme.VoraBackground
import ar.edu.ort.vora.ui.theme.VoraGreen
import ar.edu.ort.vora.ui.theme.VoraMint
import ar.edu.ort.vora.ui.theme.VoraSurface

@Composable
fun PhotoLoggingScreen(
    onBack: () -> Unit,
    onReview: () -> Unit,
) {
    Column(
        Modifier
            .fillMaxSize()
            .background(VoraBackground)
    ) {
        VoraTopBar(title = "Analyse Meal", back = TopBarBack.Plain, onBack = onBack)
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
                verticalArrangement = Arrangement.spacedBy(Spacing.s20),
            ) {
                Text(
                    text = "VISOR DE CÁMARA E IA",
                    style = MaterialTheme.typography.labelSmall,
                    color = VoraMint,
                )

                // Simulación del visor de cámara con marco decorativo
                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(240.dp)
                        .clip(MaterialTheme.shapes.large)
                        .background(VoraGreen)
                        .border(2.dp, VoraMint, MaterialTheme.shapes.large),
                    contentAlignment = Alignment.Center,
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(Spacing.s12),
                    ) {
                        Box(
                            Modifier
                                .size(64.dp)
                                .background(VoraSurface.copy(alpha = 0.15f), MaterialTheme.shapes.medium),
                            contentAlignment = Alignment.Center,
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.ic_camera),
                                contentDescription = null,
                                tint = VoraSurface,
                                modifier = Modifier.size(36.dp),
                            )
                        }
                        Text(
                            text = "Plato detectado: Ensalada mediterránea",
                            style = MaterialTheme.typography.titleMedium,
                            color = VoraSurface,
                        )
                        Text(
                            text = "Enfoque y luz óptimos para análisis",
                            style = MaterialTheme.typography.bodySmall,
                            color = VoraMint,
                        )
                    }
                }

                DarkPanel(
                    padding = Spacing.s16,
                    verticalArrangement = Arrangement.spacedBy(Spacing.s8),
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(Spacing.s8),
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_sparkles),
                            contentDescription = null,
                            tint = VoraMint,
                            modifier = Modifier.size(20.dp),
                        )
                        Text(
                            text = "VORA AI identificó 4 ingredientes probables",
                            style = MaterialTheme.typography.bodyMedium,
                            color = VoraSurface,
                        )
                    }
                }

                Spacer(Modifier.height(Spacing.s8))

                PrimaryButton(
                    text = "Review & Add",
                    onClick = onReview,
                    modifier = Modifier.fillMaxWidth(),
                    size = ButtonSize.Large,
                )

                SecondaryButton(
                    text = "Retake Photo",
                    onClick = { /* Simula volver a tomar la foto */ },
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }
    }
}

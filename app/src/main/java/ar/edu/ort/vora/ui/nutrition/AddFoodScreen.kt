package ar.edu.ort.vora.ui.nutrition

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ar.edu.ort.vora.R
import ar.edu.ort.vora.ui.components.InfoCard
import ar.edu.ort.vora.ui.components.TopBarBack
import ar.edu.ort.vora.ui.components.VoraTopBar
import ar.edu.ort.vora.ui.theme.Spacing
import ar.edu.ort.vora.ui.theme.VoraBackground
import ar.edu.ort.vora.ui.theme.VoraGreen
import ar.edu.ort.vora.ui.theme.VoraMint
import ar.edu.ort.vora.ui.theme.VoraTextPrimary
import ar.edu.ort.vora.ui.theme.VoraTextSecondary

@Composable
fun AddFoodScreen(
    onBack: () -> Unit,
    onVoice: () -> Unit,
    onPhoto: () -> Unit,
    onManual: () -> Unit,
) {
    Column(
        Modifier
            .fillMaxSize()
            .background(VoraBackground)
    ) {
        VoraTopBar(title = "Add Food", back = TopBarBack.Plain, onBack = onBack)
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
                    .padding(horizontal = Spacing.screen, vertical = Spacing.s16),
                verticalArrangement = Arrangement.spacedBy(Spacing.s12),
            ) {
                Text(
                    text = "¿CÓMO PREFERÍS REGISTRAR?",
                    style = MaterialTheme.typography.labelSmall,
                    color = VoraMint,
                )

                OptionCard(
                    title = "Voice Logging",
                    description = "Dictá lo que comiste y VORA AI calculará los nutrientes",
                    iconRes = R.drawable.ic_mic,
                    onClick = onVoice,
                )

                OptionCard(
                    title = "Scan Photo",
                    description = "Sacale una foto a tu plato para identificar la comida",
                    iconRes = R.drawable.ic_camera,
                    onClick = onPhoto,
                )

                OptionCard(
                    title = "Registro manual",
                    description = "Ingresá nombre, fecha, calorías y macros manualmente",
                    iconRes = R.drawable.ic_plus,
                    onClick = onManual,
                )
            }
        }
    }
}

@Composable
private fun OptionCard(
    title: String,
    description: String,
    iconRes: Int,
    onClick: () -> Unit,
) {
    InfoCard(
        modifier = Modifier.clickable(onClick = onClick),
        shape = MaterialTheme.shapes.medium,
        padding = Spacing.s16,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Spacing.s12),
        ) {
            Box(
                Modifier
                    .size(44.dp)
                    .background(VoraGreen, MaterialTheme.shapes.small),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    painter = painterResource(iconRes),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.surface,
                    modifier = Modifier.size(24.dp),
                )
            }
            Column(Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    color = VoraTextPrimary,
                )
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodySmall,
                    color = VoraTextSecondary,
                )
            }
            Icon(
                painter = painterResource(R.drawable.ic_arrow_right),
                contentDescription = null,
                tint = VoraTextSecondary,
                modifier = Modifier.size(16.dp),
            )
        }
    }
}

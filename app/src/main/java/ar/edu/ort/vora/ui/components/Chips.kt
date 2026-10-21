package ar.edu.ort.vora.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ar.edu.ort.vora.R
import ar.edu.ort.vora.ui.theme.PillShape
import ar.edu.ort.vora.ui.theme.Spacing
import ar.edu.ort.vora.ui.theme.VoraBorder
import ar.edu.ort.vora.ui.theme.VoraDestructive
import ar.edu.ort.vora.ui.theme.VoraGreen
import ar.edu.ort.vora.ui.theme.VoraMint
import ar.edu.ort.vora.ui.theme.VoraSurface
import ar.edu.ort.vora.ui.theme.VoraTextPrimary

enum class ChipStyle {
    Default,    // blanco con borde
    Mint,       // opción única elegida (Dietary preference: "Everything")
    Green,      // opción única elegida (dificultad del entrenamiento: "Good")
    Checked,    // selección múltiple: borde menta y check ("Chicken ✓")
    Removable,  // ítem a quitar: borde gris y círculo con x roja ("Peanuts ⊗")
}

// Chip de Figma: radio 100, borde 1, padding 8/14, separación 6 hasta el ícono, texto 13 (500, elegido 600).
@Composable
fun VoraChip(text: String, onClick: () -> Unit, modifier: Modifier = Modifier, style: ChipStyle = ChipStyle.Default) {
    val (container, content) = when (style) {
        ChipStyle.Mint -> VoraMint to VoraTextPrimary
        ChipStyle.Green -> VoraGreen to VoraSurface
        else -> VoraSurface to VoraTextPrimary
    }
    val border = when (style) {
        ChipStyle.Green -> null
        ChipStyle.Checked -> BorderStroke(1.dp, VoraMint)
        else -> BorderStroke(1.dp, VoraBorder)
    }
    Surface(onClick = onClick, modifier = modifier, shape = PillShape, color = container, contentColor = content, border = border) {
        Row(
            Modifier.padding(horizontal = Spacing.s14, vertical = Spacing.s8),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Spacing.s6),
        ) {
            Text(
                text,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = if (style == ChipStyle.Mint || style == ChipStyle.Green) FontWeight.SemiBold else FontWeight.Medium,
            )
            when (style) {
                ChipStyle.Checked -> Icon(painterResource(R.drawable.ic_check), contentDescription = null, tint = VoraGreen)
                ChipStyle.Removable -> Icon(painterResource(R.drawable.ic_circle_x), contentDescription = "Quitar", tint = VoraDestructive)
                else -> Unit
            }
        }
    }
}


package ar.edu.ort.vora.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ar.edu.ort.vora.R
import ar.edu.ort.vora.ui.theme.Spacing
import ar.edu.ort.vora.ui.theme.VoraBorder
import ar.edu.ort.vora.ui.theme.VoraGreen
import ar.edu.ort.vora.ui.theme.VoraMint
import ar.edu.ort.vora.ui.theme.VoraMintTranslucent
import ar.edu.ort.vora.ui.theme.VoraSurface
import ar.edu.ort.vora.ui.theme.VoraTextPrimary

// Tarjeta blanca: radio 16 y padding 16. Filas de comida: radio 12 y padding 12.
@Composable
fun InfoCard(
    modifier: Modifier = Modifier,
    shape: Shape = MaterialTheme.shapes.medium,
    padding: Dp = Spacing.cardPadding,
    content: @Composable ColumnScope.() -> Unit,
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = shape,
        color = VoraSurface,
        border = BorderStroke(1.dp, VoraBorder),
    ) {
        Column(Modifier.padding(padding), content = content)
    }
}

// Panel destacado: radio 20, padding 20 y separación de 16 entre grupos. El de Nutrition usa padding 16.
@Composable
fun DarkPanel(
    modifier: Modifier = Modifier,
    padding: Dp = Spacing.s20,
    verticalArrangement: Arrangement.Vertical = Arrangement.spacedBy(Spacing.s16),
    content: @Composable ColumnScope.() -> Unit,
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
        color = VoraGreen,
        contentColor = VoraSurface,
    ) {
        Column(Modifier.padding(padding), verticalArrangement = verticalArrangement, content = content)
    }
}

// Recomendación breve: menta al 12,16 %, borde menta, texto verde, radio 12 y padding 12.
@Composable
fun CoachNote(text: String, modifier: Modifier = Modifier, @DrawableRes icon: Int = R.drawable.ic_sparkles) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.small,
        color = VoraMintTranslucent,
        contentColor = VoraGreen,
        border = BorderStroke(1.dp, VoraMint.copy(alpha = 0.3f)),
    ) {
        Row(
            Modifier.padding(Spacing.s12),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Spacing.s8),
        ) {
            Icon(painterResource(icon), contentDescription = null)
            Text(text, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

// Consejo destacado: panel verde con título menta y texto blanco, radio 16 y padding 16.
@Composable
fun AdviceCard(title: String, text: String, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        color = VoraGreen,
        contentColor = VoraSurface,
    ) {
        Column(Modifier.padding(Spacing.s16), verticalArrangement = Arrangement.spacedBy(Spacing.s8)) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(Spacing.s8)) {
                Icon(painterResource(R.drawable.ic_sparkles), contentDescription = null, tint = VoraMint)
                Text(title.uppercase(), style = MaterialTheme.typography.labelSmall, color = VoraMint)
            }
            Text(text, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

// Insight con orbe (Completed): menta translúcida, radio 16 y padding 16.
@Composable
fun InsightCard(title: String, text: String, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        color = VoraMintTranslucent,
        contentColor = VoraTextPrimary,
    ) {
        Column(Modifier.padding(Spacing.s16), verticalArrangement = Arrangement.spacedBy(Spacing.s8)) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(Spacing.s8)) {
                VoraOrb(size = 16.dp)
                Text(title.uppercase(), style = MaterialTheme.typography.labelLarge, color = VoraGreen)
            }
            Text(text, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

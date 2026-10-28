package ar.edu.ort.vora.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.semantics.ProgressBarRangeInfo
import androidx.compose.ui.semantics.progressBarRangeInfo
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ar.edu.ort.vora.ui.theme.PillShape
import ar.edu.ort.vora.ui.theme.Spacing
import ar.edu.ort.vora.ui.theme.VoraBorder
import ar.edu.ort.vora.ui.theme.VoraError
import ar.edu.ort.vora.ui.theme.VoraGreen
import ar.edu.ort.vora.ui.theme.VoraMintTranslucent
import ar.edu.ort.vora.ui.theme.VoraSurface
import ar.edu.ort.vora.ui.theme.VoraTextPrimary
import ar.edu.ort.vora.ui.theme.VoraTextSecondary
import ar.edu.ort.vora.ui.theme.VoraWhiteTranslucent

// Pista completa y relleno superpuesto, como en Figma. Barras de 4. Sobre fondo oscuro la pista va blanca translúcida.
@Composable
fun ProgressBar(
    progress: Float,
    modifier: Modifier = Modifier,
    color: Color = VoraGreen,
    height: Dp = 4.dp,
    trackColor: Color = VoraBorder,
) {
    val value = progress.coerceIn(0f, 1f)
    Box(
        modifier
            .fillMaxWidth()
            .height(height)
            .background(trackColor, PillShape)
            .semantics { progressBarRangeInfo = ProgressBarRangeInfo(value, 0f..1f) }
    ) {
        if (value > 0f) {
            Box(
                Modifier
                    .fillMaxWidth(value)
                    .fillMaxHeight()
                    .background(color, PillShape)
            )
        }
    }
}

// Macronutriente: nombre a la izquierda, valor a la derecha y barra debajo.
@Composable
fun MacroProgress(label: String, value: String, progress: Float, color: Color, modifier: Modifier = Modifier) {
    Column(modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(Spacing.s6)) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(label, style = MaterialTheme.typography.bodyMedium, color = VoraTextSecondary)
            Text(value, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold, color = VoraTextPrimary)
        }
        ProgressBar(progress, color = color)
    }
}

// Anillo de avance global: 72 en Figma, pista completa y arco superpuesto.
@Composable
fun ProgressRing(
    progress: Float,
    modifier: Modifier = Modifier,
    size: Dp = 72.dp,
    strokeWidth: Dp = 7.dp,
    color: Color = VoraGreen,
    content: @Composable BoxScope.() -> Unit = {},
) {
    val value = progress.coerceIn(0f, 1f)
    Box(
        modifier
            .size(size)
            .semantics { progressBarRangeInfo = ProgressBarRangeInfo(value, 0f..1f) },
        contentAlignment = Alignment.Center,
    ) {
        Canvas(Modifier.fillMaxSize()) {
            val stroke = strokeWidth.toPx()
            val topLeft = Offset(stroke / 2, stroke / 2)
            val arcSize = Size(this.size.width - stroke, this.size.height - stroke)
            drawArc(VoraBorder, 0f, 360f, false, topLeft, arcSize, style = Stroke(stroke))
            if (value > 0f) {
                drawArc(color, -90f, 360f * value, false, topLeft, arcSize, style = Stroke(stroke, cap = StrokeCap.Round))
            }
        }
        content()
    }
}

// Etiqueta compacta de disponibilidad ("ONLINE"): radio 100. dark = sobre DarkPanel ("AI ONLINE" de Home).
@Composable
fun StatusTag(text: String, modifier: Modifier = Modifier, dark: Boolean = false) {
    Text(
        text.uppercase(),
        modifier = modifier
            .background(if (dark) VoraWhiteTranslucent else VoraMintTranslucent, PillShape)
            .padding(horizontal = Spacing.s10, vertical = Spacing.s4),
        style = MaterialTheme.typography.labelSmall,
        color = if (dark) VoraSurface else VoraGreen,
    )
}

// Señal de estado de 8 (rojo = "Not started").
@Composable
fun StatusDot(modifier: Modifier = Modifier, color: Color = VoraError) {
    Box(modifier.size(8.dp).background(color, PillShape))
}

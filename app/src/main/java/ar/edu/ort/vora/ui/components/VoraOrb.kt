package ar.edu.ort.vora.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import ar.edu.ort.vora.ui.theme.OrbCoreBrush
import ar.edu.ort.vora.ui.theme.OrbGlowBrush
import ar.edu.ort.vora.ui.theme.OrbRingBrush
import ar.edu.ort.vora.ui.theme.VoraMint

// Proporciones de Figma: anillo 89 % (aro de 7 sobre 120, con separación hasta el núcleo) y núcleo 75 %.
// La sombra del núcleo no escala con el orbe.
@Composable
fun VoraOrb(
    modifier: Modifier = Modifier,
    size: Dp = 120.dp,
    glowAlpha: Float = 0.2f,
    content: @Composable BoxScope.() -> Unit = {},
) {
    Box(modifier.size(size), contentAlignment = Alignment.Center) {
        Box(
            Modifier
                .size(size)
                .alpha(glowAlpha)
                .background(OrbGlowBrush, CircleShape)
        )
        Box(
            Modifier
                .size(size * 107 / 120)
                .alpha(0.8f)
                .drawBehind {
                    val stroke = this.size.minDimension * 7 / 107
                    drawCircle(
                        OrbRingBrush,
                        radius = (this.size.minDimension - stroke) / 2,
                        style = Stroke(stroke),
                    )
                }
        )
        Box(
            Modifier
                .size(size * 90 / 120)
                .dropShadow(
                    CircleShape,
                    Shadow(radius = 24.dp, color = VoraMint, offset = DpOffset(0.dp, 8.dp), alpha = 0.149f),
                )
                .background(OrbCoreBrush, CircleShape),
            contentAlignment = Alignment.Center,
            content = content,
        )
    }
}

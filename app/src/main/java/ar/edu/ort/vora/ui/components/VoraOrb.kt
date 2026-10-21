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
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import ar.edu.ort.vora.ui.theme.OrbCoreBrush
import ar.edu.ort.vora.ui.theme.OrbGlowBrush
import ar.edu.ort.vora.ui.theme.OrbRingBrush
import ar.edu.ort.vora.ui.theme.VoraMint

// Proporciones de Figma con el orbe de 120: anillo 107 y núcleo 90.
@Composable
fun VoraOrb(
    modifier: Modifier = Modifier,
    size: Dp = 120.dp,
    content: @Composable BoxScope.() -> Unit = {},
) {
    Box(modifier.size(size), contentAlignment = Alignment.Center) {
        Box(
            Modifier
                .size(size)
                .alpha(0.2f)
                .background(OrbGlowBrush, CircleShape)
        )
        Box(
            Modifier
                .size(size * 107 / 120)
                .alpha(0.8f)
                .background(OrbRingBrush, CircleShape)
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

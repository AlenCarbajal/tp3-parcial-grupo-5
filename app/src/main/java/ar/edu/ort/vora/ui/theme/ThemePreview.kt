package ar.edu.ort.vora.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ar.edu.ort.vora.ui.components.VoraOrb

// Catálogo del tema para comparar con Figma. No forma parte de la navegación.
@Preview(showBackground = true, widthDp = 390, heightDp = 1300)
@Composable
fun ThemeCatalogPreview() {
    VoraCoachTheme {
        Column(
            Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .verticalScroll(rememberScrollState())
                .padding(Spacing.screen),
            verticalArrangement = Arrangement.spacedBy(Spacing.groupGap),
        ) {
            Text("Colores", style = MaterialTheme.typography.titleLarge)
            listOf(
                "Primary" to VoraGreen, "Mint" to VoraMint, "Lime" to VoraLime,
                "Surface" to VoraSurface, "Background" to VoraBackground, "Border" to VoraBorder,
                "Text primary" to VoraTextPrimary, "Text secondary" to VoraTextSecondary,
                "Text muted" to VoraTextMuted, "Error" to VoraError, "Success" to VoraSuccess,
                "Mint 12%" to VoraMintTranslucent,
            ).chunked(3).forEach { row ->
                Row(horizontalArrangement = Arrangement.spacedBy(Spacing.xs)) {
                    row.forEach { (name, color) -> Swatch(name, color, Modifier.weight(1f)) }
                }
            }

            Text("Tipografía", style = MaterialTheme.typography.titleLarge)
            with(MaterialTheme.typography) {
                listOf(
                    "displaySmall 32/700" to displaySmall, "headlineSmall 24/600" to headlineSmall,
                    "titleLarge 20/700" to titleLarge, "titleMedium 18/600" to titleMedium,
                    "bodyMedium 13/400" to bodyMedium, "labelLarge 14/600" to labelLarge,
                    "labelMedium 11/600" to labelMedium, "LABELSMALL 10/600" to labelSmall,
                )
            }.forEach { (name, style) -> Text(name, style = style) }

            Text("Formas", style = MaterialTheme.typography.titleLarge)
            Row(horizontalArrangement = Arrangement.spacedBy(Spacing.xs)) {
                with(MaterialTheme.shapes) {
                    listOf("8" to extraSmall, "12" to small, "16" to medium, "20" to large, "píldora" to PillShape)
                }.forEach { (name, shape) -> ShapeSample(name, shape, Modifier.weight(1f)) }
            }

            Text("Orbe VORA", style = MaterialTheme.typography.titleLarge)
            Row(
                horizontalArrangement = Arrangement.spacedBy(Spacing.xl),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                VoraOrb()
                VoraOrb(size = 16.dp)
            }
        }
    }
}

@Composable
private fun Swatch(name: String, color: Color, modifier: Modifier) {
    Column(modifier) {
        Box(
            Modifier
                .fillMaxWidth()
                .height(40.dp)
                .background(color, MaterialTheme.shapes.small)
                .border(1.dp, VoraBorder, MaterialTheme.shapes.small)
        )
        Text(name, style = MaterialTheme.typography.labelMedium, color = VoraTextSecondary)
    }
}

@Composable
private fun ShapeSample(name: String, shape: Shape, modifier: Modifier) {
    Box(
        modifier
            .size(56.dp)
            .background(VoraGreen, shape),
        contentAlignment = Alignment.Center,
    ) {
        Text(name, style = MaterialTheme.typography.labelSmall, color = VoraSurface)
    }
}

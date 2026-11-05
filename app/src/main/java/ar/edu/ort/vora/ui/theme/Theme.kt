package ar.edu.ort.vora.ui.theme

import androidx.compose.material3.LocalMinimumInteractiveComponentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.unit.Dp

// Figma solo define tema claro.
private val VoraColorScheme = lightColorScheme(
    primary = VoraGreen,
    onPrimary = VoraSurface,
    secondary = VoraMint,
    onSecondary = VoraGreen,
    tertiary = VoraLime,
    onTertiary = VoraGreen,
    background = VoraBackground,
    onBackground = VoraTextPrimary,
    surface = VoraSurface,
    onSurface = VoraTextPrimary,
    surfaceVariant = VoraBackground,
    onSurfaceVariant = VoraTextSecondary,
    outline = VoraBorder,
    outlineVariant = VoraBorder,
    error = VoraError,
    onError = VoraSurface,
)

@Composable
fun VoraCoachTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = VoraColorScheme,
        typography = Typography,
        shapes = Shapes,
    ) {
        // Sin el mínimo táctil de 48: botones y chips miden lo que dice Figma y no suman aire alrededor.
        CompositionLocalProvider(LocalMinimumInteractiveComponentSize provides Dp.Unspecified, content = content)
    }
}

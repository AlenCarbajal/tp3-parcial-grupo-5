package ar.edu.ort.vora.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

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
        content = content,
    )
}

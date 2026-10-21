package ar.edu.ort.vora.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import ar.edu.ort.vora.R

val Manrope = FontFamily(
    Font(R.font.manrope_regular, FontWeight.Normal),
    Font(R.font.manrope_medium, FontWeight.Medium),
    Font(R.font.manrope_semibold, FontWeight.SemiBold),
    Font(R.font.manrope_bold, FontWeight.Bold),
)

private fun manrope(size: Int, weight: FontWeight, letterSpacing: TextUnit = 0.sp) = TextStyle(
    fontFamily = Manrope,
    fontWeight = weight,
    fontSize = size.sp,
    letterSpacing = letterSpacing,
)

// Escala de Figma sobre los estilos de Material 3. Interlineado "Auto" de Figma: el natural de la fuente.
val Typography = Typography(
    displaySmall = manrope(32, FontWeight.Bold),          // cifras grandes de pantalla
    headlineLarge = manrope(28, FontWeight.Bold),
    headlineMedium = manrope(26, FontWeight.Bold),
    headlineSmall = manrope(24, FontWeight.SemiBold),     // cifra destacada
    titleLarge = manrope(20, FontWeight.Bold),            // título de pantalla
    titleMedium = manrope(18, FontWeight.SemiBold),       // título de tarjeta
    titleSmall = manrope(16, FontWeight.SemiBold),
    bodyLarge = manrope(15, FontWeight.Normal),
    bodyMedium = manrope(13, FontWeight.Normal),          // cuerpo
    bodySmall = manrope(12, FontWeight.Normal),
    labelLarge = manrope(14, FontWeight.SemiBold),        // acción principal
    labelMedium = manrope(11, FontWeight.SemiBold),       // barra inferior
    labelSmall = manrope(10, FontWeight.SemiBold, 0.5.sp), // etiquetas en mayúsculas
)

// Título de panel destacado (22 SemiBold), sin slot en Material 3.
val PanelTitleStyle = manrope(22, FontWeight.SemiBold)

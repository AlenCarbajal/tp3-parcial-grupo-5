package ar.edu.ort.vora.ui.theme

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

val Shapes = Shapes(
    extraSmall = RoundedCornerShape(8.dp),
    small = RoundedCornerShape(12.dp),   // campos, mensajes, filas
    medium = RoundedCornerShape(16.dp),  // tarjetas
    large = RoundedCornerShape(20.dp),   // paneles
    extraLarge = RoundedCornerShape(24.dp),
)

// Botones, chips y etiquetas.
val PillShape = CircleShape

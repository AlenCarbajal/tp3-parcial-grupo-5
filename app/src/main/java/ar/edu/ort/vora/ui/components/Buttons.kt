package ar.edu.ort.vora.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import ar.edu.ort.vora.ui.theme.PillShape
import ar.edu.ort.vora.ui.theme.Spacing
import ar.edu.ort.vora.ui.theme.VoraBorder
import ar.edu.ort.vora.ui.theme.VoraGreen
import ar.edu.ort.vora.ui.theme.VoraLime
import ar.edu.ort.vora.ui.theme.VoraSurface
import ar.edu.ort.vora.ui.theme.VoraTextMuted
import ar.edu.ort.vora.ui.theme.VoraTextPrimary
import ar.edu.ort.vora.ui.theme.VoraTextSecondary
import ar.edu.ort.vora.ui.theme.VoraWhiteTranslucent

// Tamaños de Figma: Small 13 SemiBold 8/16 (Home), Medium 14 SemiBold 10/20 (paneles), Large 15 Bold 14/24 (Edit profile).
// Split: mismo texto que Medium con padding lateral chico, para botones repartidos en una fila (Voice, Photo, Search).
enum class ButtonSize(val vertical: Int, val horizontal: Int) {
    Small(8, 16),
    Medium(10, 20),
    Large(14, 24),
    Split(10, 8),
}

@Composable
private fun ButtonSize.textStyle(): TextStyle = when (this) {
    ButtonSize.Small -> MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold)
    ButtonSize.Medium, ButtonSize.Split -> MaterialTheme.typography.labelLarge
    ButtonSize.Large -> MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
}

@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    size: ButtonSize = ButtonSize.Medium,
    @DrawableRes icon: Int? = null,
    enabled: Boolean = true,
) {
    PillButton(text, onClick, modifier, size, icon, enabled, container = VoraGreen, content = VoraSurface)
}

@Composable
fun LimeButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    size: ButtonSize = ButtonSize.Medium,
    @DrawableRes icon: Int? = null,
    enabled: Boolean = true,
) {
    PillButton(text, onClick, modifier, size, icon, enabled, container = VoraLime, content = VoraGreen)
}

// Blanco con borde: prompts de voz y acciones secundarias sobre fondo claro.
@Composable
fun SecondaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    size: ButtonSize = ButtonSize.Medium,
    @DrawableRes icon: Int? = null,
    enabled: Boolean = true,
) {
    PillButton(
        text, onClick, modifier, size, icon, enabled,
        container = VoraSurface,
        content = VoraTextPrimary,
        border = BorderStroke(1.dp, VoraBorder),
    )
}

// Sobre DarkPanel: relleno blanco translúcido (Photo, Search) o solo borde translúcido (Details).
@Composable
fun PanelButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    size: ButtonSize = ButtonSize.Medium,
    outlined: Boolean = false,
    @DrawableRes icon: Int? = null,
    enabled: Boolean = true,
) {
    PillButton(
        text, onClick, modifier, size, icon, enabled,
        container = if (outlined) Color.Transparent else VoraWhiteTranslucent,
        content = VoraSurface,
        border = if (outlined) BorderStroke(1.dp, VoraWhiteTranslucent) else null,
    )
}

// Acción contextual de texto ("View History") o enlace subrayado ("Type instead").
@Composable
fun VoraTextButton(text: String, onClick: () -> Unit, modifier: Modifier = Modifier, underline: Boolean = false) {
    TextButton(onClick = onClick, modifier = modifier, contentPadding = PaddingValues(Spacing.s4)) {
        Text(
            text,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = if (underline) FontWeight.Normal else FontWeight.SemiBold,
                textDecoration = if (underline) TextDecoration.Underline else null,
            ),
            color = if (underline) VoraTextSecondary else VoraGreen,
        )
    }
}

@Composable
private fun PillButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier,
    size: ButtonSize,
    @DrawableRes icon: Int?,
    enabled: Boolean,
    container: Color,
    content: Color,
    border: BorderStroke? = null,
) {
    Button(
        onClick = onClick,
        modifier = modifier.defaultMinSize(minWidth = 1.dp, minHeight = 1.dp),
        enabled = enabled,
        shape = PillShape,
        border = border,
        contentPadding = PaddingValues(horizontal = size.horizontal.dp, vertical = size.vertical.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = container,
            contentColor = content,
            disabledContainerColor = VoraBorder,
            disabledContentColor = VoraTextMuted,
        ),
    ) {
        if (icon != null) {
            Icon(painterResource(icon), contentDescription = null)
            Spacer(Modifier.width(Spacing.s8))
        }
        Text(text, style = size.textStyle(), maxLines = 1)
    }
}

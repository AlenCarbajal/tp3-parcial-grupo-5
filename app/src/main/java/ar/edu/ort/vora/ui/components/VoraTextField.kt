package ar.edu.ort.vora.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import ar.edu.ort.vora.ui.theme.Spacing
import ar.edu.ort.vora.ui.theme.VoraBorder
import ar.edu.ort.vora.ui.theme.VoraError
import ar.edu.ort.vora.ui.theme.VoraGreen
import ar.edu.ort.vora.ui.theme.VoraSurface
import ar.edu.ort.vora.ui.theme.VoraTextMuted
import ar.edu.ort.vora.ui.theme.VoraTextPrimary
import ar.edu.ort.vora.ui.theme.VoraTextSecondary

// Figma (Edit profile): etiqueta en mayúsculas a 6 del campo, valor 15 Regular, padding 12/16, borde 1, radio 12.
@Composable
fun VoraTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    placeholder: String? = null,
    error: String? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    singleLine: Boolean = true,
) {
    val shape = MaterialTheme.shapes.small
    val textStyle = MaterialTheme.typography.bodyLarge
    Column(modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(Spacing.s6)) {
        Text(label.uppercase(), style = MaterialTheme.typography.labelSmall, color = VoraTextSecondary)
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            textStyle = textStyle.copy(color = VoraTextPrimary),
            keyboardOptions = keyboardOptions,
            singleLine = singleLine,
            cursorBrush = SolidColor(VoraGreen),
            decorationBox = { innerTextField ->
                Box(
                    Modifier
                        .fillMaxWidth()
                        .background(VoraSurface, shape)
                        .border(1.dp, if (error != null) VoraError else VoraBorder, shape)
                        .padding(horizontal = Spacing.s16, vertical = Spacing.s12)
                ) {
                    if (value.isEmpty() && placeholder != null) {
                        Text(placeholder, style = textStyle, color = VoraTextMuted)
                    }
                    innerTextField()
                }
            },
        )
        if (error != null) {
            Text(error, style = MaterialTheme.typography.bodySmall, color = VoraError)
        }
    }
}

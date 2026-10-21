package ar.edu.ort.vora.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import ar.edu.ort.vora.R
import ar.edu.ort.vora.ui.theme.PillShape
import ar.edu.ort.vora.ui.theme.Spacing
import ar.edu.ort.vora.ui.theme.VoraBorder
import ar.edu.ort.vora.ui.theme.VoraGreen
import ar.edu.ort.vora.ui.theme.VoraMint
import ar.edu.ort.vora.ui.theme.VoraSurface
import ar.edu.ort.vora.ui.theme.VoraTextMuted
import ar.edu.ort.vora.ui.theme.VoraTextPrimary
import ar.edu.ort.vora.ui.theme.VoraWhiteTranslucent

// Botón de micrófono del asistente: verde de 72 en reposo (sombra menta) y menta de 80 al escuchar. Ícono de 28.
@Composable
fun MicButton(onClick: () -> Unit, modifier: Modifier = Modifier, listening: Boolean = false) {
    val size = if (listening) 80.dp else 72.dp
    Surface(
        onClick = onClick,
        modifier = modifier
            .size(size)
            .then(
                if (listening) Modifier
                else Modifier.dropShadow(PillShape, Shadow(radius = 24.dp, color = VoraMint, offset = DpOffset(0.dp, 8.dp), alpha = 0.15f))
            ),
        shape = PillShape,
        color = if (listening) VoraMint else VoraGreen,
        contentColor = if (listening) VoraGreen else VoraSurface,
    ) {
        Box(contentAlignment = Alignment.Center) {
            Icon(painterResource(R.drawable.ic_mic_large), contentDescription = if (listening) "Detener" else "Hablar")
        }
    }
}

// Orbe del asistente con el ícono de ondas (Idle, Listening, Processing, Speaking).
@Composable
fun VoiceOrb(modifier: Modifier = Modifier, listening: Boolean = false) {
    VoraOrb(modifier, size = 170.dp, glowAlpha = if (listening) 0.45f else 0.2f, ripple = listening) {
        Icon(painterResource(R.drawable.ic_audio_lines), contentDescription = null, tint = VoraSurface)
    }
}

// Tarjeta translúcida sobre fondo oscuro (transcripción en vivo, respuesta del asistente).
@Composable
fun DarkTranscriptCard(text: String, modifier: Modifier = Modifier, label: String? = null) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        color = Color.White.copy(alpha = 0.06f),
        contentColor = VoraSurface,
        border = BorderStroke(1.dp, VoraWhiteTranslucent),
    ) {
        Column(Modifier.padding(Spacing.s16), verticalArrangement = Arrangement.spacedBy(Spacing.s6)) {
            if (label != null) Text(label.uppercase(), style = MaterialTheme.typography.labelSmall, color = VoraMint)
            Text(text, style = MaterialTheme.typography.bodyLarge)
        }
    }
}

// Burbujas del chat con VORA: usuario a la derecha en verde, asistente a la izquierda en blanco con borde y orbe.
@Composable
fun ChatBubble(text: String, time: String, fromUser: Boolean, modifier: Modifier = Modifier) {
    Row(
        modifier.fillMaxWidth(),
        horizontalArrangement = if (fromUser) Arrangement.End else Arrangement.spacedBy(Spacing.s8),
        verticalAlignment = Alignment.Top,
    ) {
        if (!fromUser) VoraOrb(size = 24.dp)
        Surface(
            modifier = Modifier.widthIn(max = 260.dp),
            shape = MaterialTheme.shapes.medium,
            color = if (fromUser) VoraGreen else VoraSurface,
            contentColor = if (fromUser) VoraSurface else VoraTextPrimary,
            border = if (fromUser) null else BorderStroke(1.dp, VoraBorder),
        ) {
            Column(Modifier.padding(Spacing.s12), verticalArrangement = Arrangement.spacedBy(Spacing.s4)) {
                Text(text, style = MaterialTheme.typography.bodyMedium)
                Text(
                    time,
                    modifier = if (fromUser) Modifier.align(Alignment.End) else Modifier,
                    style = MaterialTheme.typography.labelSmall,
                    color = if (fromUser) VoraMint else VoraTextMuted,
                )
            }
        }
    }
}

// Barra de escritura del chat: agregar, campo "Ask VORA anything...", micrófono y enviar. Se acomoda sobre la barra del sistema y el teclado.
@Composable
fun ChatInputBar(value: String, onValueChange: (String) -> Unit, onSend: () -> Unit, modifier: Modifier = Modifier) {
    Row(
        modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .imePadding()
            .padding(horizontal = Spacing.screen, vertical = Spacing.s8),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Spacing.s8),
    ) {
        IconCircleButton(R.drawable.ic_plus, "Agregar", onClick = {})
        Row(
            Modifier
                .weight(1f)
                .background(VoraSurface, PillShape)
                .border(1.dp, VoraBorder, PillShape)
                .padding(horizontal = Spacing.s16, vertical = Spacing.s10),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(Modifier.weight(1f)) {
                if (value.isEmpty()) Text("Ask VORA anything...", style = MaterialTheme.typography.bodyMedium, color = VoraTextMuted)
                BasicTextField(
                    value,
                    onValueChange,
                    textStyle = MaterialTheme.typography.bodyMedium.copy(color = VoraTextPrimary),
                    singleLine = true,
                    cursorBrush = SolidColor(VoraGreen),
                )
            }
            Icon(painterResource(R.drawable.ic_mic), contentDescription = "Dictar", tint = VoraTextMuted)
        }
        Surface(onClick = onSend, modifier = Modifier.size(32.dp), shape = PillShape, color = VoraGreen, contentColor = VoraSurface) {
            Box(contentAlignment = Alignment.Center) {
                Icon(painterResource(R.drawable.ic_send), contentDescription = "Enviar", modifier = Modifier.size(16.dp))
            }
        }
    }
}

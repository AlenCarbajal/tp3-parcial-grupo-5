package ar.edu.ort.vora.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import ar.edu.ort.vora.ui.theme.VoraError
import ar.edu.ort.vora.ui.theme.VoraSurface
import ar.edu.ort.vora.ui.theme.VoraTextPrimary
import ar.edu.ort.vora.ui.theme.VoraTextSecondary

@Composable
fun ConfirmDialog(
    title: String,
    message: String,
    confirmText: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    dismissText: String = "Cancelar",
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = VoraSurface,
        shape = MaterialTheme.shapes.medium,
        title = { Text(title, style = MaterialTheme.typography.titleMedium, color = VoraTextPrimary) },
        text = { Text(message, style = MaterialTheme.typography.bodyMedium, color = VoraTextSecondary) },
        confirmButton = { VoraTextButton(confirmText, onConfirm, color = VoraError) },
        dismissButton = { VoraTextButton(dismissText, onDismiss) },
    )
}
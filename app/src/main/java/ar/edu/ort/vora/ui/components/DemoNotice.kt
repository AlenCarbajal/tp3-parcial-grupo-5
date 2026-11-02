package ar.edu.ort.vora.ui.components

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import ar.edu.ort.vora.R

// Acciones sin servicio real (NAV03): avisan que es una demo en lugar de quedar sin respuesta.
@Composable
fun rememberDemoNotice(): () -> Unit {
    val context = LocalContext.current
    return remember(context) {
        { Toast.makeText(context, R.string.common_demo_notice, Toast.LENGTH_SHORT).show() }
    }
}

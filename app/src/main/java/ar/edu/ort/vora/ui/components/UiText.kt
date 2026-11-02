package ar.edu.ort.vora.ui.components

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

// Mensaje que arma un ViewModel sin depender de Context: la pantalla lo resuelve con asString().
data class UiText(@StringRes val id: Int, val args: List<Any> = emptyList())

@Composable
fun UiText.asString(): String = stringResource(id, *args.toTypedArray())

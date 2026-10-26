package ar.edu.ort.vora.ui.coach

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ar.edu.ort.vora.R
import ar.edu.ort.vora.ui.components.ButtonSize
import ar.edu.ort.vora.ui.components.ChatBubble
import ar.edu.ort.vora.ui.components.ChatInputBar
import ar.edu.ort.vora.ui.components.DarkTranscriptCard
import ar.edu.ort.vora.ui.components.IconCircleButton
import ar.edu.ort.vora.ui.components.InfoCard
import ar.edu.ort.vora.ui.components.LimeButton
import ar.edu.ort.vora.ui.components.MicButton
import ar.edu.ort.vora.ui.components.MintButton
import ar.edu.ort.vora.ui.components.PrimaryButton
import ar.edu.ort.vora.ui.components.ProgressBar
import ar.edu.ort.vora.ui.components.SecondaryButton
import ar.edu.ort.vora.ui.components.StatusDot
import ar.edu.ort.vora.ui.components.StatusTag
import ar.edu.ort.vora.ui.components.VoiceOrb
import ar.edu.ort.vora.ui.components.VoraChip
import ar.edu.ort.vora.ui.components.VoraOrb
import ar.edu.ort.vora.ui.components.VoraTextButton
import ar.edu.ort.vora.ui.components.VoraTopBar
import ar.edu.ort.vora.ui.components.rememberDemoNotice
import ar.edu.ort.vora.ui.theme.PillShape
import ar.edu.ort.vora.ui.theme.Spacing
import ar.edu.ort.vora.ui.theme.VoraBackground
import ar.edu.ort.vora.ui.theme.VoraBorder
import ar.edu.ort.vora.ui.theme.VoraCoachTheme
import ar.edu.ort.vora.ui.theme.VoraError
import ar.edu.ort.vora.ui.theme.VoraGreen
import ar.edu.ort.vora.ui.theme.VoraMint
import ar.edu.ort.vora.ui.theme.VoraSurface
import ar.edu.ort.vora.ui.theme.VoraTextMuted
import ar.edu.ort.vora.ui.theme.VoraTextPrimary
import ar.edu.ort.vora.ui.theme.VoraTextSecondary
import ar.edu.ort.vora.ui.theme.VoraWhiteTranslucent
import kotlinx.coroutines.delay

// Asistente VORA con estados simulados (NAV05): no usa el micrófono ni pide permisos.
// Recorrido: Idle → Listening → Processing → Speaking → Conversation. Si se corta la escucha antes de oír algo, va a Error.

private const val Transcript = "I had chicken, rice and some vegetables for lunch."
private const val Answer =
    "That sounds like a balanced lunch. I've estimated around 520 calories with approximately 38 grams of protein."
private const val AnswerMillis = 22_000

private val WhiteSoft = VoraSurface.copy(alpha = 0.6f)

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CoachIdleScreen(onBack: () -> Unit, onTalk: () -> Unit, onTypeInstead: () -> Unit) {
    CoachLayout(
        dark = false,
        topBar = { VoraTopBar("VORA Coach", onBack = onBack) { StatusTag("Online") } },
        bottom = {
            MicButton(onClick = onTalk)
            Spacer(Modifier.height(Spacing.s8))
            Text("TAP TO TALK", style = MaterialTheme.typography.labelLarge.copy(letterSpacing = 0.5.sp), color = VoraGreen)
            VoraTextButton("Type instead", onTypeInstead, color = VoraTextSecondary)
        },
    ) {
        VoiceOrb()
        Spacer(Modifier.height(Spacing.s24))
        Text("How can I help?", style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold), color = VoraTextPrimary)
        Spacer(Modifier.height(Spacing.titleGap))
        Text(
            "Talk naturally about your workout, nutrition or progress.",
            modifier = Modifier.widthIn(max = 280.dp),
            style = MaterialTheme.typography.bodyMedium,
            color = VoraTextSecondary,
            textAlign = TextAlign.Center,
        )
        Spacer(Modifier.height(Spacing.s24))
        Text("SUGGESTED QUESTIONS", style = MaterialTheme.typography.labelSmall, color = VoraTextMuted)
        Spacer(Modifier.height(Spacing.s12))
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(Spacing.s8, Alignment.CenterHorizontally),
            verticalArrangement = Arrangement.spacedBy(Spacing.s8),
        ) {
            listOf("Start my workout", "Log my breakfast", "What should I eat tonight?", "How am I doing?").forEach {
                VoraChip(it, onClick = onTypeInstead)
            }
        }
        Spacer(Modifier.height(Spacing.s20))
        InfoCard(
            Modifier
                .clip(MaterialTheme.shapes.medium)
                .clickable(onClick = onTypeInstead),
            padding = Spacing.s12,
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("LAST CONVERSATION", Modifier.weight(1f), style = MaterialTheme.typography.labelSmall, color = VoraTextMuted)
                Text("2 hours ago", style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Normal), color = VoraTextMuted)
            }
            Spacer(Modifier.height(Spacing.s4))
            Text("\"Create a 30-minute workout for me.\"", style = MaterialTheme.typography.bodyMedium, color = VoraTextPrimary)
        }
    }
}

// La transcripción aparece palabra por palabra y al terminar pasa sola a Processing.
// Tocar el micrófono corta la escucha: sin palabras todavía, es el caso de error ("didn't catch that").
@Composable
fun CoachListeningScreen(onBack: () -> Unit, onFinished: (heard: Boolean) -> Unit) {
    val words = Transcript.split(" ")
    var shown by rememberSaveable { mutableIntStateOf(0) }
    LaunchedEffect(Unit) {
        while (shown < words.size) {
            delay(350)
            shown++
        }
        delay(1200)
        onFinished(true)
    }

    CoachLayout(
        dark = true,
        topBar = {
            VoraTopBar("Listening", dark = true, onBack = onBack) {
                StatusDot(Modifier.size(6.dp), color = VoraMint)
                Text("LIVE AUDIO", style = MaterialTheme.typography.labelSmall, color = VoraMint)
            }
        },
        bottom = {
            MicButton(onClick = { onFinished(shown > 0) }, listening = true)
            Spacer(Modifier.height(Spacing.s8))
            VoraTextButton("Cancel", onBack, color = VoraMint)
        },
    ) {
        VoiceOrb(listening = true)
        Spacer(Modifier.height(Spacing.s40))
        DarkTranscriptCard(
            text = if (shown == 0) "…" else "\"" + words.take(shown).joinToString(" ") + "…\"",
            label = "Live transcript",
        )
        Spacer(Modifier.height(Spacing.s20))
        Text("Speak naturally. I'm listening.", style = MaterialTheme.typography.bodySmall, color = WhiteSoft)
    }
}

@Composable
fun CoachProcessingScreen(onBack: () -> Unit, onAnswer: () -> Unit) {
    LaunchedEffect(Unit) {
        delay(2500)
        onAnswer()
    }

    CoachLayout(
        dark = true,
        topBar = { VoraTopBar("Processing", dark = true, onBack = onBack) },
        bottom = { VoraTextButton("Cancel", onBack, color = VoraSurface.copy(alpha = 0.7f)) },
    ) {
        VoiceOrb()
        Spacer(Modifier.height(Spacing.s24))
        Text("THINKING...", style = MaterialTheme.typography.labelLarge.copy(letterSpacing = 1.sp), color = VoraMint)
        Spacer(Modifier.height(Spacing.s16))
        DarkTranscriptCard("\"$Transcript\"")
        Spacer(Modifier.height(Spacing.s20))
        Row(verticalAlignment = Alignment.CenterVertically) {
            CircularProgressIndicator(Modifier.size(14.dp), color = WhiteSoft, strokeWidth = 2.dp, trackColor = Color.Transparent)
            Spacer(Modifier.width(Spacing.s8))
            Text("Checking your nutrition target...", style = MaterialTheme.typography.bodySmall, color = WhiteSoft)
        }
    }
}

// Reproducción simulada de la respuesta: la barra avanza sola y se puede pausar o repetir. "Tap to Interrupt" pasa al chat.
@Composable
fun CoachSpeakingScreen(onBack: () -> Unit, onInterrupt: () -> Unit) {
    val demo = rememberDemoNotice()
    var elapsed by rememberSaveable { mutableIntStateOf(0) }
    var playing by rememberSaveable { mutableStateOf(true) }
    LaunchedEffect(playing) {
        while (playing && elapsed < AnswerMillis) {
            delay(100)
            elapsed += 100
        }
        if (elapsed >= AnswerMillis) playing = false
    }

    CoachLayout(
        dark = true,
        topBar = { VoraTopBar("VORA Speaking", dark = true, onBack = onBack) { StatusTag("Speaking", dark = true) } },
    ) {
        VoiceOrb(listening = true)
        Spacer(Modifier.height(Spacing.s40))
        DarkTranscriptCard("\"$Answer\"")
        Spacer(Modifier.height(Spacing.s24))
        ProgressBar(elapsed / AnswerMillis.toFloat(), color = VoraMint, trackColor = VoraWhiteTranslucent)
        Spacer(Modifier.height(Spacing.s8))
        Row(Modifier.fillMaxWidth()) {
            Text(clock(elapsed), Modifier.weight(1f), style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Normal), color = WhiteSoft)
            Text("-" + clock(AnswerMillis - elapsed), style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Normal), color = WhiteSoft)
        }
        Spacer(Modifier.height(Spacing.s20))
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(Spacing.s24)) {
            IconCircleButton(R.drawable.ic_repeat, "Repetir", onClick = { elapsed = 0; playing = true }, dark = true)
            IconCircleButton(
                if (playing) R.drawable.ic_pause else R.drawable.ic_play,
                if (playing) "Pausar" else "Reproducir",
                onClick = { if (elapsed >= AnswerMillis) elapsed = 0; playing = !playing },
                accent = true,
            )
            IconCircleButton(R.drawable.ic_circle_dot, "Enfocar", onClick = demo, dark = true)
        }
        Spacer(Modifier.height(Spacing.s24))
        LimeButton("Tap to Interrupt", onInterrupt, Modifier.fillMaxWidth())
    }
}

private class Message(val text: String, val time: String, val fromUser: Boolean, val audio: String? = null)

private val History = listOf(
    Message("What should I eat for dinner?", "12:41", fromUser = true),
    Message(
        "You're currently 36g short of your protein target today. I'd highly recommend grilled chicken breast with roasted seasonal vegetables and a side of Greek yoghurt.",
        "12:41",
        fromUser = false,
        audio = "Listen to response (0:14)",
    ),
    Message("Can you make that vegetarian?", "12:42", fromUser = true),
    Message("Absolutely. Try grilled organic tofu with roasted vegetables, quinoa and Greek yoghurt.", "12:43", fromUser = false),
)

// Chat de demostración: lo que se escribe se agrega y VORA contesta con un aviso de modo demo.
@Composable
fun CoachChatScreen(onBack: () -> Unit, onListen: () -> Unit) {
    var draft by rememberSaveable { mutableStateOf("") }
    var sent by rememberSaveable { mutableStateOf(listOf<String>()) }
    val messages = History + sent.flatMap {
        listOf(Message(it, "Now", fromUser = true), Message("Demo mode: VORA's answers are simulated in this version.", "Now", fromUser = false))
    }
    val listState = rememberLazyListState()
    LaunchedEffect(messages.size) { listState.animateScrollToItem(messages.size) }

    Column(
        Modifier
            .fillMaxSize()
            .background(VoraBackground)
            .statusBarsPadding(),
    ) {
        Surface(color = VoraSurface) {
            Column {
                VoraTopBar("VORA Coach", onBack = onBack, titleDot = true) { VoraOrb(size = 24.dp) }
                HorizontalDivider(color = VoraBorder)
            }
        }
        LazyColumn(
            Modifier
                .weight(1f)
                .fillMaxWidth(),
            state = listState,
            contentPadding = PaddingValues(horizontal = Spacing.screen, vertical = Spacing.s16),
            verticalArrangement = Arrangement.spacedBy(Spacing.s16),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            item { Text("TODAY AT 12:40 PM", style = MaterialTheme.typography.labelSmall, color = VoraTextMuted) }
            items(messages) {
                ChatBubble(
                    it.text,
                    it.time,
                    it.fromUser,
                    Modifier.widthIn(max = Spacing.maxContentWidth),
                    audioLabel = it.audio,
                    onListen = onListen,
                )
            }
        }
        Surface(color = VoraSurface) {
            Column {
                HorizontalDivider(color = VoraBorder)
                ChatInputBar(
                    value = draft,
                    onValueChange = { draft = it },
                    onSend = {
                        if (draft.isNotBlank()) {
                            sent = sent + draft.trim()
                            draft = ""
                        }
                    },
                )
            }
        }
    }
}

// "Allow Access" no pide el permiso real: el micrófono no se usa (NAV05), así que avisa que es una demo.
@Composable
fun CoachErrorScreen(onBack: () -> Unit, onTryAgain: () -> Unit, onTypeInstead: () -> Unit) {
    val demo = rememberDemoNotice()
    var showPermission by rememberSaveable { mutableStateOf(true) }

    CoachLayout(
        dark = false,
        topBar = { VoraTopBar("System Alert", onBack = onBack) },
        bottom = {
            MintButton("Try again", onTryAgain, Modifier.fillMaxWidth())
            VoraTextButton("Type instead", onTypeInstead, color = VoraTextSecondary)
        },
    ) {
        Spacer(Modifier.height(Spacing.s16))
        VoraOrb(size = 100.dp, muted = true) {
            Icon(painterResource(R.drawable.ic_rotate_cw), contentDescription = null, tint = VoraSurface, modifier = Modifier.size(28.dp))
        }
        Spacer(Modifier.height(Spacing.s24))
        Text("Sorry, I didn't catch that", style = MaterialTheme.typography.titleLarge, color = VoraTextPrimary, textAlign = TextAlign.Center)
        Spacer(Modifier.height(Spacing.s8))
        Text(
            "Try speaking again or type your request instead. Make sure your microphone is enabled.",
            modifier = Modifier.widthIn(max = 300.dp),
            style = MaterialTheme.typography.bodyMedium,
            color = VoraTextSecondary,
            textAlign = TextAlign.Center,
        )
        if (showPermission) {
            Spacer(Modifier.height(Spacing.s24))
            InfoCard {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(Spacing.s12)) {
                    Box(
                        Modifier
                            .size(32.dp)
                            .background(VoraError.copy(alpha = 0.12f), PillShape),
                        contentAlignment = Alignment.Center,
                    ) {
                        Icon(painterResource(R.drawable.ic_mic), contentDescription = null, tint = VoraError, modifier = Modifier.size(16.dp))
                    }
                    Text("Microphone access needed", style = MaterialTheme.typography.titleSmall, color = VoraTextPrimary)
                }
                Spacer(Modifier.height(Spacing.s12))
                Text(
                    "VORA uses your microphone to listen to your voice commands and provide real-time hands-free coaching.",
                    style = MaterialTheme.typography.bodySmall,
                    color = VoraTextSecondary,
                )
                Spacer(Modifier.height(Spacing.s16))
                Row(horizontalArrangement = Arrangement.spacedBy(Spacing.s8)) {
                    PrimaryButton("Allow Access", demo, Modifier.weight(1f), size = ButtonSize.Small)
                    SecondaryButton("Not Now", { showPermission = false }, Modifier.weight(1f), size = ButtonSize.Small)
                }
            }
        }
    }
}

// Encabezado arriba, contenido con scroll y acciones fijas abajo. Los íconos claros de la barra de estado los pone VoraNavHost.
@Composable
private fun CoachLayout(
    dark: Boolean,
    topBar: @Composable () -> Unit,
    bottom: @Composable ColumnScope.() -> Unit = {},
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(
        Modifier
            .fillMaxSize()
            .background(if (dark) VoraGreen else VoraBackground)
            .systemBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        topBar()
        Column(
            Modifier
                .weight(1f)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Column(
                Modifier
                    .widthIn(max = Spacing.maxContentWidth)
                    .fillMaxWidth()
                    .padding(horizontal = Spacing.screen, vertical = Spacing.s16),
                horizontalAlignment = Alignment.CenterHorizontally,
                content = content,
            )
        }
        Column(
            Modifier
                .widthIn(max = Spacing.maxContentWidth)
                .fillMaxWidth()
                .padding(horizontal = Spacing.screen)
                .padding(bottom = Spacing.s16),
            horizontalAlignment = Alignment.CenterHorizontally,
            content = bottom,
        )
    }
}

private fun clock(millis: Int): String {
    val seconds = millis / 1000
    return "%d:%02d".format(seconds / 60, seconds % 60)
}

@Preview(name = "Coach Idle", widthDp = 390, heightDp = 844)
@Composable
private fun CoachIdlePreview() {
    VoraCoachTheme { CoachIdleScreen({}, {}, {}) }
}

@Preview(name = "Coach Listening", widthDp = 390, heightDp = 844)
@Composable
private fun CoachListeningPreview() {
    VoraCoachTheme { CoachListeningScreen({}, {}) }
}

@Preview(name = "Coach Processing", widthDp = 390, heightDp = 844)
@Composable
private fun CoachProcessingPreview() {
    VoraCoachTheme { CoachProcessingScreen({}, {}) }
}

@Preview(name = "Coach Speaking", widthDp = 390, heightDp = 844)
@Composable
private fun CoachSpeakingPreview() {
    VoraCoachTheme { CoachSpeakingScreen({}, {}) }
}

@Preview(name = "Coach Conversation", widthDp = 390, heightDp = 844)
@Composable
private fun CoachChatPreview() {
    VoraCoachTheme { CoachChatScreen({}, {}) }
}

@Preview(name = "Coach Error", widthDp = 390, heightDp = 844)
@Composable
private fun CoachErrorPreview() {
    VoraCoachTheme { CoachErrorScreen({}, {}, {}) }
}

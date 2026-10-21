package ar.edu.ort.vora.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ar.edu.ort.vora.R
import ar.edu.ort.vora.ui.theme.PanelTitleStyle
import ar.edu.ort.vora.ui.theme.Spacing
import ar.edu.ort.vora.ui.theme.VoraCoachTheme
import ar.edu.ort.vora.ui.theme.VoraGreen
import ar.edu.ort.vora.ui.theme.VoraLime
import ar.edu.ort.vora.ui.theme.VoraMint
import ar.edu.ort.vora.ui.theme.VoraTextMuted
import ar.edu.ort.vora.ui.theme.VoraTextSecondary

// Catálogo de componentes con las muestras de "Sistema de diseño — VORA / Elementos de interfaz".
// No forma parte de la navegación.
@Preview(showBackground = true, widthDp = 390, heightDp = 2000)
@Composable
fun ComponentsCatalogPreview() {
    VoraCoachTheme {
        var chip by remember { mutableIntStateOf(1) }
        var tab by remember { mutableIntStateOf(2) }
        var name by remember { mutableStateOf("Alex") }
        Column(Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
            VoraTopBar("Edit profile", onBack = {})
            Column(
                Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = Spacing.screen),
                verticalArrangement = Arrangement.spacedBy(Spacing.groupGap),
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(Spacing.s8), verticalAlignment = Alignment.CenterVertically) {
                    PrimaryButton("Start workout", onClick = {}, size = ButtonSize.Small)
                    PrimaryButton("Save changes", onClick = {}, size = ButtonSize.Large, modifier = Modifier.weight(1f))
                }
                DarkPanel {
                    Row(horizontalArrangement = Arrangement.spacedBy(Spacing.s8)) {
                        LimeButton("Start Workout", onClick = {})
                        PanelButton("Details", onClick = {}, outlined = true)
                    }
                }
                Row(horizontalArrangement = Arrangement.spacedBy(Spacing.s8), verticalAlignment = Alignment.CenterVertically) {
                    SecondaryButton("Start my workout", onClick = {}, size = ButtonSize.Small)
                    VoraTextButton("View History", onClick = {})
                    VoraTextButton("Type instead", onClick = {}, underline = true)
                }
                Row(horizontalArrangement = Arrangement.spacedBy(Spacing.s8)) {
                    listOf("Great", "Good", "Tough", "Too Easy").forEachIndexed { i, label ->
                        VoraChip(label, onClick = { chip = i }, style = if (i == chip) ChipStyle.Green else ChipStyle.Default)
                    }
                }

                VoraTextField(name, { name = it }, label = "First name")
                Row(horizontalArrangement = Arrangement.spacedBy(Spacing.s16)) {
                    VoraTextField("178 cm", {}, label = "Height", modifier = Modifier.weight(1f))
                    VoraTextField("-5", {}, label = "Weight", error = "Debe ser mayor o igual a 0", modifier = Modifier.weight(1f))
                }

                DarkPanel {
                    Column(verticalArrangement = Arrangement.spacedBy(Spacing.s4)) {
                        Text("READY FOR TODAY?", style = MaterialTheme.typography.labelSmall, color = VoraMint)
                        Text("Upper Body Strength", style = PanelTitleStyle)
                        Text("42 min • 5 exercises • Focus: Chest & Back", style = MaterialTheme.typography.bodyMedium, color = VoraTextMuted)
                    }
                    Row(horizontalArrangement = Arrangement.spacedBy(Spacing.s8)) {
                        LimeButton("Start Workout", onClick = {})
                        PanelButton("Details", onClick = {}, outlined = true)
                    }
                }
                InfoCard(shape = MaterialTheme.shapes.small, padding = Spacing.s12) {
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Breakfast", style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.SemiBold)
                        Text("420 kcal", style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)
                    }
                    Text(
                        "Greek yoghurt · berries · granola",
                        style = MaterialTheme.typography.bodySmall,
                        color = VoraTextSecondary,
                        modifier = Modifier.padding(top = 2.dp),
                    )
                }
                DarkPanel(padding = Spacing.s16) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(Spacing.s12)) {
                        VoraOrb(size = 28.dp, glowAlpha = 0.3f)
                        Text("Tell Vora what you ate", style = MaterialTheme.typography.titleSmall, color = VoraMint)
                    }
                    Text(
                        "Vora will automatically estimate the portions, ingredients, and log the nutrition details.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = VoraTextMuted,
                    )
                    Row(horizontalArrangement = Arrangement.spacedBy(Spacing.s8)) {
                        LimeButton("Voice", onClick = {}, modifier = Modifier.weight(1f), size = ButtonSize.Split, icon = R.drawable.ic_mic)
                        PanelButton("Photo", onClick = {}, modifier = Modifier.weight(1f), size = ButtonSize.Split, icon = R.drawable.ic_camera)
                        PanelButton("Search", onClick = {}, modifier = Modifier.weight(1f), size = ButtonSize.Split, icon = R.drawable.ic_search)
                    }
                }

                InfoCard {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(Spacing.s12)) {
                        ProgressRing(0.67f) {
                            Text("67%", style = MaterialTheme.typography.labelLarge)
                        }
                        Text("Goal:\n2,100 kcal", style = MaterialTheme.typography.bodySmall, color = VoraTextSecondary)
                    }
                }
                MacroProgress("Protein", "104g / 140g", 104f / 140f, VoraGreen)
                MacroProgress("Carbs", "152g / 220g", 152f / 220f, VoraMint)
                MacroProgress("Fat", "48g / 65g", 48f / 65f, VoraLime)
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(Spacing.s8)) {
                    StatusTag("Online")
                    StatusDot()
                    Text("Workout Progress", style = MaterialTheme.typography.bodyMedium)
                    Text("Not started 0%", style = MaterialTheme.typography.bodyMedium, color = VoraTextSecondary)
                }

                CoachNote("Used to personalise your recommendations.", icon = R.drawable.ic_info)
                CoachNote("You're on track today. A protein-rich dinner would help you reach your target.")
                AdviceCard(
                    "Vora advice",
                    "\"Completing today's intermediate workout and a protein-rich dinner will secure your optimal target recovery.\"",
                )
                InsightCard(
                    "Vora insight",
                    "You increased your Seated Cable Row volume by 8% compared with your previous session.",
                )
            }
            VoraBottomBar(selectedIndex = tab, onSelect = { tab = it })
        }
    }
}

// Patrones de las pantallas: encabezados, chips de preferencias, acceso y asistente.
@Preview(showBackground = true, widthDp = 390, heightDp = 2200)
@Composable
fun ScreenPatternsPreview() {
    VoraCoachTheme {
        var period by remember { mutableIntStateOf(0) }
        var email by remember { mutableStateOf("") }
        var message by remember { mutableStateOf("") }
        Column(
            Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(Spacing.groupGap),
        ) {
            VoraTopBar("Add Food", back = TopBarBack.Plain)
            VoraTopBar("Nutrition preferences")
            VoraTopBar("Workout progress", actions = { IconCircleButton(R.drawable.ic_more, "Más opciones", onClick = {}) })
            VoraTopBar("Workout Overview", back = TopBarBack.Plain, centered = true)
            Column(Modifier.background(VoraGreen)) {
                VoraTopBar("Listening", dark = true, actions = {
                    StatusDot(color = VoraMint)
                    Text("LIVE AUDIO", style = MaterialTheme.typography.labelSmall, color = VoraMint)
                })
            }

            Column(Modifier.padding(horizontal = Spacing.screen), verticalArrangement = Arrangement.spacedBy(Spacing.groupGap)) {
                SegmentedControl(listOf("Week", "Month", "3 Months"), period, { period = it })
                Row(horizontalArrangement = Arrangement.spacedBy(Spacing.s8)) {
                    VoraChip("Everything", onClick = {}, style = ChipStyle.Mint)
                    VoraChip("Vegetarian", onClick = {})
                }
                Row(horizontalArrangement = Arrangement.spacedBy(Spacing.s8)) {
                    VoraChip("Chicken", onClick = {}, style = ChipStyle.Checked)
                    VoraChip("Eggs", onClick = {}, style = ChipStyle.Checked)
                }
                Row(horizontalArrangement = Arrangement.spacedBy(Spacing.s8)) {
                    VoraChip("Peanuts", onClick = {}, style = ChipStyle.Removable)
                    VoraChip("Shellfish", onClick = {}, style = ChipStyle.Removable)
                }

                VoraLogo(Modifier.align(Alignment.CenterHorizontally))
                AuthTextField(email, { email = it }, label = "Email Address", icon = R.drawable.ic_mail, placeholder = "alex.morgan@email.com")
                AuthTextField(
                    "secret123", {}, label = "Password", icon = R.drawable.ic_lock, password = true,
                    labelAction = { VoraTextButton("Forgot your password?", onClick = {}) },
                )
                AuthButton("Sign In", onClick = {})
                Row(horizontalArrangement = Arrangement.spacedBy(Spacing.s8)) {
                    SocialButton(R.drawable.ic_logo_apple, "Continuar con Apple", onClick = {}, modifier = Modifier.weight(1f))
                    SocialButton(R.drawable.ic_logo_google, "Continuar con Google", onClick = {}, modifier = Modifier.weight(1f), brandColors = true)
                    SocialButton(R.drawable.ic_scan_face, "Continuar con Face ID", onClick = {}, modifier = Modifier.weight(1f), highlighted = true)
                }
            }

            Column(
                Modifier
                    .fillMaxWidth()
                    .background(VoraGreen)
                    .padding(Spacing.screen),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(Spacing.s24),
            ) {
                VoiceOrb(listening = true)
                DarkTranscriptCard("\"I had chicken, rice and some vegetables for lunch...\"", label = "Live transcript")
                MicButton(onClick = {}, listening = true)
            }
            Column(
                Modifier.padding(horizontal = Spacing.screen),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(Spacing.s16),
            ) {
                VoiceOrb()
                MicButton(onClick = {})
                ChatBubble("What should I eat for dinner?", "12:41", fromUser = true)
                ChatBubble("You're currently 35g short of your protein target today.", "12:41", fromUser = false)
            }
            ChatInputBar(message, { message = it }, onSend = {})
        }
    }
}

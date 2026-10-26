package ar.edu.ort.vora.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ar.edu.ort.vora.R
import ar.edu.ort.vora.ui.theme.PillShape
import ar.edu.ort.vora.ui.theme.Spacing
import ar.edu.ort.vora.ui.theme.VoraBorder
import ar.edu.ort.vora.ui.theme.VoraGreen
import ar.edu.ort.vora.ui.theme.VoraMint
import ar.edu.ort.vora.ui.theme.VoraSurface
import ar.edu.ort.vora.ui.theme.VoraTextMuted
import ar.edu.ort.vora.ui.theme.VoraTextPrimary
import ar.edu.ort.vora.ui.theme.VoraTextSecondary
import ar.edu.ort.vora.ui.theme.VoraWhiteTranslucent

data class BottomBarItem(val label: String, @DrawableRes val icon: Int)

val VoraBottomBarItems = listOf(
    BottomBarItem("Home", R.drawable.ic_nav_home),
    BottomBarItem("Workout", R.drawable.ic_nav_workout),
    BottomBarItem("Nutrition", R.drawable.ic_nav_nutrition),
    BottomBarItem("Progress", R.drawable.ic_nav_progress),
    BottomBarItem("Profile", R.drawable.ic_nav_profile),
)

@Composable
fun VoraBottomBar(
    selectedIndex: Int,
    onSelect: (Int) -> Unit,
    modifier: Modifier = Modifier,
    items: List<BottomBarItem> = VoraBottomBarItems,
) {
    Surface(modifier.fillMaxWidth(), color = VoraSurface) {
        Column {
            HorizontalDivider(color = VoraBorder)
            Row(
                Modifier
                    .navigationBarsPadding()
                    .padding(vertical = Spacing.s8),
            ) {
                items.forEachIndexed { index, item ->
                    val selected = index == selectedIndex
                    val color = if (selected) VoraGreen else VoraTextMuted
                    Column(
                        Modifier
                            .weight(1f)
                            .clickable(role = Role.Tab) { onSelect(index) }
                            .padding(vertical = Spacing.s8),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(Spacing.s4),
                    ) {
                        Icon(painterResource(item.icon), contentDescription = null, tint = color, modifier = Modifier.size(20.dp))
                        // Una sola línea: con letra grande o pantallas angostas achica en vez de cortarse.
                        BasicText(
                            item.label,
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Medium,
                                color = color,
                                textAlign = TextAlign.Center,
                            ),
                            maxLines = 1,
                            autoSize = TextAutoSize.StepBased(minFontSize = 8.sp, maxFontSize = MaterialTheme.typography.labelMedium.fontSize),
                        )
                    }
                }
            }
        }
    }
}

// Botón circular de 32 (volver, más opciones, notificaciones): blanco con borde, o translúcido sobre fondo oscuro.
// accent: botón principal de 44 en menta (pausa del audio en VORA Speaking).
@Composable
fun IconCircleButton(
    @DrawableRes icon: Int,
    contentDescription: String?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    dark: Boolean = false,
    accent: Boolean = false,
) {
    Surface(
        onClick = onClick,
        modifier = modifier.size(if (accent) 44.dp else 32.dp),
        shape = PillShape,
        color = when {
            accent -> VoraMint
            dark -> VoraWhiteTranslucent
            else -> VoraSurface
        },
        contentColor = when {
            accent -> VoraGreen
            dark -> VoraSurface
            else -> VoraTextPrimary
        },
        border = if (dark || accent) null else BorderStroke(1.dp, VoraBorder),
    ) {
        Box(contentAlignment = Alignment.Center) {
            Icon(painterResource(icon), contentDescription = contentDescription, modifier = Modifier.size(if (accent) 20.dp else 16.dp))
        }
    }
}

enum class TopBarBack {
    None,
    Plain,   // chevron suelto (Add Food, Review Meal)
    Circle,  // chevron en botón circular de 32 (Profile, Voice, Progress)
}

// Encabezado de Figma: fila de 350 (margen 20), padding superior 12, chevron 20 o botón de 32, 8/12 hasta el título.
// Con centered, el título va al centro (Workout Overview, Exercise Guide).
@Composable
fun VoraTopBar(
    title: String,
    modifier: Modifier = Modifier,
    back: TopBarBack = TopBarBack.Circle,
    onBack: () -> Unit = {},
    centered: Boolean = false,
    dark: Boolean = false,
    actions: @Composable RowScope.() -> Unit = {},
) {
    val contentColor = if (dark) VoraSurface else VoraTextPrimary
    val titleStyle = if (back == TopBarBack.Plain || centered) {
        MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.SemiBold)
    } else {
        MaterialTheme.typography.titleLarge
    }
    Box(
        modifier
            .fillMaxWidth()
            .padding(start = Spacing.screen, end = Spacing.screen, top = Spacing.s12, bottom = Spacing.s12),
        contentAlignment = Alignment.CenterStart,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(if (back == TopBarBack.Circle) Spacing.s12 else Spacing.s8),
        ) {
            when (back) {
                TopBarBack.None -> Unit
                TopBarBack.Plain -> Icon(
                    painterResource(R.drawable.ic_chevron_left),
                    contentDescription = "Volver",
                    tint = contentColor,
                    modifier = Modifier.clickable(role = Role.Button, onClick = onBack),
                )
                TopBarBack.Circle -> IconCircleButton(R.drawable.ic_chevron_left, "Volver", onBack, dark = dark)
            }
            if (!centered) Text(title, style = titleStyle, color = contentColor)
        }
        if (centered) {
            Text(title, style = titleStyle, color = contentColor, modifier = Modifier.align(Alignment.Center))
        }
        Row(
            Modifier.align(Alignment.CenterEnd),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Spacing.s8),
            content = actions,
        )
    }
}

// Selector de período (Week / Month / 3 Months): contenedor blanco y opción activa verde.
@Composable
fun SegmentedControl(options: List<String>, selectedIndex: Int, onSelect: (Int) -> Unit, modifier: Modifier = Modifier) {
    Row(
        modifier
            .fillMaxWidth()
            .background(VoraSurface, PillShape)
            .padding(Spacing.s4),
    ) {
        options.forEachIndexed { index, option ->
            val selected = index == selectedIndex
            Text(
                option,
                modifier = Modifier
                    .weight(1f)
                    .background(if (selected) VoraGreen else Color.Transparent, PillShape)
                    .clickable(role = Role.Tab) { onSelect(index) }
                    .padding(vertical = Spacing.s8),
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Medium,
                color = if (selected) VoraSurface else VoraTextSecondary,
                textAlign = TextAlign.Center,
            )
        }
    }
}

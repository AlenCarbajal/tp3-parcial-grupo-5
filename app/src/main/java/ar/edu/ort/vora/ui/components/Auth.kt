package ar.edu.ort.vora.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.LinearGradientShader
import androidx.compose.ui.graphics.RadialGradientShader
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.text.style.TextOverflow
import ar.edu.ort.vora.ui.theme.LogoShadow
import ar.edu.ort.vora.R
import ar.edu.ort.vora.ui.theme.AuthFieldBackground
import ar.edu.ort.vora.ui.theme.AuthFieldBorder
import ar.edu.ort.vora.ui.theme.AuthLime
import ar.edu.ort.vora.ui.theme.AuthOutline
import ar.edu.ort.vora.ui.theme.LogoBadgeText
import ar.edu.ort.vora.ui.theme.LogoDark
import ar.edu.ort.vora.ui.theme.LogoLight
import ar.edu.ort.vora.ui.theme.LogoMid
import ar.edu.ort.vora.ui.theme.PillShape
import ar.edu.ort.vora.ui.theme.Spacing
import ar.edu.ort.vora.ui.theme.VoraGreen
import ar.edu.ort.vora.ui.theme.VoraMintTranslucent
import ar.edu.ort.vora.ui.theme.VoraSurface
import ar.edu.ort.vora.ui.theme.VoraTextMuted
import ar.edu.ort.vora.ui.theme.VoraTextPrimary

private val AuthShape = RoundedCornerShape(16.dp)

enum class LogoStyle {
    SignIn,  // esfera de 64, degradado radial y aro blanco de 4
    SignUp,  // esfera de 56, degradado diagonal, brillo menta y punto blanco
}

// Logo de acceso: esfera con anillo interno blanco al 30 % y badge "AI" que sobresale 4 abajo a la derecha.
@Composable
fun VoraLogo(modifier: Modifier = Modifier, style: LogoStyle = LogoStyle.SignIn) {
    val signUp = style == LogoStyle.SignUp
    val size = if (signUp) 56.dp else 64.dp
    val shadowColor = if (signUp) LogoShadow else Color.Black
    val shadowAlpha = if (signUp) 0.2f else 0.1f
    Box(modifier.size(size)) {
        if (signUp) {
            Box(
                Modifier
                    .align(Alignment.Center)
                    .requiredSize(64.dp)
                    .blur(16.dp, BlurredEdgeTreatment.Unbounded)
                    .background(LogoMid.copy(alpha = 0.3f), PillShape)
            )
        }
        Box(
            Modifier
                .size(size)
                .dropShadow(PillShape, Shadow(radius = 6.dp, color = shadowColor, spread = (-4).dp, offset = DpOffset(0.dp, 4.dp), alpha = shadowAlpha))
                .dropShadow(PillShape, Shadow(radius = 15.dp, color = shadowColor, spread = (-3).dp, offset = DpOffset(0.dp, 10.dp), alpha = shadowAlpha))
                .then(if (signUp) Modifier else Modifier.dropShadow(PillShape, Shadow(radius = 0.dp, color = Color.White, spread = 4.dp)))
                .background(if (signUp) LogoSignUpBrush else LogoSignInBrush, PillShape),
            contentAlignment = Alignment.Center,
        ) {
            Box(
                Modifier
                    .size(20.dp)
                    .background(Color.White.copy(alpha = 0.3f), PillShape),
                contentAlignment = Alignment.Center,
            ) {
                if (signUp) Box(Modifier.size(10.dp).background(Color.White, PillShape))
            }
        }
        Text(
            "AI",
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .offset(x = if (signUp) 8.dp else 4.dp, y = 4.dp)
                .background(LogoDark, PillShape)
                .then(if (signUp) Modifier else Modifier.border(1.dp, Color.White, PillShape))
                .padding(horizontal = Spacing.s8, vertical = 2.dp),
            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, letterSpacing = 0.5.sp, lineHeight = 15.sp),
            color = if (signUp) AuthLime else LogoBadgeText,
        )
    }
}

// Sign In: radial con el centro arriba a la izquierda, por eso el verde oscuro casi no se ve.
private val LogoSignInBrush = object : ShaderBrush() {
    override fun createShader(size: Size) = RadialGradientShader(
        center = Offset(size.width * 0.3f, size.height * 0.3f),
        radius = size.width,
        colors = listOf(LogoLight, LogoMid, LogoDark),
        colorStops = listOf(0f, 0.5f, 1f),
    )
}

// Sign Up: lineal de abajo a la izquierda (verde oscuro) hacia arriba a la derecha (lima).
private val LogoSignUpBrush = object : ShaderBrush() {
    override fun createShader(size: Size) = LinearGradientShader(
        from = Offset(0f, size.height),
        to = Offset(size.width, 0f),
        colors = listOf(LogoDark, LogoMid, AuthLime),
    )
}

// Campo de acceso: etiqueta 12 Bold, fondo #F3F9F5 al 60 %, borde #E2EDE7, radio 16, alto 55, ícono a la izquierda.
// outlined es el de Sign Up: etiqueta 16 Regular, fondo blanco, borde #6B7280, radio 12 y alto 52.
@Composable
fun AuthTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    @DrawableRes icon: Int,
    modifier: Modifier = Modifier,
    placeholder: String? = null,
    password: Boolean = false,
    outlined: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    labelAction: @Composable () -> Unit = {},
) {
    val textStyle = (if (outlined) MaterialTheme.typography.titleSmall else MaterialTheme.typography.labelLarge)
        .copy(fontWeight = FontWeight.Normal, color = VoraTextPrimary)
    val labelStyle = if (outlined) {
        MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Normal)
    } else {
        MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold, letterSpacing = 0.3.sp)
    }
    val fieldShape = if (outlined) MaterialTheme.shapes.small else AuthShape
    Column(modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(Spacing.s8)) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Text(label, style = labelStyle, color = VoraTextPrimary)
            labelAction()
        }
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            textStyle = textStyle,
            singleLine = true,
            keyboardOptions = if (password) keyboardOptions.copy(keyboardType = KeyboardType.Password, autoCorrectEnabled = false) else keyboardOptions,
            visualTransformation = if (password) PasswordVisualTransformation() else VisualTransformation.None,
            cursorBrush = SolidColor(VoraGreen),
            decorationBox = { innerTextField ->
                Row(
                    Modifier
                        .fillMaxWidth()
                        .background(if (outlined) VoraSurface else AuthFieldBackground, fieldShape)
                        .border(1.dp, if (outlined) AuthOutline else AuthFieldBorder, fieldShape)
                        .padding(horizontal = Spacing.s16, vertical = if (outlined) 13.dp else 15.5.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(Spacing.s12),
                ) {
                    Icon(painterResource(icon), contentDescription = null, tint = if (outlined) VoraTextPrimary else VoraTextMuted, modifier = Modifier.size(16.dp))
                    Box(Modifier.weight(1f)) {
                        if (value.isEmpty() && placeholder != null) {
                            Text(placeholder, style = textStyle, color = VoraTextMuted, maxLines = 1, overflow = TextOverflow.Ellipsis)
                        }
                        innerTextField()
                    }
                    if (password) Icon(painterResource(R.drawable.ic_eye), contentDescription = null, tint = if (outlined) VoraTextPrimary else VoraTextMuted, modifier = Modifier.size(16.dp))
                }
            },
        )
    }
}

// CTA de acceso: lima #CCE65E, radio 16, alto 56, padding 16/24 y sombra lima (0/6, blur 20, 35 %).
@Composable
fun AuthButton(text: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .dropShadow(AuthShape, Shadow(radius = 20.dp, color = AuthLime, offset = DpOffset(0.dp, 6.dp), alpha = 0.35f)),
        shape = AuthShape,
        contentPadding = PaddingValues(horizontal = Spacing.s24, vertical = Spacing.s16),
        colors = ButtonDefaults.buttonColors(containerColor = AuthLime, contentColor = VoraGreen),
    ) {
        Text(text, style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold))
        Spacer(Modifier.width(Spacing.s8))
        Icon(painterResource(R.drawable.ic_arrow_right), contentDescription = null, modifier = Modifier.size(16.dp))
    }
}

// Botón de acceso alternativo: blanco con borde, radio 16, alto 46. highlighted = Face ID (menta translúcida).
// brandColors deja los colores propios del logo (Google) en lugar de teñirlo.
@Composable
fun SocialButton(
    @DrawableRes icon: Int,
    contentDescription: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    highlighted: Boolean = false,
    brandColors: Boolean = false,
) {
    Surface(
        onClick = onClick,
        modifier = modifier,
        shape = AuthShape,
        color = if (highlighted) VoraMintTranslucent else VoraSurface,
        contentColor = VoraGreen,
        border = BorderStroke(1.dp, AuthFieldBorder),
    ) {
        Box(Modifier.padding(vertical = Spacing.s12), contentAlignment = Alignment.Center) {
            Icon(
                painterResource(icon),
                contentDescription = contentDescription,
                modifier = Modifier.size(22.dp),
                tint = if (brandColors) Color.Unspecified else LocalContentColor.current,
            )
        }
    }
}

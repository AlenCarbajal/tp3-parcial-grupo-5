package ar.edu.ort.vora.ui.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withLink
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ar.edu.ort.vora.R
import ar.edu.ort.vora.ui.components.AuthButton
import ar.edu.ort.vora.ui.components.AuthTextField
import ar.edu.ort.vora.ui.components.IconCircleButton
import ar.edu.ort.vora.ui.components.InfoCard
import ar.edu.ort.vora.ui.components.LogoStyle
import ar.edu.ort.vora.ui.components.SocialButton
import ar.edu.ort.vora.ui.components.VoraChip
import ar.edu.ort.vora.ui.components.VoraLogo
import ar.edu.ort.vora.ui.components.rememberDemoNotice
import ar.edu.ort.vora.ui.theme.AuthAccent
import ar.edu.ort.vora.ui.theme.AuthFieldBorder
import ar.edu.ort.vora.ui.theme.AuthScreenTint
import ar.edu.ort.vora.ui.theme.AuthTextSecondary
import ar.edu.ort.vora.ui.theme.LogoDark
import ar.edu.ort.vora.ui.theme.PillShape
import ar.edu.ort.vora.ui.theme.Spacing
import ar.edu.ort.vora.ui.theme.VoraBorder
import ar.edu.ort.vora.ui.theme.VoraCoachTheme
import ar.edu.ort.vora.ui.theme.VoraGreen
import ar.edu.ort.vora.ui.theme.VoraMint
import ar.edu.ort.vora.ui.theme.VoraSurface
import ar.edu.ort.vora.ui.theme.VoraTextPrimary

// Acceso de demostración (sin Firebase): cualquier dato entra a Home.
@Composable
fun SignInScreen(onSignIn: () -> Unit, onSignUp: () -> Unit, onBack: () -> Unit) {
    val demo = rememberDemoNotice()
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    AuthLayout(onBack = onBack, onLanguage = demo) {
        AuthHeader(tagline = "Your strength, nutrition, and progress plan in one place.")
        Spacer(Modifier.height(Spacing.s32))
        AuthCard {
            Column(verticalArrangement = Arrangement.spacedBy(Spacing.s16)) {
                AuthTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = "Email Address",
                    icon = R.drawable.ic_mail,
                    placeholder = "alex.morgan@email.com",
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email, imeAction = ImeAction.Next),
                )
                AuthTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = "Password",
                    icon = R.drawable.ic_lock,
                    placeholder = "••••••••••••",
                    password = true,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    labelAction = { LinkText("", "Forgot your password?", demo, MaterialTheme.typography.bodySmall, AuthAccent, underline = false) },
                )
            }
            AuthButton("Sign In", onClick = onSignIn)
            SocialSignIn("Or continue with", demo)
        }
        Spacer(Modifier.height(Spacing.s24))
        SecurityNote("End-to-end encrypted biometric sign-in", MaterialTheme.typography.bodySmall, AuthTextSecondary)
        Spacer(Modifier.height(Spacing.s32))
        Spacer(Modifier.weight(1f))
        LinkText("Don't have an account? ", "Sign up here", onSignUp, MaterialTheme.typography.labelLarge, AuthTextSecondary)
    }
}

@Composable
fun SignUpScreen(onSignUp: () -> Unit, onSignIn: () -> Unit, onBack: () -> Unit) {
    val demo = rememberDemoNotice()
    var name by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    AuthLayout(onBack = onBack, onLanguage = demo) {
        AuthHeader(signUp = true)
        Spacer(Modifier.height(Spacing.s24))
        AuthCard {
            Column(verticalArrangement = Arrangement.spacedBy(Spacing.s16)) {
                AuthTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = "Full Name",
                    icon = R.drawable.ic_user,
                    placeholder = "Alex Morgan",
                    outlined = true,
                    keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words, imeAction = ImeAction.Next),
                )
                AuthTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = "Email Address",
                    icon = R.drawable.ic_mail,
                    placeholder = "alex.morgan@email.com",
                    outlined = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email, imeAction = ImeAction.Next),
                )
                Column(verticalArrangement = Arrangement.spacedBy(Spacing.s12)) {
                    AuthTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = "Password",
                        icon = R.drawable.ic_lock,
                        placeholder = "At least 8 characters",
                        password = true,
                        outlined = true,
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    )
                    PasswordHint(valid = password.length >= 8, onHelp = demo)
                }
            }
            AuthButton("Sign Up", onClick = onSignUp)
            SocialSignIn("Or sign up with", demo)
        }
        Spacer(Modifier.height(Spacing.s24))
        SecurityNote("Biometric sign-in", MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Normal), VoraTextPrimary)
        Spacer(Modifier.height(Spacing.s16))
        LinkText("Having account issues? ", "Recover", demo, MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Normal), VoraTextPrimary)
        Spacer(Modifier.height(Spacing.s16))
        Spacer(Modifier.weight(1f))
        LinkText("Already have an account? ", "Sign In", onSignIn, MaterialTheme.typography.labelLarge, AuthTextSecondary)
    }
}

// Fondo con el brillo menta de Figma, scroll y ancho máximo. El pie queda abajo en pantallas altas
// porque la columna mide al menos el alto disponible y los Spacer con weight ocupan el resto.
@Composable
private fun AuthLayout(onBack: () -> Unit, onLanguage: () -> Unit, content: @Composable ColumnScope.() -> Unit) {
    BoxWithConstraints(
        Modifier
            .fillMaxSize()
            .background(AuthScreenTint)
            .drawBehind {
                drawRect(
                    Brush.radialGradient(
                        listOf(VoraMint.copy(alpha = 0.18f), Color.Transparent),
                        center = Offset(size.width / 2, 0f),
                        radius = size.width * 0.9f,
                    )
                )
            }
            .systemBarsPadding()
            .imePadding()
    ) {
        val minHeight = maxHeight
        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Column(
                Modifier
                    .heightIn(min = minHeight)
                    .widthIn(max = Spacing.maxContentWidth)
                    .fillMaxWidth()
                    .padding(horizontal = Spacing.s24)
                    .padding(top = Spacing.s12, bottom = Spacing.s24),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    IconCircleButton(R.drawable.ic_chevron_left, "Volver", onBack)
                    VoraChip("EN", onClick = onLanguage, trailingIcon = R.drawable.ic_chevron_down)
                }
                Spacer(Modifier.height(Spacing.s24))
                content()
            }
        }
    }
}

@Composable
private fun AuthHeader(tagline: String? = null, signUp: Boolean = false) {
    VoraLogo(style = if (signUp) LogoStyle.SignUp else LogoStyle.SignIn)
    Spacer(Modifier.height(Spacing.s16))
    // Sign Up usa el título más grande (28 / 700) que Sign In (24 / 700).
    Text(
        "VORA Coach",
        style = if (signUp) {
            MaterialTheme.typography.headlineLarge.copy(letterSpacing = (-0.7).sp)
        } else {
            MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold, letterSpacing = (-0.6).sp)
        },
        color = LogoDark,
    )
    Spacer(Modifier.height(Spacing.s4))
    Text(
        "ADAPTIVE INTELLIGENCE",
        style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold, letterSpacing = 1.2.sp),
        color = AuthAccent,
    )
    if (tagline != null) {
        Spacer(Modifier.height(Spacing.s8))
        Text(
            tagline,
            modifier = Modifier.widthIn(max = 272.dp),
            style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Normal),
            color = AuthTextSecondary,
            textAlign = TextAlign.Center,
        )
    }
}

// Tarjeta del formulario: radio 24, padding 24, separación 20, borde #E2EDE7 y sombra 0/8 blur 30 al 4 %.
@Composable
private fun AuthCard(content: @Composable ColumnScope.() -> Unit) {
    val shape = MaterialTheme.shapes.extraLarge
    InfoCard(
        modifier = Modifier.dropShadow(shape, Shadow(radius = 30.dp, color = LogoDark, offset = DpOffset(0.dp, 8.dp), alpha = 0.04f)),
        shape = shape,
        padding = Spacing.s24,
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(Spacing.s20), content = content)
    }
}

@Composable
private fun SocialSignIn(label: String, onClick: () -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(Spacing.s16)) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(Spacing.s12)) {
            HorizontalDivider(Modifier.weight(1f), color = AuthFieldBorder)
            Text(label, style = MaterialTheme.typography.bodySmall, color = AuthTextSecondary)
            HorizontalDivider(Modifier.weight(1f), color = AuthFieldBorder)
        }
        Row(horizontalArrangement = Arrangement.spacedBy(Spacing.s12)) {
            SocialButton(R.drawable.ic_logo_apple, "Apple", onClick, Modifier.weight(1f))
            SocialButton(R.drawable.ic_logo_google, "Google", onClick, Modifier.weight(1f), brandColors = true)
            SocialButton(R.drawable.ic_scan_face, "Face ID", onClick, Modifier.weight(1f), highlighted = true)
        }
    }
}

@Composable
private fun PasswordHint(valid: Boolean, onHelp: () -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            Modifier
                .size(14.dp)
                .background(if (valid) VoraGreen else VoraBorder, PillShape),
            contentAlignment = Alignment.Center,
        ) {
            Icon(painterResource(R.drawable.ic_check), contentDescription = null, tint = VoraSurface, modifier = Modifier.size(9.dp))
        }
        Text(
            "8+ secure characters",
            modifier = Modifier
                .weight(1f)
                .padding(start = Spacing.s8),
            style = MaterialTheme.typography.bodySmall,
            color = VoraTextPrimary,
        )
        LinkText("", "Password help?", onHelp, MaterialTheme.typography.bodySmall, VoraTextPrimary, bold = false)
    }
}

@Composable
private fun SecurityNote(text: String, style: TextStyle, color: Color) {
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(Spacing.s6)) {
        Icon(painterResource(R.drawable.ic_shield_check), contentDescription = null, tint = VoraGreen, modifier = Modifier.size(14.dp))
        Text(text, style = style, color = color)
    }
}

// Texto con un enlace al final ("Don't have an account? Sign up here"); el enlace es accesible como link.
@Composable
private fun LinkText(
    prefix: String,
    link: String,
    onClick: () -> Unit,
    style: TextStyle,
    color: Color,
    underline: Boolean = true,
    bold: Boolean = true,
) {
    val linkStyle = SpanStyle(
        fontWeight = if (bold) FontWeight.Bold else null,
        textDecoration = if (underline) TextDecoration.Underline else null,
    )
    Text(
        buildAnnotatedString {
            append(prefix)
            withLink(LinkAnnotation.Clickable(link, TextLinkStyles(linkStyle)) { onClick() }) { append(link) }
        },
        style = style,
        color = color,
        textAlign = TextAlign.Center,
    )
}

@Preview(name = "Sign In", widthDp = 390, heightDp = 844)
@Preview(name = "Sign In chico", widthDp = 320, heightDp = 640)
@Composable
private fun SignInPreview() {
    VoraCoachTheme { SignInScreen({}, {}, {}) }
}

@Preview(name = "Sign Up", widthDp = 390, heightDp = 844)
@Preview(name = "Sign Up apaisado", widthDp = 844, heightDp = 390)
@Composable
private fun SignUpPreview() {
    VoraCoachTheme { SignUpScreen({}, {}, {}) }
}

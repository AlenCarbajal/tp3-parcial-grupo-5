package ar.edu.ort.vora.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

// Tokens de "Sistema de diseño — VORA" (specs/06-design-tokens.md).
val VoraGreen = Color(0xFF123D35)
val VoraMint = Color(0xFF65D6B0)
val VoraLime = Color(0xFFC9E86A)
val VoraSurface = Color(0xFFFFFFFF)
val VoraBackground = Color(0xFFF7F8F5)
val VoraBorder = Color(0xFFE4E9E6)
val VoraTextPrimary = Color(0xFF17211F)
val VoraTextSecondary = Color(0xFF68736F)
val VoraTextMuted = Color(0xFF9AA39F)
val VoraError = Color(0xFFD95D5D)
val VoraSuccess = Color(0xFF27AE60)
val VoraMintTranslucent = Color(0x1F65D6B0)
val VoraWhiteTranslucent = Color(0x1FFFFFFF)
val VoraDestructive = Color(0xFFDC2626)     // ícono para quitar ("Foods to avoid")

// Acceso (Sign In / Sign Up): paleta propia de esas pantallas en Figma.
val AuthLime = Color(0xFFCCE65E)
val AuthFieldBackground = Color(0x99F3F9F5)  // #F3F9F5 al 60 %
val AuthFieldBorder = Color(0xFFE2EDE7)
val AuthScreenTint = Color(0xFFF3F9F5)
val AuthTextSecondary = Color(0xFF6B8780)
val AuthAccent = Color(0xCC065F46)  // #065F46 al 80 %
val AuthOutline = Color(0xFF6B7280)  // borde de los campos de Sign Up
val LogoLight = Color(0xFF86EFAC)
val LogoMid = Color(0xFF34D399)
val LogoDark = Color(0xFF0E382F)
val LogoBadgeText = Color(0xFF6EE7B7)
val LogoShadow = Color(0xFF064E3B)  // sombras del logo de Sign Up

// Degradados del orbe VORA (orb-glow, orb-ring y orb-core en Figma), de izquierda a derecha.
val OrbGlowBrush = Brush.radialGradient(listOf(VoraMint, VoraGreen))
val OrbRingBrush = Brush.horizontalGradient(0f to VoraLime, 0.5f to VoraMint, 1f to VoraGreen)
val OrbCoreBrush = Brush.horizontalGradient(0f to VoraMint, 1f to VoraGreen)

// Orbe gris del error de micrófono (System Alert).
val OrbMutedGlowBrush = Brush.radialGradient(listOf(VoraTextMuted, VoraTextSecondary))
val OrbMutedRingBrush = Brush.horizontalGradient(0f to VoraBorder, 0.5f to VoraTextMuted, 1f to VoraTextSecondary)
val OrbMutedCoreBrush = Brush.horizontalGradient(0f to VoraTextMuted, 1f to VoraTextSecondary)

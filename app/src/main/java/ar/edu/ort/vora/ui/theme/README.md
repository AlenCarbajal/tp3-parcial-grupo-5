# Tema VORA (`ui/theme`)

Tema único de la app, tomado de la página "Sistema de diseño — VORA" del Figma del parcial y de `specs/06-design-tokens.md`. Las pantallas no definen colores, tamaños ni fuentes sueltos: usan estos tokens o los componentes de `ui/components`.

Se aplica con `VoraCoachTheme { ... }`, que ya envuelve la navegación en `MainActivity`. Solo existe tema claro, porque Figma no define modo oscuro.

Para revisarlo contra Figma, abrí `ThemePreview.kt` en Android Studio con la vista **Split**. Ahí están los colores, la tipografía, las formas y el orbe.

## Archivos

| Archivo | Contenido |
|---|---|
| `Color.kt` | Colores y degradados del orbe |
| `Type.kt` | Fuente Manrope y escala tipográfica |
| `Shape.kt` | Radios de esquina y `PillShape` |
| `Spacing.kt` | Escala de espaciado y márgenes |
| `Theme.kt` | `VoraCoachTheme`: asigna los tokens a Material 3 |
| `ThemePreview.kt` | Catálogo visual (solo preview) |

## Colores (`Color.kt`)

| Token | Valor | Rol en Material 3 | Uso |
|---|---|---|---|
| `VoraGreen` | `#123D35` | `primary` | Acciones, paneles oscuros, progreso |
| `VoraMint` | `#65D6B0` | `secondary` | Coach, voz, carbohidratos |
| `VoraLime` | `#C9E86A` | `tertiary` | CTA destacado sobre panel oscuro, grasas |
| `VoraSurface` | `#FFFFFF` | `surface` | Tarjetas y campos |
| `VoraBackground` | `#F7F8F5` | `background` | Fondo de pantalla |
| `VoraBorder` | `#E4E9E6` | `outline` | Bordes, separadores, pista de progreso |
| `VoraTextPrimary` | `#17211F` | `onBackground`, `onSurface` | Texto principal |
| `VoraTextSecondary` | `#68736F` | `onSurfaceVariant` | Texto secundario, etiquetas de campo |
| `VoraTextMuted` | `#9AA39F` | | Metadatos, ícono y etiqueta inactivos de la barra inferior |
| `VoraError` | `#D95D5D` | `error` | "NOT STARTED", Log out, errores de validación |
| `VoraSuccess` | `#27AE60` | | Estados correctos |
| `VoraMintTranslucent` | `#65D6B0` al 12 % | | Mensajes y recomendaciones del coach |
| `VoraWhiteTranslucent` | `#FFFFFF` al 12 % | | Botones secundarios sobre panel oscuro (Photo, Search) |

### Orbe VORA

El orbe (`ui/components/VoraOrb.kt`) tiene tres capas centradas. Las medidas de referencia son las del orbe de 120 dp y se escalan con el tamaño:

| Capa | Tamaño | Relleno | Opacidad / efecto |
|---|---|---|---|
| `orb-glow` | 100 % | Radial `#65D6B0` → `#123D35` (`OrbGlowBrush`) | 20 % por defecto, 30 % en el orbe de 28 dp (`glowAlpha`) |
| `orb-ring` | 89 % (107/120) | Aro de 7/120 de grosor, lineal de izquierda a derecha `#C9E86A` 0 → `#65D6B0` 0,5 → `#123D35` 1 (`OrbRingBrush`) | 80 % |
| `orb-core` | 75 % (90/120) | Lineal de izquierda a derecha `#65D6B0` → `#123D35` (`OrbCoreBrush`) | Sombra x 0, y 8, blur 24, `#65D6B0` al 14,9 %. No escala |

Entre el aro y el núcleo queda una separación por donde se ve el fondo. Tamaños usados en Figma: 120 dp (Completed), 28 dp (panel de Nutrition) y 16 dp (VORA INSIGHT).

## Tipografía (`Type.kt`)

Fuente **Manrope** embebida en `res/font` (`manrope_regular`, `manrope_medium`, `manrope_semibold` y `manrope_bold`, pesos 400, 500, 600 y 700). Viene de Google Fonts y tiene licencia SIL Open Font License 1.1. El interlineado es 1,4 veces el tamaño.

| Estilo de Material 3 | Tamaño / peso | Uso |
|---|---|---|
| `displaySmall` | 32 / 700 | Cifras grandes |
| `headlineLarge` | 28 / 700 | Titulares |
| `headlineMedium` | 26 / 700 | Titulares |
| `headlineSmall` | 24 / 600 | Escala alta |
| `titleLarge` | 20 / 700 | Título de pantalla |
| `titleMedium` | 18 / 600 | Título de tarjeta |
| `titleSmall` | 16 / 600 | Título de panel |
| `bodyLarge` | 15 / 400 | Texto destacado |
| `bodyMedium` | 13 / 400 | Cuerpo |
| `bodySmall` | 12 / 400 | Texto de apoyo, errores de campo |
| `labelLarge` | 14 / 600 | Botones y acciones |
| `labelMedium` | 11 / 600 | Barra inferior (activo 600, inactivo 500) |
| `labelSmall` | 10 / 600, espaciado 0,5 | Etiquetas en mayúsculas |

## Formas (`Shape.kt`)

| Token | Radio | Uso |
|---|---|---|
| `shapes.extraSmall` | 8 | Elementos chicos |
| `shapes.small` | 12 | Campos, mensajes, filas |
| `shapes.medium` | 16 | Tarjetas |
| `shapes.large` | 20 | Paneles |
| `shapes.extraLarge` | 24 | Panel oscuro |
| `PillShape` | Píldora | Botones, chips, etiquetas |

## Espaciado (`Spacing.kt`)

Escala: `xxs` 4 · `xs` 8 · `s` 12 · `m` 16 · `l` 20 · `xl` 24 · `xxl` 32 · `xxxl` 40 (dp).

| Token | Valor | Uso |
|---|---|---|
| `Spacing.screen` | 20 | Margen lateral de pantalla (ancho de referencia: 390 dp) |
| `Spacing.cardPadding` | 16 | Padding interno de tarjetas y paneles |
| `Spacing.groupGap` | 12 | Separación entre grupos |

Otras medidas de Figma: título → descripción 4, fila de comida con padding 12, barra de progreso de 4 de alto.

## Íconos

Los íconos son de [Lucide](https://lucide.dev) (licencia ISC), igual que en Figma: de trazo, con puntas y uniones redondeadas. Están en `res/drawable` como vectores con grilla de 24, y el color lo pone el componente con `tint`.

| Recurso | Ícono Lucide | Tamaño | Trazo | Uso |
|---|---|---|---|---|
| `ic_nav_home` | `house` | 20 | 2 | Barra inferior: Home |
| `ic_nav_workout` | `dumbbell` | 20 | 2 | Barra inferior: Workout |
| `ic_nav_nutrition` | `apple` | 20 | 2 | Barra inferior: Nutrition |
| `ic_nav_progress` | `chart-line` | 20 | 2 | Barra inferior: Progress |
| `ic_nav_profile` | `user` | 20 | 2 | Barra inferior: Profile |
| `ic_sparkles` | `sparkles` | 16 | 2 | Insights y recomendaciones de VORA |
| `ic_mic` | `mic` | 14,8 | 1,5 | Botón Voice |
| `ic_camera` | `camera` | 12 | 1,5 | Botón Photo |
| `ic_search` | `search` | 12 | 1,5 | Botón Search |
| `ic_arrow_back` | `arrow-left` | 24 | 2 | Volver en la barra superior |

Los tamaños y trazos salen del export de Figma. El trazo está en dp, así que en la grilla de 24 vale `trazo × 24 / tamaño`. Para sumar un ícono, buscalo en lucide.dev con el mismo nombre que la capa de Figma y copiá sus trazados con ese grosor.

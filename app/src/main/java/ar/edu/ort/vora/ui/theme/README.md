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

Fuente **Manrope** embebida en `res/font` (`manrope_regular`, `manrope_medium`, `manrope_semibold` y `manrope_bold`, pesos 400, 500, 600 y 700). Viene de Google Fonts y tiene licencia SIL Open Font License 1.1. El interlineado es "Auto", como en Figma: el natural de la fuente.

| Estilo | Tamaño / peso | Uso en Figma |
|---|---|---|
| `displaySmall` | 32 / 700 | Cifras grandes de pantalla |
| `headlineLarge` | 28 / 700 | Titulares |
| `headlineMedium` | 26 / 700 | Titulares |
| `headlineSmall` | 24 / 600 | Cifra destacada ("1,420") |
| `PanelTitleStyle` | 22 / 600 | Título de panel destacado ("Upper Body Strength") |
| `titleLarge` | 20 / 700 | Título de pantalla ("Edit profile") |
| `titleMedium` | 18 / 600 | Título de tarjeta |
| `titleSmall` | 16 / 600 | Título de panel chico ("Tell Vora what you ate") |
| `bodyLarge` | 15 / 400 | Valor de campo de formulario |
| `bodyMedium` | 13 / 400 | Cuerpo. En 600: acción contextual ("View History") y botón chico |
| `bodySmall` | 12 / 400 | Texto de apoyo, errores de campo |
| `labelLarge` | 14 / 600 | Acción principal ("Start Workout") |
| `labelMedium` | 11 / 600 | Barra inferior (activo 600, inactivo 500). En 400: tiempo ("2 hours ago") |
| `labelSmall` | 10 / 600, espaciado 0,5 | Etiquetas en mayúsculas ("TODAY", "FIRST NAME") |

## Formas (`Shape.kt`)

| Token | Radio | Uso |
|---|---|---|
| `shapes.extraSmall` | 8 | Elementos chicos |
| `shapes.small` | 12 | Campos, mensajes, filas de comida |
| `shapes.medium` | 16 | Tarjetas, consejo destacado |
| `Radius18Shape` | 18 | Radio intermedio de la familia |
| `shapes.large` | 20 | Paneles destacados |
| `shapes.extraLarge` | 24 | Paneles destacados grandes |
| `PillShape` | 100 (píldora) | Botones, chips, etiquetas |

## Espaciado (`Spacing.kt`)

Escala de Figma (espacio / valor): `s4` · `s6` · `s8` · `s10` · `s12` · `s14` · `s16` · `s20` · `s24` · `s32` · `s40` (dp). No es una grilla de 4: se usa el valor más cercano del patrón.

| Token | Valor | Uso |
|---|---|---|
| `Spacing.screen` | 20 | Margen lateral de pantalla (ancho de referencia: 390 dp, contenido de 350) |
| `Spacing.cardPadding` | 16 | Padding interno de tarjetas |
| `Spacing.groupGap` | 12 | Separación entre grupos |
| `Spacing.titleGap` | 4 | Título → descripción |

## Medidas de los elementos de interfaz

Tomadas de la página "Elementos de interfaz" del sistema de diseño de Figma. Se implementan en `ui/components`.

| Elemento | Especificación |
|---|---|
| Botón chico (Home) | 13 / 600, padding 8/16, radio 100 |
| Botón mediano (paneles) | 14 / 600, padding 10/20, radio 100 |
| Botón grande (Edit profile) | 15 / 700, padding 14/24, radio 100 |
| Botones sobre panel oscuro | CTA lima; "Details" con borde blanco translúcido; Photo y Search con relleno blanco translúcido |
| Prompts de voz | Chip blanco con borde |
| Campo de formulario | Etiqueta en mayúsculas a 6 del campo, valor 15 / 400, padding 12/16, borde 1 `#E4E9E6`, radio 12, separación entre campos 16 |
| Tarjeta | Blanca, borde 1, radio 16, padding 16 |
| Fila de comida | Radio 12, padding 12, separación de texto 2 |
| Panel destacado | Verde, radio 20, padding 20, separación 16, título 22 / 600 |
| Barra inferior | Borde superior 1; activo `#123D35` 600; inactivo `#9AA39F` 500; íconos 20 con trazo 2 |
| Anillo de progreso | 72, pista completa y arco superpuesto |
| Barra de progreso | 4 de alto, pista completa y relleno superpuesto |
| Señal / etiqueta | Punto de 8; etiqueta con radio 100 |
| Recomendación breve | Menta al 12,16 %, borde menta, texto verde, radio 12, padding 12 |
| Consejo destacado | Panel verde, contenido blanco, título menta, radio 16, padding 16 |

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
| `ic_info` | `info` | 16 | 2 | Recomendaciones informativas |
| `ic_mic` | `mic` | 14,8 | 1,5 | Botón Voice |
| `ic_camera` | `camera` | 12 | 1,5 | Botón Photo |
| `ic_search` | `search` | 12 | 1,5 | Botón Search |
| `ic_arrow_back` | `arrow-left` | 24 | 2 | Volver en la barra superior |

Los tamaños y trazos salen del export de Figma. El trazo está en dp, así que en la grilla de 24 vale `trazo × 24 / tamaño`. Para sumar un ícono, buscalo en lucide.dev con el mismo nombre que la capa de Figma y copiá sus trazados con ese grosor.

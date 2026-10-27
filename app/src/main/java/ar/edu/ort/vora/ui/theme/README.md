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
| `VoraDestructive` | `#DC2626` | | Ícono para quitar un ítem ("Foods to avoid") |

### Pantallas de acceso (Sign In / Sign Up)

Figma usa una paleta propia en Login, distinta de la del resto de la app:

| Token | Valor | Uso |
|---|---|---|
| `AuthLime` | `#CCE65E` | CTA "Sign In" / "Sign Up" y su sombra |
| `AuthFieldBackground` | `#F3F9F5` al 60 % | Fondo de los campos |
| `AuthFieldBorder` | `#E2EDE7` | Borde de campos y botones de acceso alternativo |
| `AuthScreenTint` | `#F3F9F5` | Tinte del fondo de la pantalla |
| `AuthTextSecondary` | `#6B8780` | Bajada, "Or continue with", notas y pie |
| `AuthAccent` | `#065F46` al 80 % | "ADAPTIVE INTELLIGENCE" y "Forgot your password?" |
| `AuthOutline` | `#6B7280` | Borde de los campos de Sign Up |
| `LogoLight` / `LogoMid` / `LogoDark` | `#86EFAC` / `#34D399` / `#0E382F` | Degradado radial del logo |
| `LogoBadgeText` | `#6EE7B7` | Texto del badge "AI" |

### Orbe VORA

El orbe (`ui/components/VoraOrb.kt`) tiene tres capas centradas. Las medidas de referencia son las del orbe de 120 dp y se escalan con el tamaño:

| Capa | Tamaño | Relleno | Opacidad / efecto |
|---|---|---|---|
| `orb-glow` | 100 % | Radial `#65D6B0` → `#123D35` (`OrbGlowBrush`) | 20 % por defecto, 30 % en el orbe de 28 dp (`glowAlpha`) |
| `orb-ring` | 89 % (107/120) | Aro de 7/120 de grosor, lineal de izquierda a derecha `#C9E86A` 0 → `#65D6B0` 0,5 → `#123D35` 1 (`OrbRingBrush`) | 80 % |
| `orb-core` | 75 % (90/120) | Lineal de izquierda a derecha `#65D6B0` → `#123D35` (`OrbCoreBrush`) | Sombra x 0, y 8, blur 24, `#65D6B0` al 14,9 %. No escala |

Para el error de micrófono (System Alert) el orbe va en gris con `muted = true` (`OrbMutedGlowBrush`, `OrbMutedRingBrush` y `OrbMutedCoreBrush`, hechos con los grises del tema).

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
| `Spacing.maxContentWidth` | 480 | Ancho máximo del contenido; en tablets o apaisado se centra |

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
| Barra superior | Fila de 350 con padding 12/20. Chevron suelto de 20 a 8 del título 20 / 600 (Add Food), o botón circular de 32 blanco con borde a 12 del título 20 / 700 (Profile, Voice). Variante con título centrado (Workout Overview) y acciones a la derecha (botón "…", etiqueta de estado, X). `titleDot`: punto menta de 6 a 6 del título (chat del Coach) |
| Botón circular | 32, radio 16, blanco con borde 1 `#E4E9E6`; translúcido sobre fondo oscuro |
| Chip | Radio 100, borde 1, padding 8/14, separación 6 hasta el ícono, texto 13 / 500 (elegido 600). Variantes: blanco, menta elegido, verde elegido, con check (borde menta) y para quitar (círculo con x `#DC2626` de 12) |
| Selector de período | Contenedor blanco tipo píldora; opción activa verde con texto blanco |
| Logo de acceso | Esfera de 64 con degradado radial, anillo interno blanco al 30 % de 20, sombras 0/4 blur 6 y 0/10 blur 15 al 10 %; badge "AI" `#0E382F` con borde blanco, padding 2/8, texto 10 / 700 |
| Campo de acceso | Etiqueta 12 / 700 (no en mayúsculas), valor 14 / 400, alto 55, radio 16, ícono a la izquierda |
| CTA de acceso | `#CCE65E`, radio 16, alto 56, padding 16/24, sombra 0/6 blur 20 al 35 %, flecha a la derecha |
| Acceso alternativo | Blanco con borde, radio 16, alto 46; Face ID en menta translúcida |
| Orbe del asistente | 170, mismas proporciones que el orbe de 120; al escuchar, brillo al 45 % y aro exterior de 115 % en menta al 30 % |
| Sobre panel oscuro (Coach) | "Cancel" con `VoraTextButton(color)`; barra de audio con `ProgressBar(trackColor = VoraWhiteTranslucent)`; pausa en `IconCircleButton(accent)` menta de 44 |
| Botón menta | `MintButton`: "Try again" del error de micrófono |
| Respuesta con audio | `ChatBubble(audioLabel)`: fila "Listen to response" debajo de una línea |
| Micrófono | Verde de 72 en reposo (sombra menta 0/8 blur 24 al 15 %); menta de 80 al escuchar; ícono de 28 con trazo 2,5 |

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
| `ic_chevron_left` | `chevron-left` | 20 | 2 | Volver en la barra superior |
| `ic_check` | `check` | 12 | 2 | Chip seleccionado |
| `ic_circle_x` | `circle-x` | 12 | 2 | Chip para quitar (`#DC2626`) |
| `ic_arrow_right` | `arrow-right` | 20 | 2 | CTA de acceso |
| `ic_mail` / `ic_lock` / `ic_user` / `ic_eye` | `mail` / `lock` / `user` / `eye` | 20 | 2 | Campos de acceso |
| `ic_scan_face` | `scan-face` | 20 | 2 | Acceso con Face ID |
| `ic_more` / `ic_bell` / `ic_close` / `ic_plus` / `ic_send` | `ellipsis` / `bell` / `x` / `plus` / `send` | 20 | 2 | Acciones de encabezado y chat |
| `ic_calendar` / `ic_flame` / `ic_shield_check` / `ic_chevron_down` | `calendar` / `flame` / `shield-check` / `chevron-down` | 14–16 | 2 | Entrenamiento recomendado, racha, notas de seguridad y selector de idioma |
| `ic_play` / `ic_pause` / `ic_repeat` / `ic_circle_dot` / `ic_rotate_cw` | `play` / `pause` / `repeat-2` / `circle-dot` / `rotate-cw` | 12–28 | 2 | Respuesta hablada del Coach, reintentar en el error de micrófono |
| `ic_audio_lines` | `audio-lines` | 32 | 2 | Centro del orbe del asistente |
| `ic_mic_large` | `mic` | 28 | 2,5 | Botón de micrófono del asistente |

Los logos de Apple y Google de "Or continue with" son marcas, no íconos de Lucide: `ic_logo_apple` (se tiñe como el resto) e `ic_logo_google` (conserva sus cuatro colores), de 22.

Los tamaños y trazos salen del export de Figma. El trazo está en dp, así que en la grilla de 24 vale `trazo × 24 / tamaño`. Para sumar un ícono, buscalo en lucide.dev con el mismo nombre que la capa de Figma y copiá sus trazados con ese grosor.

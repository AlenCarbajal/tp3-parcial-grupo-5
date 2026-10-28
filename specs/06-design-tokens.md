# Tokens de diseño (tomados de "Sistema de diseño — VORA", Figma)
Fuente: página del sistema de diseño (valores observados 06/10/2026). Tipografía: **Manrope** (Regular 400, Medium 500, SemiBold 600, Bold 700). Mobile 390 px, margen de pantalla 20 px.

## Colores
| Token | Valor | Uso |
|---|---|---|
| Primary (verde principal) | #123D35 | Acciones, paneles, progreso |
| Mint (menta) | #65D6B0 | Coach, voz, carbohidratos |
| Lime (lima) | #C9E86A | CTA destacado en paneles oscuros, grasas |
| Surface | #FFFFFF | Tarjetas y campos |
| Background | #F7F8F5 | Fondo de pantalla |
| Border | #E4E9E6 | Separadores, bordes |
| Text primary | #17211F | |
| Text secondary | #68736F | |
| Text muted | #9AA39F | Metadatos, tab inactivo |
| Error / atención | #D95D5D | "NOT STARTED", Log out |
| Success | #27AE60 | |
| Mint translúcida | #65D6B0 con alfa 12,16 % (0x1F) | Mensajes y recomendaciones |

## Tipografía (px → sp)
10, 11, 12, 13, 14, 15, 16, 18, 20, 24 (+22, 26, 28 titulares, 32 cifras).
- Cuerpo: 13 / 400. Acción: 13–14 / 600. Título tarjeta: 18 / 600. Título pantalla: 20 / 700. Escala alta: 24 / 600.
- Etiquetas en mayúsculas: 10 / 600. Tab bar activo: 11 / 600, inactivo 11 / 500.

## Espaciado
4, 6, 8, 10, 12, 14, 16, 20, 24, 32, 40. Padding de tarjeta 16, gap entre grupos 12, título→descripción 4, margen 20, fila de comida padding 12.

## Radios
8, 12 (campos/mensajes/filas), 16 (tarjetas), 18, 20–24 (paneles), 100 (píldora: botones, chips, etiquetas).

## Componentes a crear (Alén, T11)
PrimaryButton (verde, píldora), LimeButton (CTA sobre panel oscuro), SecondaryButton (borde), Chip, InfoCard (blanca, r16), DarkPanel (verde, r20), CoachMessage (menta translúcida r12), VoraTextField (r12, borde, label en mayúsculas), ProgressRing, ProgressBar (4 px), BottomBar (5 destinos, 20 px icono), TopBar con atrás.

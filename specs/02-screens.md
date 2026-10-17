# Inventario de pantallas (COMPLETAR con los nombres reales de Figma)
Fuente: resumen del docente (37 pantallas en 7 flujos). Los nombres deben contrastarse con Figma.
Estado: F = funcional (Retrofit/Room) · D = demostración

| # | Flujo | Pantalla | Ruta | Destinos | Estado | Responsable |
|---|---|---|---|---|---|---|
| 1 | Acceso | Iniciar sesión | login | home | D | |
| 2 | Acceso | Crear cuenta | signup | home | D | |
| 3 | Inicio | Dashboard | home | workout, nutrition, progress, profile, coach, notifications | D | |
| 4 | Inicio | Notificaciones | notifications | atrás | D | |
| 5-10 | Asistente | Listo / Escuchando / Procesando / Respuesta / Conversación / Error de micrófono | coach | (estados de una pantalla) | D | |
| 11 | Entrenamiento | Dashboard | workout | plan, detalle | F | |
| 12 | Entrenamiento | Plan semanal | workout/plan | detalle | D | |
| 13 | Entrenamiento | Detalle | workout/{id} | preparación | F | |
| 14 | Entrenamiento | Preparación | workout/{id}/prep | activo | D | |
| 15 | Entrenamiento | Activo | workout/{id}/active | guía, completado | D | |
| 16 | Entrenamiento | Guía | workout/{id}/guide | atrás | D | |
| 17 | Entrenamiento | Completado | workout/{id}/done | home | D | |
| 18-25 | Nutrición | Dashboard · Comidas del día · Agregar · Voz · Análisis · Foto · Revisión · Detalle | nutrition/... | | F (comidas) / D | |
| 26-31 | Progreso | Resumen del día · Dashboard · Entrenamiento · Nutrición · Tendencias · Revisión semanal | progress/... | | D | |
| 32-37 | Perfil | Dashboard · Editar perfil · Objetivos · Pref. nutricionales · Voz e IA · Configuración | profile/... | | D | |

(Las rutas y destinos son una propuesta inicial: ajustarlas a lo que muestre el prototipo de Figma.)

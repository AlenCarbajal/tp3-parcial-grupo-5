# Constitution – VORA Coach (reglas fijas del equipo)

1. Android, Kotlin, Jetpack Compose. Paquete base: `ar.edu.ort.vora`.
2. Arquitectura en capas: UI (Compose) -> ViewModel -> Repository -> fuentes (Retrofit / Room DAO).
3. La UI NUNCA llama a Retrofit ni a un DAO. Solo habla con un ViewModel.
4. Tres modelos separados: DTO (JSON), Model (app), Entity (Room). Con mappers explícitos.
5. Hilt para inyección: @HiltAndroidApp, @AndroidEntryPoint, @HiltViewModel, repos por constructor, módulos para Retrofit/Room/DAO.
6. ViewModel expone estado observable (StateFlow) y usa coroutines. Rotar no vuelve a consultar.
7. Un solo tema (`ui/theme`) y componentes reutilizables (`ui/components`). Nada de colores/tamaños sueltos en pantallas.
8. Error de red NUNCA borra la caché Room ni los registros locales.
9. No inventar endpoints de login ni de guardado de comidas. Lo que se guarda, se guarda en Room.
10. No pedir permisos (micrófono/cámara) que no se usen.
11. Git: ramas `feature/<area>-<tarea>`, Pull Request revisado por otra persona, commits chicos con mensaje claro y el ID de spec (ej: "RF02: cachea detalle en Room").
12. Cambió el comportamiento -> se actualiza la spec ANTES que el código.
13. El uso de IA generativa se declara en el README.

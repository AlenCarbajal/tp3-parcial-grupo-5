# Requisitos y criterios de aceptación
Estado: [ ] pendiente  [~] en curso  [x] verificado

## Obligatorios funcionales
- RF01 [ ] Catálogo: GET /v1/workouts muestra nombre, nivel, duración. Cada ítem abre SU detalle con GET /v1/workouts/{id} (ejercicios con series, reps, instrucciones).
- RF02 [ ] Caché Room: catálogo y detalles guardados. Server detenido + detalle ya visto => se ve desde Room. Detalle nunca descargado => "No disponible sin conexión".
- RF03 [ ] Carga manual de comida: nombre, fecha, tipo (desayuno/almuerzo/merienda/cena), kcal, proteínas, carbohidratos, grasas. Validar nombre no vacío y valores >= 0.
- RF04 [ ] Persistencia Room: listado por fecha; borrar con confirmación; sobrevive a cerrar/abrir la app; el total diario cambia al agregar/eliminar.
- RF05 [ ] Estados remotos: carga, contenido, vacío, error con botón Reintentar. Fallo de red no borra caché ni registros.
- RF06 [ ] DTO / Model / Entity separados; Repository coordina fuentes; la UI no toca Retrofit ni DAO.
- RF07 [ ] ViewModel con estado observable (catálogo y comidas), coroutines; rotación sin consultas duplicadas ni pérdida de estado.
- RF08 [ ] Hilt (sobre Dagger): Application, EntryPoint, HiltViewModel, repos por constructor, módulos que proveen Retrofit/Room/DAO. Saber explicar qué construye Hilt.

## UI
- UI01 [ ] Maquetación fiel a Figma: estructura, colores, tipografías, espaciados, iconos, estados. Sin textos cortados ni superposiciones.
- UI02 [ ] Tema único + componentes reutilizables (botón, card, campo, bottom nav). Un arreglo se propaga a todas las pantallas.
- UI03 [ ] Se ve bien en el tamaño de referencia de Figma y en otro emulador; el contenido largo hace scroll.

## Navegación
- NAV01 [ ] README con cada pantalla de Figma, su destino y estado (funcional/demo).
- NAV02 [ ] Acceso, Home, Workout, Nutrition, Progress, Profile y Coach conectados según Figma; volver/cerrar/cancelar; botón atrás del sistema coherente.
- NAV03 [ ] Todo control visible lleva a su destino (o muestra resultado de ejemplo / aviso "modo demostración").
- NAV04 [ ] Cada entrenamiento abre su propio detalle; las selecciones se mantienen en el recorrido.
- NAV05 [ ] Coach (escucha, procesando, respuesta) y revisión de análisis de comida simulados con estados; sin pedir permisos que no se usan.

## Entrega
- ENT01 [ ] Repo único en GitHub, commits de TODOS los integrantes, tag de entrega (ej: entrega-tp3).
- ENT02 [ ] Compila y corre sin errores; README con integrantes, link a Figma, inventario, URL del server, video de demo, 5 comparaciones visuales, versiones usadas, declaración de IA.
- Hito 1 (21/oct): compila, tema + componentes, inventario, navegación entre áreas, 1 consulta Retrofit, 1 insert+lectura Room, todos con >=1 commit.
- Hito 2 / entrega (28/oct): todo completo y verificado.

## Demo (5 pasos)
1 Recorrer pantallas con atrás y bottom nav. 2 Catálogo remoto, abrir 2 detalles distintos. 3 Registrar comida, cerrar/abrir, verificar, borrar, ver total. 4 Server apagado: detalle cacheado OK; probar error, vacío, reintento. 5 Mostrar commits de cada uno; explicar ViewModel, repos, Hilt.

## Extras (solo con el núcleo cerrado)
Firebase hasta 10 pts (Auth 4, Firestore 4, reglas 2). Otras ampliaciones hasta 3 pts dentro del máximo de 10.

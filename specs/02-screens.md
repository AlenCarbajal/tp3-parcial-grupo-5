# Inventario de pantallas (37 de Figma + 1 extra obligatoria)
Fuente: Figma "Parcial Domiciliario 7 Oct 26", páginas de flujo. Estado: F = funcional (Retrofit/Room) · D = demostración.
Barra inferior (5 destinos constantes): Home · Workout · Nutrition · Progress · Profile. Visible en Home, Workout, Nutrition, Progress, Profile (y en Notifications, Review/Analyse/Lunch en Nutrition, sub-pantallas de Progress de nivel 1).

## 1. Login Flow (2)
| # | Pantalla (Figma) | Ruta | Va a | Estado |
|---|---|---|---|---|
| 1 | Sign In | login | home (Sign In), signup ("Sign up here") | D |
| 2 | Sign Up | signup | home (Sign Up), login ("Sign In") | D |

## 2. Home Flow (2)
| # | Pantalla | Ruta | Va a | Estado |
|---|---|---|---|---|
| 3 | Home (Good morning, Alex Morgan) | home | notifications (campana), coach ("Start voice session"), workout/{id}/prep ("Start workout"), workout ("Start my workout"), nutrition/add ("Log breakfast"), progress/today ("Today's Overview") | D |
| 4 | Notifications | notifications | atrás; weekly review → progress/weekly | D |

## 3. AI Voice Flow (6) — rutas propias, mismo flujo
| # | Pantalla | Ruta | Va a | Estado |
|---|---|---|---|---|
| 5 | VORA Coach (How can I help?) | coach | coach/listening ("Tap to talk"), coach/chat ("Type instead"), atrás | D |
| 6 | Listening | coach/listening | coach/processing (Cancel → atrás) | D |
| 7 | Processing | coach/processing | coach/speaking (Cancel → atrás) | D |
| 8 | VORA Speaking | coach/speaking | coach/chat ("Tap to Interrupt") | D |
| 9 | VORA Coach (conversación) | coach/chat | atrás | D |
| 10 | System Alert (error de micrófono) | coach/error | coach/listening ("Try again"), coach/chat ("Type instead") | D |

## 4. Workout Flow (7)
| # | Pantalla | Ruta | Va a | Estado |
|---|---|---|---|---|
| 11 | Workouts (dashboard) | workout | workout/plan (icono calendario), workout/{id}/prep (Start Workout), workout/{id} (Details), progress ("View progress") | **F** (lista Retrofit+Room) |
| 12 | Your Plan | workout/plan | workout/{id} (tarjeta), workout/{id}/prep | D |
| 13 | Workout Overview | workout/{id} | workout/{id}/prep (Start Workout); fila de ejercicio → workout/{id}/guide/{exerciseId} | **F** (detalle Retrofit+Room) |
| 14 | Get Ready | workout/{id}/prep | workout/{id}/active (Start Workout), atrás (X) | D |
| 15 | Active (Exercise 2 of 5) | workout/{id}/active | workout/{id}/done (Finish Workout) | D |
| 16 | Exercise Guide | workout/{id}/guide/{exerciseId} | atrás (Back to Workout) | D (datos del detalle) |
| 17 | Completed (You did well!) | workout/{id}/done | home (Done), atrás a workout/{id} (Back to Workout) | D |

## 5. Nutrition Flow (8) + 1 extra
| # | Pantalla | Ruta | Va a | Estado |
|---|---|---|---|---|
| 18 | Nutrition (dashboard) | nutrition | nutrition/meals ("View History"/comidas), nutrition/voice (Voice), nutrition/photo (Photo), nutrition/search (Search) | **F** (total del día de Room) |
| 19 | Today's Meals | nutrition/meals | nutrition/add (+ Add Dinner), nutrition/meal/{id} | **F** (lista por fecha, borrar) |
| 20 | Add Food | nutrition/add | nutrition/voice, nutrition/photo, nutrition/search, **nutrition/manual** | D |
| 21 | Voice Logging | nutrition/voice | nutrition/analyzing (Done Speaking), nutrition/manual (Type instead) | D |
| 22 | VORA AI (Analyzing Meal) | nutrition/analyzing | nutrition/review (tras simular), atrás (Cancel) | D |
| 23 | Analyse Meal (foto) | nutrition/photo | nutrition/review (Review & Add), Retake Photo | D |
| 24 | Review Meal | nutrition/review | nutrition/meals (Add to Lunch / Save without logging) | D |
| 25 | Lunch (detalle de comida) | nutrition/meal/{id} | atrás; Edit/Duplicate Meal | **F** (borrar con confirmación) |
| **extra** | **Registro manual (NO está en Figma)** | nutrition/manual | nutrition/meals | **F** (RF03: formulario con validación → Room) |

> "Log Manually" y "Type instead" existen en Figma pero no tienen pantalla destino. El parcial exige carga manual con validación, así que hay que crear esa pantalla reutilizando los componentes del sistema de diseño (campos, botón principal). Documentarla en el README como pantalla adicional.
> "Search Database" (nutrition/search): no hay pantalla en Figma; se resuelve como nutrition/manual o se deja deshabilitada y se documenta.

## 6. Progress Flow (6)
| # | Pantalla | Ruta | Va a | Estado |
|---|---|---|---|---|
| 26 | Today's Overview | progress/today | workout, atrás | D |
| 27 | Your progress (dashboard) | progress | progress/workout, progress/nutrition, progress/health ("Activity View"), progress/weekly ("See weekly review") | D |
| 28 | Workout progress | progress/workout | atrás | D |
| 29 | Nutrition progress | progress/nutrition | atrás | D |
| 30 | Health trends | progress/health | atrás | D |
| 31 | Your weekly review | progress/weekly | coach ("Ask VORA"), atrás (Done) | D |

## 7. Profile Flow (6)
| # | Pantalla | Ruta | Va a | Estado |
|---|---|---|---|---|
| 32 | Profile | profile | profile/edit, profile/goals, profile/nutrition, profile/connected → settings, profile/settings (engranaje) | D |
| 33 | Edit profile | profile/edit | atrás (Save changes) | D |
| 34 | Your goals | profile/goals | atrás (Save preferences) | D |
| 35 | Nutrition preferences | profile/nutrition | atrás (Save preferences) | D |
| 36 | VORA Coach (Voz e IA) | profile/voice | atrás | D |
| 37 | Settings | profile/settings | profile/voice, atrás; Log out → login | D |

Nota: "Your Preferences" en Profile lleva a profile/nutrition; "VORA Personalisation → Review preferences" a profile/goals. Verificar en el prototipo de Figma y ajustar.

## Reglas de navegación
- Bottom bar: navegar con `launchSingleTop` + `restoreState` + `popUpTo(startDestination){saveState=true}`.
- Atrás del sistema vuelve a la pantalla previa lógica; desde un destino de la bottom bar vuelve a home.
- Log out limpia el back stack y va a login.

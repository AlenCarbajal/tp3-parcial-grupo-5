
package ar.edu.ort.vora.navigation

import androidx.activity.compose.LocalActivity
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import ar.edu.ort.vora.progress.ProgressDashboardScreen
import ar.edu.ort.vora.progress.ProgressScreen
import ar.edu.ort.vora.ui.auth.SignInScreen
import ar.edu.ort.vora.ui.auth.SignUpScreen
import ar.edu.ort.vora.ui.coach.CoachChatScreen
import ar.edu.ort.vora.ui.coach.CoachErrorScreen
import ar.edu.ort.vora.ui.coach.CoachIdleScreen
import ar.edu.ort.vora.ui.coach.CoachListeningScreen
import ar.edu.ort.vora.ui.coach.CoachProcessingScreen
import ar.edu.ort.vora.ui.coach.CoachSpeakingScreen
import ar.edu.ort.vora.ui.components.VoraBottomBar
import ar.edu.ort.vora.ui.home.HomeScreen
import ar.edu.ort.vora.ui.home.NotificationsScreen

// Destinos de la barra inferior, en el orden de VoraBottomBarItems (íconos y textos de Figma).
private val bottomTabs = listOf(Routes.HOME, Routes.WORKOUT, Routes.NUTRITION, Routes.PROGRESS, Routes.PROFILE)

// Acceso y Coach: dibujan su fondo detrás de la barra de estado y manejan sus propios márgenes del sistema.
private val edgeToEdgeRoutes = setOf(
    Routes.LOGIN, Routes.SIGNUP,
    Routes.COACH, Routes.COACH_LISTENING, Routes.COACH_PROCESSING, Routes.COACH_SPEAKING, Routes.COACH_CHAT, Routes.COACH_ERROR,
)

private val darkRoutes = setOf(Routes.COACH_LISTENING, Routes.COACH_PROCESSING, Routes.COACH_SPEAKING)

// Rutas donde la bottom bar se ve (según specs/02-screens.md).
private val routesWithBottomBar = setOf(
    Routes.HOME, Routes.WORKOUT, Routes.NUTRITION, Routes.PROGRESS, Routes.PROFILE,
    Routes.NOTIFICATIONS, Routes.NUTRITION_PHOTO, Routes.NUTRITION_REVIEW, Routes.NUTRITION_MEAL,
    Routes.PROGRESS_TODAY, Routes.PROGRESS_WORKOUT, Routes.PROGRESS_NUTRITION,
    Routes.PROGRESS_HEALTH, Routes.PROGRESS_WEEKLY,
)

@Composable
fun VoraNavHost(navController: NavHostController = rememberNavController()) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route
    val showBottomBar = currentRoute in routesWithBottomBar
    // Las pantallas verdes del Coach van con íconos claros en la barra de estado; el resto, oscuros.
    val window = LocalActivity.current?.window
    val view = LocalView.current
    val darkStatusBar = currentRoute in darkRoutes
    SideEffect { window?.let { WindowCompat.getInsetsController(it, view).isAppearanceLightStatusBars = !darkStatusBar } }
    // Notifications no tiene pestaña propia: en Figma queda marcado Home.
    val selectedTab = if (currentRoute == Routes.NOTIFICATIONS) 0 else bottomTabs.indexOfFirst { currentRoute?.startsWith(it) == true }
    val go: (String) -> Unit = { navController.navigate(it) }
    val goTab: (String) -> Unit = { route ->
        navController.navigate(route) {
            popUpTo(Routes.HOME) { saveState = true }
            launchSingleTop = true
            restoreState = true
        }
    }
    // Al entrar se saca el acceso de la pila: desde Home, atrás cierra la app en vez de volver al login.
    val enterApp: () -> Unit = { navController.navigate(Routes.HOME) { popUpTo(Routes.LOGIN) { inclusive = true } } }
    val back: () -> Unit = { navController.popBackStack() }
    // Cada paso del Coach reemplaza al anterior: atrás vuelve a la pantalla inicial del asistente.
    val coachStep: (String) -> Unit = { route ->
        navController.navigate(route) {
            popUpTo(Routes.COACH)
            launchSingleTop = true
        }
    }
    val backDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher
    val goBack: () -> Unit = { backDispatcher?.onBackPressed() }

  <<<<<<< feature/lisandro-coach-progress-profile
          // PROGRESS - TODAY
          composable(Routes.PROGRESS_TODAY) {
              TodaysOverviewScreen(
                  onStartWorkout = {
                      navController.navigate(Routes.WORKOUT)
                  },
                  onOpenDashboard = {
                      navController.navigate(Routes.PROGRESS_DASHBOARD)
                  }
              )
          }

          // PROGRESS - DASHBOARD
          composable(Routes.PROGRESS_DASHBOARD) {
              ProgressDashboardScreen(
                  onNavigate = { route ->
                      navController.navigate(route)
                  }
              )
          }

          // PROGRESS - WORKOUT
          composable(Routes.PROGRESS_WORKOUT) {
              WorkoutProgressScreen(
                  onBack = {
                      navController.popBackStack()
                  }
              )
          }

          // PROGRESS - NUTRITION
          composable(Routes.PROGRESS_NUTRITION) {
              NutritionProgressScreen(
                  onBack = {
                      navController.popBackStack()
                  }
              )
          }

          // PROGRESS - HEALTH
          composable(Routes.PROGRESS_HEALTH) {
              HealthTrendsScreen(
                  onBack = {
                      navController.popBackStack()
                  }
              )
          }

          // PROGRESS - WEEKLY REVIEW
          composable(Routes.PROGRESS_WEEKLY) {
              WeeklyReviewScreen(
                  onBack = {
                      navController.popBackStack()
                  },
                  onAskCoach = {
                      navController.navigate(Routes.COACH)
                  }
              )
          }

          // PROFILE
          composable(Routes.PROFILE) {
              PlaceholderScreen(
                  title = "Perfil",
                  links = arrayOf(
                      Routes.HOME to "Volver al inicio"
                  ),
                  onNavigate = { route ->
                      navController.navigate(route)
                  }
              )

      Scaffold(
          bottomBar = {
              if (showBottomBar) {
                  VoraBottomBar(selectedIndex = selectedTab, onSelect = { goTab(bottomTabs[it]) })
              }
          },
          contentWindowInsets = if (currentRoute in edgeToEdgeRoutes) WindowInsets(0) else ScaffoldDefaults.contentWindowInsets,
      ) { padding ->
          NavHost(
              navController = navController,
              startDestination = Routes.LOGIN,
              modifier = Modifier
                  .padding(padding)
                  .consumeWindowInsets(padding),
          ) {
              // Login
              composable(Routes.LOGIN) {
                  SignInScreen(onSignIn = enterApp, onSignUp = { navController.navigate(Routes.SIGNUP) { launchSingleTop = true } }, onBack = goBack)
              }
              composable(Routes.SIGNUP) {
                  SignUpScreen(
                      onSignUp = enterApp,
                      onSignIn = { navController.navigate(Routes.LOGIN) { popUpTo(Routes.LOGIN) { inclusive = true } } },
                      onBack = goBack,
                  )
              }

              // Home
              composable(Routes.HOME) {
                  HomeScreen(
                      onNotifications = { go(Routes.NOTIFICATIONS) },
                      onVoiceSession = { go(Routes.COACH) },
                      onStartWorkout = { go(Routes.workoutPrep("1")) },
                      onLogMeal = { go(Routes.NUTRITION_ADD) },
                      onTodayOverview = { go(Routes.PROGRESS_TODAY) },
                  )
              }
              composable(Routes.NOTIFICATIONS) {
                  NotificationsScreen(
                      onWeeklyReview = { go(Routes.PROGRESS_WEEKLY) },
                      onWorkout = { goTab(Routes.WORKOUT) },
                      onNutrition = { goTab(Routes.NUTRITION) },
                      onProgress = { goTab(Routes.PROGRESS) },
                  )
              }

              // Coach (asistente VORA)
              composable(Routes.COACH) {
                  CoachIdleScreen(onBack = back, onTalk = { go(Routes.COACH_LISTENING) }, onTypeInstead = { go(Routes.COACH_CHAT) })
              }
              composable(Routes.COACH_LISTENING) {
                  CoachListeningScreen(onBack = back, onFinished = { heard -> coachStep(if (heard) Routes.COACH_PROCESSING else Routes.COACH_ERROR) })
              }
              composable(Routes.COACH_PROCESSING) {
                  CoachProcessingScreen(onBack = back, onAnswer = { coachStep(Routes.COACH_SPEAKING) })
              }
              composable(Routes.COACH_SPEAKING) {
                  CoachSpeakingScreen(onBack = back, onInterrupt = { coachStep(Routes.COACH_CHAT) })
              }
              composable(Routes.COACH_CHAT) {
                  CoachChatScreen(onBack = back, onListen = { go(Routes.COACH_SPEAKING) })
              }
              composable(Routes.COACH_ERROR) {
                  CoachErrorScreen(
                      onBack = back,
                      onTryAgain = { coachStep(Routes.COACH_LISTENING) },
                      onTypeInstead = { coachStep(Routes.COACH_CHAT) },
                  )
              }

              // Workout
              composable(Routes.WORKOUT) {
                  PlaceholderScreen(
                      "Workouts",
                      Routes.WORKOUT_PLAN to "Plan",
                      Routes.workoutPrep("1") to "Start Workout",
                      Routes.workoutDetail("1") to "Details",
                      Routes.PROGRESS to "View progress",
                      onNavigate = go,
                  )
              }
              composable(Routes.WORKOUT_PLAN) { PlaceholderScreen("Your Plan", Routes.workoutDetail("1") to "Tarjeta", onNavigate = go) }
              composable(Routes.WORKOUT_DETAIL) {
                  PlaceholderScreen("Workout Overview", Routes.workoutPrep("1") to "Start Workout", Routes.workoutGuide("1", "1") to "Ejercicio", onNavigate = go)
              }
              composable(Routes.WORKOUT_PREP) { PlaceholderScreen("Get Ready", Routes.workoutActive("1") to "Start Workout", onNavigate = go) }
              composable(Routes.WORKOUT_ACTIVE) { PlaceholderScreen("Active", Routes.workoutDone("1") to "Finish Workout", onNavigate = go) }
              composable(Routes.WORKOUT_GUIDE) { PlaceholderScreen("Exercise Guide") {} }
              composable(Routes.WORKOUT_DONE) { PlaceholderScreen("Completed", Routes.HOME to "Done", onNavigate = go) }

              // Nutrition
              composable(Routes.NUTRITION) {
                  PlaceholderScreen(
                      "Nutrition",
                      Routes.NUTRITION_MEALS to "Comidas",
                      Routes.NUTRITION_VOICE to "Voice",
                      Routes.NUTRITION_PHOTO to "Photo",
                      Routes.NUTRITION_MANUAL to "Search",
                      onNavigate = go,
                  )
              }
              composable(Routes.NUTRITION_MEALS) {
                  PlaceholderScreen("Today's Meals", Routes.NUTRITION_ADD to "Add Dinner", Routes.nutritionMeal("1") to "Detalle", onNavigate = go)
              }
              composable(Routes.NUTRITION_ADD) {
                  PlaceholderScreen("Add Food", Routes.NUTRITION_VOICE to "Voice", Routes.NUTRITION_PHOTO to "Photo", Routes.NUTRITION_MANUAL to "Manual", onNavigate = go)
              }
              composable(Routes.NUTRITION_VOICE) {
                  PlaceholderScreen("Voice Logging", Routes.NUTRITION_ANALYZING to "Done Speaking", Routes.NUTRITION_MANUAL to "Type instead", onNavigate = go)
              }
              composable(Routes.NUTRITION_ANALYZING) { PlaceholderScreen("Analyzing Meal", Routes.NUTRITION_REVIEW to "Siguiente", onNavigate = go) }
              composable(Routes.NUTRITION_PHOTO) { PlaceholderScreen("Analyse Meal", Routes.NUTRITION_REVIEW to "Review & Add", onNavigate = go) }
              composable(Routes.NUTRITION_REVIEW) { PlaceholderScreen("Review Meal", Routes.NUTRITION_MEALS to "Add to Lunch", onNavigate = go) }
              composable(Routes.NUTRITION_MEAL) { PlaceholderScreen("Lunch") {} }
              composable(Routes.NUTRITION_MANUAL) { PlaceholderScreen("Registro manual", Routes.NUTRITION_MEALS to "Guardar", onNavigate = go) }

              // Progress (pantallas reales de Lisandro)
              composable(Routes.PROGRESS) { ProgressDashboardScreen() }
              composable(Routes.PROGRESS_TODAY) { ProgressScreen() }
              composable(Routes.PROGRESS_WORKOUT) { PlaceholderScreen("Workout progress") {} }
              composable(Routes.PROGRESS_NUTRITION) { PlaceholderScreen("Nutrition progress") {} }
              composable(Routes.PROGRESS_HEALTH) { PlaceholderScreen("Health trends") {} }
              composable(Routes.PROGRESS_WEEKLY) { PlaceholderScreen("Your weekly review", Routes.COACH to "Ask VORA", onNavigate = go) }

              // Profile
              composable(Routes.PROFILE) {
                  PlaceholderScreen(
                      "Perfil",
                      Routes.PROFILE_EDIT to "Edit",
                      Routes.PROFILE_GOALS to "Goals",
                      Routes.PROFILE_NUTRITION to "Nutrition prefs",
                      Routes.PROFILE_SETTINGS to "Settings",
                      onNavigate = go,
                  )
              }
              composable(Routes.PROFILE_EDIT) { PlaceholderScreen("Edit profile") {} }
              composable(Routes.PROFILE_GOALS) { PlaceholderScreen("Your goals") {} }
              composable(Routes.PROFILE_NUTRITION) { PlaceholderScreen("Nutrition preferences") {} }
              composable(Routes.PROFILE_VOICE) { PlaceholderScreen("VORA Coach (Voz e IA)") {} }
              composable(Routes.PROFILE_SETTINGS) {
                  PlaceholderScreen("Settings", Routes.PROFILE_VOICE to "Voz e IA", Routes.LOGIN to "Log out") {
                      if (it == Routes.LOGIN) {
                          navController.navigate(Routes.LOGIN) { popUpTo(navController.graph.id) { inclusive = true } }
                      } else {
                          navController.navigate(it)
                      }
                  }
              }
  >>>>>>> develop
        }
    }
}

@Composable
private fun PlaceholderScreen(
    title: String,
    links: Array<Pair<String, String>> = emptyArray(),
    onNavigate: (String) -> Unit = {}
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = title)

        links.forEach { (route, label) ->
            Button(
                onClick = {
                    onNavigate(route)
                }
            ) {
                Text(text = label)
            }
        }
    }
}

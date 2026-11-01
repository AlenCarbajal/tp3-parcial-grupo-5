package ar.edu.ort.vora.domain.model

import org.junit.Assert.assertEquals
import org.junit.Test

// RF04: total diario de calorías y macros.
class DailyTotalsTest {

    private fun meal(kcal: Int, carbs: Double, fat: Double, protein: Double) = Meal(
        name = "Comida",
        date = "2026-11-01",
        type = MealType.LUNCH,
        kcal = kcal,
        carbs = carbs,
        fat = fat,
        protein = protein,
    )

    @Test
    fun listaVacia_dejaTodoEnCero() {
        assertEquals(DailyTotals(), emptyList<Meal>().toDailyTotals())
    }

    @Test
    fun unaComida_esSuPropioTotal() {
        val totals = listOf(meal(420, 24.0, 18.0, 38.0)).toDailyTotals()

        assertEquals(420, totals.kcal)
        assertEquals(24.0, totals.carbs, 0.001)
        assertEquals(18.0, totals.fat, 0.001)
        assertEquals(38.0, totals.protein, 0.001)
    }

    @Test
    fun variasComidas_suman() {
        val totals = listOf(
            meal(420, 24.0, 18.0, 38.0),
            meal(300, 10.5, 5.5, 12.0),
        ).toDailyTotals()

        assertEquals(720, totals.kcal)
        assertEquals(34.5, totals.carbs, 0.001)
        assertEquals(23.5, totals.fat, 0.001)
        assertEquals(50.0, totals.protein, 0.001)
    }
}

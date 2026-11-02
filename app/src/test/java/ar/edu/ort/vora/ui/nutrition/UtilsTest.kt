package ar.edu.ort.vora.ui.nutrition

import ar.edu.ort.vora.R
import ar.edu.ort.vora.domain.model.MealType
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

// Fechas ISO usadas para listar las comidas por día.
class UtilsTest {

    @Test
    fun shiftIso_avanzaYRetrocedeUnDia() {
        assertEquals("2026-11-02", shiftIso("2026-11-01", 1))
        assertEquals("2026-10-31", shiftIso("2026-11-01", -1))
    }

    @Test
    fun shiftIso_cruzaFinDeMesYDeAnio() {
        assertEquals("2026-03-01", shiftIso("2026-02-28", 1))
        assertEquals("2027-01-01", shiftIso("2026-12-31", 1))
    }

    @Test
    fun isValidIsoDate_aceptaFechasReales() {
        assertTrue(isValidIsoDate("2026-11-01"))
        assertTrue(isValidIsoDate("2028-02-29")) // 2028 es bisiesto
    }

    @Test
    fun isValidIsoDate_rechazaFormatosYFechasInvalidas() {
        assertFalse(isValidIsoDate("2026-2-3"))
        assertFalse(isValidIsoDate("2027-02-29")) // 2027 no es bisiesto
        assertFalse(isValidIsoDate("hola"))
        assertFalse(isValidIsoDate(""))
    }

    @Test
    fun label_deCadaTipoDeComida() {
        assertEquals(R.string.nutrition_meal_breakfast, MealType.BREAKFAST.labelRes)
        assertEquals(R.string.nutrition_meal_lunch, MealType.LUNCH.labelRes)
        assertEquals(R.string.nutrition_meal_dinner, MealType.DINNER.labelRes)
        assertEquals(R.string.nutrition_meal_snack, MealType.SNACK.labelRes)
    }
}

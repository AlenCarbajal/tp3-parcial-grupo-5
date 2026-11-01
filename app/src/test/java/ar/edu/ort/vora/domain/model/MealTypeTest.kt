package ar.edu.ort.vora.domain.model

import org.junit.Assert.assertEquals
import org.junit.Test

class MealTypeTest {

    @Test
    fun fromName_entiendeInglesYEspanol_sinImportarMayusculas() {
        assertEquals(MealType.BREAKFAST, MealType.fromName("desayuno"))
        assertEquals(MealType.LUNCH, MealType.fromName("ALMUERZO"))
        assertEquals(MealType.DINNER, MealType.fromName("Dinner"))
        assertEquals(MealType.SNACK, MealType.fromName("merienda"))
    }

    @Test
    fun fromName_conUnValorRaro_devuelveSnack() {
        assertEquals(MealType.SNACK, MealType.fromName("xyz"))
    }
}

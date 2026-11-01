package ar.edu.ort.vora.ui.nutrition

import ar.edu.ort.vora.domain.model.MealType
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

// RF03: validación del registro manual de comidas.
class MealFormTest {

    private fun validForm() = MealFormState(
        name = "Pollo con arroz",
        type = MealType.LUNCH,
        kcal = "500",
        carbs = "40",
        fat = "12",
        protein = "35",
        date = "2026-11-01",
    )

    @Test
    fun formularioValido_noTieneErrores_yDevuelveLaComida() {
        val form = validForm()

        assertFalse(form.errors().hasErrors)

        val meal = form.toMealOrNull()
        assertNotNull(meal)
        assertEquals("Pollo con arroz", meal!!.name)
        assertEquals(MealType.LUNCH, meal.type)
        assertEquals(500, meal.kcal)
        assertEquals("2026-11-01", meal.date)
        assertEquals(40.0, meal.carbs, 0.001)
        assertEquals(12.0, meal.fat, 0.001)
        assertEquals(35.0, meal.protein, 0.001)
    }

    @Test
    fun nombreVacio_esInvalido() {
        val form = validForm().copy(name = "")

        assertNotNull(form.errors().name)
        assertNull(form.toMealOrNull())
    }

    @Test
    fun nombreSoloConEspacios_esInvalido() {
        val form = validForm().copy(name = "   ")

        assertNotNull(form.errors().name)
        assertNull(form.toMealOrNull())
    }

    @Test
    fun nombreConEspacios_seRecorta() {
        val meal = validForm().copy(name = "  Pollo  ").toMealOrNull()

        assertEquals("Pollo", meal!!.name)
    }

    @Test
    fun caloriasNegativas_sonInvalidas() {
        val errors = validForm().copy(kcal = "-5").errors()

        assertEquals("No puede ser negativo.", errors.kcal)
        assertTrue(errors.hasErrors)
    }

    @Test
    fun caloriasConDecimales_sonInvalidas() {
        val errors = validForm().copy(kcal = "12.5").errors()

        assertEquals("Ingresá un entero.", errors.kcal)
    }

    @Test
    fun campoVacio_esObligatorio() {
        val errors = validForm().copy(kcal = "", protein = "", carbs = "", fat = "").errors()

        assertEquals("Obligatorio.", errors.kcal)
        assertEquals("Obligatorio.", errors.protein)
        assertEquals("Obligatorio.", errors.carbs)
        assertEquals("Obligatorio.", errors.fat)
    }

    @Test
    fun macrosNegativos_sonInvalidos() {
        val errors = validForm().copy(carbs = "-1", fat = "-0.5", protein = "-3").errors()

        assertEquals("No puede ser negativo.", errors.carbs)
        assertEquals("No puede ser negativo.", errors.fat)
        assertEquals("No puede ser negativo.", errors.protein)
    }

    @Test
    fun macroConTexto_oInfinito_esNumeroInvalido() {
        val errors = validForm().copy(carbs = "abc", fat = "Infinity").errors()

        assertEquals("Número inválido.", errors.carbs)
        assertEquals("Número inválido.", errors.fat)
    }

    @Test
    fun macroConComaDecimal_seAcepta() {
        val meal = validForm().copy(carbs = "40,5", protein = "35.5").toMealOrNull()

        assertNotNull(meal)
        assertEquals(40.5, meal!!.carbs, 0.001)
        assertEquals(35.5, meal.protein, 0.001)
    }

    @Test
    fun cerosSonValoresValidos() {
        val meal = validForm().copy(kcal = "0", carbs = "0", fat = "0", protein = "0").toMealOrNull()

        assertNotNull(meal)
        assertEquals(0, meal!!.kcal)
    }

    @Test
    fun fechaConFormatoIncorrecto_esInvalida() {
        assertNotNull(validForm().copy(date = "01/11/2026").errors().date)
        assertNotNull(validForm().copy(date = "").errors().date)
    }

    @Test
    fun fechaInexistente_esInvalida() {
        assertNotNull(validForm().copy(date = "2026-13-45").errors().date)
        assertNotNull(validForm().copy(date = "2026-02-30").errors().date)
    }

    @Test
    fun variosErrores_seReportanJuntos() {
        val errors = MealFormState().errors()

        assertTrue(errors.hasErrors)
        assertNotNull(errors.name)
        assertNotNull(errors.kcal)
        assertNotNull(errors.date)
    }
}

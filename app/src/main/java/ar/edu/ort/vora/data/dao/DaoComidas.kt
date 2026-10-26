package ar.edu.ort.vora.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ar.edu.ort.vora.data.entities.MealEntity

@Dao
interface MealDao {

    //query de traer todas las comidas
    @Query("SELECT * FROM meals")
    suspend fun getAllMeals(): List<MealEntity.comida>

    @Query("SELECT * FROM meals WHERE fecha = :fecha")
    fun getbyDate(fecha: String): List<MealEntity.comida>



    // cuando ve que un id ya existe lo actualiza (me daba error por algun motivo y se me recomendo esto, Mistral, TODO: consultar)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeal(comida: MealEntity.comida)

    @Delete
    suspend fun deleteMeal(comida: MealEntity.comida)



}
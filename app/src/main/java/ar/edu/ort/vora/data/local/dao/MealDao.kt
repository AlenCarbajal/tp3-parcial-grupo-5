package ar.edu.ort.vora.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ar.edu.ort.vora.data.local.entity.MealEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MealDao {


    //query de traer todas las comidas
    @Query("SELECT * FROM meals")
    fun getAllMeals(): Flow<List<MealEntity>>

    @Query("SELECT * FROM meals WHERE date = :date")
    fun getbyDate(date: String): Flow<List<MealEntity>>



    // cuando ve que un id ya existe lo actualiza (me daba error por algun motivo y se me recomendo esto, Mistral, TODO: consultar)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeal(mealEntity: MealEntity)

    @Delete
    suspend fun deleteMeal(mealEntity: MealEntity)


}
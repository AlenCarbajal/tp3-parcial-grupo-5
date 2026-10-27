package ar.edu.ort.vora.data.repository

import ar.edu.ort.vora.data.local.dao.MealDao
import ar.edu.ort.vora.data.mapper.toDomain
import ar.edu.ort.vora.data.mapper.toEntity
import ar.edu.ort.vora.data.remote.ApiService
import ar.edu.ort.vora.domain.model.Meal
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MealRepository @Inject constructor(
    private val mealDao: MealDao
) {


    // Usa tu getbyDate() del DAO
    fun getMealsByDate(fecha: String): Flow<List<Meal>> {
        return mealDao.getbyDate(fecha).map { entities ->
            entities.map { entity -> entity.toDomain() }
        }
    }



    suspend fun addMeal(meal: Meal) {
        mealDao.insertMeal(meal.toEntity())
    }

    suspend fun deleteMeal(meal: Meal) {
        mealDao.deleteMeal(meal.toEntity())
    }
}
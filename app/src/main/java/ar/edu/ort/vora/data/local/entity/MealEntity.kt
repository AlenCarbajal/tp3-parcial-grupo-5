package ar.edu.ort.vora.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import ar.edu.ort.vora.domain.model.MealType


    @Entity(tableName = "meals")
    data class MealEntity(

        @PrimaryKey(autoGenerate = true)
        val id: Int = 0,

        val name: String,
        val date: String,
        val type: MealType, //desayuno almuerzo cena
        val kcal: Int,
        val carbs: Double,
        val fat: Double,
        val protein: Double

    )

package ar.edu.ort.vora.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

class MealEntity {

    @Entity(tableName = "meals")
    data class comida(

        @PrimaryKey(autoGenerate = true)
        val id: Int = 0,

        val nombre: String,
        val descripcion: String,
        val calorias: Int

    )
}
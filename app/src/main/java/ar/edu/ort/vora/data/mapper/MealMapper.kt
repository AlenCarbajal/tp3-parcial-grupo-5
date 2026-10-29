package ar.edu.ort.vora.data.mapper

import ar.edu.ort.vora.data.local.entity.MealEntity as MealEntity
import ar.edu.ort.vora.domain.model.Meal


// Convierte de Entidad (Room) a Modelo de Dominio
fun MealEntity.toDomain(): Meal =
    Meal(
        id = id,
        name = name,
        date = date,
        type = type,
        kcal = kcal,
        protein = protein,
        carbs = carbs,
        fat = fat
    )

// Convierte de Modelo de Dominio a Entidad (Room)
fun Meal.toEntity(): MealEntity =
    MealEntity(
        id = id,
        name = name,
        date = date,
        type = type,
        kcal = kcal,
        protein = protein,
        carbs = carbs,
        fat = fat
    )

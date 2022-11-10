package ru.geekbrains.android2_2.model

data class Weather(
    val city: City = getDefaultCity(),
    val temperature: Int = 0,
    val wind: Int = 0,
    val wetness: Int = 0
)

fun getDefaultCity() = City("Москва")

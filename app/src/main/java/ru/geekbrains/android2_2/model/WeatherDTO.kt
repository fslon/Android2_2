package ru.geekbrains.android2_2.model

data class WeatherDTO(
    val fact: FactDTO?
)

data class FactDTO(
    val temp: Int?,
    val wind_speed: Double?,
    val humidity: Int?
)
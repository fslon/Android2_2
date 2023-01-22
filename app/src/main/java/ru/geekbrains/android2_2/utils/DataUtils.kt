package ru.geekbrains.android2_2.utils

import ru.geekbrains.android2_2.model.FactDTO
import ru.geekbrains.android2_2.model.Weather
import ru.geekbrains.android2_2.model.WeatherDTO
import ru.geekbrains.android2_2.model.getDefaultCity


fun convertDtoToModel(weatherDTO: WeatherDTO): List<Weather> {
    val fact: FactDTO = weatherDTO.fact!!
    return listOf(
        Weather(
            getDefaultCity(), fact.temp!!, fact.wind_speed!!,
            fact.humidity!!, fact.condition!!
        )
    )

}

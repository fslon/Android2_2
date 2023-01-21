package ru.geekbrains.android2_2.repository

import ru.geekbrains.android2_2.model.Weather
import ru.geekbrains.android2_2.model.getRussianCities
import ru.geekbrains.android2_2.model.getWorldCities

class MainRepositoryImpl : MainRepository {
    override fun getWeatherFromServer(): Weather {
        return Weather()
    }

    override fun getWeatherFromLocalStorageRus(): List<Weather> {
        return getRussianCities()
    }

    override fun getWeatherFromLocalStorageWorld(): List<Weather> {
        return getWorldCities()
    }


}
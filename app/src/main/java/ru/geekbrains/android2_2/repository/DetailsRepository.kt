package ru.geekbrains.android2_2.repository

import ru.geekbrains.android2_2.model.WeatherDTO

interface DetailsRepository {
    fun getWeatherDetailsFromServer(
        lat: Double,
        lon: Double,
        callback: retrofit2.Callback<WeatherDTO>
    )


}
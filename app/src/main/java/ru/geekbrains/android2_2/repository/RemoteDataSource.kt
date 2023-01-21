package ru.geekbrains.android2_2.repository

import com.google.gson.GsonBuilder
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.geekbrains.android2_2.model.WeatherDTO

private const val REQUEST_API_KEY = "X-Yandex-API-Key"
const val YOUR_API_KEY = "b8071b48-3dde-46cb-a01d-808d4e60bd46"

class RemoteDataSource {

    private val weatherApi = Retrofit.Builder()
        .baseUrl("https://api.weather.yandex.ru/")
        .addConverterFactory(
            GsonConverterFactory.create(
                GsonBuilder().setLenient().create()
            )
        )
        .build().create(WeatherAPI::class.java)

    fun getWeatherDetails(lat: Double, lon: Double, callback: Callback<WeatherDTO>) {
        weatherApi.getWeather(
            YOUR_API_KEY, lat,
            lon
        ).enqueue(callback)
    }
}
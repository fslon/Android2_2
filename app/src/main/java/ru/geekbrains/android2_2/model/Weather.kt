package ru.geekbrains.android2_2.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Weather(
    val city: City = getDefaultCity(),
    val temperature: Int = 0,
    val wind: Int = 0,
    val wetness: Int = 0
) : Parcelable

fun getDefaultCity() = City("Москва")

fun getWorldCities(): List<Weather> {
    return listOf(
        Weather(City("Берлин"), 10, 2, 30),
        Weather(City("Париж"), 16, 3, 60),
        Weather(City("Монако"), 35, 1, 20),
        Weather(City("Рим"), 20, 6, 65),
        Weather(City("Ванкувер"), 13, 5, 80),
        Weather(City("Пуэрто-Рико"), 24, 4, 53),
        Weather(City("Гонконг"), 19, 9, 60),
        Weather(City("Мадрид"), 29, 1, 32),
        Weather(City("Осло"), 17, 6, 99)
    )
}

fun getRussianCities(): List<Weather> {
    return listOf(
        Weather(City("Саратов"), 14, 2, 30),
        Weather(City("Казань"), 0, 3, 60),
        Weather(City("Москва"), 25, 2, 52),
        Weather(City("Владивосток"), 20, 6, 65),
        Weather(City("Якутск"), -5, 5, 80),
        Weather(City("Санкт-Петербург"), 17, 4, 53),
        Weather(City("Хабаровск"), 19, 9, 60),
        Weather(City("Ростов-на-Дону"), 29, 1, 32),
        Weather(City("Анапа"), 35, 0, 40)
    )
}


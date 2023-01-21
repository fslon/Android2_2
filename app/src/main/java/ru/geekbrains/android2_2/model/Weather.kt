package ru.geekbrains.android2_2.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Weather(
    val city: City = getDefaultCity(),
    val temperature: Int = 0,
    val wind: Double = 0.0,
    val wetness: Int = 0,
    val condition: String = "sunny"
) : Parcelable

fun getDefaultCity() = City("Москва")

fun getWorldCities() = listOf(
    Weather(City("Берлин", 52.520008, 13.404954), 10, 2.0, 30),
    Weather(City("Париж", 48.856613, 2.352222), 16, 3.0, 60),
    Weather(City("Монако", 43.738419, 7.424616), 35, 1.0, 20),
    Weather(City("Рим", 41.902782, 12.496365), 20, 6.0, 65),
    Weather(City("Ванкувер", 49.2608724, -123.113952), 13, 5.0, 80),
    Weather(City("Пуэрто-Рико", 18.2247706, -66.4858295), 24, 4.0, 53),
    Weather(City("Гонконг", 22.2793278, 114.1628131), 19, 9.0, 60),
    Weather(City("Мадрид", 40.4167047, -3.7035825), 29, 1.0, 32),
    Weather(City("Осло", 59.9133301, 10.7389701), 17, 6.0, 99)
)


fun getRussianCities() = listOf(
    Weather(City("Саратов", 51.530018, 46.034683), 14, 2.0, 30),
    Weather(City("Казань", 55.7823547, 49.1242266), 0, 3.0, 60),
    Weather(City("Москва", 55.755863, 37.6177), 25, 2.0, 52),
    Weather(City("Владивосток", 43.1150678, 131.8855768), 20, 6.0, 65),
    Weather(City("Якутск", 62.0274078, 129.7319787), -5, 5.0, 80),
    Weather(City("Санкт-Петербург", 59.938732, 30.316229), 17, 4.0, 53),
    Weather(City("Хабаровск", 48.481403, 135.076935), 19, 9.0, 60),
    Weather(City("Ростов-на-Дону", 47.2213858, 39.7114196), 29, 1.0, 32),
    Weather(City("Анапа", 44.7876642, 37.3817164), 35, 0.0, 40)
)



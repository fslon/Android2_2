package ru.geekbrains.android2_2.room

import ru.geekbrains.android2_2.model.Weather

interface LocalRepository {
    fun getAllHistory(): List<Weather>
    fun saveEntity(weather: Weather)
}

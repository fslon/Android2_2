package ru.geekbrains.android2_2.room

import ru.geekbrains.android2_2.model.City
import ru.geekbrains.android2_2.model.Weather

class LocalRepositoryImpl(private val localDataSource: HistoryDao) :
    LocalRepository {
    override fun getAllHistory(): List<Weather> {
        var result: List<HistoryEntity> = arrayListOf()
        val thread1 = Thread {
            result = localDataSource.all()
        }
        thread1.start()
        thread1.join()
        return convertHistoryEntityToWeather(result)
    }

    override fun saveEntity(weather: Weather) {
        Thread {
            localDataSource.insert(convertWeatherToEntity(weather))
        }.start()
    }

    fun convertHistoryEntityToWeather(entityList: List<HistoryEntity>):
            List<Weather> {
        return entityList.map {
            Weather(City(it.city, 0.0, 0.0), it.temperature, 0.0, 0, it.condition)
        }
    }

    fun convertWeatherToEntity(weather: Weather): HistoryEntity {
        return HistoryEntity(
            0, weather.city.city, weather.temperature,
            weather.condition
        )
    }
}

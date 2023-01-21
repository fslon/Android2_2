package ru.geekbrains.android2_2.repository

import okhttp3.Callback

class DetailsRepositoryImpl(private val remoteDataSource: RemoteDataSource) :
    DetailsRepository {
    override fun getWeatherDetailsFromServer(
        requestLink: String, callback:
        Callback
    ) {
        remoteDataSource.getWeatherDetails(requestLink, callback)
    }
}
package ru.geekbrains.android2_2.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.geekbrains.android2_2.repository.MainRepository
import ru.geekbrains.android2_2.repository.MainRepositoryImpl
import java.lang.Thread.sleep

class MainViewModel(
    private val liveDataToObserve: MutableLiveData<Any> = MutableLiveData(),
    private val mainRepositoryImpl: MainRepository = MainRepositoryImpl(),

    ) :
    ViewModel() {

    fun getLiveData() = liveDataToObserve

    fun getWeatherFromLocalSourceRus() = getDataFromLocalSource(isRussian = true)
    fun getWeatherFromLocalSourceWorld() = getDataFromLocalSource(isRussian = false)
    fun getWeatherFromRemoteSource() = getDataFromLocalSource(isRussian = true)


    private fun getDataFromLocalSource(isRussian: Boolean) {

        liveDataToObserve.value = AppState.Loading
        Thread {

            sleep(1500)
//            when ((1..2).random()) {
//                1 -> liveDataToObserve.postValue(AppState.Success(if (isRussian) repositoryImpl.getWeatherFromLocalStorageRus() else repositoryImpl.getWeatherFromLocalStorageWorld()))
//                2 -> liveDataToObserve.postValue(AppState.Error(NullPointerException()))
//            }
            liveDataToObserve.postValue(AppState.Success(if (isRussian) mainRepositoryImpl.getWeatherFromLocalStorageRus() else mainRepositoryImpl.getWeatherFromLocalStorageWorld()))
//
        }.start()


    }


}
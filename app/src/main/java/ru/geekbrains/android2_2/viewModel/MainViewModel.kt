package ru.geekbrains.android2_2.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.geekbrains.android2_2.model.Repository
import ru.geekbrains.android2_2.model.RepositoryImpl
import java.lang.NullPointerException
import java.lang.Thread.sleep

class MainViewModel(private val liveDataToObserve: MutableLiveData<Any> = MutableLiveData(),
private val repositoryImpl: Repository = RepositoryImpl()) :
    ViewModel() {

    fun getLiveData() = liveDataToObserve

    fun getWeatherFromLocalSource() = getDataFromLocalSource()
    fun getWeatherFromRemoteSource() = getDataFromLocalSource()

    fun getData(): LiveData<Any> {
        getDataFromLocalSource()
        return liveDataToObserve
    }

    private fun getDataFromLocalSource() {

        liveDataToObserve.value = AppState.Loading
        Thread {

            sleep(3000)
            when ((1..2).random()){

                 1 -> liveDataToObserve.postValue(AppState.Success(repositoryImpl.getWeatherFromLocalStorage()))
                2 -> liveDataToObserve.postValue(AppState.Error(NullPointerException()))

            }
//            liveDataToObserve.postValue(AppState.Success(Any()))

        }.start()


    }


}
package ru.geekbrains.android2_2.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.geekbrains.android2_2.view.AppState
import java.lang.Exception
import java.lang.NullPointerException
import java.lang.Thread.sleep
import kotlin.random.Random

class MainViewModel(private val liveDataToObserve: MutableLiveData<Any> = MutableLiveData()) :
    ViewModel() {

    fun getLiveData() = liveDataToObserve

    fun getWeather() = getDataFromLocalSource()

    fun getData(): LiveData<Any> {
        getDataFromLocalSource()
        return liveDataToObserve
    }

    private fun getDataFromLocalSource() {
//        Thread{
//            for ( i in 1..5){
//            sleep(4000)
//            liveDataToObserve.postValue("дропдьлдпрль $i")}
//        }.start()

        liveDataToObserve.value = AppState.Loading
        Thread {

            sleep(3000)
            when ((1..2).random()){

                 1 -> liveDataToObserve.postValue(AppState.Success(Any()))
                2 -> liveDataToObserve.postValue(AppState.Error(NullPointerException()))

            }
//            liveDataToObserve.postValue(AppState.Success(Any()))

        }.start()


    }


}
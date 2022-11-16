package ru.geekbrains.android2_2.viewModel

import ru.geekbrains.android2_2.model.Weather

sealed class AppState {
    data class Success(val weatherData: List<Weather>) : AppState()
    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()

}

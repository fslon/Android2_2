package ru.geekbrains.android2_2.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.geekbrains.android2_2.app.App.Companion.getHistoryDao
import ru.geekbrains.android2_2.room.LocalRepository
import ru.geekbrains.android2_2.room.LocalRepositoryImpl

class HistoryViewModel(
    val historyLiveData: MutableLiveData<AppState> = MutableLiveData(),
    private val historyRepository: LocalRepository =
        LocalRepositoryImpl(getHistoryDao())
) : ViewModel() {
    fun getAllHistory() {
        historyLiveData.value = AppState.Loading
        historyLiveData.value =
            AppState.Success(historyRepository.getAllHistory())
    }
}

package com.handsome.club.hnh.cookbook.ui.main

import androidx.lifecycle.viewModelScope
import com.handsome.club.hnh.cookbook.model.food.PopulateFoodRepositoryUseCase
import com.handsome.club.hnh.cookbook.ui.base.BaseViewModel
import com.handsome.club.hnh.cookbook.ui.base.ScreenError
import com.handsome.club.hnh.cookbook.ui.base.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

data class MainScreenState(
    val initialLoading: Boolean = true,
    val error: ScreenError? = null
) : ScreenState

@HiltViewModel
class MainViewModel @Inject constructor(
    private val populateFoodRepositoryUseCase: PopulateFoodRepositoryUseCase
) : BaseViewModel<MainScreenState>(MainScreenState()) {

    init {
        viewModelScope.launch {
            screenState = when (populateFoodRepositoryUseCase().isSuccess) {
                true -> screenState.copy(initialLoading = false)
                false -> screenState.copy(initialLoading = false, error = ScreenError.DataLoadingFailed)
            }
        }
    }

}
package com.lyvetech.transnature.features.feed.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lyvetech.transnature.core.util.Resource
import com.lyvetech.transnature.core.util.asLiveData
import com.lyvetech.transnature.features.feed.di.DefaultDispatcher
import com.lyvetech.transnature.features.feed.domain.model.Trail
import com.lyvetech.transnature.features.feed.domain.usecase.GetTrailsUseCaseImpl
import com.lyvetech.transnature.features.feed.domain.usecase.UpdateTrailUseCaseImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(
    private val getTrailsUseCaseImpl: GetTrailsUseCaseImpl,
    private val updateTrailUseCase: UpdateTrailUseCaseImpl,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading = _isLoading.asLiveData()

    private val _dataFetchState = MutableLiveData<Boolean>()
    val dataFetchState = _dataFetchState.asLiveData()

    private val _trails = MutableLiveData<List<Trail>?>()
    val trails = _trails.asLiveData()

    init {
        getAllTrails()
    }

    private fun getAllTrails() {
        _isLoading.postValue(true)
        viewModelScope.launch {
            when (val result = getTrailsUseCaseImpl.invoke()) {
                is Resource.Success -> {
                    _isLoading.value = false
                    if (result.data != null) {
                        val trails = result.data
                        _dataFetchState.value = true
                        _trails.value = trails
                    }
                }
                is Resource.Error -> {
                    _isLoading.value = false
                    _dataFetchState.value = false
                    if (result.data != null) {
                        val trails = result.data
                        _trails.value = trails
                    }
                }
                is Resource.Loading -> _isLoading.postValue(true)
            }
        }
    }
}
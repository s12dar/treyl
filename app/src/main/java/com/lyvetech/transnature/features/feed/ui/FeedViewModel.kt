package com.lyvetech.transnature.features.feed.ui

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lyvetech.transnature.core.util.Resource
import com.lyvetech.transnature.features.feed.domain.usecase.GetAllTrailsUseCase
import com.lyvetech.transnature.features.feed.domain.usecase.GetSearchedTrailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(
    private val getAllTrailsUseCase: GetAllTrailsUseCase,
    private val getSearchedTrailsUseCase: GetSearchedTrailsUseCase
) : ViewModel() {

    private val _searchQuery = mutableStateOf("")
    val searchQuery: State<String> = _searchQuery

    private val _trailState = mutableStateOf(FeedScreenViewState())
    val trailState: State<FeedScreenViewState> = _trailState

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var trailJob: Job? = null

    init {
        getAllTrails()
    }

//    fun onSearchedTrails(query: String) {
//        _searchQuery.value = query
//        searchJob?.cancel()
//        searchJob = viewModelScope.launch {
//            delay(500)
//            getSearchedTrailsUseCase(query)
//                .onEach { result ->
//                    when (result) {
//                        is Resource.Success -> {
//                            _trailState.value = trailState.value.copy(
//                                trailItems = result.data ?: emptyList(),
//                                isLoading = false
//                            )
//                        }
//                        is Resource.Error -> {
//                            _trailState.value = trailState.value.copy(
//                                trailItems = result.data ?: emptyList(),
//                                isLoading = false
//                            )
//
//                            _eventFlow.emit(
//                                UIEvent.ShowSnackBar(
//                                    result.message ?: "Unknown error"
//                                )
//                            )
//                        }
//                        is Resource.Loading -> {
//                            _trailState.value = trailState.value.copy(
//                                trailItems = result.data ?: emptyList(),
//                                isLoading = true
//                            )
//                        }
//                    }
//                }.launchIn(this)
//        }
//    }

    private fun getAllTrails() {
        trailJob?.cancel()
        trailJob = viewModelScope.launch {
            getAllTrailsUseCase()
                .onEach { result ->
                    when (result) {
                        is Resource.Success -> {
                            _trailState.value = trailState.value.copy(
                                trailItems = result.data ?: emptyList(),
                                isLoading = false
                            )
                            Log.i("DEBUG", trailState.value.trailItems.size.toString())
                        }
                        is Resource.Error -> {
                            _trailState.value = trailState.value.copy(
                                trailItems = result.data ?: emptyList(),
                                isLoading = false
                            )

                            _eventFlow.emit(
                                UIEvent.ShowSnackBar(
                                    result.message ?: "Unknown error"
                                )
                            )
                        }
                        is Resource.Loading -> {
                            _trailState.value = trailState.value.copy(
                                trailItems = result.data ?: emptyList(),
                                isLoading = true
                            )
                        }
                    }
                }.launchIn(this)
        }
    }

    sealed class UIEvent {
        data class ShowSnackBar(val message: String) : UIEvent()
    }
}
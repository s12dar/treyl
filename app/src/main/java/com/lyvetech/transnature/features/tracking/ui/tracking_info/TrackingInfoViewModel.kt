package com.lyvetech.transnature.features.tracking.ui.tracking_info

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lyvetech.transnature.core.di.DefaultDispatcher
import com.lyvetech.transnature.features.tracking.domain.model.Session
import com.lyvetech.transnature.features.tracking.domain.usecase.GetSessionsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrackingInfoViewModel @Inject constructor(
    private val getSessionsUseCase: GetSessionsUseCase,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher
) : ViewModel() {

    fun getSessions(): List<Session> {
        val sessions = mutableListOf<Session>()
        viewModelScope.launch(defaultDispatcher) {
            sessions.addAll(getSessionsUseCase.invoke())
        }
        return sessions
    }
}
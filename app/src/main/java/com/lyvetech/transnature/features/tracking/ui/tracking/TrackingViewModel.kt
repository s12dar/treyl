package com.lyvetech.transnature.features.tracking.ui.tracking

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lyvetech.transnature.core.di.DefaultDispatcher
import com.lyvetech.transnature.features.tracking.domain.model.Session
import com.lyvetech.transnature.features.tracking.domain.usecase.InsertSessionUseCaseImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrackingViewModel @Inject constructor(
    private val insertSessionUseCase: InsertSessionUseCaseImpl,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher
) : ViewModel() {

    fun insertSession(session: Session) {
        viewModelScope.launch(defaultDispatcher) {
            insertSessionUseCase.invoke(session)
        }
    }
}
package com.lyvetech.transnature.features.feed.ui.feed_info

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lyvetech.transnature.features.feed.di.DefaultDispatcher
import com.lyvetech.transnature.features.feed.domain.model.Trail
import com.lyvetech.transnature.features.feed.domain.usecase.UpdateTrailUseCaseImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedInfoViewModel @Inject constructor(
    private val updateTrailUseCase: UpdateTrailUseCaseImpl,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher
) : ViewModel() {

    fun updateTrail(trail: Trail) {
        viewModelScope.launch(defaultDispatcher) {
            updateTrailUseCase.invoke(trail)
        }
    }
}
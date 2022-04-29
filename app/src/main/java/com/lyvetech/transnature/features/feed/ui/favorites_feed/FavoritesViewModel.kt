package com.lyvetech.transnature.features.feed.ui.favorites_feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lyvetech.transnature.core.di.DefaultDispatcher
import com.lyvetech.transnature.features.feed.domain.model.Trail
import com.lyvetech.transnature.features.feed.domain.usecase.GetFavTrailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val getFavTrailsUseCase: GetFavTrailsUseCase,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher
) : ViewModel() {

    fun getFavTrails(): List<Trail> {
        val favTrails = mutableListOf<Trail>()
        viewModelScope.launch(defaultDispatcher) {
            favTrails.addAll(getFavTrailsUseCase.invoke())
        }
        return favTrails
    }
}
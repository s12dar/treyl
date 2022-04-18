package com.lyvetech.transnature.features.feed.ui

import com.lyvetech.transnature.features.feed.domain.model.Trail

data class FeedScreenViewState(
    val trailItems: List<Trail> = emptyList(),
    val isLoading: Boolean = false
)
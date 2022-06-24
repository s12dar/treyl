package com.lyvetech.transnature

import com.lyvetech.transnature.features.feed.data.local.entity.TrailEntity

val fakeTrailEntity = TrailEntity(
    name = "Name",
    desc = "Desc",
    imgRefs = listOf("abc", "def"),
    location = "San Francisco",
    startLatitude = 12.2,
    startLongitude = 12.3,
    endLatitude = 34.2,
    endLongitude = 34.3,
    distanceInMeters = 123,
    peakPointInMeters = 234,
    averageTimeInMillis = 1234,
    difficultyLevel = "Medium",
    accession = "Via red flags",
    warning = "Weather conditions",
    isFav = true,
    tag = "test_trail",
    id = 1
)

var fakeUpdatedTrailEntity = fakeTrailEntity.copy(desc = "Updated desc")

val fakeTrailsList = listOf(
    fakeTrailEntity
)